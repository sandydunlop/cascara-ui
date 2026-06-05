package io.github.qishr.cascara.ui.style.standard;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.style.part.CellRules;
import io.github.qishr.cascara.ui.style.part.ColumnHeaderRules;
import io.github.qishr.cascara.ui.style.part.ColumnLineRules;
import io.github.qishr.cascara.ui.style.part.RowAndCellPseudoRules;
import io.github.qishr.cascara.ui.style.part.RowRules;
import io.github.qishr.cascara.ui.style.part.TabularRules;
import io.github.qishr.cascara.ui.theme.ColorID;

public class TableViewStyle extends ControlStyle {
    private static final String CONTROL_CLASS = "table-view";
    private static final String ROW_CLASS = "table-row-cell";
    private static final String CELL_CLASS = "table-cell";

    public TableViewStyle() {
        super();

        incorporateRules(new TabularRules(CONTROL_CLASS));
        incorporateRules(new ColumnHeaderRules(CONTROL_CLASS));

        defineRule(newRule()
            .addSelector (classSelector("table-view.no-headers .column-header"))
            .addSelector (classSelector("table-view.no-headers .column-header-background"))
            .addSelector (classSelector("table-view.no-headers .column-header-background .filler "))
            .addDeclaration(FX_SIZE, literal("0.1px"), false)
            .addDeclaration(FX_PREF_HEIGHT, literal(ZERO), false)
            .addDeclaration(FX_PADDING, literal(ZERO), false)
            .addDeclaration(VISIBILITY, literal("collapse"), false)
            .build()
        );

        incorporateRules(new RowRules(ROW_CLASS));
        incorporateRules(new CellRules(CELL_CLASS));
        incorporateRules(new RowAndCellPseudoRules(CONTROL_CLASS, ROW_CLASS, CELL_CLASS));
        incorporateRules(new ColumnLineRules(CONTROL_CLASS));


        // Controls inside cells:
        // Although the following rules used in TreeTableView as well as TableView.
        // the same selectors are used so they only have to be defined once, therefore
        // they are not defined in TreeTableViewStyle,

        defineRule(newRule()
            .addSelector (classSelector("check-box-table-cell > .check-box"))
            .addDeclaration(FX_OPACITY, literal("1"), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("check-box-table-cell > .check-box > .box"))
            .addDeclaration(FX_BACKGROUND_COLOR, literal("transparent"), false)
            .addDeclaration(FX_BACKGROUND_INSETS, literal(ZERO), false)
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.INPUT_BORDER)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("check-box-table-cell > .check-box:selected > .box > .mark"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.SIDEBAR_FOREGROUND)), false)
            .build()
        );
    }
}
