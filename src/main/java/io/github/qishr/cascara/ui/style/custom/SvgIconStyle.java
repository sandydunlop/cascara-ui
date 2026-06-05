package io.github.qishr.cascara.ui.style.custom;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.theme.ColorID;


public class SvgIconStyle extends ControlStyle {

    public static final String ICON = "cascara-svg-icon";
    public static final String SHAPE = "cascara-svg-shape";
    public static final String FILL = "cascara-svg-fill";
    public static final String STROKE = "cascara-svg-stroke";

    public SvgIconStyle() {
        super();

        defineRule(newRule()
            .addSelector (classSelector(ICON))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.TRANSPARENT)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector(ICON + " ." + STROKE))
            .addDeclaration(FX_STROKE, values(color(ColorID.CONTROL_FOREGROUND)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector(ICON + " ." + FILL))
            .addDeclaration(FX_FILL, values(color(ColorID.CONTROL_FOREGROUND)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector(ICON + ":disabled"))
            .addDeclaration(FX_OPACITY, literal("0.4"), false)
            .build()
        );
    }
}
