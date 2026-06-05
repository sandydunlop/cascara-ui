package io.github.qishr.cascara.ui.style.part;

import io.github.qishr.cascara.ui.style.PartStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class CellRules extends PartStyle {
    public CellRules(String controlClass) {
        super();
        this.controlClass = controlClass;

        defineRule(newRule()
            .addSelector (selector(""))
            .addSelector (pseudoSelector("last-visible"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.SIDEBAR_BACKGROUND)), false)
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.SIDEBAR_BACKGROUND)), false)
            .addDeclaration(FX_FILL, values(color(ColorID.SIDEBAR_FOREGROUND)), false)
            .addDeclaration(FX_FONT_SIZE, literal("1em"), false)
            .addDeclaration(FX_ALIGNMENT, literal("center-left"), false)
            .addDeclaration(FX_PADDING, literal("0"), false)
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.SIDEBAR_FOREGROUND)), false)
            .build()
        );
    }
}
