package io.github.qishr.cascara.ui.style.standard;

import io.github.qishr.cascara.ui.style.ControlStyle;

public class GridPaneStyle extends ControlStyle {
        public GridPaneStyle() {
        super();
        defineRule(newRule()
            .addSelector (classSelector("grid-pane"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(TRANSPARENT), false)
            .build()
        );
    }
}
