package io.github.qishr.cascara.ui.style.standard;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class TabPaneStyle extends ControlStyle {
    public TabPaneStyle() {
        super();

        defineRule(newRule()
            .addSelector (classSelector("tab-pane"))
            .addDeclaration(FX_TAB_MIN_HEIGHT, values(em(1.5)), false)
            .addDeclaration(FX_TAB_MAX_HEIGHT, values(em(3)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-header-area"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.DOCAREA_HEADER_TABS_BACKGROUND)), false)
            .addDeclaration(FX_PADDING, literal(ZERO), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-header-area .headers-region"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.DOCAREA_HEADER_TABS_BACKGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-header-area .tab-header-background"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.DOCAREA_HEADER_TABS_BACKGROUND)), false)
            .build()
        );

        // Tabs

        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-header-area > .headers-region > .tab"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.TAB_INACTIVE_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_INSETS, values(ZERO), false)
            .addDeclaration(FX_BORDER_COLOR, values(sides(color(ColorID.TAB_INACTIVE_BACKGROUND), color(ColorID.TAB_INACTIVE_BACKGROUND), color(ColorID.TAB_INACTIVE_BACKGROUND), color(ColorID.TAB_INACTIVE_BACKGROUND))), false)
            .addDeclaration(FX_BORDER_WIDTH, values(px(1)), false)
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.TAB_INACTIVE_FOREGROUND)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-header-area > .headers-region > .tab:selected"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.TAB_ACTIVE_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_INSETS, values(ZERO), false)
            .addDeclaration(FX_BORDER_COLOR, values(sides(color(ColorID.TAB_SELECTED_BORDER_TOP), color(ColorID.TAB_ACTIVE_BACKGROUND), color(ColorID.TAB_ACTIVE_BORDER), color(ColorID.TAB_ACTIVE_BACKGROUND))), false)
            .addDeclaration(FX_BORDER_WIDTH, values(px(1)), false)
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.TAB_ACTIVE_FOREGROUND)), false)
            .build()
        );

        // Tab label

        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-header-area > .headers-region > .tab > .tab-container > .tab-label"))
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.TAB_INACTIVE_FOREGROUND)), false)
            .addDeclaration(FX_FONT_SIZE, values(em(1)), false)
            .addDeclaration(FX_PADDING, values(px(3)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-header-area > .headers-region > .tab:hover > .tab-container > .tab-label"))
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.ACCENT_BACKGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-header-area > .headers-region > .tab:selected > .tab-container > .tab-label"))
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.TAB_ACTIVE_FOREGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-header-area > .headers-region > .tab:selected:hover > .tab-container > .tab-label"))
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.TAB_ACTIVE_FOREGROUND)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("tab-pane:focused > .tab-header-area > .headers-region > .tab:selected .focus-indicator"))
            .addDeclaration(FX_BORDER_WIDTH, values(ZERO), false)
            .addDeclaration(FX_BORDER_COLOR, values(TRANSPARENT, TRANSPARENT), false)
            .build()
        );

        // More tabs button

        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-header-area > .control-buttons-tab > .container > .tab-down-button"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(TRANSPARENT), false)
            .addDeclaration(FX_BACKGROUND_INSETS, literal("0 0 4 0"), false)
            .addDeclaration(FX_PADDING, literal("4 8 9 8"), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, literal(ZERO), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-header-area > .control-buttons-tab > .container > .tab-down-button:hover"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_BACKGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-header-area > .control-buttons-tab > .container > .tab-down-button:pressed"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_BACKGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-header-area > .control-buttons-tab > .container > .tab-down-button > .arrow"))
            .addDeclaration(FX_PADDING, literal("0.08em"), false)
            .addDeclaration(FX_SCALE_SHAPE, literal(FALSE), false)
            .addDeclaration(FX_SHAPE, shape("M13.576,7.495c0,0.829-0.672,1.5-1.5,1.5c-0.828,0-1.5-0.672-1.5-1.5s0.672-1.5,1.5-1.5C12.904,5.995,13.576,6.667,13.576,7.495z M12.076,10.995c-0.828,0-1.5,0.672-1.5,1.5c0,0.829,0.672,1.5,1.5,1.5c0.828,0,1.5-0.672,1.5-1.5C13.576,11.667,12.904,10.995,12.076,10.995z M12.076,15.995c-0.828,0-1.5,0.672-1.5,1.5s0.672,1.5,1.5,1.5c0.828,0,1.5-0.672,1.5-1.5S12.904,15.995,12.076,15.995z"), false)
            .addDeclaration(FX_BACKGROUND_INSETS, literal(ZERO), false)
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_FOREGROUND)), false)
            .build()
        );





        // Tab pane inside tab pane

        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-content-area .tab-pane"))
            .addSelector (classSelector("tab-pane > .tab-content-area .tab-pane > .tab-header-area"))
            .addSelector (classSelector("tab-pane > .tab-content-area .tab-pane > .tab-header-area > .headers-region"))
            .addSelector (classSelector("tab-pane > .tab-content-area .tab-pane > .tab-header-area > .tab-header-background"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(TRANSPARENT), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-content-area .tab-pane > .tab-header-area .tab-header-background"))
            .addDeclaration(FX_BORDER_COLOR, values(TRANSPARENT, TRANSPARENT, derive(color(ColorID.FOREGROUND), -50),  TRANSPARENT), false)
            .addDeclaration(FX_BORDER_INSETS, literal("0 0 0 0"), false)
            .addDeclaration(FX_BORDER_WIDTH, literal("0 0 1 0"), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-content-area .tab-pane .tab"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.DOCUMENT_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_INSETS, literal("0"), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, literal("0"), false)
            .addDeclaration(FX_BORDER_COLOR, values(TRANSPARENT, TRANSPARENT, derive(color(ColorID.FOREGROUND), -35),  TRANSPARENT), false)
            .addDeclaration(FX_BORDER_WIDTH, literal("0 0 1 0"), false)
            .addDeclaration(FX_BORDER_RADIUS, literal("0 0 0 0"), false)
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.FOREGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-content-area .tab-pane .tab:selected"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.DOCUMENT_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_INSETS, literal("0 0 0 0"), false)
            .addDeclaration(FX_BORDER_COLOR, literal("transparent transparent -color-foreground transparent"), false)
            .addDeclaration(FX_BORDER_WIDTH, literal("0 0 1 0"), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-content-area .tab-pane > .tab-header-area > .headers-region > .tab > .tab-container > .tab-label"))
            .addDeclaration(FX_TEXT_FILL, values(derive(color(ColorID.FOREGROUND), -35)), false)
            .addDeclaration(FX_FONT_SIZE, values(em(0.8)), false)
            .addDeclaration(TEXT_TRANSFORM, values(UPPERCASE), false)
            .addDeclaration(FX_PADDING, values(px(3)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-content-area .tab-pane > .tab-header-area > .headers-region > .tab:hover > .tab-container > .tab-label"))
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.FOREGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-content-area .tab-pane > .tab-header-area > .headers-region > .tab:selected > .tab-container > .tab-label"))
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.FOREGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-content-area .tab-pane > .tab-header-area > .headers-region > .tab:selected:hover > .tab-container > .tab-label"))
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.FOREGROUND)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-content-area .tab-pane > .tab-content-area"))
            .addDeclaration(FX_PADDING, values(sides(px(16), ZERO, ZERO, ZERO)), false)
            .build()
        );
    }
}
