package io.github.qishr.cascara.ui.style.standard;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class ButtonStyle extends ControlStyle {
    public ButtonStyle() {
        super();

        defineRule(newRule()
            .addSelector (classSelector("button"))
            .addSelector (classSelector("toggle-button"))
            .addDeclaration(SHRINK_ANIMATE_ON_PRESS, literal(TRUE), false)
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_BACKGROUND)), false)
            .addDeclaration(FX_BORDER_COLOR, values(TRANSPARENT), false)
            .addDeclaration(FX_BORDER_WIDTH, values(px(1)), false)
            .addDeclaration(FX_FONT_SIZE, literal("12"), false)
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.BUTTON_FOREGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("button:hover"))
            .addSelector (classSelector("toggle-button:hover"))
            .addSelector (classSelector("button:focused:hover"))
            .addSelector (classSelector("toggle-button:focused:hover"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_HOVER_BACKGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("button:pressed"))
            .addSelector (classSelector("toggle-button:pressed"))
            // .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.BUTTON_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_BACKGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("button:disabled"))
            .addSelector (classSelector("button:default:disabled"))
            .addSelector (classSelector("toggle-button:disabled"))
            .addDeclaration(FX_OPACITY, literal("0.4"), false)
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_BACKGROUND)), false)
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.BUTTON_FOREGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("button:default"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_BACKGROUND)), false)
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.BUTTON_FOREGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("button:default:hover"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(derive(color(ColorID.BUTTON_BACKGROUND), -40)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("button:default:pressed "))
            .addDeclaration(FX_BACKGROUND_COLOR, values(derive(color(ColorID.BUTTON_BACKGROUND), -40)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("toggle-button:selected"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_BACKGROUND)), false)
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.BUTTON_FOREGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("toggle-button:selected:hover"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(derive(color(ColorID.BUTTON_BACKGROUND), -40)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("toggle-button:selected:pressed"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(derive(color(ColorID.BUTTON_BACKGROUND), -40)), false)
            .build()
        );
    }
}
