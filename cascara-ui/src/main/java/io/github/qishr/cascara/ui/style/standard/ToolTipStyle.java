package io.github.qishr.cascara.ui.style.standard;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class ToolTipStyle extends ControlStyle {
    public ToolTipStyle() {
        super();
        defineRule(newRule()
            .addSelector (classSelector("tooltip"))
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.TEXT_FOREGROUND)), false)
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.TOOLTIP_BORDER)), false)
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.CONTROL_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_INSETS, literal(ZERO), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, literal("0 0 0 0"), false)
            .addDeclaration(FX_PADDING, literal("0.3em 0.6em 0.3em 0.6em"), false)
            .addDeclaration(FX_FONT_WEIGHT, literal(LIGHTER), false)
            .addDeclaration(FX_FONT_SIZE, literal("1.16em"), false)
            .addDeclaration(FX_BORDER_WIDTH, literal("1px"), false)
            // .addDeclaration(FX_EFFECT, literal("dropshadow( three-pass-box , rgba(0,0,0,0.5) , 10, 0.0 , 0 , 3 )"), false)
            .addDeclaration(FX_EFFECT, values(dropshadow(color(ColorID.WIDGET_SHADOW))), false)
            .build()
        );
    }
}
