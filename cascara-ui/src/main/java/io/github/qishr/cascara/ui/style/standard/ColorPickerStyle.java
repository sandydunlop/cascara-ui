package io.github.qishr.cascara.ui.style.standard;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class ColorPickerStyle extends ControlStyle {
    public ColorPickerStyle() {
        super();
        defineRule(newRule()
            .addSelector (classSelector("color-picker"))
            .addDeclaration(SHRINK_ANIMATE_ON_PRESS, literal(TRUE), false)
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.INPUT_BACKGROUND)), false)
            .addDeclaration(FX_FONT_SIZE, literal("1em"), false)
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.BUTTON_FOREGROUND)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("color-picker > .arrow-button"))
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.BUTTON_FOREGROUND)), false)

            .addDeclaration(FX_BORDER_WIDTH, literal(ZERO), false)
            .addDeclaration(FX_BACKGROUND_INSETS, literal(ZERO), false)
            .addDeclaration(FX_BORDER_COLOR, values(TRANSPARENT), false)

            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_BACKGROUND)), false)
            .addDeclaration(FX_PADDING, literal("0.8em 0.4em 0.7em 0.4em"), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, literal("2"), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("color-picker > .arrow-button > arrow"))
            .addDeclaration(FX_SHAPE, shape(SHAPE_ARROW_DOWN), false)
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_FOREGROUND)), false)
            .build()
        );
    }
}
