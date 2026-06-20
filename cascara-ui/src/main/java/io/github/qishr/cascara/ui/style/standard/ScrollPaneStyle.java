package io.github.qishr.cascara.ui.style.standard;

import io.github.qishr.cascara.ui.style.ControlStyle;

public class ScrollPaneStyle extends ControlStyle {
    public ScrollPaneStyle() {
        super();
        defineRule(newRule()
            .addSelector (classSelector("scroll-pane"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(TRANSPARENT), false)
            .addDeclaration(FX_BORDER_WIDTH, literal(ZERO), false)
            .addDeclaration(FX_BACKGROUND_INSETS, literal(ZERO), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("scroll-pane .corner"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(TRANSPARENT), false)
            .addDeclaration(FX_BACKGROUND_INSETS, literal(ZERO), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, literal(ZERO), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("scroll-pane > .viewport"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(TRANSPARENT), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("list-view > .virtual-flow > .corner"))
            .addSelector (classSelector("tree-view > .virtual-flow > .corner"))
            .addSelector (classSelector("table-view > .virtual-flow > .corner"))
            .addSelector (classSelector("tree-table-view > .virtual-flow > .corner"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(TRANSPARENT), false)
            .build()
        );
    }
}
