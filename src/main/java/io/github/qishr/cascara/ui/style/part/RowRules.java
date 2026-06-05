package io.github.qishr.cascara.ui.style.part;

import io.github.qishr.cascara.ui.style.PartStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class RowRules extends PartStyle {
    public RowRules(String controlClass) {
        super();
        this.controlClass = controlClass;

        defineRule(newRule()
            .addSelector (selector(""))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.SIDEBAR_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_INSETS, literal(ZERO), false)
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.SIDEBAR_BACKGROUND)), false)
            .addDeclaration(FX_PADDING, literal("0"), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("text"))
            .addDeclaration(FX_FILL, values(color(ColorID.SIDEBAR_FOREGROUND)), false)
            .addDeclaration(FX_PADDING, literal("0"), false)
            .build()
        );
    }
}
