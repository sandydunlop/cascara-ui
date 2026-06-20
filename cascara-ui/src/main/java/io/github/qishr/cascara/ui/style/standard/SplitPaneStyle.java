package io.github.qishr.cascara.ui.style.standard;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class SplitPaneStyle extends ControlStyle {
    public SplitPaneStyle() {
        super();
        defineRule(newRule()
            .addSelector (classSelector("split-pane"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.CONTROL_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_INSETS, values(ZERO), false)
            .addDeclaration(FX_BORDER_INSETS, values(ZERO), false)
            .addDeclaration(FX_BORDER_WIDTH, values(ZERO), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("split-pane:horizontal > .split-pane-divider"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.CONTROL_BACKGROUND), TRANSPARENT), false)
            .addDeclaration(FX_BACKGROUND_INSETS, literal("0, 0 2 0 2"), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("split-pane:vertical > .split-pane-divider"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.CONTROL_BACKGROUND), TRANSPARENT), false)
            .addDeclaration(FX_BACKGROUND_INSETS, literal("0, 2 0 2 0"), false)
            .build()
        );
    }
}
