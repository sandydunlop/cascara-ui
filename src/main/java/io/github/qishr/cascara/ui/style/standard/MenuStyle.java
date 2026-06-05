package io.github.qishr.cascara.ui.style.standard;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class MenuStyle extends ControlStyle {
    public MenuStyle() {
        super();
        defineRule(newRule() // This affects choice box and menubutotn menus too
            .addSelector (classSelector("menu"))
            .addSelector (classSelector("context-menu"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.MENU_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_INSETS, values(ZERO), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, values(px(4)), true)
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.MENU_BORDER)), false)
            .addDeclaration(FX_BORDER_RADIUS, values(px(4)), true)
            .addDeclaration(FX_BORDER_STYLE, values(SOLID), false)
            .addDeclaration(FX_PADDING, values(sides(em(.1),em(.1),em(.1),em(.1))), false)
            .addDeclaration(FX_EFFECT, values(dropshadow(color(ColorID.WIDGET_SHADOW))), false)
            .build()
        );


        // Menu Items

        defineRule(newRule()
            .addSelector (classSelector("menu-item"))
            .addSelector (classSelector("menu-bar > .container > .menu-button > .menu-item"))
            .addSelector (classSelector("menu-bar > .container > .menu-button > .menu"))
            .addSelector (classSelector("context-menu > .menu-item"))
            .addDeclaration(FX_BACKGROUND_RADIUS, values(px(4)), false)
            .addDeclaration(FX_BORDER_COLOR, values(TRANSPARENT), false)
            .addDeclaration(FX_BORDER_WIDTH, values(px(1)), false)
            .addDeclaration(FX_BORDER_RADIUS, values(px(4)), false)
            .addDeclaration(FX_EFFECT, values(NULL), false)
            .addDeclaration(FX_PADDING, values(sides(em(.2), em(.7), em(.2), em(.7))), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("menu-item > .label"))
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.MENU_FOREGROUND)), false)
            .addDeclaration(FX_PADDING, values(sides(ZERO, em(1), ZERO, ZERO)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("menu-item:hover"))
            .addSelector (classSelector("menu-item:selected"))
            .addSelector (classSelector("menu-item:focused"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.MENU_SELECTION_BACKGROUND)), false)
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.MENU_SELECTION_BORDER)), false)
            .addDeclaration(FX_BORDER_WIDTH, values(px(1)), false)
            .addDeclaration(FX_BORDER_RADIUS, values(px(4)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("menu-item:hover > .label"))
            .addSelector (classSelector("menu-bar > .container > .menu-button > .menu-item:hover > .label"))
            .addSelector (classSelector("menu-bar > .container > .menu-button > .menu:hover > .label"))
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.MENU_SELECTION_FOREGROUND)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("menu .accelerator-text"))
            .addSelector (classSelector("context-menu .accelerator-text"))
            .addDeclaration(FX_TEXT_FILL, values(derive(color(ColorID.MENU_FOREGROUND), -50)), true)
            .addDeclaration(FX_FONT_SIZE, values(em(.75)), false)
            .addDeclaration(FX_PADDING, values(sides(ZERO, ZERO, ZERO, ZERO)), false)
            .build()
        );


        // Menu Bar

        defineRule(newRule()
            .addSelector (classSelector("menu-bar"))
            .addDeclaration(FX_PADDING, values(sides(px(2), px(2), px(2), px(4))), false)
            .addDeclaration(FX_SPACING, values(ZERO), false)
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.MENU_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_INSETS, values(ZERO), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, values(ZERO), false)
            .build()
        );


        // Menu Bar Items

        defineRule(newRule()
            .addSelector (classSelector("menu-bar > .container > .menu-button"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(TRANSPARENT), false)
            .addDeclaration(FX_BACKGROUND_INSETS, values(ZERO), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, values(ZERO), false)
            .addDeclaration(FX_BORDER_COLOR, values(TRANSPARENT), false)
            .addDeclaration(FX_BORDER_WIDTH, values(px(1)), false)
            .addDeclaration(FX_BORDER_RADIUS, values(px(4)), false)
            .addDeclaration(FX_EFFECT, values(NULL), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("menu-bar > .container > .menu-button > .label"))
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.MENU_FOREGROUND)), false)
            .addDeclaration(FX_PADDING, values(sides(em(.1),em(.3),em(.1),em(.3))), false)
            .build()
        );

        // Unwanted arrow next to menu bar item
        defineRule(newRule()
            .addSelector (classSelector("menu-bar > .container > .menu-button > .arrow-button"))
            .addDeclaration(FX_PADDING, values(ZERO), false)
            .addDeclaration(VISIBILITY, values(COLLAPSE), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("menu-bar > .container > .menu-button > .arrow-button > .arrow"))
            .addDeclaration(FX_PADDING, values(ZERO), false)
            .addDeclaration(FX_SHAPE, values(NULL), false)
            .addDeclaration(VISIBILITY, values(COLLAPSE), false)
            .build()
        );

        // Hover

        defineRule(newRule()
            .addSelector (classSelector("menu-bar > .container > .menu-button:hover"))
            .addSelector (classSelector("menu-bar > .container > .menu-button:focused"))
            .addSelector (classSelector("menu-bar > .container > .menu-button:showing"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.MENU_SELECTION_BACKGROUND)), false)
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.MENU_SELECTION_BORDER)), false)
            .addDeclaration(FX_BORDER_WIDTH, values(px(1)), false)
            .addDeclaration(FX_BORDER_RADIUS, values(px(4)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("menu-bar > .container > .menu-button:hover > .label"))
            .addSelector (classSelector("menu-bar > .container > .menu-button:focused > .label"))
            .addSelector (classSelector("menu-bar > .container > .menu-button:showing > .label"))
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.MENU_SELECTION_FOREGROUND)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("menu-bar .menu-button .context-menu"))
            .addDeclaration(FX_TRANSLATE_X, literal("-3px"), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("menu-item .context-menu"))
            .addDeclaration(FX_TRANSLATE_Y, literal("-3px"), false)
            .addDeclaration(FX_TRANSLATE_X, values(ZERO), false)
            .build()
        );


        // Separators

        defineRule(newRule()
            .addSelector (classSelector("context-menu .separator"))
            .addDeclaration(FX_PADDING, values(sides(em(0.5), em(1), em(0.5), em(1))), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("context-menu .separator:horizontal .line"))
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.MENU_SEPARATOR_BACKGROUND), TRANSPARENT, TRANSPARENT, TRANSPARENT), false)
            .addDeclaration(FX_BORDER_INSETS, values(ZERO), false)
            .build()
        );


        // Arrows

        defineRule(newRule()
            .addSelector (classSelector("menu > .right-container > .arrow"))
            .addDeclaration(FX_PADDING, values(sides(em(.5), em(.2), em(.5), em(.2))), false)
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.MENU_FOREGROUND)), false)
            .addDeclaration(FX_SHAPE, shape("M0,-4L4,0L0,4Z"), false)
            .addDeclaration(FX_SCALE_SHAPE, values(FALSE), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("menu:focused > .right-container > .arrow"))
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.MENU_FOREGROUND)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("menu-up-arrow"))
            .addDeclaration(FX_PADDING, values(sides(em(.7), em(.4), ZERO, em(.4))), false)
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.MENU_FOREGROUND)), false)
            .addDeclaration(FX_SHAPE, shape("M0 1 L1 1 L.5 0 Z"), false)
            .addDeclaration(FX_EFFECT, literal("innershadow( two-pass-box , rgba(0,0,0,0.6) , 4, 0.0 , 0 , 1 )"), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("menu-down-arrow"))
            .addDeclaration(FX_PADDING, values(sides(em(.7),em(.4),ZERO,em(.4))), false)
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.MENU_FOREGROUND)), false)
            .addDeclaration(FX_SHAPE, shape("M0 0 L1 0 L.5 1 Z"), false)
            .addDeclaration(FX_EFFECT, literal("innershadow( two-pass-box , rgba(0,0,0,0.6) , 4, 0.0 , 0 , 1 )"), false)
            .build()
        );
    }
}
