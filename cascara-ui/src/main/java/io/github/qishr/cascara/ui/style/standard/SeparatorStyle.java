package io.github.qishr.cascara.ui.style.standard;

import io.github.qishr.cascara.ui.style.ControlStyle;

public class SeparatorStyle extends ControlStyle {
    public SeparatorStyle() {
        super();
        defineRule(newRule()
            .addSelector (selector(""))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color()), false)
            .build()
        );
    }
}
