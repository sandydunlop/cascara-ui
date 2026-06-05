package io.github.qishr.cascara.ui.style.standard;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.style.part.CellPseudoRules;
import io.github.qishr.cascara.ui.style.part.TabularRules;

public class ListViewStyle extends ControlStyle {
    private static final String CONTROL_CLASS = "list-view";
    private static final String CELL_CLASS = "list-cell";

    public ListViewStyle() {
        super();

        incorporateRules(new TabularRules(CONTROL_CLASS));
        incorporateRules(new CellPseudoRules(CONTROL_CLASS, CELL_CLASS));
    }
}
