package io.github.qishr.cascara.ui.style.part;

import io.github.qishr.cascara.ui.style.PartStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class CellPseudoRules extends PartStyle {
    public CellPseudoRules(String controlClass, String cellClass) {
        super();
        this.controlClass = controlClass;

        // Selected

        defineRule(newRule()
            .addSelector (childSelector(".virtual-flow > .clipped-container > .sheet > ." + cellClass + ":filled:selected"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.LIST_SELECTION_INACTIVE_BACKGROUND)), false)
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.LIST_SELECTION_INACTIVE_BACKGROUND)), false)
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.LIST_SELECTION_INACTIVE_FOREGROUND)), false)
            .build()
        );


        // Focused & Selected

        defineRule(newRule()
            .addSelector (pseudoSelector("focused > .virtual-flow > .clipped-container > .sheet > ." + cellClass + ":filled:selected"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.LIST_SELECTION_ACTIVE_BACKGROUND)), false)
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.LIST_SELECTION_FOCUS_OUTLINE)), false)
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.LIST_SELECTION_ACTIVE_FOREGROUND)), false)
            .build()
        );


        // Hovered

        defineRule(newRule()
            .addSelector (childSelector(".virtual-flow > .clipped-container > .sheet > ." + cellClass + ":filled:hover"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.LIST_HOVER_BACKGROUND)), false)
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.LIST_HOVER_BACKGROUND)), false)
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.LIST_SELECTION_ACTIVE_FOREGROUND)), false)
            .build()
        );


        // Hovered & Focused & Selected

        defineRule(newRule()
            .addSelector (pseudoSelector("focused > .virtual-flow > .clipped-container > .sheet > ." + cellClass + ":filled:selected:hover"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.LIST_SELECTION_ACTIVE_BACKGROUND)), false)
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.LIST_SELECTION_FOCUS_OUTLINE)), false)
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.LIST_SELECTION_ACTIVE_FOREGROUND)), false)
            .build()
        );
    }
}
