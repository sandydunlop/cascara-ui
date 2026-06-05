package io.github.qishr.cascara.ui.style.custom;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class FormStyle extends ControlStyle {
    public static final String INPUT_ERROR = "input-error";

    public FormStyle() {
        super();

        defineRule(newRule()
            .addSelector (classSelector(INPUT_ERROR))
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.ERROR_FOREGROUND)), true)
            .addDeclaration(FX_BORDER_WIDTH, values(px(1)), true)
            .build()
        );
    }
}
