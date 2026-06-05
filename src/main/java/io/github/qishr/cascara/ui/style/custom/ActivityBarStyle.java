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








        defineRule(newRule()
            .addSelector (classSelector("cascara-activity-bar > .tab-pane"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.SIDEBAR_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_INSETS, values(ZERO), false)
            .addDeclaration(FX_PADDING, values(ZERO), false)
            .addDeclaration(FX_BORDER_WIDTH, values(ZERO), false)
            .addDeclaration(FX_BORDER_STYLE, values(NONE), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("cascara-activity-bar > .tab-header-area"))
            .addDeclaration(FX_PADDING, values(ZERO), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("cascara-activity-bar > .tab-header-background"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.ACTIVITYBAR_INACTIVE_BACKGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("cascara-activity-bar > .tab-pane > .tab-header-area > .headers-region > .tab"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.ACTIVITYBAR_INACTIVE_BACKGROUND), color(ColorID.ACTIVITYBAR_INACTIVE_BACKGROUND)), false)
            //     -fx-background-insets: 0 0 0 0, 0 0 0 0;
            .addDeclaration(FX_BACKGROUND_INSETS, values(sides(px(0),px(0),px(0),px(0)),sides(px(0),px(0),px(0),px(0))), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, values(ZERO), false)
            .addDeclaration(FX_PADDING, sides(px(8),px(8),px(8),px(8)), false)
            .addDeclaration(FX_BORDER_WIDTH, values(ZERO), false)
            .build()
        );


        defineRule(newRule()
            .addSelector (classSelector("cascara-activity-bar > .tab-pane > .tab-header-area > .headers-region > .tab:selected"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.ACTIVITYBAR_ACTIVE_BORDER), color(ColorID.ACTIVITYBAR_ACTIVE_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_INSETS, values(sides(px(0),px(0),px(0),px(0)),sides(px(3),px(0),px(0),px(0))), false)
            .addDeclaration(FX_BORDER_WIDTH, values(ZERO), false)
            .build()
        );


        defineRule(newRule()
            .addSelector (classSelector("cascara-activity-bar > .tab-pane > .tab-header-area > .tab-header-background"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.ACTIVITYBAR_INACTIVE_BACKGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("cascara-activity-bar > .tab-pane:focused .tab-header-area .headers-region .tab:selected .focus-indicator"))
            .addDeclaration(FX_BORDER_INSETS, sides(px(0),px(0),px(0),px(0)), false)
            .addDeclaration(FX_BORDER_WIDTH, values(ZERO), false)
            .addDeclaration(FX_BORDER_RADIUS, values(ZERO), false)
            .build()
        );



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






        // defineRule(newRule()
        //     .addSelector (classSelector("sidebar .accordion > .titled-pane"))
        //     .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.SIDEBAR_BACKGROUND)), false)
        //     .addDeclaration(FX_BACKGROUND_INSETS, values(ZERO), false)
        //     .addDeclaration(FX_BACKGROUND_RADIUS, values(ZERO), false)
        //     .addDeclaration(FX_BORDER_WIDTH, values(ZERO), false)
        //     // .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.SIDEBAR_BORDER)), false)
        //     .addDeclaration(FX_TEXT_FILL, values(color(ColorID.SIDEBAR_FOREGROUND)), false)
        //     .build()
        // );
        // defineRule(newRule()
        //     .addSelector (classSelector("sidebar .accordion > .titled-pane > .title"))
        //     .addDeclaration(FX_EFFECT, literal(NONE), false)
        //     .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.SIDEBAR_HEADER_BACKGROUND)), false)
        //     .addDeclaration(FX_BACKGROUND_INSETS, values(ZERO), false)
        //     .addDeclaration(FX_BACKGROUND_RADIUS, values(ZERO), false)
        //     .addDeclaration(FX_PADDING, sides(px(2), px(2), px(2), px(0)), false)
        //     .build()
        // );
        // defineRule(newRule()
        //     .addSelector (classSelector("sidebar .accordion > .titled-pane > .title > .text"))
        //     .addDeclaration(FX_FILL, values(color(ColorID.SIDEBAR_HEADER_FOREGROUND)), false)
        //     .addDeclaration(FX_FONT_SIZE, values(em(0.85)), false)
        //     .addDeclaration(FX_FONT_WEIGHT, values(BOLD), false)
        //     .addDeclaration(FX_PADDING, sides(px(0), px(0), px(0), px(0)), false)
        //     .build()
        // );
        // defineRule(newRule()
        //     .addSelector (classSelector("sidebar .accordion > .titled-pane > .content"))
        //     .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.SIDEBAR_BORDER)), false)
        //     .addDeclaration(FX_BORDER_INSETS, values(ZERO), false)
        //     .addDeclaration(FX_BORDER_WIDTH, values(ZERO), false)
        //     .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.SIDEBAR_BACKGROUND)), false)
        //     .addDeclaration(FX_PADDING, values(ZERO), false)
        //     .build()
        // );



        // defineRule(newRule()
        //     .addSelector (classSelector("sidebar .accordion > .titled-pane > .content .table-view"))
        //     .addSelector (classSelector("sidebar .accordion > .titled-pane > .content .tree-view"))
        //     .addSelector (classSelector("sidebar .accordion > .titled-pane > .content .tree-table-view"))
        //     .addDeclaration(FX_BORDER_WIDTH, values(ZERO), false)
        //     .addDeclaration(FX_PADDING, sides(ZERO, ZERO, ZERO, ZERO), false)
        //     .build()
        // );
        // defineRule(newRule()
        //     .addSelector (classSelector("sidebar .accordion > .titled-pane > .content  .tree-view .tree-cell > .tree-disclosure-node"))
        //     .addSelector (classSelector("sidebar .accordion > .titled-pane > .content  .tree-table-view .tree-table-row-cell > .tree-disclosure-node"))
        //     .addSelector (classSelector("sidebar .accordion > .titled-pane > .content  .tree-table-view > .virtual-flow > .clipped-container > .sheet > .tree-table-row-cell > .tree-disclosure-node > .arrow"))
        //     .addDeclaration(FX_BORDER_WIDTH, values(ZERO), false)
        //     .addDeclaration(FX_PADDING, sides(px(2), px(2), px(2), px(12)), false)
        //     .build()
        // );


        // defineRule(newRule()
        //     .addSelector (classSelector("sidebar .accordion > .titled-pane > .title > .arrow-button"))
        //     .addDeclaration(FX_BACKGROUND_INSETS, literal(ZERO), false)
        //     .addDeclaration(FX_BACKGROUND_RADIUS, literal(ZERO), false)
        //     .addDeclaration(FX_PADDING, sides(px(2), px(8), px(2), px(4)), false)
        //     .build()
        // );
        // defineRule(newRule()
        //     .addSelector (classSelector("sidebar .accordion > .titled-pane > .title > .arrow-button > .arrow"))
        //     .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.SIDEBAR_HEADER_FOREGROUND)), false)
        //     .addDeclaration(FX_SHAPE, shape(SHAPE_ARROW_DOWN), false)
        //     .addDeclaration(FX_EFFECT, values(NULL), false)
        //     .addDeclaration(FX_PADDING, sides(px(2), px(2), px(2), px(0)), false)
        //     .addDeclaration(FX_SCALE_SHAPE, values(FALSE), false)
        //     .build()
        // );
        // defineRule(newRule()
        //     .addSelector (classSelector("sidebar .accordion > .titled-pane:focused > .title > .arrow-button > .arrow"))
        //     .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.SIDEBAR_HEADER_FOREGROUND)), false)
        //     .addDeclaration(FX_BACKGROUND_INSETS, values(ZERO), false)
        //     .addDeclaration(FX_EFFECT, values(NULL), false)
        //     .build()
        // );
    }
}
