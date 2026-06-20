package io.github.qishr.cascara.ui.style.part;

import io.github.qishr.cascara.ui.style.PartStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

/// Rules shared between ListView, TableView, TreeView, and TreeTableView
public class TabularRules extends PartStyle {
    public TabularRules(String controlClass) {
        super();
        this.controlClass = controlClass;

        defineRule(newRule()
            .addSelector (selector(""))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.SIDEBAR_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_INSETS, literal(ZERO), false)
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.SIDEBAR_BACKGROUND)), false)
            .addDeclaration(FX_BORDER_INSETS, literal(ZERO), false)
            .addDeclaration(FX_BORDER_RADIUS, literal(ZERO), false)
            .addDeclaration(FX_PADDING, values(ZERO), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("placeholder"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.SIDEBAR_BACKGROUND)), false)
            .addDeclaration(FX_OPACITY, literal(ZERO), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (childSelector(" .virtual-flow > .clipped-container > .sheet > .list-cell"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.SIDEBAR_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_INSETS, literal(ZERO), false)
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.SIDEBAR_BACKGROUND)), false)
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.SIDEBAR_FOREGROUND)), false)
            .build()
        );
    }
}
