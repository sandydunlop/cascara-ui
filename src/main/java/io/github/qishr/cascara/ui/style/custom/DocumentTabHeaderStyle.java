package io.github.qishr.cascara.ui.style.custom;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class DocumentTabHeaderStyle extends ControlStyle {
    public static final String DOCUMENT_TAB_HEADER = "doc-tab-header";
    public static final String DOCUMENT_TAB_PANE = "doc-tab-pane";

    public DocumentTabHeaderStyle() {
        super();

        defineRule(newRule()
            .addSelector (classSelector("doc-tab-pane"))
            .addDeclaration(FX_TAB_MIN_HEIGHT, values(em(1.5)), false)
            .addDeclaration(FX_TAB_MAX_HEIGHT, values(em(3)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("doc-tab-pane > .tab-header-area"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.DOCAREA_HEADER_TABS_BACKGROUND)), false)
            .addDeclaration(FX_PADDING, literal(ZERO), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("doc-tab-pane > .tab-header-area .headers-region"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.DOCAREA_HEADER_TABS_BACKGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("doc-tab-pane > .tab-header-area .tab-header-background"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.DOCAREA_HEADER_TABS_BACKGROUND)), false)
            .build()
        );

        // Tabs

        defineRule(newRule()
            .addSelector (classSelector("doc-tab-pane > .tab-header-area > .headers-region > .tab"))
            .addSelector (classSelector("doc-tab-pane > .tab-header-area > .headers-region > .tab:top"))
            .addSelector (classSelector("doc-tab-pane > .tab-header-area > .headers-region > .tab:right"))
            .addSelector (classSelector("doc-tab-pane > .tab-header-area > .headers-region > .tab:bottom"))
            .addSelector (classSelector("doc-tab-pane > .tab-header-area > .headers-region > .tab:left"))
            .addSelector (classSelector("doc-tab-pane:focused > .tab-header-area > .headers-region > .tab"))
            .addSelector (classSelector("doc-tab-pane:focused > .tab-header-area > .headers-region > .tab:top"))
            .addSelector (classSelector("doc-tab-pane:focused > .tab-header-area > .headers-region > .tab:right"))
            .addSelector (classSelector("doc-tab-pane:focused > .tab-header-area > .headers-region > .tab:bottom"))
            .addSelector (classSelector("doc-tab-pane:focused > .tab-header-area > .headers-region > .tab:right"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.TAB_INACTIVE_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_INSETS, values(ZERO), false)
            .addDeclaration(FX_BORDER_COLOR, values(sides(color(ColorID.TAB_INACTIVE_BACKGROUND), color(ColorID.TAB_INACTIVE_BACKGROUND), color(ColorID.TAB_INACTIVE_BACKGROUND), color(ColorID.TAB_INACTIVE_BACKGROUND))), false)
            .addDeclaration(FX_BORDER_WIDTH, values(px(1)), false)
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.TAB_INACTIVE_FOREGROUND)), false)
            // .addDeclaration(FX_PADDING, sides(0,8,0,8), false)
            .addDeclaration(FX_PADDING, sides(0,0,0,0), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("doc-tab-pane > .tab-header-area > .headers-region > .tab:selected"))
            .addSelector (classSelector("doc-tab-pane > .tab-header-area > .headers-region > .tab:top:selected"))
            .addSelector (classSelector("doc-tab-pane > .tab-header-area > .headers-region > .tab:right:selected"))
            .addSelector (classSelector("doc-tab-pane > .tab-header-area > .headers-region > .tab:left:selected"))
            .addSelector (classSelector("doc-tab-pane > .tab-header-area > .headers-region > .tab:bottom:selected"))
            .addSelector (classSelector("doc-tab-pane:focused > .tab-header-area > .headers-region > .tab:selected"))
            .addSelector (classSelector("doc-tab-pane:focused > .tab-header-area > .headers-region > .tab:top:selected"))
            .addSelector (classSelector("doc-tab-pane:focused > .tab-header-area > .headers-region > .tab:right:selected"))
            .addSelector (classSelector("doc-tab-pane:focused > .tab-header-area > .headers-region > .tab:left:selected"))
            .addSelector (classSelector("doc-tab-pane:focused > .tab-header-area > .headers-region > .tab:bottom:selected"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.TAB_ACTIVE_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_INSETS, values(ZERO), false)
            .addDeclaration(FX_BORDER_COLOR, values(sides(color(ColorID.TAB_SELECTED_BORDER_TOP), color(ColorID.TAB_ACTIVE_BACKGROUND), color(ColorID.TAB_ACTIVE_BORDER), color(ColorID.TAB_ACTIVE_BACKGROUND))), false)
            .addDeclaration(FX_BORDER_WIDTH, values(px(1)), false)
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.TAB_ACTIVE_FOREGROUND)), false)
            .addDeclaration(FX_PADDING, sides(0,0,0,0), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector(DOCUMENT_TAB_HEADER))
            .addDeclaration(FX_PADDING, sides(6,10,6,6), false)
            .build()
        );

        //
        // Button
        //

        defineRule(newRule()
            .addSelector (classSelector(DOCUMENT_TAB_HEADER + " .tab-button"))
            .addSelector (classSelector(DOCUMENT_TAB_HEADER + " .tab-button:pressed"))
            .addDeclaration(FX_MAX_HEIGHT, values(px(15)), false)
            .addDeclaration(FX_MAX_WIDTH, values(px(15)), false)
            .addDeclaration(FX_MIN_HEIGHT, values(px(15)), false)
            .addDeclaration(FX_MIN_WIDTH, values(px(15)), false)
            .addDeclaration(FX_PADDING, values(px(2)), false)
            .addDeclaration(FX_BACKGROUND_COLOR, values(TRANSPARENT), true)
            .addDeclaration(FX_BORDER_WIDTH, values(ZERO), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector(DOCUMENT_TAB_HEADER + " .cascara-svg-icon .cascara-svg-stroke"))
            .addSelector (classSelector(DOCUMENT_TAB_HEADER + " .cascara-svg-icon .cascara-svg-fill"))
            .addDeclaration(FX_STROKE, values(color(ColorID.CONTROL_FOREGROUND)), true)
            .addDeclaration(FX_FILL, values(color(ColorID.CONTROL_FOREGROUND)), true)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector(DOCUMENT_TAB_HEADER + " .cascara-svg-icon:hover .cascara-svg-stroke"))
            .addSelector (classSelector(DOCUMENT_TAB_HEADER + " .cascara-svg-icon:hover .cascara-svg-fill"))
            .addDeclaration(FX_STROKE, values(color(ColorID.BUTTON_FOREGROUND)), true)
            .addDeclaration(FX_FILL, values(color(ColorID.BUTTON_FOREGROUND)), true)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector(DOCUMENT_TAB_HEADER + " .tab-button:hover"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_HOVER_BACKGROUND)), true)
            .build()
        );

        //
        // Label
        //

        defineRule(newRule()
            .addSelector (classSelector(DOCUMENT_TAB_HEADER + " .tab-label"))
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.TAB_INACTIVE_FOREGROUND)), true)
            .addDeclaration(FX_PADDING, sides(2, 0, 2, 0), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector(DOCUMENT_TAB_HEADER + ":active .tab-label"))
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.TAB_ACTIVE_FOREGROUND)), true)
            .addDeclaration(FX_PADDING, sides(2, 0, 2, 0), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector(DOCUMENT_TAB_HEADER + ":transient .tab-label"))
            .addDeclaration(FX_FONT_STYLE, values(ITALIC), true)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector(DOCUMENT_TAB_HEADER + ":gitmodified .tab-label"))
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.GIT_MODIFIED_FOREGROUND)), true)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector(DOCUMENT_TAB_HEADER + ":gituntracked .tab-label"))
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.GIT_UNTRACKED_FOREGROUND)), true)
            .build()
        );

    }
}
