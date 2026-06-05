package io.github.qishr.cascara.ui.style.standard;

import io.github.qishr.cascara.ui.style.ControlStyle;

public class SpinnerStyle extends ControlStyle {
    public SpinnerStyle() {
        super();
        defineRule(newRule()
            .addSelector (selector(""))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color()), false)
            .addDeclaration(FX_BACKGROUND_COLOR, literal(""), false)
            .build()
        );
    }
}
