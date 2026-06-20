package io.github.qishr.cascara.ui.style.standard;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class HyperlinkStyle extends ControlStyle {
        public HyperlinkStyle() {
        super();

        defineRule(newRule()
            .addSelector (classSelector("hyperlink"))
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.LINK_ACTIVE_FOREGROUND)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("hyperlink:focused"))
            .addSelector (classSelector("hyperlink:hover"))
            .addDeclaration(FX_UNDERLINE, values(TRUE), false)
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.LINK_INACTIVE_FOREGROUND)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("hyperlink:disabled"))
            .addDeclaration(FX_OPACITY, literal("0.4"), false)
            .build()
        );
    }
}
