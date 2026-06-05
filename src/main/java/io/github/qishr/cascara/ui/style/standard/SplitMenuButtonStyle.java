package io.github.qishr.cascara.ui.style.standard;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class SplitMenuButtonStyle extends ControlStyle {
    public SplitMenuButtonStyle() {
        super();

        defineRule(newRule()
            .addSelector (classSelector("split-menu-button"))
            .addDeclaration(SHRINK_ANIMATE_ON_PRESS, literal(TRUE), false)
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_BACKGROUND)), false)
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.BUTTON_FOREGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("split-menu-button > .label"))
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.BUTTON_FOREGROUND)), false)
            .addDeclaration(FX_FONT_SIZE, literal("1em"), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("split-menu-button > .arrow-button"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(TRANSPARENT), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("split-menu-button > .arrow-button > .arrow"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_FOREGROUND)), false)
            .addDeclaration(FX_SHAPE, shape(SHAPE_ARROW_DOWN), false)
            .build()
        );



        // Hpver

        defineRule(newRule()
            .addSelector (classSelector("split-menu-button > .arrow-button:hover"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_HOVER_BACKGROUND)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("split-menu-button:hover"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_HOVER_BACKGROUND)), false)
            .build()
        );




        // Disabled

        defineRule(newRule()
            .addSelector (classSelector("split-menu-button:disabled"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_BACKGROUND)), false)
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.BUTTON_FOREGROUND)), false)
            .addDeclaration(FX_OPACITY, literal("0.4"), false)
            .build()
        );
    }
}
