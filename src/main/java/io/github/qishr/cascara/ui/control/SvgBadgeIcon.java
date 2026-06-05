package io.github.qishr.cascara.ui.control;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class SvgBadgeIcon extends StackPane {
    private final SvgIcon baseIcon;
    private Node badge;
    // private RotateTransition spinTransition;

    public SvgBadgeIcon(SvgIcon icon) {
        this.baseIcon = icon;
        this.getStyleClass().add("svg-badge-icon");

        // Ensure the container doesn't block layout
        this.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);

        // Add the base icon as the bottom layer
        getChildren().add(baseIcon);
    }

    /**
     * Attaches a badge to the icon.
     * @param badgeNode The node to overlay (Circle, Label, another SvgIcon, etc.)
     * @param alignment Where to place the badge (e.g., Pos.BOTTOM_RIGHT)
     */
    public void setBadge(Node badgeNode, Pos alignment) {
        if (this.badge != null) {
            getChildren().remove(this.badge);
        }
        this.badge = badgeNode;
        if (badgeNode != null) {
            getChildren().add(badgeNode);
            StackPane.setAlignment(badgeNode, alignment);
            // Prevent the badge from growing the container if not desired
            badgeNode.setMouseTransparent(true);
        }
    }

    // public void setSpinning(boolean spinning) {
    //     if (spinning) {
    //         if (spinTransition == null) {
    //             spinTransition = new RotateTransition(Duration.seconds(2), baseIcon);
    //             spinTransition.setByAngle(360);
    //             spinTransition.setCycleCount(Animation.INDEFINITE);
    //             spinTransition.setInterpolator(Interpolator.LINEAR);
    //         }
    //         spinTransition.play();
    //     } else if (spinTransition != null) {
    //         spinTransition.stop();
    //         baseIcon.setRotate(0);
    //     }
    // }

    public SvgIcon getBaseIcon() {
        return baseIcon;
    }
}