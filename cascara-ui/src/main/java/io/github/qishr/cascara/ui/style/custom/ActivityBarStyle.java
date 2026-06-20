package io.github.qishr.cascara.ui.style.custom;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class ActivityBarStyle extends ControlStyle {
    public ActivityBarStyle() {
        super();

        defineRule(newRule()
            .addSelector (classSelector("cascara-activity-bar > .cascara-activity-bar-background"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.SIDEBAR_BACKGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("cascara-activity-bar > .cascara-activity-bar-tabs"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.ACTIVITYBAR_INACTIVE_BACKGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("cascara-activity-bar > .cascara-activity-bar-buttons"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.ACTIVITYBAR_INACTIVE_BACKGROUND)), false)
            .build()
        );

        //
        // Icon
        //

        defineRule(newRule()
            .addSelector (classSelector("cascara-activity-bar .cascara-svg-icon:hover"))
            .addDeclaration(FX_CURSOR, values(CURSOR_HAND), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("cascara-activity-bar .cascara-svg-icon .cascara-svg-stroke"))
            .addDeclaration(FX_STROKE, values(color(ColorID.ACTIVITYBAR_INACTIVE_FOREGROUND)), true)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("cascara-activity-bar .cascara-svg-icon:hover .cascara-svg-stroke"))
            .addDeclaration(FX_STROKE, values(color(ColorID.ACTIVITYBAR_ACTIVE_FOREGROUND)), true)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("cascara-activity-bar .cascara-svg-icon .cascara-svg-fill"))
            .addDeclaration(FX_FILL, values(color(ColorID.ACTIVITYBAR_INACTIVE_FOREGROUND)), true)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("cascara-activity-bar .cascara-svg-icon:hover .cascara-svg-fill"))
            .addDeclaration(FX_FILL, values(color(ColorID.ACTIVITYBAR_ACTIVE_FOREGROUND)), true)
            .build()
        );

        //
        // Tab Pane
        //

        defineRule(newRule()
            .addSelector (classSelector("svg-icon-tab-pane"))
            .addSelector (classSelector("svg-icon-tab-pane > .tab-header-area"))
            .addSelector (classSelector("svg-icon-tab-pane > .tab-header-area > .headers-region"))
            .addSelector (classSelector("svg-icon-tab-pane > .tab-header-area > .tab-header-background"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.SIDEBAR_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_INSETS, values(ZERO), false)
            .addDeclaration(FX_PADDING, values(ZERO), false)
            .addDeclaration(FX_BORDER_WIDTH, values(ZERO), false)
            .addDeclaration(FX_BORDER_STYLE, values(NONE), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("svg-icon-tab-pane > .tab-header-area"))
            .addDeclaration(FX_PADDING, values(ZERO), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("svg-icon-tab-pane > .tab-header-area > .tab-header-background"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.ACTIVITYBAR_INACTIVE_BACKGROUND)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("svg-icon-tab-pane > .tab-header-area > .headers-region > .tab"))
            .addSelector (classSelector("svg-icon-tab-pane > .tab-header-area > .headers-region > .tab:top"))
            .addSelector (classSelector("svg-icon-tab-pane > .tab-header-area > .headers-region > .tab:right"))
            .addSelector (classSelector("svg-icon-tab-pane > .tab-header-area > .headers-region > .tab:bottom"))
            .addSelector (classSelector("svg-icon-tab-pane > .tab-header-area > .headers-region > .tab:left"))
            .addSelector (classSelector("svg-icon-tab-pane:focused > .tab-header-area > .headers-region > .tab"))
            .addSelector (classSelector("svg-icon-tab-pane:focused > .tab-header-area > .headers-region > .tab:top"))
            .addSelector (classSelector("svg-icon-tab-pane:focused > .tab-header-area > .headers-region > .tab:right"))
            .addSelector (classSelector("svg-icon-tab-pane:focused > .tab-header-area > .headers-region > .tab:bottom"))
            .addSelector (classSelector("svg-icon-tab-pane:focused > .tab-header-area > .headers-region > .tab:right"))

            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.ACTIVITYBAR_INACTIVE_BACKGROUND), color(ColorID.ACTIVITYBAR_INACTIVE_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_INSETS, values(sides(px(0),px(0),px(0),px(0)),sides(px(0),px(0),px(0),px(0))), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, values(ZERO), false)
            .addDeclaration(FX_PADDING, sides(px(12),px(8),px(12),px(8)), false)
            .addDeclaration(FX_BORDER_WIDTH, values(ZERO), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("svg-icon-tab-pane > .tab-header-area > .headers-region > .tab:selected"))
            .addSelector (classSelector("svg-icon-tab-pane > .tab-header-area > .headers-region > .tab:top:selected"))
            .addSelector (classSelector("svg-icon-tab-pane > .tab-header-area > .headers-region > .tab:right:selected"))
            .addSelector (classSelector("svg-icon-tab-pane > .tab-header-area > .headers-region > .tab:left:selected"))
            .addSelector (classSelector("svg-icon-tab-pane > .tab-header-area > .headers-region > .tab:bottom:selected"))
            .addSelector (classSelector("svg-icon-tab-pane:focused > .tab-header-area > .headers-region > .tab:selected"))
            .addSelector (classSelector("svg-icon-tab-pane:focused > .tab-header-area > .headers-region > .tab:top:selected"))
            .addSelector (classSelector("svg-icon-tab-pane:focused > .tab-header-area > .headers-region > .tab:right:selected"))
            .addSelector (classSelector("svg-icon-tab-pane:focused > .tab-header-area > .headers-region > .tab:left:selected"))
            .addSelector (classSelector("svg-icon-tab-pane:focused > .tab-header-area > .headers-region > .tab:bottom:selected"))

            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.ACTIVITYBAR_ACTIVE_BORDER), color(ColorID.ACTIVITYBAR_ACTIVE_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_INSETS, values(sides(px(0),px(0),px(0),px(0)), sides(px(3),px(0),px(0),px(0))), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, values(ZERO), false)
            .addDeclaration(FX_PADDING, sides(px(12),px(8),px(12),px(8)), false)
            .addDeclaration(FX_BORDER_WIDTH, values(ZERO), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("svg-icon-tab-pane > .tab-header-area > .headers-region > .tab:selected .focus-indicator"))
            .addSelector (classSelector("svg-icon-tab-pane > .tab-header-area > .headers-region > .tab:top:selected .focus-indicator"))
            .addSelector (classSelector("svg-icon-tab-pane > .tab-header-area > .headers-region > .tab:right:selected .focus-indicator"))
            .addSelector (classSelector("svg-icon-tab-pane > .tab-header-area > .headers-region > .tab:left:selected .focus-indicator"))
            .addSelector (classSelector("svg-icon-tab-pane > .tab-header-area > .headers-region > .tab:bottom:selected .focus-indicator"))
            .addSelector (classSelector("svg-icon-tab-pane:focused > .tab-header-area > .headers-region > .tab:selected .focus-indicator"))
            .addSelector (classSelector("svg-icon-tab-pane:focused > .tab-header-area > .headers-region > .tab:top:selected .focus-indicator"))
            .addSelector (classSelector("svg-icon-tab-pane:focused > .tab-header-area > .headers-region > .tab:right:selected .focus-indicator"))
            .addSelector (classSelector("svg-icon-tab-pane:focused > .tab-header-area > .headers-region > .tab:left:selected .focus-indicator"))
            .addSelector (classSelector("svg-icon-tab-pane:focused > .tab-header-area > .headers-region > .tab:bottom:selected .focus-indicator"))

            .addDeclaration(FX_BORDER_INSETS, sides(px(0),px(0),px(0),px(0)), false)
            .addDeclaration(FX_BORDER_WIDTH, values(ZERO), false)
            .addDeclaration(FX_BORDER_RADIUS, values(ZERO), false)
            .addDeclaration(FX_PADDING, sides(px(12),px(8),px(12),px(8)), false)
            .addDeclaration(FX_BORDER_COLOR, values(TRANSPARENT), false)
            .build()
        );

        //
        // Label
        //

        defineRule(newRule()
            .addSelector (classSelector("svg-icon-tab-pane > .tab-header-area > .headers-region > .tab > .tab-container > .tab-label"))
            // .addDeclaration(FX_PADDING, values(px(8)), false)
            // .addDeclaration(FX_PADDING, sides(4,16,4,16), false)
            .addDeclaration(FX_PADDING, values(px(2)), false)
            // .addDeclaration(FX_PADDING, values(px(4)), false)
            // .addDeclaration(FX_MIN_HEIGHT, values(px(48)), false)
            .build()
        );

        //
        // Content
        //

        defineRule(newRule()
            .addSelector (classSelector("svg-icon-tab-pane > .tab-content-area"))
            .addDeclaration(FX_PADDING, values(sides(ZERO, ZERO, ZERO, ZERO)), false)
            .build()
        );

        //
        // Badges
        //

        defineRule(newRule()
            .addSelector (classSelector("mini-progress-badge"))
            .addDeclaration(FX_PROGRESS_COLOR, values(color(ColorID.ACCENT_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_INSETS, values(ZERO), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("mini-progress-badge .indicator"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(TRANSPARENT), false)
            .addDeclaration(FX_PADDING, values(ZERO), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("mini-progress-badge .percentage"))
            .addDeclaration(FX_FILL, values(TRANSPARENT), false)
            .addDeclaration(FX_FONT_SIZE, values(ZERO), false)
            .build()
        );
    }
}
