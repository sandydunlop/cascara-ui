package io.github.qishr.cascara.ui.api;

import java.util.List;

import io.github.qishr.cascara.common.diagnostic.Diagnostic;
import io.github.qishr.cascara.ui.data.TextBuffer;

public interface TextEditor {
    void initialize();

    TextBuffer getTextBuffer();
    void setTextBuffer(TextBuffer buffer);

    void scrollTo(int line);
    void scrollTo(int line, int column);

    void setHighlights(List<HighlightingToken> tokens);

    void setDiagnostics(List<Diagnostic> diagnostics);
}
