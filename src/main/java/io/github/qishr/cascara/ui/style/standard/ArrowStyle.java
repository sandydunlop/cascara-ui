package io.github.qishr.cascara.ui.style.standard;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class ArrowStyle extends ControlStyle {
    public ArrowStyle() {
        super();

        // Arrows

        // defineRule(newRule()
        //     .addSelector (classSelector("combo-box-base > .arrow-button > .arrow"))
        //     .addSelector (classSelector("choice-box > .open-button > .arrow"))
        //     .addSelector (classSelector("menu-button > .arrow-button > .arrow"))
        //     .addSelector (classSelector("split-menu-button > .arrow-button > .arrow"))
        //     .addDeclaration(FX_PADDING, literal("0.2em 0.4em 0.2em 0.4em"), false)
        //     .addDeclaration(FX_SHAPE, shape(SHAPE_ARROW_DOWN), false)
        //     .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_FOREGROUND)), false)
        // );
        defineRule(newRule()
            .addSelector (classSelector("menu-button:openvertically > .arrow-button > .arrow"))
            .addDeclaration(FX_SHAPE, shape(SHAPE_ARROW_DOWN), false)
            .build()
        );

        // Nested Columns

        defineRule(newRule()
            .addSelector (classSelector("table-view > .column-header-background .nested-column-header > .column-header > GridPane > .arrow"))
            .addSelector (classSelector("tree-table-view > .column-header-background .nested-column-header > .column-header > GridPane > .arrow"))
            .addDeclaration(FX_PADDING, literal("0.5em 0.4em 0.5em 0.4em"), false)
            .addDeclaration(FX_SHAPE, shape(SHAPE_ARROW_DOWN), false)
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_FOREGROUND)), false)
            .build()
        );
    }
}
