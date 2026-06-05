package io.github.qishr.cascara.ui.color;

public class ColorException extends Exception {
    private final String message;

    public ColorException(String message) {
        this.message = message;
    }

    public ColorException(String message, Exception e) {
        this.message = message;
        this.initCause(e);
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
