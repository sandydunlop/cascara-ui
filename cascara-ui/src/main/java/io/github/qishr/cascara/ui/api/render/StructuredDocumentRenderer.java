package io.github.qishr.cascara.ui.api.render;

import io.github.qishr.cascara.common.lang.ast.AstNode;
import io.github.qishr.cascara.common.lang.processor.Parser;
import io.github.qishr.cascara.common.lang.token.Token;

public interface StructuredDocumentRenderer extends ScalarRenderer {
    Parser<? extends AstNode, ? extends Token> getParser();
}
