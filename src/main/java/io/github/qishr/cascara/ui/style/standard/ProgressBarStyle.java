package io.github.qishr.cascara.ui.style.standard;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class ProgressBarStyle extends ControlStyle {
    public ProgressBarStyle() {
        super();
        defineRule(newRule()
            .addSelector (classSelector("progress-bar > .bar"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.ACCENT_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, values(px(4)), false)
            .addDeclaration(FX_BACKGROUND_INSETS, literal(px(1)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("progress-bar > .track"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.CONTROL_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_INSETS, literal(ZERO), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, values(px(4)), false)
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.CONTROL_FOREGROUND)), false)
            .addDeclaration(FX_BORDER_RADIUS, values(px(4)), false)
            .addDeclaration(FX_BORDER_INSETS, literal(ZERO), false)
            .addDeclaration(FX_PREF_HEIGHT, literal("4px"), false)
            .addDeclaration(FX_MIN_HEIGHT, literal("4px"), false)
            .addDeclaration(FX_MAX_HEIGHT, literal("4px"), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("progress-bar:indeterminate > .track"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(TRANSPARENT), false)
            .build()
        );
    }
}
