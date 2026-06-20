package io.github.qishr.cascara.ui.style.custom;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class CustomStyle extends ControlStyle {
    public CustomStyle() {
        super();
        defineRule(newRule()
            .addSelector (classSelector("code-area"))
            .addDeclaration(FX_FONT_SIZE, literal("14pt"), false)
            .addDeclaration(FX_FILL, values(color(ColorID.EDITOR_FOREGROUND)), false)
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.EDITOR_BACKGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("lineno"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.EDITOR_BACKGROUND)), false)
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.EDITOR_FOREGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("caret"))
            .addDeclaration(FX_STROKE, values(color(ColorID.EDITOR_FOREGROUND)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("default-text"))
            .addDeclaration(FX_FILL, values(color(ColorID.FOREGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("empty-background"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(mapped(ColorID.EMPTY_BACKGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("cascara-document"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(mapped(ColorID.DOCUMENT_BACKGROUND)), false)
            .addDeclaration(FX_TEXT_FILL, values(mapped(ColorID.TEXT_FOREGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("cascara-stage-pane"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(TRANSPARENT), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("cascara-stage-root"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.CONTROL_BACKGROUND)), false)
            .build()
        );

        // cascara://organizer/CASC-00025001 : automatically decide if border is shown or define it in theitlebar theme
        defineRule(newRule()
            .addSelector (classSelector("cascara-stage-pane .cascara-window-layout"))
            // .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.TITLEBAR_ACTIVE_BACKGROUND)), false)
            .addDeclaration(FX_BORDER_WIDTH, values(ZERO), false)
            .build()
        );
        // defineRule(newRule()
        //     .addSelector (classSelector("cascara-stage-pane:inactive .cascara-window-layout"))
        //     .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.TITLEBAR_INACTIVE_BACKGROUND)), false)
        //     .build()
        // );

        defineRule(newRule()
            .addSelector (classSelector("gear-busy"))
            // .addDeclaration(FX_FILL, values(color()), false)
            .addDeclaration(FX_EFFECT, literal("dropshadow(three-pass-box, -color-control-foreground, 10, 0, 0, 0)"), false)
            .build()
        );

        //
        // Search Results
        //

        defineRule(newRule()
            .addSelector (classSelector("search-match"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(ColorID.EDITOR_MATCH_HIGHLIGHT_BACKGROUND), false)
            .addDeclaration(FX_TEXT_FILL, values(ColorID.EDITOR_MATCH_HIGHLIGHT_FOREGROUND), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("search-matches"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(ColorID.EDITOR_MATCH_BACKGROUND), false)
            .addDeclaration(FX_TEXT_FILL, values(ColorID.EDITOR_MATCH_FOREGROUND), false)
            .build()
        );

    }
}
