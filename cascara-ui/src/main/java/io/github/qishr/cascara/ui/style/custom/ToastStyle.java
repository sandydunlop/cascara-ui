package io.github.qishr.cascara.ui.style.custom;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class ToastStyle extends ControlStyle {
    public ToastStyle() {
        super();
        defineRule(newRule()
            .addSelector (classSelector("toast-root"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.NOTIFICATION_BACKGROUND)), false)
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.NOTIFICATION_BORDER)), false)
            .addDeclaration(FX_BORDER_WIDTH, values(px(1)), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, values(px(4)), false)
            .addDeclaration(FX_BORDER_RADIUS, values(px(4)), false)
            .addDeclaration(FX_PADDING, values(px(12)), false)
            .addDeclaration(FX_EFFECT, values(dropshadow(color(ColorID.WIDGET_SHADOW))), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("toast-title"))
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.NOTIFICATION_LINK)), false)
            .addDeclaration(FX_FONT_WEIGHT, values(BOLD), false)
            .addDeclaration(FX_FONT_SIZE, values(px(13)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("toast-message"))
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.NOTIFICATION_FOREGROUND)), false)
            .addDeclaration(FX_FONT_SIZE, values(px(12)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("toast-root.info"))
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.NOTIFICATION_ICON_INFO)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("toast-root.error"))
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.NOTIFICATION_ICON_ERROR)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("toast-root.warning"))
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.NOTIFICATION_ICON_WARNING)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("toast-root.success"))
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.NOTIFICATION_ICON_INFO)), false)
            .build()
        );
    }
}