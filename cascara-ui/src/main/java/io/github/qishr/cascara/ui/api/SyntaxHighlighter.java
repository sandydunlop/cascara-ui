package io.github.qishr.cascara.ui.api;

import java.util.List;

import io.github.qishr.cascara.common.service.ServiceProvider;
import io.github.qishr.cascara.common.util.ContentType;
import io.github.qishr.cascara.ui.data.TextBuffer;
import javafx.collections.ObservableList;

public interface SyntaxHighlighter extends ServiceProvider {
    List<ContentType> getSupportedContentTypes();
    void setTextBuffer(TextBuffer buffer);
    TextBuffer getTextBuffer();
    boolean supportsSemanticHighlighting();
    ObservableList<HighlightingToken> getHighlightingTokens();
}
