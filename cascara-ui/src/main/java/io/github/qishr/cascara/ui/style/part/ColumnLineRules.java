package io.github.qishr.cascara.ui.style.part;

import io.github.qishr.cascara.ui.style.PartStyle;

public class ColumnLineRules extends PartStyle {
    public ColumnLineRules(String controlClass) {
        super();
        this.controlClass = controlClass;
        defineRule(newRule()
            .addSelector (classSelector("column-grid-lines > .virtual-flow > .clipped-container > .sheet > .table-row-cell .table-cell"))
            .addDeclaration(FX_BORDER_COLOR, literal("transparent, inputBorder, transparent, transparent"), false)
            .addDeclaration(FX_BORDER_WIDTH, literal("1px"), false)
            .addDeclaration(FX_BORDER_INSETS, literal("0 0 0 0"), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("column-grid-lines > .column-header-background > .nested-column-header > .nested-column-header > .column-header.table-column"))
            .addSelector (classSelector("column-grid-lines > .column-header-background > .nested-column-header > .column-header.table-column"))
            .addDeclaration(FX_BORDER_COLOR, literal("inputBorder, inputBorder, transparent, transparent"), false)
            .addDeclaration(FX_BORDER_WIDTH, literal("1px"), false)
            .addDeclaration(FX_BORDER_INSETS, literal("-1 0 0 0"), false)
            .build()
        );
    }
}
