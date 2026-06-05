package io.github.qishr.cascara.ui.style.standard;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class AccordionStyle extends ControlStyle {
    public AccordionStyle() {
        super();

        defineRule(newRule()
            .addSelector (classSelector("accordion"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.SIDEBAR_HEADER_BACKGROUND)), false)
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.SIDEBAR_BORDER)), false)
            .addDeclaration(FX_BORDER_WIDTH, values(ZERO), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("accordion > .titled-pane"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.SIDEBAR_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_INSETS, values(ZERO), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, values(ZERO), false)
            .addDeclaration(FX_BORDER_WIDTH, values(ZERO), false)
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.SIDEBAR_FOREGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("accordion > .titled-pane > .title"))
            .addDeclaration(FX_EFFECT, literal(NONE), false)
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.SIDEBAR_HEADER_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_INSETS, values(ZERO), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, values(ZERO), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("accordion > .titled-pane > .title > .text"))
            .addDeclaration(FX_FILL, values(color(ColorID.SIDEBAR_HEADER_FOREGROUND)), false)
            .addDeclaration(FX_FONT_SIZE, values(em(0.75)), false)
            .addDeclaration(FX_FONT_WEIGHT, values(BOLD), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("accordion > .titled-pane > .content"))
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.SIDEBAR_BORDER)), false)
            .addDeclaration(FX_BORDER_INSETS, values(ZERO), false)
            .addDeclaration(FX_BORDER_WIDTH, values(ZERO), false)
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.SIDEBAR_BACKGROUND)), false)
            .addDeclaration(FX_PADDING, values(ZERO), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("accordion > .titled-pane > .content .table-view"))
            .addSelector (classSelector("accordion > .titled-pane > .content .tree-view"))
            .addSelector (classSelector("accordion > .titled-pane > .content .tree-table-view"))
            .addDeclaration(FX_BORDER_WIDTH, values(ZERO), false)
            .addDeclaration(FX_PADDING, values(ZERO), false)
            .build()
        );
    }
}
