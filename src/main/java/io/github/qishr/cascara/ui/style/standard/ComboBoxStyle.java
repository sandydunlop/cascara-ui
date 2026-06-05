package io.github.qishr.cascara.ui.style.standard;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class ComboBoxStyle extends ControlStyle {
    public ComboBoxStyle() {
        super();

        defineRule(newRule()
            .addSelector (classSelector("combo-box"))
            .addSelector (classSelector("combo-box:hover"))
            .addSelector (classSelector("combo-box:focused"))
            .addSelector (classSelector("combo-box:focused:hover"))

            .addSelector (classSelector("combo-box-base"))
            .addSelector (classSelector("combo-box-base:hover"))
            .addSelector (classSelector("combo-box-base:focused"))
            .addSelector (classSelector("combo-box-base:focused:hover"))

            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.INPUT_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_INSETS, values(ZERO), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, values(px(2)), false)
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.INPUT_BORDER)), false)
            .addDeclaration(FX_BORDER_WIDTH, values(px(1)), false)
            .addDeclaration(FX_BORDER_RADIUS, values(px(2)), false)
            .addDeclaration(FX_PADDING, values(ZERO), false)
            .addDeclaration(FX_FONT_SIZE, values(em(1)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("combo-box .text-input"))
            .addSelector (classSelector("combo-box .label"))
            .addSelector (classSelector("combo-box .list-cell"))
            .addSelector (classSelector("combo-box-base .label"))
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.INPUT_FOREGROUND)), true)
            .build()
        );


        defineRule(newRule()
            .addSelector(classSelector("combo-box > .arrow-button"))
            .addSelector(classSelector("combo-box-base > .arrow-button"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, values(px(2)), false)
            .addDeclaration(FX_BACKGROUND_INSETS, values(ZERO), false)
            .addDeclaration(FX_BORDER_INSETS, values(ZERO), false)
            .addDeclaration(FX_BORDER_WIDTH, values(ZERO), false)
            .addDeclaration(FX_MAX_WIDTH, values(em(0.5)), false)
            .addDeclaration(FX_PADDING, values(sides(em(0.8), em(0.4), em(0.7), em(0.4))), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("combo-box > .arrow-button > .arrow"))
            .addSelector(classSelector("combo-box-base > .arrow-button > .arrow"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_FOREGROUND)), false)
            .addDeclaration(FX_SHAPE, shape(SHAPE_ARROW_DOWN), false)
            .build()
        );


        // Hover

        defineRule(newRule()
            .addSelector(classSelector("combo-box > .arrow-button:hover"))
            .addSelector(classSelector("combo-box-base > .arrow-button:hover"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_HOVER_BACKGROUND)), false)
            .build()
        );


        // Disabled

        defineRule(newRule()
            .addSelector (classSelector("combo-box:disabled"))
            .addDeclaration(FX_OPACITY, literal("0.4"), false)
            .build()
        );
    }
}
