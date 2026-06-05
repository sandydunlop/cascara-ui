package io.github.qishr.cascara.ui.control;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import io.github.qishr.cascara.common.lang.exception.ParserException;
import io.github.qishr.cascara.lang.xml.ast.XmlNode;
import io.github.qishr.cascara.lang.xml.processor.XmlParser;
import io.github.qishr.cascara.ui.style.custom.SvgIconStyle;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;

public class SvgIcon extends StackPane {
    private int size;
    private Node badge;
    private XmlParser xmlParser = new XmlParser();

    private Stack<String> fillStack = new Stack<>();
    private Stack<String> strokeStack = new Stack<>();

    public SvgIcon(String svgSource, int size) {
        this();
        setSize(size);
        loadFromSource(svgSource);
    }

    public SvgIcon() {
        this.size = 24;
        this.getStyleClass().add(SvgIconStyle.ICON);

        // Create a background so the entire area catches mouse events
        this.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setPickOnBounds(true);
    }

    public void setSize(int size) {
        this.size = size;
        this.setMinSize(size, size);
        this.setMaxSize(size, size);
        this.setPrefSize(size, size);
    }

    public void loadFromSource(String svgContent) {
        XmlNode root = null;
        try {
            root = xmlParser.parse(svgContent);
            if (root == null) {
                root = loadDefault();
            }
        } catch (ParserException _) {
            root = loadDefault();
        }

        Node svgContentNode = processNode(root);

        // Apply automatic scaling based on the viewBox
        applyViewBoxScaling(root, svgContentNode);

        this.getChildren().clear();
        this.getChildren().add(svgContentNode);
    }

    /// Attaches a badge to the icon.
    /// @param badgeInfo Metadata of the badge to overlay
    public void setBadge(Badge badgeInfo) {
        if (this.badge != null) {
            getChildren().remove(this.badge);
        }

        if (badgeInfo == null || badgeInfo.getBadgeType() == BadgeType.NONE) {
            Tooltip.install(this, null);
            return;
        }

        // 1. Setup Tooltip
        if (badgeInfo.getBadgeText() != null && !badgeInfo.getBadgeText().isEmpty()) {
            Tooltip tooltip = new Tooltip(badgeInfo.getBadgeText());
            Tooltip.install(this, tooltip);
        }

        // 2. Create the Badge Node
        this.badge = createBadgeNode(badgeInfo);

        if (this.badge != null) {
            getChildren().add(this.badge);
            StackPane.setAlignment(this.badge, Pos.BOTTOM_RIGHT);
            this.badge.setMouseTransparent(true);
        }
    }


    private Node createBadgeNode(Badge badgeInfo) {
        StackPane container = new StackPane();
        String typeClass = "badge-" + badgeInfo.getBadgeType().toString().toLowerCase();
        container.getStyleClass().setAll("cascara-badge", typeClass);

        Integer count = badgeInfo.getBadgeNumber();
        if (count != null && count > 0) {
            Label label = new Label(String.valueOf(count));
            label.getStyleClass().add("badge-text");

            // Slightly smaller font helps the "pill" look
            label.setFont(javafx.scene.text.Font.font("Verdana", 9));

            container.getChildren().add(label);
            container.setPadding(new Insets(1, 4, 1, 4));

            // Set both Min and Max to keep it from growing
            container.setMinSize(14, 14);
            container.setMaxSize(Region.USE_PREF_SIZE, 14); // Allow width to grow for numbers, but cap height
        } else {
            container.setMinSize(10, 10);
            container.setMaxSize(10, 10);
        }

        // The translate values move the badge relative to the BOTTOM_RIGHT anchor.
        container.setTranslateX(4);
        container.setTranslateY(4);

        return container;
    }

    public void setSource(String svgSource) {
        loadFromSource(svgSource);
    }

    private void applyViewBoxScaling(XmlNode root, Node content) {
        String viewBox = root.getAttribute("viewBox");
        if (viewBox != null) {
            String[] parts = viewBox.split("[\\s,]+");
            if (parts.length == 4) {
                double vbWidth = Double.parseDouble(parts[2]);
                double vbHeight = Double.parseDouble(parts[3]);

                // Calculate the scale factor to fit the requested 'size'
                double scaleX = size / vbWidth;
                double scaleY = size / vbHeight;

                // Use the smaller of the two to maintain aspect ratio
                double uniformScale = Math.min(scaleX, scaleY);

                content.setScaleX(uniformScale);
                content.setScaleY(uniformScale);
            }
        } else {
            // Fallback if no viewBox: try width/height attributes
            String w = root.getAttribute("width");
            String h = root.getAttribute("height");
            if (w != null && h != null) {
                double rawW = Double.parseDouble(w.replaceAll("[^\\d.]", ""));
                double rawH = Double.parseDouble(h.replaceAll("[^\\d.]", ""));
                content.setScaleX(size / rawW);
                content.setScaleY(size / rawH);
            }
        }
    }

    private Node processNode(XmlNode xmlNode) {
        String name = xmlNode.getName().toLowerCase();

        String fill = xmlNode.getAttribute("fill");
        String stroke = xmlNode.getAttribute("stroke");
        if (fill == null && !fillStack.isEmpty()) {
            fill = fillStack.peek();
        }
        if (stroke == null && !strokeStack.isEmpty()) {
            stroke = strokeStack.peek();
        }
        switch (name) {
            case "svg":
            case "g":
                Group group = new Group();

                fillStack.add(fill);
                strokeStack.add(stroke);

                for (XmlNode child : xmlNode.getChildren()) {
                    group.getChildren().add(processNode(child));
                }
                applyTransforms(group, xmlNode);

                fillStack.pop();
                strokeStack.pop();

                return group;

            case "circle":
                double cx = Double.parseDouble(xmlNode.getAttributeOrDefault("cx", "0"));
                double cy = Double.parseDouble(xmlNode.getAttributeOrDefault("cy", "0"));
                double r = Double.parseDouble(xmlNode.getAttributeOrDefault("r", "0"));

                Circle circle = new Circle(cx, cy, r);
                applyShapeStyles(circle, fill, stroke);
                applyTransforms(circle, xmlNode);

                return circle;

            case "path":
                SVGPath path = new SVGPath();
                path.setContent(xmlNode.getAttribute("d"));
                applyShapeStyles(path, fill, stroke);
                applyTransforms(path, xmlNode);

                return path;

            default:
                return new Group();
        }
    }


    private void applyShapeStyles(Shape shape, String fill, String stroke) {
        shape.getStyleClass().add(SvgIconStyle.SHAPE); // Base class for all shapes

        // String styleClass = null;
        boolean styleSet = false;

        try {
            if (fill != null && !fill.equalsIgnoreCase("none")) {
                shape.getStyleClass().add(SvgIconStyle.FILL);
                // styleClass = SvgIconStyle.FILL;
                styleSet = true;
            } else {
                shape.setFill(Color.TRANSPARENT);
            }
        } catch (Exception _) {}

        try {
            if (stroke != null && !stroke.equalsIgnoreCase("none")) {
                shape.getStyleClass().add(SvgIconStyle.STROKE);
                // styleClass = SvgIconStyle.STROKE;
                styleSet = true;
            } else {
                shape.setStroke(Color.TRANSPARENT);
            }
        } catch (Exception _) {}

        if (!styleSet) {
            shape.getStyleClass().add(SvgIconStyle.FILL);
        }
    }

    private void applyTransforms(Node node, XmlNode xmlNode) {
        String transform = xmlNode.getAttribute("transform");
        if (transform == null || transform.isEmpty()) return;

        // Pattern to match translate(x, y) or translate(x)
        Pattern translatePattern = Pattern.compile("translate\\s*\\(\\s*([\\d.-]+)(?:\\s*[,\\s]\\s*([\\d.-]+))?\\s*\\)");
        Matcher m = translatePattern.matcher(transform);

        if (m.find()) {
            double tx = Double.parseDouble(m.group(1));
            double ty = (m.group(2) != null) ? Double.parseDouble(m.group(2)) : 0;

            node.setTranslateX(tx);
            node.setTranslateY(ty);
        }

        // Pattern to match scale(factor) or scale(sx, sy)
        Pattern scalePattern = Pattern.compile("scale\\s*\\(\\s*([\\d.-]+)(?:\\s*[,\\s]\\s*([\\d.-]+))?\\s*\\)");
        Matcher sm = scalePattern.matcher(transform);

        if (sm.find()) {
            double sx = Double.parseDouble(sm.group(1));
            double sy = (sm.group(2) != null) ? Double.parseDouble(sm.group(2)) : sx;

            node.setScaleX(sx);
            node.setScaleY(sy);
        }
    }

    private XmlNode loadDefault() {
        try {
            String s = getTextResource("image.svg");
            return xmlParser.parse(s);
        } catch (Exception e) {
            // The resource should ALWAYS be there
            e.printStackTrace();
            return null;
        }
    }

    private final String getTextResource(String resourcePath) throws Exception {
        InputStream is = getClass().getResourceAsStream(resourcePath);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            return br.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            return "";
        }
    }
}
