package io.github.qishr.cascara.ui.style.standard;

import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class TabPaneStyle extends ControlStyle {
    public TabPaneStyle() {
        super();

        //
        // Tab Pane
        //

        defineRule(newRule()
            .addSelector (classSelector("tab-pane"))
            .addSelector (classSelector("tab-pane > .tab-header-area"))
            .addSelector (classSelector("tab-pane > .tab-header-area > .headers-region"))
            .addSelector (classSelector("tab-pane > .tab-header-area > .tab-header-background"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(TRANSPARENT), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-header-area .tab-header-background"))
            .addDeclaration(FX_BORDER_COLOR, values(TRANSPARENT, TRANSPARENT, derive(color(ColorID.FOREGROUND), -35),  TRANSPARENT), false)
            .addDeclaration(FX_BORDER_INSETS, literal("0 0 0 0"), false)
            .addDeclaration(FX_BORDER_WIDTH, literal("0 0 1 0"), false)
            .build()
        );

        //
        // Tab
        //

        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-header-area > .headers-region > .tab"))
            .addSelector (classSelector("tab-pane > .tab-header-area > .headers-region > .tab:top"))
            .addSelector (classSelector("tab-pane > .tab-header-area > .headers-region > .tab:right"))
            .addSelector (classSelector("tab-pane > .tab-header-area > .headers-region > .tab:bottom"))
            .addSelector (classSelector("tab-pane > .tab-header-area > .headers-region > .tab:left"))
            .addSelector (classSelector("tab-pane:focused > .tab-header-area > .headers-region > .tab"))
            .addSelector (classSelector("tab-pane:focused > .tab-header-area > .headers-region > .tab:top"))
            .addSelector (classSelector("tab-pane:focused > .tab-header-area > .headers-region > .tab:right"))
            .addSelector (classSelector("tab-pane:focused > .tab-header-area > .headers-region > .tab:bottom"))
            .addSelector (classSelector("tab-pane:focused > .tab-header-area > .headers-region > .tab:right"))

            .addDeclaration(FX_BACKGROUND_INSETS, literal("0"), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, literal("0"), false)
            .addDeclaration(FX_BACKGROUND_COLOR, values(TRANSPARENT), false)

            .addDeclaration(FX_BORDER_COLOR, values(TRANSPARENT, TRANSPARENT, derive(color(ColorID.FOREGROUND), -35), TRANSPARENT), false)
            .addDeclaration(FX_BORDER_WIDTH, literal("0 0 1 0"), false)
            .addDeclaration(FX_BORDER_RADIUS, literal("0 0 0 0"), false)

            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.FOREGROUND)), false)
            .addDeclaration(FX_PADDING, sides(0,8,0,8), false)

            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-header-area > .headers-region > .tab:selected"))
            .addSelector (classSelector("tab-pane > .tab-header-area > .headers-region > .tab:top:selected"))
            .addSelector (classSelector("tab-pane > .tab-header-area > .headers-region > .tab:right:selected"))
            .addSelector (classSelector("tab-pane > .tab-header-area > .headers-region > .tab:left:selected"))
            .addSelector (classSelector("tab-pane > .tab-header-area > .headers-region > .tab:bottom:selected"))
            .addSelector (classSelector("tab-pane:focused > .tab-header-area > .headers-region > .tab:selected"))
            .addSelector (classSelector("tab-pane:focused > .tab-header-area > .headers-region > .tab:top:selected"))
            .addSelector (classSelector("tab-pane:focused > .tab-header-area > .headers-region > .tab:right:selected"))
            .addSelector (classSelector("tab-pane:focused > .tab-header-area > .headers-region > .tab:left:selected"))
            .addSelector (classSelector("tab-pane:focused > .tab-header-area > .headers-region > .tab:bottom:selected"))

            .addDeclaration(FX_BACKGROUND_RADIUS, values(ZERO), false)
            .addDeclaration(FX_BACKGROUND_INSETS, sides(ZERO, ZERO, ZERO, ZERO), false)
            .addDeclaration(FX_BACKGROUND_COLOR, values(TRANSPARENT), false)

            .addDeclaration(FX_BORDER_WIDTH, literal("0 0 1 0"), false)
            .addDeclaration(FX_BORDER_INSETS, sides(ZERO, ZERO, ZERO, ZERO), false)
            .addDeclaration(FX_BORDER_RADIUS, values(ZERO), false)
            .addDeclaration(FX_BORDER_COLOR, values(color(ColorID.FOREGROUND)), false)

            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.FOREGROUND)), false)
            .addDeclaration(FX_PADDING, sides(0,8,0,8), false)

            .build()
        );


        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-header-area > .headers-region > .tab:selected .focus-indicator"))
            .addSelector (classSelector("tab-pane > .tab-header-area > .headers-region > .tab:top:selected .focus-indicator"))
            .addSelector (classSelector("tab-pane > .tab-header-area > .headers-region > .tab:right:selected .focus-indicator"))
            .addSelector (classSelector("tab-pane > .tab-header-area > .headers-region > .tab:left:selected .focus-indicator"))
            .addSelector (classSelector("tab-pane > .tab-header-area > .headers-region > .tab:bottom:selected .focus-indicator"))
            .addSelector (classSelector("tab-pane:focused > .tab-header-area > .headers-region > .tab:selected .focus-indicator"))
            .addSelector (classSelector("tab-pane:focused > .tab-header-area > .headers-region > .tab:top:selected .focus-indicator"))
            .addSelector (classSelector("tab-pane:focused > .tab-header-area > .headers-region > .tab:right:selected .focus-indicator"))
            .addSelector (classSelector("tab-pane:focused > .tab-header-area > .headers-region > .tab:left:selected .focus-indicator"))
            .addSelector (classSelector("tab-pane:focused > .tab-header-area > .headers-region > .tab:bottom:selected .focus-indicator"))
            .addDeclaration(FX_BORDER_WIDTH, literal("0 0 0 0"), false)
            .addDeclaration(FX_BORDER_INSETS, sides(ZERO, ZERO, ZERO, ZERO), false)
            .addDeclaration(FX_BORDER_RADIUS, values(ZERO), false)
            // .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.DOCUMENT_BACKGROUND)), false)
            .addDeclaration(FX_BACKGROUND_COLOR, values(TRANSPARENT), false)

            .build()
        );

        //
        // Label
        //

        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-header-area > .headers-region > .tab > .tab-container > .tab-label"))
            .addDeclaration(FX_TEXT_FILL, values(derive(color(ColorID.FOREGROUND), -35)), false)
            .addDeclaration(FX_FONT_SIZE, values(em(0.8)), false)
            .addDeclaration(TEXT_TRANSFORM, values(UPPERCASE), false)
            .addDeclaration(FX_PADDING, values(px(3)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-header-area > .headers-region > .tab:hover > .tab-container > .tab-label"))
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.FOREGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-header-area > .headers-region > .tab:selected > .tab-container > .tab-label"))
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.FOREGROUND)), false)
            .build()
        );
        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-header-area > .headers-region > .tab:selected:hover > .tab-container > .tab-label"))
            .addDeclaration(FX_TEXT_FILL, values(color(ColorID.FOREGROUND)), false)
            .build()
        );

        //
        // Content
        //

        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-content-area"))
            .addDeclaration(FX_PADDING, values(sides(px(16), ZERO, ZERO, ZERO)), false)
            .build()
        );

        //
        // More tabs button
        //

        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-header-area > .control-buttons-tab > .container > .tab-down-button"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(TRANSPARENT), false)
            .addDeclaration(FX_BACKGROUND_INSETS, literal("0 0 4 0"), false)
            .addDeclaration(FX_PADDING, literal("4 8 9 8"), false)
            .addDeclaration(FX_BACKGROUND_RADIUS, literal(px(2)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-header-area > .control-buttons-tab > .container > .tab-down-button:hover"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_BACKGROUND)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-header-area > .control-buttons-tab > .container > .tab-down-button:pressed"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_BACKGROUND)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-header-area > .control-buttons-tab > .container > .tab-down-button > .arrow"))
            .addDeclaration(FX_PADDING, literal("0.08em"), false)
            .addDeclaration(FX_SCALE_SHAPE, literal(FALSE), false)
            .addDeclaration(FX_SHAPE, shape("M 0 0 h 7 l -3.5 4 z"), false)
            .addDeclaration(FX_BACKGROUND_INSETS, literal(ZERO), false)
            .addDeclaration(FX_BACKGROUND_COLOR, values(derive(color(ColorID.FOREGROUND), -35)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector("tab-pane > .tab-header-area > .control-buttons-tab > .container > .tab-down-button:hover > .arrow"))
            .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.BUTTON_FOREGROUND)), false)
            .build()
        );







        // // Tab pane inside tab pane

        // defineRule(newRule()
        //     .addSelector (classSelector("tab-pane > .tab-content-area .tab-pane"))
        //     .addSelector (classSelector("tab-pane > .tab-content-area .tab-pane > .tab-header-area"))
        //     .addSelector (classSelector("tab-pane > .tab-content-area .tab-pane > .tab-header-area > .headers-region"))
        //     .addSelector (classSelector("tab-pane > .tab-content-area .tab-pane > .tab-header-area > .tab-header-background"))
        //     .addDeclaration(FX_BACKGROUND_COLOR, values(TRANSPARENT), false)
        //     .build()
        // );
        // defineRule(newRule()
        //     .addSelector (classSelector("tab-pane > .tab-content-area .tab-pane > .tab-header-area .tab-header-background"))
        //     .addDeclaration(FX_BORDER_COLOR, values(TRANSPARENT, TRANSPARENT, derive(color(ColorID.FOREGROUND), -50),  TRANSPARENT), false)
        //     .addDeclaration(FX_BORDER_INSETS, literal("0 0 0 0"), false)
        //     .addDeclaration(FX_BORDER_WIDTH, literal("0 0 1 0"), false)
        //     .build()
        // );
        // defineRule(newRule()
        //     .addSelector (classSelector("tab-pane > .tab-content-area .tab-pane .tab"))
        //     .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.DOCUMENT_BACKGROUND)), false)
        //     .addDeclaration(FX_BACKGROUND_INSETS, literal("0"), false)
        //     .addDeclaration(FX_BACKGROUND_RADIUS, literal("0"), false)
        //     .addDeclaration(FX_BORDER_COLOR, values(TRANSPARENT, TRANSPARENT, derive(color(ColorID.FOREGROUND), -35),  TRANSPARENT), false)
        //     .addDeclaration(FX_BORDER_WIDTH, literal("0 0 1 0"), false)
        //     .addDeclaration(FX_BORDER_RADIUS, literal("0 0 0 0"), false)
        //     .addDeclaration(FX_TEXT_FILL, values(color(ColorID.FOREGROUND)), false)
        //     .build()
        // );
        // defineRule(newRule()
        //     .addSelector (classSelector("tab-pane > .tab-content-area .tab-pane .tab:selected"))
        //     .addDeclaration(FX_BACKGROUND_COLOR, values(color(ColorID.DOCUMENT_BACKGROUND)), false)
        //     .addDeclaration(FX_BACKGROUND_INSETS, literal("0 0 0 0"), false)
        //     .addDeclaration(FX_BORDER_COLOR, literal("transparent transparent -color-foreground transparent"), false)
        //     .addDeclaration(FX_BORDER_WIDTH, literal("0 0 1 0"), false)
        //     .build()
        // );

        // defineRule(newRule()
        //     .addSelector (classSelector("tab-pane > .tab-content-area .tab-pane > .tab-header-area > .headers-region > .tab > .tab-container > .tab-label"))
        //     .addDeclaration(FX_TEXT_FILL, values(derive(color(ColorID.FOREGROUND), -35)), false)
        //     .addDeclaration(FX_FONT_SIZE, values(em(0.8)), false)
        //     .addDeclaration(TEXT_TRANSFORM, values(UPPERCASE), false)
        //     .addDeclaration(FX_PADDING, values(px(3)), false)
        //     .build()
        // );
        // defineRule(newRule()
        //     .addSelector (classSelector("tab-pane > .tab-content-area .tab-pane > .tab-header-area > .headers-region > .tab:hover > .tab-container > .tab-label"))
        //     .addDeclaration(FX_TEXT_FILL, values(color(ColorID.FOREGROUND)), false)
        //     .build()
        // );
        // defineRule(newRule()
        //     .addSelector (classSelector("tab-pane > .tab-content-area .tab-pane > .tab-header-area > .headers-region > .tab:selected > .tab-container > .tab-label"))
        //     .addDeclaration(FX_TEXT_FILL, values(color(ColorID.FOREGROUND)), false)
        //     .build()
        // );
        // defineRule(newRule()
        //     .addSelector (classSelector("tab-pane > .tab-content-area .tab-pane > .tab-header-area > .headers-region > .tab:selected:hover > .tab-container > .tab-label"))
        //     .addDeclaration(FX_TEXT_FILL, values(color(ColorID.FOREGROUND)), false)
        //     .build()
        // );

        // defineRule(newRule()
        //     .addSelector (classSelector("tab-pane > .tab-content-area .tab-pane > .tab-content-area"))
        //     .addDeclaration(FX_PADDING, values(sides(px(16), ZERO, ZERO, ZERO)), false)
        //     .build()
        // );












        // defineRule(newRule()
        //     .addSelector (classSelector("tab-pane > .tab-header-area > .headers-region > .tab"))
        //     .addSelector (classSelector("tab-pane:focused > .tab-header-area > .headers-region > .tab"))
        //     .build()
        // );

    }
}
