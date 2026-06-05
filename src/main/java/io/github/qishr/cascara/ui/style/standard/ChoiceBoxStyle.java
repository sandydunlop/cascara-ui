package io.github.qishr.cascara.ui.style.standard;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class ChoiceBoxStyle extends ControlStyle {
    public ChoiceBoxStyle() {
        super();
        defineRule(newRule()
            .addSelector (classSelector("choice-box"))
            .addSelector (classSelector("choice-box:hover"))
            .addSelector (classSelector("choice-box:focused"))
            .addSelector (classSelector("choice-box:focused:hover"))
            // .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.INPUT_BORDER),color(ColorID.INPUT_BACKGROUND)), false)
            // .addDeclaration(FX_BACKGROUND_INSETS, values(ZERO), false)
            // .addDeclaration(FX_BACKGROUND_RADIUS, values(px(2)), false)
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.INPUT_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_INSETS, values(ZERO), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, values(px(2)), false)
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.INPUT_BORDER)), false)
            .addDeclaration(FX_BORDER_WIDTH, values(px(1)), false)
            .addDeclaration(FX_BORDER_RADIUS, values(px(2)), false)

            .addDeclaration(FX_PADDING, values(ZERO), false)
            .addDeclaration(FX_FONT_SIZE, literal("1em"), false)
            // .addDeclaration(FX_TEXT_FILL, values(color(ColorID.INPUT_FOREGROUND)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("choice-box .text-field"))
            .addSelector (classSelector("choice-box .text-input"))
            .addSelector (classSelector("choice-box .label"))
            .addSelector (classSelector("choice-box .list-cell"))
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.INPUT_FOREGROUND)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("choice-box > .open-button"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, values(px(2)), false)
            .addDeclaration(FX_BACKGROUND_INSETS, values(ZERO), false)
            .addDeclaration(FX_BORDER_INSETS, values(ZERO), false)
            .addDeclaration(FX_BORDER_WIDTH, values(ZERO), false)
            .addDeclaration(FX_MAX_WIDTH, values(em(0.5)), false)
            .addDeclaration(FX_MAX_HEIGHT, values(em(1)), false)
            .addDeclaration(FX_PADDING, values(sides(em(0.8), em(0.4), em(0.7), em(0.4))), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("choice-box > .open-button > .arrow"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_FOREGROUND)), false)
            .addDeclaration(FX_SHAPE, shape(SHAPE_ARROW_DOWN), false)
            .build()
        );


        // Hover

        defineRule(newRule()
            .addSelector(classSelector("choice-box > .open-button:hover"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_HOVER_BACKGROUND)), false)
            .build()
        );


        // Disabled

        defineRule(newRule()
            .addSelector (classSelector("choice-box:disabled"))
            .addDeclaration(FX_OPACITY, literal("0.4"), false)
            .build()
        );
    }
}
