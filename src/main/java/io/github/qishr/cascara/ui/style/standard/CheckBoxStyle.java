package io.github.qishr.cascara.ui.style.standard;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class CheckBoxStyle extends ControlStyle {
    public CheckBoxStyle() {
        super();

        defineRule(newRule()
            .addSelector (classSelector("check-box"))
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.FOREGROUND)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("check-box > .box"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.CHECKBOX_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_INSETS, values(ZERO), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, values(px(2)), false)
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.CHECKBOX_BORDER)), false)
            .addDeclaration(FX_BORDER_WIDTH, values(px(1)), false)
            .addDeclaration(FX_BORDER_STYLE, values(SOLID), false)
            .addDeclaration(FX_BORDER_RADIUS, values(px(2)), false)
            .addDeclaration(FX_PREF_WIDTH, values(em(1.1)), false)
            .addDeclaration(FX_PREF_HEIGHT, values(em(1.1)), false)
            .addDeclaration(FX_PADDING, values(px(1)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("check-box > .box > .mark"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(TRANSPARENT), false)
            .addDeclaration(FX_BACKGROUND_INSETS, values(ZERO), false)
            .addDeclaration(FX_PADDING, values(px(2)), false)
            .addDeclaration(FX_SHAPE, shape("M17.939,5.439L7.5,15.889l-5.439-5.449l0.879-0.879L7.5,14.111 l9.561-9.551L17.939,5.439z"), false)
            .build()
        );


        // Selected

        defineRule(newRule()
            .addSelector (classSelector("check-box:selected > .box"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.CHECKBOX_SELECTED_BACKGROUND)), false)
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.CHECKBOX_SELECTED_BORDER)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("check-box:selected > .box > .mark"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.CHECKBOX_FOREGROUND)), false)
            .build()
        );


        // Focused

        defineRule(newRule()
            .addSelector (classSelector("check-box:focused > .box"))
            .addDeclaration(FX_BACKGROUND_INSETS, values(ZERO), false)
            // .addDeclaration(FX_BACKGROUND_RADIUS, values(VALUE_ZERO), false)
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.CHECKBOX_BORDER)), false)
            .addDeclaration(FX_BORDER_WIDTH, values(px(1)), false)
            .addDeclaration(FX_BORDER_STYLE, values(SOLID), false)
            .addDeclaration(FX_BORDER_INSETS, values(ZERO), false)
            // .addDeclaration(FX_BORDER_WIDTH, literal("1, 2"), false)
            // .addDeclaration(FX_BORDER_STYLE, literal("segments(1, 2), solid"), false)
            // .addDeclaration(FX_BORDER_INSETS, literal("-3, 0"), false)
            .build()
        );

        // Focused & Selected

        defineRule(newRule()
            .addSelector (classSelector("check-box:focused:selected > .box"))
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.CHECKBOX_SELECTED_BORDER)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("check-box:focused:selected:hover > .box"))
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.CHECKBOX_SELECTED_BORDER)), false)
            .build()
        );




        // Indeterminate

        defineRule(newRule()
            .addSelector (classSelector("check-box:focused:indeterminate > .box"))
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.CHECKBOX_BORDER)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("check-box:indeterminate > .box > .mark"))
            .addDeclaration(FX_SHAPE, shape("M0,0H10V2H0Z"), false)
            .addDeclaration(FX_BACKGROUND_COLOR, values(TRANSPARENT), false)
            .build()
        );


        // Disabled

        defineRule(newRule()
            .addSelector (classSelector("check-box:disabled"))
            .addDeclaration(FX_OPACITY, literal("0.4"), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("check-box:disabled > .box"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.CHECKBOX_DISABLED_BACKGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("check-box:disabled > .box > .mark"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.CHECKBOX_DISABLED_FOREGROUND)), false)
            .build()
        );

    }
}
