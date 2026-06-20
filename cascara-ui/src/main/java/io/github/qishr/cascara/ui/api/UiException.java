package io.github.qishr.cascara.ui.api;

import io.github.qishr.cascara.common.diagnostic.LocalizableRuntimeException;
import io.github.qishr.cascara.common.diagnostic.code.DiagnosticCode;

public class UiException extends LocalizableRuntimeException {

    /// Constructor for errors without an exception.
    public UiException(DiagnosticCode code, Object... details) {
        super(code, details);
    }

    /// Constructor for errors with an exception.
    public UiException(Throwable cause, DiagnosticCode code, Object... details) {
        super(cause, code, details);
    }

}
