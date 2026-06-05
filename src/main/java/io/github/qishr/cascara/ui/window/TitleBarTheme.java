package io.github.qishr.cascara.ui.window;

import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.paint.Color;

/**
 * Defines the structural configuration of a custom title bar.
 * The visual "look" is handled via the styleClass in CSS.
 */
public record TitleBarTheme(
    String id,
    String styleClass,
    double height,
    Pos titleAlignment,
    List<TitleButtonType> leftButtons,
    List<TitleButtonType> rightButtons,
    boolean useStretchingLines,
    ShadowProfile activeProfile,
    ShadowProfile inactiveProfile
) {
    public enum TitleButtonType {
        CLOSE_BUTTON, MINIMIZE_BUTTON, MAXIMIZE_BUTTON, MENU_BUTTON
    }

    // Factory method for your current System 8 look
    public static TitleBarTheme macSystem8() {
        return new TitleBarTheme(
            "sys8",
            "theme-sys8",
            24.0,
            Pos.CENTER,
            List.of(TitleButtonType.CLOSE_BUTTON),
            List.of(TitleButtonType.MAXIMIZE_BUTTON), // System 8 "Zoom" box
            true,
            // In your Designer State
            new ShadowProfile(15, Color.rgb(0,0,0,0.5), 2),
            new ShadowProfile(8, Color.rgb(0,0,0,0.3), 1)
        );
    }

    // Factory method for a modern Windows look
    public static TitleBarTheme modern() {
        return new TitleBarTheme(
            "modern",
            "theme-modern",
            32.0,
            Pos.CENTER_LEFT,
            List.of(),
            List.of(TitleButtonType.MINIMIZE_BUTTON, TitleButtonType.MAXIMIZE_BUTTON, TitleButtonType.CLOSE_BUTTON),
            false,
            new ShadowProfile(15, Color.rgb(0,0,0,0.5), 1),
            new ShadowProfile(8, Color.rgb(0,0,0,0.3), 1)
        );
    }

    public static TitleBarTheme windows7() {
        return new TitleBarTheme(
            "win7",
            "theme-win7",
            30.0,
            Pos.CENTER_LEFT,
            List.of(),
            List.of(TitleButtonType.MINIMIZE_BUTTON, TitleButtonType.MAXIMIZE_BUTTON, TitleButtonType.CLOSE_BUTTON),
            false,
            new ShadowProfile(15, Color.rgb(0,0,0,0.5), 2),
            new ShadowProfile(8, Color.rgb(0,0,0,0.3), 1)
        );
    }


        // public static TitleBarTheme windows7() {
        //     return new TitleBarTheme(
        //         "win7",
        //         "theme-win7",
        //         30.0,
        //         Pos.CENTER_LEFT,
        //         List.of(), // No buttons on left
        //         List.of(TitleButtonType.MINIMIZE_BUTTON,
        //                 TitleButtonType.MAXIMIZE_BUTTON,
        //                 TitleButtonType.CLOSE_BUTTON),
        //         false,
        //         new ShadowProfile(15, Color.rgb(0,0,0,0.5), 2),
        //         new ShadowProfile(8, Color.rgb(0,0,0,0.3), 1)
        //     );
        // }


    // Factory method for a modern Windows look
    public static TitleBarTheme windows10() {
        return new TitleBarTheme(
            "win10",
            "theme-win10",
            32.0,
            Pos.CENTER_LEFT,
            List.of(),
            List.of(TitleButtonType.MINIMIZE_BUTTON, TitleButtonType.MAXIMIZE_BUTTON, TitleButtonType.CLOSE_BUTTON),
            false,
            new ShadowProfile(15, Color.rgb(0,0,0,0.5), 2),
            new ShadowProfile(8, Color.rgb(0,0,0,0.3), 1)
        );
    }
}
