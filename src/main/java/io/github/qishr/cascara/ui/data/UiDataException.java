package io.github.qishr.cascara.ui.data;

import io.github.qishr.cascara.common.util.CascaraRuntimeException;

public class UiDataException extends CascaraRuntimeException {
    public UiDataException(String message) {
        super(message);
    }

    public UiDataException(String message, Exception e) {
        super(message, e);
    }
}
