package io.github.qishr.cascara.ui.style.custom;

import io.github.qishr.cascara.common.diagnostic.Diagnostic.Level;
import io.github.qishr.cascara.ui.style.ControlStyle;
import io.github.qishr.cascara.ui.theme.ColorID;

public class DiagnosticIconStyle extends ControlStyle {

    public static final String ERROR = "diag-icon-error";
    public static final String WARN = "diag-icon-warn";
    public static final String INFO = "diag-icon-info";
    public static final String DEBUG = "diag-icon-debug";
    public static final String TRACE = "diag-icon-trace";

    // private static final String ICON = "diag-icon";

    public static String classFor(Level level) {
        return switch (level) {
            case ERROR   -> DiagnosticIconStyle.ERROR;
            case WARN    -> DiagnosticIconStyle.WARN;
            case DEBUG   -> DiagnosticIconStyle.DEBUG;
            case TRACE   -> DiagnosticIconStyle.TRACE;
            default      -> DiagnosticIconStyle.INFO;
        };
    }

    public static String svgFor(Level level) {
        String path = switch (level) {
            case ERROR   -> "M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm1 15h-2v-2h2v2zm0-4h-2V7h2v6z";
            case WARN -> "M1 21h22L12 2 1 21zm12-3h-2v-2h2v2zm0-4h-2v-4h2v4z";
            default      -> "M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm1 15h-2v-6h2v6zm0-8h-2V7h2v2z";
        };
        return String.format(
            "<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 24 24\"><path d=\"%s\" /></svg>",
            path
        );
    }

    public DiagnosticIconStyle() {
        super();

        defineRule(newRule()
            .addSelector (classSelector(ERROR))
            .addDeclaration(FX_OPACITY, literal("0.6"), true)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector(ERROR + " ." + SvgIconStyle.FILL))
            .addDeclaration(FX_FILL, values(color(ColorID.EDITOR_ERROR_FOREGROUND)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector(WARN + " ." + SvgIconStyle.FILL))
            .addDeclaration(FX_FILL, values(color(ColorID.EDITOR_WARN_FOREGROUND)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector(INFO + " ." + SvgIconStyle.FILL))
            .addDeclaration(FX_FILL, values(color(ColorID.EDITOR_INFO_FOREGROUND)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector(DEBUG + " ." + SvgIconStyle.FILL))
            .addDeclaration(FX_FILL, values(color(ColorID.SIDEBAR_FOREGROUND)), false)
            .build()
        );

        defineRule(newRule()
            .addSelector (classSelector(TRACE + " ." + SvgIconStyle.FILL))
            .addDeclaration(FX_FILL, values(color(ColorID.SIDEBAR_FOREGROUND)), false)
            .build()
        );
    }
}
