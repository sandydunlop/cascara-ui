package io.github.qishr.cascara.ui.style.standard;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class RadioButtonStyle extends ControlStyle {
    public RadioButtonStyle() {
        super();

        defineRule(newRule()
            .addSelector (classSelector("radio-button"))
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.FOREGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("radio-button > .radio"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.CHECKBOX_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, values(em(1)), false)
            .addDeclaration(FX_BACKGROUND_INSETS, values(ZERO), false)
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.CHECKBOX_BORDER)), false)
            .addDeclaration(FX_BORDER_STYLE, values(SOLID), false)
            .addDeclaration(FX_BORDER_RADIUS, values(em(1)), false)

            // Why does this break it?
            // Looks like it just doesn't have a border color property
            // .addDeclaration(FX_BORDER_COLOR, values(ColorID.RADIO_ACTIVE_BORDER), false)

            .addDeclaration(FX_BORDER_INSETS, values(ZERO), false)
            .addDeclaration(FX_BORDER_WIDTH, values(px(1)), false)
            .addDeclaration(FX_PADDING, values(px(2)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("radio-button > .radio > .dot"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(TRANSPARENT), false)
            .addDeclaration(FX_BACKGROUND_INSETS, values(ZERO), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, values(em(1)), false)
            .addDeclaration(FX_PADDING, values(em(0.3)), false)
            .build()
        );

        // Selected

        defineRule(newRule()
            .addSelector (classSelector("radio-button:selected > .radio"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.CHECKBOX_SELECTED_BACKGROUND)), false)
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.CHECKBOX_SELECTED_BORDER)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("radio-button:selected > .radio > .dot"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.CHECKBOX_FOREGROUND)), false)
            .build()
        );

        // Focused

        defineRule(newRule()
            .addSelector (classSelector("radio-button:focused > .radio"))
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.CHECKBOX_BORDER)), false)
            .addDeclaration(FX_BORDER_STYLE, values(SOLID), false)
            .addDeclaration(FX_BORDER_RADIUS, values(em(1)), false)
            .addDeclaration(FX_BORDER_INSETS, values(ZERO), false)
            .addDeclaration(FX_BORDER_WIDTH, values(px(1)), false)
            .build()
        );

        // Focus & Selected

        defineRule(newRule()
            .addSelector (classSelector("radio-button:focused:selected > .radio"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.CHECKBOX_SELECTED_BACKGROUND)), false)
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.CHECKBOX_SELECTED_BORDER)), false)
            .build()
        );

        // Hover

        defineRule(newRule()
            .addSelector (classSelector("radio-button:hover > .radio"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.CHECKBOX_BACKGROUND)), false)
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.CHECKBOX_BORDER)), false)
            .build()
        );

        // Hover & Focus

        defineRule(newRule()
            .addSelector (classSelector("radio-button:focused:hover > .radio"))
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.CHECKBOX_BORDER)), false)
            .build()
        );

        // Disabled

        defineRule(newRule()
            .addSelector (classSelector("radio-button:disabled"))
            .addDeclaration(FX_OPACITY, literal("0.4"), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("radio-button:disabled > .radio"))
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.CHECKBOX_BORDER)), false)
            .build()
        );
    }
}
