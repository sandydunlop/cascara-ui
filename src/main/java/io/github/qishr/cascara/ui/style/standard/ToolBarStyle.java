package io.github.qishr.cascara.ui.style.standard;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class ToolBarStyle extends ControlStyle {
    public ToolBarStyle() {
        super();
        defineRule(newRule()
            .addSelector (classSelector("tool-bar"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.CONTROL_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_INSETS, values(ZERO), false)
            .addDeclaration(FX_BORDER_INSETS, values(ZERO), false)
            .addDeclaration(FX_BORDER_WIDTH, values(ZERO), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("toolbar-separator"))
            .addDeclaration(FX_SEPARATOR_STROKE, literal("red"), false)
            .build()
        );

        // Buttons

        defineRule(newRule()
            .addSelector (classSelector("tool-bar .button"))
            .addSelector (classSelector("tool-bar .toggle-button"))
            .addSelector (classSelector("tool-bar .menu-button"))
            .addSelector (classSelector("tool-bar .split-menu-button, .tool-bar .split-menu-button > .label, .tool-bar .split-menu-button > .arrow-button"))
            .addDeclaration(FX_BACKGROUND_COLOR, literal(TRANSPARENT), false)
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.ACTIVITYBAR_INACTIVE_FOREGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("tool-bar .button:hover"))
            .addSelector (classSelector("tool-bar .toggle-button:hover"))
            .addSelector (classSelector("tool-bar .menu-button:hover"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_BACKGROUND)), false)
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.BUTTON_FOREGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("tool-bar .button:hover"))
            .addSelector (classSelector("tool-bar .toggle-button:hover"))
            .addDeclaration(FX_BORDER_WIDTH, literal(ZERO), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("tool-bar .split-menu-button:hover > .label"))
            .addSelector (classSelector("tool-bar .split-menu-button:hover > .arrow-button"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_BACKGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("tool-bar .split-menu-button > .label:hover"))
            .addDeclaration(FX_BORDER_COLOR, literal(TRANSPARENT), false)
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_BACKGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("tool-bar .split-menu-button > .arrow-button:hover"))
            .addDeclaration(FX_BORDER_COLOR, literal(TRANSPARENT), false)
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_BACKGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("tool-bar .button:pressed"))
            .addSelector (classSelector("tool-bar .toggle-button:pressed"))
            .addSelector (classSelector("tool-bar .menu-button:pressed"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_BACKGROUND)), false)
            .build()
        );

        // Toggle Buttons

        defineRule(newRule()
            .addSelector (classSelector("tool-bar .toggle-button:selected"))
            .addDeclaration(FX_BACKGROUND_COLOR, literal(TRANSPARENT), false)
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.ACTIVITYBAR_INACTIVE_FOREGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("tool-bar .toggle-button:selected:hover"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(derive(color(ColorID.BUTTON_BACKGROUND), 40)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("tool-bar .toggle-button:selected:pressed"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(derive(color(ColorID.BUTTON_BACKGROUND), 40)), false)
            .build()
        );

        // Menu Buttons

        defineRule(newRule()
            .addSelector (classSelector("tool-bar .menu-button"))
            .addDeclaration(FX_BORDER_COLOR, literal(TRANSPARENT), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("tool-bar .menu-button:showing"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_BACKGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("tool-bar .split-menu-button > .label"))
            .addDeclaration(FX_PADDING, literal("0.333333em 0.333333em 0.333333em 0.5em"), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("tool-bar .split-menu-button > .arrow-button"))
            .addDeclaration(FX_PADDING, literal("0.5em 0.583333em 0.5em 0.333333em"), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("tool-bar .split-menu-button:showing > .arrow-button"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_BACKGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("tool-bar .split-menu-button:showing > .label"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_BACKGROUND)), false)
            .build()
        );
    }
}
