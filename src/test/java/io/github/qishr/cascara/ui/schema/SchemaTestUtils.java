package io.github.qishr.cascara.ui.schema;

import static org.mockito.Mockito.mock;

import java.net.URI;

import io.github.qishr.cascara.common.lang.ast.MapAstNode;
import io.github.qishr.cascara.schema.structure.ObjectSchemaNode;

public class SchemaTestUtils {

    @SuppressWarnings("rawtypes")
    public static ObjectSchemaNode createAnchoredSchema(String name, URI uri) {
        ObjectSchemaNode node = new ObjectSchemaNode(null);
        node.setOriginUri(uri);
        // Mock a basic AST so resolveInternal doesn't NPE
        MapAstNode mockAst = mock(MapAstNode.class);
        node.setOriginAst(mockAst);
        return node;
    }
}