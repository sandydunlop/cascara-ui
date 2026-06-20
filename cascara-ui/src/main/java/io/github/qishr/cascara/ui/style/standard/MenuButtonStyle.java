package io.github.qishr.cascara.ui.style.standard;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class MenuButtonStyle extends ControlStyle {
    public MenuButtonStyle() {
        super();
        defineRule(newRule()
            .addSelector (classSelector("menu-button"))
            .addDeclaration(SHRINK_ANIMATE_ON_PRESS, literal(TRUE), false)
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_BACKGROUND)), false)
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.BUTTON_FOREGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("menu-button > .label"))
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.BUTTON_FOREGROUND)), false)
            .addDeclaration(FX_FONT_SIZE, literal("1em"), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("menu-button > .arrow-button"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(TRANSPARENT), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("menu-button > .arrow-button > .arrow"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_FOREGROUND)), false)
            .addDeclaration(FX_SHAPE, shape(SHAPE_ARROW_DOWN), false)
            .build()
        );


        // Hover

        defineRule(newRule()
            .addSelector (classSelector("menu-button:hover"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_HOVER_BACKGROUND)), false)
            // .addDeclaration(FX_BORDER_COLOR, expression(color(Palette.MENU_SELECTION_BORDER)), false)
            .build()
        );


        // Disabled

        defineRule(newRule()
            .addSelector (classSelector("menu-button:disabled"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(derive(color(ColorID.BUTTON_BACKGROUND), -50)), false)
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.BUTTON_FOREGROUND)), false)
            .addDeclaration(FX_OPACITY, literal("0.4"), false)
            .build()
        );
    }
}
