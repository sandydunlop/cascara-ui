package io.github.qishr.cascara.ui.style.custom;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class TitleBarStyle extends ControlStyle {
    public TitleBarStyle() {
        super();
        defineRule(newRule()
            .addSelector (classSelector("title-bar"))
            .addSelector (classSelector("title-bar-button-box"))
            .addSelector (classSelector("title-bar-title-box"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.TITLEBAR_ACTIVE_BACKGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("title-bar .line"))
            .addDeclaration(FX_FILL, values(color(ColorID.TITLEBAR_ACTIVE_FOREGROUND)), false)
            .addDeclaration(FX_OPACITY, literal("0.6"), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("title-bar-title"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.TITLEBAR_ACTIVE_BACKGROUND)), false)
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.TITLEBAR_ACTIVE_FOREGROUND)), false)
            .addDeclaration(FX_FONT_WEIGHT, values(BOLD), false)
            .addDeclaration(FX_FONT_SIZE, values(em(1.1)), false)
            .addDeclaration(FX_FONT_FAMILY, literal("Dosis"), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("title-bar-button"))
            .addSelector (classSelector("title-bar-button:hover"))
            .addSelector (classSelector("title-bar-button:focused"))
            .addSelector (classSelector("title-bar-button:focused:hover"))
            .addDeclaration(FX_BACKGROUND_INSETS, literal("1"), false)
            .addDeclaration(FX_BACKGROUND_COLOR, literal("linear-gradient(to bottom right, -color-control-foreground-dark, -color-control-foreground)"), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, literal(ZERO), false)
            .addDeclaration(FX_BORDER_RADIUS, literal("0, 0, 0"), false)
            .addDeclaration(FX_BORDER_INSETS, literal("0, 1, 2"), false)
            .addDeclaration(FX_BORDER_COLOR, literal("-color-control-foreground-dark -color-control-foreground  -color-control-foreground  -color-control-foreground-dark , derive(-color-control-background, -30%), -color-control-foreground -color-control-foreground-dark  -color-control-foreground-dark  -color-control-foreground"), false)
            .addDeclaration(FX_OPACITY, literal("0.6"), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("title-bar-button:pressed"))
            .addDeclaration(FX_BACKGROUND_INSETS, literal("1"), false)
            .addDeclaration(FX_BACKGROUND_COLOR, literal("linear-gradient(to top left, -color-control-foreground-dark, -color-control-foreground)"), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, literal(ZERO), false)
            .addDeclaration(FX_BORDER_RADIUS, literal("0, 0, 0"), false)
            .addDeclaration(FX_BORDER_INSETS, literal("0, 1, 2"), false)
            .addDeclaration(FX_BORDER_COLOR, literal("-color-control-foreground-dark -color-control-foreground  -color-control-foreground  -color-control-foreground-dark, derive(-color-control-background, -30%), -color-control-foreground-dark -color-control-foreground  -color-control-foreground  -color-control-foreground-dark"), false)
            .addDeclaration(FX_OPACITY, literal("0.6"), false)
            .build()
        );
    }
}
