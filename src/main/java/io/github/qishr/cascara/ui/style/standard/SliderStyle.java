package io.github.qishr.cascara.ui.style.standard;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class SliderStyle extends ControlStyle {
    public SliderStyle() {
        super();
        defineRule(newRule()
            .addSelector (classSelector("slider"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(TRANSPARENT), false)
            .addDeclaration(FX_BACKGROUND_INSETS, literal(ZERO), false)
            .addDeclaration(FX_EFFECT, values(NULL), false)
            .addDeclaration(FX_PADDING, literal("10px"), false)
            .build()
        );

        //
        // Axis
        //

        defineRule(newRule()
            .addSelector (classSelector("slider > .axis"))

            .addDeclaration(FX_BACKGROUND_COLOR, values(TRANSPARENT), false)
            .addDeclaration(FX_BACKGROUND_INSETS, literal(ZERO), false)
            .addDeclaration(FX_BORDER_WIDTH, literal(ZERO), false)
            .addDeclaration(FX_TICK_LABEL_FILL, values(color(ColorID.FOREGROUND)), false)
            .addDeclaration(FX_TICK_LENGTH, literal("15px"), false)
            .addDeclaration(FX_MINOR_TICK_LENGTH, literal("5px"), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("slider > .axis > .axis-tick-mark"))
            .addDeclaration(FX_STROKE, values(color(ColorID.FOREGROUND)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("slider > .axis > .axis-minor-tick-mark"))
            .addDeclaration(FX_STROKE, values(color(ColorID.FOREGROUND)), false)
            .build()
        );

        //
        // Track
        //

        defineRule(newRule()
            .addSelector (classSelector("slider > .track"))
            .addSelector (classSelector("slider:hover > .track"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.INPUT_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_INSETS, literal(ZERO), false)
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.CONTROL_FOREGROUND)), false)
            .addDeclaration(FX_BORDER_RADIUS, literal("4"), false)
            .addDeclaration(FX_BORDER_INSETS, literal(ZERO), false)
            .addDeclaration(FX_OPACITY, literal("0.7"), false)
            .addDeclaration(FX_PREF_HEIGHT, literal("8"), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("slider:vertical > .track"))
            .addDeclaration(FX_PREF_WIDTH, literal("8"), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("slider:hover > .track"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.INPUT_BACKGROUND)), false)
            .build()
        );

        //
        // Thumb
        //

        defineRule(newRule()
            .addSelector (classSelector("slider > .thumb"))
            .addDeclaration(FX_EFFECT, literal(NULL), false)
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.ACCENT_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, literal("2px"), false)
            .addDeclaration(FX_PADDING, literal("10px 4px 10px 4px"), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("slider:vertical > .thumb"))
            .addDeclaration(FX_PADDING, literal("4px 10px 4px 10px"), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("slider:hover > .thumb"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.ACCENT_HOVER_BACKGROUND)), false)
            .build()
        );

        //
        // Disabled
        //

        defineRule(newRule()
            .addSelector (classSelector("slider:disabled"))
            .addDeclaration(FX_OPACITY, literal("0.4"), false)
            .build()
        );
    }
}
