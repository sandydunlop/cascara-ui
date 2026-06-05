package io.github.qishr.cascara.ui.style.custom;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class BadgeStyle extends ControlStyle {
    public BadgeStyle() {
        super();

        defineRule(newRule()
            .addSelector (classSelector("cascara-badge"))
            // Use a very high radius; it will cap at half the height (making a circle)
            //     until the width increases (making a pill).
            .addDeclaration(FX_BACKGROUND_RADIUS, values(px(999)), false)
            .addDeclaration(FX_BORDER_RADIUS, values(px(999)), false)
            .addDeclaration(FX_ALIGNMENT, values(CENTER), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("badge-info"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BADGE_INFO_BACKGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("badge-warning .badge-text"))
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.BADGE_INFO_FOREGROUND)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("badge-error"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BADGE_ERROR_BACKGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("badge-warning .badge-text"))
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.BADGE_ERROR_FOREGROUND)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("badge-warning"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BADGE_WARN_BACKGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("badge-warning .badge-text"))
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.BADGE_WARN_FOREGROUND)), false)
            .build()
        );
    }
}
