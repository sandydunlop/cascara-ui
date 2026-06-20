package io.github.qishr.cascara.ui.style.standard;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.style.part.CellPseudoRules;
import io.github.qishr.cascara.ui.style.part.CellRules;
import io.github.qishr.cascara.ui.style.part.TabularRules;
import io.github.qishr.cascara.ui.theme.ColorID;

public class TreeViewStyle extends ControlStyle {
    private static final String CONTROL_CLASS = "tree-view";
    private static final String CELL_CLASS = "tree-cell";

    public TreeViewStyle() {
        super();

        incorporateRules(new TabularRules(CONTROL_CLASS));
        incorporateRules(new CellRules(CELL_CLASS));
        incorporateRules(new CellPseudoRules(CONTROL_CLASS, CELL_CLASS));

        // Arrow

        defineRule(newRule()
            .addSelector (classSelector("tree-cell > .tree-disclosure-node"))
            .addDeclaration(FX_PADDING, sides(px(8), px(2), px(4), px(8)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("tree-cell > .tree-disclosure-node > .arrow"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.SIDEBAR_FOREGROUND)), false)
            .addDeclaration(FX_PADDING, sides(px(8), px(2), px(4), px(8)), false)
            .addDeclaration(FX_SHAPE, shape(SHAPE_ARROW_DOWN), false)
            .addDeclaration(FX_EFFECT, values(NULL), false)
            .addDeclaration(FX_SCALE_SHAPE, values(FALSE), false)
            .addDeclaration(FX_ROTATE, degrees(-90), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("tree-cell:expanded > .tree-disclosure-node > .arrow"))
            .addDeclaration(FX_ROTATE, literal("0"), false)
            .build()
        );
    }
}
