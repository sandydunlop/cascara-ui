package io.github.qishr.cascara.ui.schema;

import io.github.qishr.cascara.common.diagnostic.code.DiagnosticCode;
import io.github.qishr.cascara.ui.api.UiException;

public class UiSchemaException extends UiException {

    /// Constructor for errors without an exception.
    public UiSchemaException(DiagnosticCode code, Object... details) {
        super(code, details);
    }

    /// Constructor for errors with an exception.
    public UiSchemaException(Throwable cause, DiagnosticCode code, Object... details) {
        super(cause, code, details);
    }

}
