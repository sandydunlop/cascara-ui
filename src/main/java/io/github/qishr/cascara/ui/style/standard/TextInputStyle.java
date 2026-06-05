package io.github.qishr.cascara.ui.style.standard;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class TextInputStyle extends ControlStyle {
    public TextInputStyle() {
        super();
        defineRule(newRule()
            .addSelector (classSelector("text-input"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.INPUT_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_INSETS, values(ZERO), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, values(px(2)), false)
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.INPUT_BORDER)), false)
            .addDeclaration(FX_BORDER_WIDTH, values(px(1)), false)
            .addDeclaration(FX_BORDER_RADIUS, values(px(2)), false)

            // .addDeclaration(FX_BACKGROUND_RADIUS, literal("2,2"), false)
            // .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.INPUT_BORDER),color(ColorID.INPUT_BACKGROUND)), false)
            // .addDeclaration(FX_BACKGROUND_INSETS, literal("0,1"), false)

            // .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.INPUT_BORDER)), false)
            // .addDeclaration(FX_BORDER_WIDTH, values(px(1)), false)

            // .addDeclaration(FX_BORDER_WIDTH, values(px(0)), false)
            .addDeclaration(FX_PROMPT_TEXT_FILL, values(color(ColorID.INPUT_PLACEHOLDER_FOREGROUND)), false)
            // .addDeclaration(FX_HIGHLIGHT_FILL, values(color(ColorID.EDITOR_SELECTION_ACTIVE_BACKGROUND)), false)
            .addDeclaration(FX_HIGHLIGHT_TEXT_FILL, values(color(ColorID.EDITOR_SELECTION_FOREGROUND)), false)
            .addDeclaration(FX_FONT_SIZE, literal("1em"), false)
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.INPUT_FOREGROUND)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("text-input:hover"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.INPUT_BACKGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("text-input:focused"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.INPUT_BACKGROUND)), false)
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.INPUT_FOREGROUND)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("text-input:disabled"))
            .addDeclaration(FX_OPACITY, literal("1"), false)
            // .addDeclaration(FX_BACKGROUND_INSETS, literal("0,1"), false)
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.INPUT_BACKGROUND)), false)
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.INPUT_FOREGROUND)), false)
            .addDeclaration(FX_PROMPT_TEXT_FILL, values(color(ColorID.INPUT_PLACEHOLDER_FOREGROUND)), false)
            .build()
        );

        // Text Field
        defineRule(newRule()
            .addSelector (classSelector("text-field > .right-button > .right-button-graphic"))
            .addDeclaration(FX_SHAPE, shape("M221.738,305.873l6.135,6.16l-2.875,2.863l-6.135-6.159l-6.263,6.237l-2.864-2.875l6.263-6.238l-6.177-6.202l2.875-2.863l6.177,6.201l6.244-6.22l2.864,2.876L221.738,305.873z"), false)
            .addDeclaration(FX_PADDING, literal("0.416667em"), false)
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.ACTIVITYBAR_ACTIVE_BACKGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("text-field > .right-button"))
            .addDeclaration(FX_CURSOR, values(CURSOR_DEFAULT), false)
            .addDeclaration(FX_BACKGROUND_INSETS, literal("-0.1666665em -0.45em -0.1666665em -0.45em"), false)
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.INPUT_BACKGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("text-field > .right-button:hover > .right-button-graphic"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.ACCENT_BACKGROUND)), false)
            .build()
        );

        // Password Field
        defineRule(newRule()
            .addSelector (classSelector("password-field"))
            .addDeclaration(RIGHT_BUTTON_VISIBLE, literal(TRUE), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("password-field > .right-button"))
            .addDeclaration(FX_PADDING, literal("0 0.166667em 0 0.166667em"), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("password-field > .right-button > .right-button-graphic"))
            .addDeclaration(FX_SHAPE, shape("M307.688,399.564c0,1.484-1.203,2.688-2.688,2.688c-1.484,0-2.688-1.203-2.688-2.688s1.203-2.688,2.688-2.688C306.484,396.876,307.688,398.08,307.688,399.564z M297.5,399h2.5c0,0,1.063-4,5-4c3.688,0,5,4,5,4h2.5c0,0-2.063-6.5-7.5-6.5C299,392.5,297.5,399,297.5,399z"), false)
            .addDeclaration(FX_SCALE_SHAPE, literal(FALSE), false)
            .addDeclaration(FX_BACKGROUND_COLOR, literal("#111"), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("password-field > .right-button:pressed"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.INPUT_BACKGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("password-field > .right-button:pressed > .right-button-graphic"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.INPUT_BACKGROUND)), false)
            .build()
        );

        // Text Area

        defineRule(newRule()
            .addSelector (classSelector("text-area"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.INPUT_BORDER),color(ColorID.INPUT_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, literal(ZERO), false)
            .addDeclaration(FX_BACKGROUND_INSETS, literal("0, 1"), false)
            // .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.INPUT_BORDER)), false)
            // .addDeclaration(FX_BORDER_WIDTH, values(px(1)), false)
            .addDeclaration(FX_BORDER_WIDTH, values(px(0)), false)


            // codeArea.setStyle("-fx-font-family: 'Comic Mono', Monaco, 'Fira Code', Monospaced; -fx-font-size: 10pt;");

            .addDeclaration(FX_TEXT_FILL, literal("#66AA88"), false)
            .addDeclaration(FX_FONT_SIZE, literal("14px"), false)



            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("text-area .content"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.INPUT_BACKGROUND)), false)
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.INPUT_BACKGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("text-area:hover .content"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.INPUT_BORDER),color(ColorID.INPUT_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, literal(ZERO), false)
            .addDeclaration(FX_BACKGROUND_INSETS, literal("0, 1"), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("text-area:focused .content"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.INPUT_BORDER),color(ColorID.INPUT_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, literal(ZERO), false)
            .addDeclaration(FX_BACKGROUND_INSETS, literal("0, 1"), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("text-area:disabled .content"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.INPUT_BORDER),color(ColorID.INPUT_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, literal(ZERO), false)
            .addDeclaration(FX_BACKGROUND_INSETS, literal("0, 1"), false)
            .build()
        );
    }
}
