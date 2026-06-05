package io.github.qishr.cascara.ui.style.custom;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class CodeAreaStyle extends ControlStyle {
    public CodeAreaStyle() {
        super();

        defineRule(newRule()
            .addSelector (classSelector("default-text"))
            .addDeclaration(FX_FILL, values(color(ColorID.TEXT_FOREGROUND)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("code-area .diag-error"))
            .addDeclaration(RTFX_UNDERLINE_COLOR, values(color(ColorID.EDITOR_ERROR_FOREGROUND)), true)
            .addDeclaration(RTFX_UNDERLINE_WIDTH, values(px(2)), true)
            .addDeclaration(RTFX_UNDERLINE_DASH_ARRAY, literal("2 2"), true)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("code-area .diag-warning"))
            .addDeclaration(RTFX_UNDERLINE_COLOR, values(color(ColorID.EDITOR_WARN_FOREGROUND)), true)
            .addDeclaration(RTFX_UNDERLINE_WIDTH, values(px(2)), true)
            .addDeclaration(RTFX_UNDERLINE_DASH_ARRAY, literal("4 4"), true)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("code-area .diag-info"))
            .addDeclaration(RTFX_UNDERLINE_COLOR, values(color(ColorID.EDITOR_INFO_FOREGROUND)), true)
            .addDeclaration(RTFX_UNDERLINE_WIDTH, values(px(2)), true)
            .addDeclaration(RTFX_UNDERLINE_DASH_ARRAY, literal("1 3"), true)
            .build()
        );
    }
}
