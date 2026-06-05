package io.github.qishr.cascara.ui.style.standard;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.style.part.CellRules;
import io.github.qishr.cascara.ui.style.part.ColumnHeaderRules;
import io.github.qishr.cascara.ui.style.part.ColumnLineRules;
import io.github.qishr.cascara.ui.style.part.RowAndCellPseudoRules;
import io.github.qishr.cascara.ui.style.part.RowRules;
import io.github.qishr.cascara.ui.style.part.TabularRules;
import io.github.qishr.cascara.ui.theme.ColorID;

public class TreeTableViewStyle extends ControlStyle {
    private static final String CONTROL_CLASS = "tree-table-view";
    private static final String ROW_CLASS = "tree-table-row-cell";
    private static final String CELL_CLASS = "tree-table-cell";

    public static final String NO_DISCLOSURE = "no-disclosure";

    public TreeTableViewStyle() {
        super();

        incorporateRules(new TabularRules(CONTROL_CLASS));
        incorporateRules(new ColumnHeaderRules(CONTROL_CLASS));
        incorporateRules(new RowRules(ROW_CLASS));

        defineRule(newRule()
            .addSelector (classSelector("tree-table-view.no-headers .column-header"))
            .addSelector (classSelector("tree-table-view.no-headers .column-header-background"))
            .addSelector (classSelector("tree-table-view.no-headers .column-header-background .filler "))
            .addDeclaration(FX_SIZE, literal("0.1px"), false)
            .addDeclaration(FX_PREF_HEIGHT, literal(ZERO), false)
            .addDeclaration(FX_PADDING, literal(ZERO), false)
            .addDeclaration(VISIBILITY, literal("collapse"), false)
            .build()
        );

        incorporateRules(new CellRules(CELL_CLASS));
        incorporateRules(new RowAndCellPseudoRules(CONTROL_CLASS, ROW_CLASS, CELL_CLASS));
        incorporateRules(new ColumnLineRules(CONTROL_CLASS));

        // Arrow

        defineRule(newRule()
            .addSelector (classSelector("tree-table-row-cell > .tree-disclosure-node"))
            .addDeclaration(FX_PADDING, sides(px(2), px(2), px(2), px(2)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("tree-table-view." + NO_DISCLOSURE + " .tree-table-row-cell > .tree-disclosure-node"))
            .addSelector (classSelector("tree-table-view." + NO_DISCLOSURE + " .tree-table-row-cell > .tree-disclosure-node > .arrow"))
            .addDeclaration(VISIBILITY, values(COLLAPSE), true)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("tree-table-view .arrow"))
            .addSelector (classSelector("tree-table-row-cell .arrow"))
            .addSelector (classSelector("tree-table-row-cell > .tree-disclosure-node > .arrow"))
            .addSelector (classSelector("tree-table-view > .virtual-flow > .clipped-container > .sheet > .tree-table-row-cell > .tree-disclosure-node > .arrow"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.SIDEBAR_FOREGROUND)), false)
            .addDeclaration(FX_PADDING, sides(px(2), px(2), px(2), px(2)), false)
            .addDeclaration(FX_SHAPE, shape(SHAPE_ARROW_DOWN), false)
            .addDeclaration(FX_EFFECT, values(NULL), false)
            .addDeclaration(FX_SCALE_SHAPE, values(FALSE), false)
            .addDeclaration(FX_ROTATE, degrees(-90), false)
            .addDeclaration(FX_FONT_SIZE, values(em(1)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("tree-table-row-cell:expanded .arrow"))
            .addSelector (classSelector("tree-table-row-cell:expanded > .tree-disclosure-node > .arrow"))
            .addSelector (classSelector("tree-table-view > .virtual-flow > .clipped-container > .sheet > .tree-table-row-cell:expanded > .tree-disclosure-node > .arrow"))
            .addDeclaration(FX_ROTATE, values(ZERO), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("tree-table-row-cell .arrow-button"))
            .addDeclaration(FX_BACKGROUND_INSETS, literal(ZERO), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, literal(ZERO), false)
            .addDeclaration(FX_PADDING, literal("0 4 0 0"), false)
            .addDeclaration(FX_FONT_SIZE, literal("1em"), false)
            .build()
        );
    }
}
