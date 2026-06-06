package io.github.qishr.cascara.ui.theme;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import io.github.qishr.cascara.ui.api.HighlightingToken;
import io.github.qishr.cascara.ui.color.ColorDefinition;
import io.github.qishr.cascara.ui.color.ColorException;
import io.github.qishr.cascara.ui.color.ColorUtil;
import io.github.qishr.cascara.ui.data.UiDataException;
import io.github.qishr.cascara.ui.option.Option;
import io.github.qishr.cascara.ui.option.OptionProvider;
import io.github.qishr.cascara.ui.option.SimpleStringOption;
import io.github.qishr.cascara.ui.option.StringOption;
import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.style.custom.ActivityBarStyle;
import io.github.qishr.cascara.ui.style.custom.BadgeStyle;
import io.github.qishr.cascara.ui.style.custom.CodeAreaStyle;
import io.github.qishr.cascara.ui.style.custom.CustomStyle;
import io.github.qishr.cascara.ui.style.custom.DocumentTabHeaderStyle;
import io.github.qishr.cascara.ui.style.custom.MarkdownStyle;
import io.github.qishr.cascara.ui.style.custom.SideBarStyle;
import io.github.qishr.cascara.ui.style.custom.SvgIconStyle;
import io.github.qishr.cascara.ui.style.custom.TagChooserStyle;
import io.github.qishr.cascara.ui.style.custom.TitleBarStyle;
import io.github.qishr.cascara.ui.style.custom.ToastStyle;
import io.github.qishr.cascara.ui.style.standard.AccordionStyle;
import io.github.qishr.cascara.ui.style.standard.ArrowStyle;
import io.github.qishr.cascara.ui.style.standard.ButtonStyle;
import io.github.qishr.cascara.ui.style.standard.CheckBoxStyle;
import io.github.qishr.cascara.ui.style.standard.ChoiceBoxStyle;
import io.github.qishr.cascara.ui.style.standard.ColorPickerStyle;
import io.github.qishr.cascara.ui.style.standard.ComboBoxStyle;
import io.github.qishr.cascara.ui.style.standard.DatePickerStyle;
import io.github.qishr.cascara.ui.style.standard.DialogStyle;
import io.github.qishr.cascara.ui.style.standard.HyperlinkStyle;
import io.github.qishr.cascara.ui.style.standard.ListViewStyle;
import io.github.qishr.cascara.ui.style.standard.MenuButtonStyle;
import io.github.qishr.cascara.ui.style.standard.MenuStyle;
import io.github.qishr.cascara.ui.style.standard.ProgressBarStyle;
import io.github.qishr.cascara.ui.style.standard.RadioButtonStyle;
import io.github.qishr.cascara.ui.style.standard.SceneStyle;
import io.github.qishr.cascara.ui.style.standard.ScrollBarStyle;
import io.github.qishr.cascara.ui.style.standard.ScrollPaneStyle;
import io.github.qishr.cascara.ui.style.standard.SliderStyle;
import io.github.qishr.cascara.ui.style.standard.SplitMenuButtonStyle;
import io.github.qishr.cascara.ui.style.standard.SplitPaneStyle;
import io.github.qishr.cascara.ui.style.standard.TabPaneStyle;
import io.github.qishr.cascara.ui.style.standard.TableViewStyle;
import io.github.qishr.cascara.ui.style.standard.TextInputStyle;
import io.github.qishr.cascara.ui.style.standard.TextStyle;
import io.github.qishr.cascara.ui.style.standard.TitledPaneStyle;
import io.github.qishr.cascara.ui.style.standard.ToolBarStyle;
import io.github.qishr.cascara.ui.style.standard.ToolTipStyle;
import io.github.qishr.cascara.ui.style.standard.TreeTableViewStyle;
import io.github.qishr.cascara.ui.style.standard.TreeViewStyle;
import io.github.qishr.cascara.ui.theme.CodeColorsStyleSheet.StyleClass;
import io.github.qishr.cascara.ui.vsix.VsixPackage;
import io.github.qishr.cascara.ui.vsix.VsixThemeInfo;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class ThemeEngine implements AutoCloseable {
    private static final String EXTENSION = "extension";

    private final Path cascaraDir = Paths.get(System.getProperty("user.home")).resolve(".cascara");
    private final Path themesDir = cascaraDir.resolve("themes");

    private static ThemeEngine instance;

    private static final StringOption defaultThemeOption = new SimpleStringOption("default", "Default");
    private static CascaraTheme defaultTheme;

    private String fontFamily = "Verdana";

    private SimpleObjectProperty<CascaraTheme> activeTheme = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Variation> activeVariation = new SimpleObjectProperty<>();

    private ThemeOptionProvider themeOptionProvider;
    private VariationOptionProvider variationOptionProvider;

    private Set<ControlStyle> userStyles = new HashSet<>();

    private ThemeEngine() {
        String resPath = "/theme/cascara-theme.casc";
        BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(resPath), StandardCharsets.UTF_8));
        String yaml = reader.lines().collect(Collectors.joining("\n"));
        defaultTheme = new CascaraTheme(yaml);
        setTheme(defaultTheme);
    }

    public static ThemeEngine instance() {
        if (instance == null) {
            instance = new ThemeEngine();
        }
        return instance;
    }

    @Override
    public void close() {
        if (themeOptionProvider != null) themeOptionProvider.close();
    }

    public OptionProvider getThemeOptionProvider() {
        if (themeOptionProvider == null) {
            try {
                themeOptionProvider = new ThemeOptionProvider();
            } catch (IOException e) {
            }
        }
        return themeOptionProvider;
    }

    public OptionProvider getVariationOptionProvider() {
        if (variationOptionProvider == null) {
            variationOptionProvider = new VariationOptionProvider();
        }
        return variationOptionProvider;
    }

    public static CascaraTheme getDefaultTheme() { return defaultTheme; }

    public Option getDefaultThemeOption() { return defaultThemeOption; }

    public CascaraTheme getTheme() { return activeTheme.get(); }

    public CascaraTheme getTheme(String themeId) {
        if (themeId == null || themeId.toLowerCase().equals("default")) {
            return defaultTheme;
        }
        if (themeId.endsWith(".vsix")) {
            CascaraTheme theme = convertVsix(themeId);
            return theme;
        } else {
            Path path = themesDir.resolve(themeId);
            if (Files.isRegularFile(path)) {
                try {
                    String source = Files.readString(themesDir.resolve(themeId));
                    CascaraTheme theme = new CascaraTheme(source);
                    return theme;
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                    return null;
                }
            }
        }
        return null;
    }

    public void setTheme(CascaraTheme theme) {
        if (theme == null) {
            throw new UiDataException("Theme must not be null");
        }
        activeTheme.set(theme);
        setVariation(theme.getVariations().getFirst());
    }

    public void setTheme(String themeId) {
        CascaraTheme theme = getTheme(themeId);
        if (theme == null) {
            setTheme(defaultTheme);
        } else {
            setTheme(theme);
        }
    }

    public void setTheme(Option themeOption) {
        setTheme(themeOption.getOptionId());
    }

    public Variation getVariation() { return activeVariation.get(); }

    public void setVariation(Variation variation) {
        activeVariation.set(variation);
    }

    public void setVariation(String variationName) {
        Variation variation = activeTheme.get().getVariation(variationName);
        if (variation != null) {
            activeVariation.set(variation);
        }
    }

    //
    // Theme Applying Methods
    //

    public boolean applyTheme(Parent parent) { return applyTheme(ThemableObject.of(parent)); }
    public boolean applyTheme(Scene scene) { return applyTheme(ThemableObject.of(scene)); }
    public void applyVariation(Variation variation, Parent parent) throws ColorException { applyVariation(variation, ThemableObject.of(parent)); }
    public void applyVariation(Variation variation, Scene scene)  throws ColorException { applyVariation(variation, ThemableObject.of(scene)); }

    //
    // User Styles
    //

    public void applyStyle(ControlStyle style, Parent parent) { applyStyle(style, ThemableObject.of(parent)); }
    public void applyStyle(ControlStyle style, Scene scene) { applyStyle(style, ThemableObject.of(scene)); }
    public void addUserStyle(ControlStyle style) { userStyles.add(style); }
    public void removeUserStyle(ControlStyle style) { userStyles.remove(style); }
    public void clearUserStyles() { userStyles.clear(); }
    public String getFontFamily() { return fontFamily; }
    public void setFontFamily(String fontFamily) { this.fontFamily = fontFamily; }

    //
    // Public Static Methods
    //

    public static String getStylesheet(Variation variation) throws ColorException {
        StringBuilder sb = new StringBuilder();
        sb.append(getUiColorsStylesheet(variation));
        sb.append("\n\n\n");
        sb.append(getCodeColorsStylesheet(variation));
        sb.append("\n\n\n");
        sb.append(getFxControlsCss());
        sb.append(getUserCss());
        return sb.toString();
    }

    public static String getUiColor(String id) {
        Variation variation = instance().getVariation();
        ColorDefinition colordef = variation.getUiColor(id);
        try {
            ColorUtil.processColor(colordef, variation);
        } catch (ColorException e) {
            e.printStackTrace();
        }
        return colordef.getHexColor();
    }

    public static String cssColorVariable(String name) {
        if (name.contains("(")) {
            return name;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("-color-");
        name.codePoints().forEach(codePoint -> {
            if (Character.isUpperCase(codePoint)) {
                sb.append("-");
                sb.append(Character.toLowerCase((char)codePoint));
            } else {
                sb.append(Character.toChars(codePoint));
            }
        });
        return sb.toString();
    }

    //
    // Private Methods
    //

    private boolean applyTheme(ThemableObject object) {
        try {
            applyVariation(activeVariation.get(), object);
            return true;
        }catch(Exception e){
            System.out.println("E: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // private boolean applyTheme(Option option, ThemableObject object) {
    //     String themeId = option.getOptionId();
    //     if (themeId.equals("default")) {
    //         applyTheme(object);
    //         return true;
    //     } else {
    //         try {
    //             String source = Files.readString(themesDir.resolve(themeId));
    //             CascaraTheme theme = new CascaraTheme(source);
    //             Variation variation = theme.getVariations().getFirst();
    //             applyVariation(variation, object);
    //             return true;
    //         } catch (ColorException | IOException e) {
    //             System.err.println(e.getMessage());
    //             return false;
    //         }
    //     }
    // }

    private void applyVariation(Variation variation, ThemableObject object) throws ColorException {
        if (object == null) {
            throw new UiDataException("Object must be a Scene or a Parent");
        }
        setFontFamily("Verdana");

        String themeCss = getStylesheet(variation);
        String dataUri = getDataUri(themeCss);

        object.getStylesheets().clear();
        object.getStylesheets().add(getClass().getResource("/io/github/qishr/cascara/ui/theme/fonts.css").toExternalForm());
        object.getStylesheets().add(dataUri);
    }

    private void applyStyle(ControlStyle style, ThemableObject object) {
        String css = buildStyleCss(style);
        String dataUri = getDataUri(css);
        object.getStylesheets().add(dataUri);
    }

    private String getDataUri(String themeCss) {
        String encodedCss = Base64.getEncoder().encodeToString(themeCss.getBytes(StandardCharsets.UTF_8));
        String dataUri = "data:text/css;base64," + encodedCss;
        return dataUri;
    }

    private static String getUserCss() {
        return instance().getUserStylesheet();
    }

    private String getUserStylesheet() {
        StringBuilder sb = new StringBuilder();
        for (ControlStyle style : userStyles) {
            sb.append(buildStyleCss(style));
        }
        return sb.toString();
    }

    private CascaraTheme convertVsix(String fileName) {
        Path cascaraDir = Paths.get(System.getProperty("user.home")).resolve(".cascara");
        Path packagesDir = cascaraDir.resolve("packages");
        Path packagePath = packagesDir.resolve(fileName);

        VsixPackage vsixPackage;
        try {
            vsixPackage = VsixPackage.load(packagePath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        CascaraTheme theme = new CascaraTheme();
        theme.setName(vsixPackage.getDisplayName());
        for (VsixThemeInfo themeInfo : vsixPackage.getThemes()) {
           String themePath = themeInfo.getPath();
           Path relativePath = Path.of(EXTENSION, themePath);
           try {
               String pathString = relativePath.toString();
               String themeString = new String(vsixPackage.extractFile(pathString));
               VSCodeTheme vsTheme = new VSCodeTheme(themeString);
               ThemeVariation variation = vsTheme.getVariation();
               variation.setPath(themeInfo.getPath());
               variation.setName(themeInfo.getLabel());
               theme.getVariations().add(variation);
               VSCodeTheme.populateMissingColorGroups(variation);
               VSCodeTheme.populateMissingColorNames(variation);
           } catch (ColorException e) {
               e.printStackTrace();
           }
        }
        return theme;
    }

    private static String getFxControlsCss() {
        List<ControlStyle> controls = new ArrayList<>();

        controls.add(new SceneStyle());
        controls.add(new TextStyle());
        controls.add(new ButtonStyle());
        controls.add(new CheckBoxStyle());
        controls.add(new RadioButtonStyle());
        controls.add(new TextInputStyle());
        controls.add(new HyperlinkStyle());
        controls.add(new TitledPaneStyle());
        controls.add(new SplitPaneStyle());
        controls.add(new TabPaneStyle());
        controls.add(new MenuButtonStyle());
        controls.add(new SplitMenuButtonStyle());
        controls.add(new ChoiceBoxStyle());
        controls.add(new ComboBoxStyle());
        controls.add(new DatePickerStyle());
        controls.add(new ColorPickerStyle());
        controls.add(new MenuStyle());

        controls.add(new DocumentTabHeaderStyle());
        controls.add(new ToolBarStyle());
        controls.add(new ListViewStyle());
        controls.add(new TableViewStyle());
        controls.add(new TreeViewStyle());
        controls.add(new TreeTableViewStyle());
        controls.add(new ArrowStyle());
        controls.add(new AccordionStyle());
        controls.add(new ProgressBarStyle());
        controls.add(new SliderStyle());
        controls.add(new ScrollPaneStyle());
        controls.add(new ScrollBarStyle());
        controls.add(new ToolTipStyle());
        controls.add(new SvgIconStyle());
        controls.add(new TitleBarStyle());
        controls.add(new CustomStyle());
        controls.add(new ActivityBarStyle());
        controls.add(new SideBarStyle());
        controls.add(new ToastStyle());
        controls.add(new BadgeStyle());
        controls.add(new MarkdownStyle());
        controls.add(new TagChooserStyle());
        controls.add(new DialogStyle());
        controls.add(new CodeAreaStyle());

        StringBuilder sb = new StringBuilder();
        for (ControlStyle control : controls) {
            sb.append(buildStyleCss(control));
        }
        return sb.toString();
    }

    //
    // Private Static Methods
    //

    private static String buildStyleCss(ControlStyle style) {
        StringBuilder sb = new StringBuilder();
        for (String rules : style.getRules()) {
            sb.append(rules);
        }
        return sb.toString();
    }

    private static String getUiColorsStylesheet(Variation variation) throws ColorException {
        String paletteColors = getPaletteColorVarsString(variation);
        StringBuilder sb = new StringBuilder();
        sb.append(".root {\n");
        sb.append(paletteColors);
        sb.append("\n");
        for (Entry<String, ColorDefinition> entry : variation.getUiColors().entrySet()) {
            ColorDefinition colordef = entry.getValue();
            sb.append("    ");
            sb.append(cssColorVariable(entry.getKey()));
            sb.append(": ");
            if (entry.getKey().equals(ColorID.WIDGET_SHADOW)) {
                String na = normalizeAlpha(colordef);
                sb.append(na);
            } else {
                ColorUtil.processColor(colordef, variation);
                if (colordef.getHexColor().isEmpty()) {
                    sb.append("#FF0000");
                } else {
                    sb.append(colordef.getHexColor());
                }
            }
            sb.append(";\n");
        }

        String fontFamily = instance().getFontFamily();
        sb.append("\n");
        sb.append("    -fx-font-family: " + fontFamily + ";\n");
        sb.append("    -fx-base: -color-control-background;\n");
        sb.append("    -fx-text-border: -color-input-border;\n");
        sb.append("    -fx-background: -color-control-background;\n");
        sb.append("    -fx-background-smoothing: true;\n");
        sb.append("    -fx-accent: -color-accent-background;\n");
        sb.append("    -fx-mark-color: -color-control-foreground;\n");
        sb.append("    -fx-control-inner-background: -color-control-background;\n");
        sb.append("    -fx-text-base-color: -color-foreground;\n");
        sb.append("    -color-control-foreground-dark: derive(-color-control-foreground, -50%);\n");
        sb.append("    -color-control-foreground-light: derive(-color-control-foreground, +30%);\n");
        sb.append("}\n");

        try {
            Files.writeString(Path.of("/Users/sandy/debug.css"), sb.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return sb.toString();
    }

    private static String normalizeAlpha(ColorDefinition input) throws ColorException {
        Color color = ColorUtil.toColor(input.getHexColor());
        return String.format("rgba(%.02f,%.02f,%.02f,%.02f)", color.getRed() * 255, color.getGreen() * 255, color.getBlue() * 255, .3);
    }

    private static String getCodeColorsStylesheet(Variation variation) {
        CodeColorsStyleSheet sheet = new CodeColorsStyleSheet();
        Set<HighlightingToken.Kind> kinds = HighlightingToken.getKinds();
        for (HighlightingToken.Kind kind : kinds) {
            StyleClass tokenClass = sheet.addClasses("syntax-" + kind.toString().toLowerCase());
            tokenClass.setAttribute("-fx-fill", getColorForTokenKind(variation, kind) + " !important");
        }

        return sheet.toString();
    }

    private static String getColorForTokenKind(Variation variation, HighlightingToken.Kind kind) {
        String tokenCategoryName = HighlightingToken.getString(kind);
        ColorDefinition color = variation.getCodeColor(tokenCategoryName);
        if (color == null || color.isBlank()) {
            return getDefaultTextColor();
        }
        return color.getHexColor();
    }

    // Beware this is CSS color variable, not color code
    private static String getDefaultTextColor() {
        return cssColorVariable(ColorID.EDITOR_FOREGROUND);
    }

    private static String formatColorDeclaration(String name, String value) {
        StringBuilder sb = new StringBuilder();
        sb.append("    ");
        sb.append(cssColorVariable(name));
        sb.append(": ");
        sb.append(value);
        sb.append(";\n");
        return sb.toString();
    }

    private static String getPaletteColorVarsString(Variation variation) {
        StringBuilder sb = new StringBuilder();
        for (Entry<String, ColorDefinition> entry : variation.getPaletteColors().entrySet()) {
            ColorDefinition colordef = entry.getValue();
            sb.append(formatColorDeclaration(entry.getKey(), colordef.getHexColor()));
        }
        sb.append(formatColorDeclaration(ColorID.TRANSPARENT,"rgba(0, 0, 0, 0.01)" ));
        return sb.toString();
    }

    public static class ThemableObject {
        private Parent parent = null;
        private Scene scene = null;

        public static ThemableObject of(Scene scene) {
            if (scene == null) {
                throw new UiDataException("Parameter must not be null");
            }
            ThemableObject object = new ThemableObject();
            object.scene = scene;
            return object;
        }

        public static ThemableObject of(Parent parent) {
            if (parent == null) {
                throw new UiDataException("Parameter nust not be null");
            }
            ThemableObject object = new ThemableObject();
            object.parent = parent;
            return object;
        }

        public ObservableList<String> getStylesheets() {
            if (parent != null) {
                return parent.getStylesheets();
            }
            if (scene != null) {
                return scene.getStylesheets();
            }
            throw new UiDataException("Inconsisten state");
        }
    }
}
