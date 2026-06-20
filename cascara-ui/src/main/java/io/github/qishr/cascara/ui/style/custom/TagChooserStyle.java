package io.github.qishr.cascara.ui.style.custom;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class TagChooserStyle extends ControlStyle {
    public TagChooserStyle() {
        super();
        defineRule(newRule()
            .addSelector (classSelector("tag-chooser"))
            .addDeclaration(FX_BACKGROUND_RADIUS, values(px(2)), false)
            .addDeclaration(FX_BORDER_WIDTH, values(px(1)), false)
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.INPUT_BORDER)), true)
            .addDeclaration(FX_BORDER_RADIUS, values(px(2)), false)
            .addDeclaration(FX_PADDING, values(px(4)), false)
            .addDeclaration(FX_CURSOR, values(CURSOR_TEXT), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("tag-chooser .text-field"))
            .addDeclaration(FX_BORDER_WIDTH, values(ZERO), false)
            .build()
        );
    }
}
