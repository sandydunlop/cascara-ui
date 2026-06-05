package io.github.qishr.cascara.ui.style.custom;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class DocumentTabHeaderStyle extends ControlStyle {
    public static final String DOCUMENT_TAB_HEADER = "document-tab-header";

    public DocumentTabHeaderStyle() {

        super();

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

        // defineRule(newRule()
        //     .addSelector (classSelector(DOCUMENT_TAB_HEADER + ":hover .tab-button"))
        //     .addDeclaration(FX_OPACITY, values("1"), true)
        //     .build()
        // );

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
