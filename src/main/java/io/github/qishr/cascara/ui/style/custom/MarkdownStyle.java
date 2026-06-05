package io.github.qishr.cascara.ui.style.custom;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class MarkdownStyle extends ControlStyle {
    public MarkdownStyle() {
        super();


        // cascara://organizer/CASC-00027AF9
        // `.text-flow` and `.text-flow .heading` are handled by the TextStyle class

        // Note:
        // EDITOR_BACKGROUND is the standard background for documents of any type
        // TEXT_FOREGROUND is for normal text
        // EDITOR_FOREGROUND is like TEXT_FOREGROUND but guaranteed to be the most readable against EDITOR_BACKGROUND
        // EDITOR_BACKGROUND is for document backgrounds
        // INPUT_BACKGROUND is slightly different from EDITOR_BACKGROUND:
        // - In a dark theme it is slightly lighter
        // - In a light theme it is slightly darker
        // There is INPUT_FOREGROUND too, which is supposed to be easier
        // to read on INPUT_BACKGROUND, but it might look too much like
        // an input field if it's used in a document.
        //
        // We should probaly introduce a EDITOR_SECONDARY_BACKGROUND to use in this class instead of INPUT_BACKGROUND.

        defineRule(newRule()
            .addSelector (classSelector("markdown-doc"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.EDITOR_BACKGROUND)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("markdown-doc *"))
            // .addSelector (classSelector("markdown-doc .label"))
            // .addSelector (classSelector("markdown-doc .text"))
            // .addSelector (classSelector("markdown-doc .hyperlink"))
            // .addSelector (classSelector("markdown-doc .blockquote-content"))
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.FOREGROUND)), false)
            .addDeclaration(FX_FILL, values(color(ColorID.FOREGROUND)), false)
            .build()
        );

        //
        // Hyperlinks
        //

        defineRule(newRule()
            // .addSelector (classSelector("hyperlink"))
            // .addSelector (classSelector("markdown-doc .hyperlink"))
            // .addSelector (classSelector("markdown-doc * > .hyperlink *"))
            .addSelector (classSelector("markdown-doc * .hyperlink *"))
            // .addSelector (classSelector("markdown-paragraph .hyperlink *"))

            // .addSelector (classSelector("markdown-paragraph  * > .hyperlink *"))
            // .addSelector (classSelector("markdown-paragraph > .hyperlink *"))
            // .addSelector (classSelector("markdown-doc .markdown-paragraph .hyperlink *"))
            // .addSelector (classSelector("markdown-doc .markdown-paragraph > .hyperlink *"))

            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.LINK_INACTIVE_FOREGROUND)), false)
            .addDeclaration(FX_FILL, values(color(ColorID.LINK_INACTIVE_FOREGROUND)), false)
            .build()
        );

        //
        // Blockquotes
        //
        defineRule(newRule()
            .addSelector(classSelector("markdown-blockquote"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.INPUT_BACKGROUND)), false)
            .addDeclaration(FX_PADDING, values(px(5), px(10)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector(classSelector("blockquote-bar"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.ACCENT_BACKGROUND)), false)
            .build()
        );

        //
        // Markdown Emphasis
        //

        defineRule(newRule()
            .addSelector (classSelector("markdown-bold"))
            .addSelector (classSelector("markdown-bold > *"))
            .addDeclaration(FX_FILL, values(color(ColorID.FOREGROUND)), false)
            .addDeclaration(FX_FONT_WEIGHT, literal(BOLD), false)
            .build()
        );

        defineRule(newRule()
            .addSelector(classSelector("markdown-italic"))
            .addSelector(classSelector("markdown-italic > *"))
            .addDeclaration(FX_FONT_STYLE, literal(ITALIC), false)
            .build()
        );

        //
        // Markdown headings
        //

        defineRule(newRule()
            .addSelector (classSelector("heading-1"))
            .addSelector (classSelector("heading-1 > *"))
            .addDeclaration(FX_FILL, values(color(ColorID.FOREGROUND)), false)
            .addDeclaration(FX_FONT_SIZE, literal(em(2)), false)
            .addDeclaration(FX_FONT_WEIGHT, literal(BOLD), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("heading-2"))
            .addSelector (classSelector("heading-2 > *"))
            .addDeclaration(FX_FILL, values(color(ColorID.FOREGROUND)), false)
            .addDeclaration(FX_FONT_SIZE, literal(em(1.8)), false)
            // .addDeclaration(FX_FONT_WEIGHT, literal(BOLD), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("heading-3"))
            .addSelector (classSelector("heading-3 > *"))
            .addDeclaration(FX_FILL, values(color(ColorID.FOREGROUND)), false)
            .addDeclaration(FX_FONT_SIZE, literal(em(1.6)), false)
            // .addDeclaration(FX_FONT_WEIGHT, literal(BOLD), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("heading-4"))
            .addSelector (classSelector("heading-4 > *"))
            .addDeclaration(FX_FILL, values(color(ColorID.FOREGROUND)), false)
            .addDeclaration(FX_FONT_SIZE, literal(em(1.4)), false)
            // .addDeclaration(FX_FONT_WEIGHT, literal(BOLD), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("heading-5"))
            .addSelector (classSelector("heading-5 > *"))
            .addDeclaration(FX_FILL, values(color(ColorID.FOREGROUND)), false)
            .addDeclaration(FX_FONT_SIZE, literal(em(1.2)), false)
            .addDeclaration(FX_FONT_WEIGHT, literal(BOLD), false)
            .build()
        );

        //
        // Markdown Lists
        //

        defineRule(newRule()
            .addSelector(classSelector("markdown-list"))
            .addDeclaration(FX_PADDING, values(px(0), px(0), px(0), px(20)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector(classSelector("list-bullet"))
            .addDeclaration(FX_FONT_FAMILY, values("Segoe UI Symbol", "DejaVu Sans", "Arial"), false)
            .addDeclaration(FX_FILL, values(color(ColorID.FOREGROUND)), false)
            .addDeclaration(FX_FONT_WEIGHT, literal(BOLD), false)
            .build()
        );

        //
        // Task List Checkboxes
        //

        defineRule(newRule()
            .addSelector(classSelector("task-list-checkbox"))
            .addDeclaration(FX_OPACITY, literal("1.0"), false)
            .build()
        );

        defineRule(newRule()
            .addSelector(classSelector("task-list-checkbox .box"))
            .addDeclaration(FX_BACKGROUND_RADIUS, values(px(3)), false)
            .addDeclaration(FX_BORDER_RADIUS, values(px(3)), false)
            .build()
        );

        // Nested spacing for VBox content within lists
        defineRule(newRule()
            .addSelector(classSelector("markdown-list .vbox"))
            .addDeclaration(FX_SPACING, values(px(5)), false)
            .build()
        );

        //
        // Code Block
        //

        defineRule(newRule()
            .addSelector(classSelector("markdown-code-block"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.INPUT_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, values(px(5)), false)
            .addDeclaration(FX_PADDING, values(px(10)), false) // Increased padding for blocks
            .addDeclaration(FX_FONT_FAMILY, literal("Monospaced"), false)
            .build()
        );

        //
        //
        //

        defineRule(newRule()
            .addSelector(classSelector("markdown-table"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.CONTROL_FOREGROUND)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector(classSelector("markdown-table .text-flow"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.EDITOR_BACKGROUND)), false)
            .build()
        );

    }
}
