package io.github.qishr.cascara.ui.data;

import io.github.qishr.cascara.common.diagnostic.code.DiagnosticCode;
import io.github.qishr.cascara.ui.api.UiException;

public class UiDataException extends UiException {

    /// Constructor for errors without an exception.
    public UiDataException(DiagnosticCode code, Object... details) {
        super(code, details);
    }

    /// Constructor for errors with an exception.
    public UiDataException(Throwable cause, DiagnosticCode code, Object... details) {
        super(cause, code, details);
    }

}
