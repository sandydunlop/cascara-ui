package io.github.qishr.cascara.ui.style.standard;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class ScrollBarStyle extends ControlStyle {
    public ScrollBarStyle() {
        super();
        defineRule(newRule()
            .addSelector (classSelector("scroll-bar"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(TRANSPARENT), false)
            // .addDeclaration(FX_OPACITY, literal("0.4"), false)
            .addDeclaration(FX_OPACITY, literal("1"), false)
            .addDeclaration(FX_PREF_WIDTH, literal("8px"), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("scroll-bar:hover"))
            .addSelector (classSelector("scroll-bar:pressed"))
            .addSelector (classSelector("scroll-bar:focused"))
            .addDeclaration(FX_OPACITY, literal("1"), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("scroll-bar > .thumb"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.SCROLL_THUMB_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_INSETS, literal(ZERO), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, literal("4px"), false)
            .addDeclaration(FX_OPACITY, literal("1"), false)
            // .addDeclaration(FX_OPACITY, literal("0.7"), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("scroll-bar > .track"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(TRANSPARENT), false)
            .addDeclaration(FX_BORDER_RADIUS, literal(ZERO), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("scroll-bar > .increment-button"))
            .addSelector (classSelector("scroll-bar > .decrement-button"))
            .addDeclaration(VISIBILITY, literal("hidden"), false)
            .addDeclaration(FX_MANAGED, literal(FALSE), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("scroll-bar:disabled"))
            .addDeclaration(VISIBILITY, literal("hidden"), false)
            .addDeclaration(FX_MANAGED, literal(FALSE), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("scroll-bar:horizontal > .increment-button > .increment-arrow"))
            .addDeclaration(FX_SHAPE, shape(" "), false)
            .addDeclaration(FX_PADDING, literal(ZERO), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("scroll-bar:horizontal > .decrement-button > .decrement-arrow"))
            .addDeclaration(FX_SHAPE, shape(" "), false)
            .addDeclaration(FX_PADDING, literal(ZERO), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("scroll-bar:vertical > .increment-button > .increment-arrow"))
            .addDeclaration(FX_SHAPE, shape(" "), false)
            .addDeclaration(FX_PADDING, literal(ZERO), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("scroll-bar:vertical > .decrement-button > .decrement-arrow"))
            .addDeclaration(FX_SHAPE, shape(" "), false)
            .addDeclaration(FX_PADDING, literal(ZERO), false)
            .build()
        );
    }
}
