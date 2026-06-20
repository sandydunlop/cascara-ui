package io.github.qishr.cascara.ui.schema;

import org.junit.jupiter.api.Test;

import io.github.qishr.cascara.common.lang.ast.MapAstNode;
import io.github.qishr.cascara.schema.util.SchemaResolver;
import io.github.qishr.cascara.schema.structure.BaseSchemaNode;
import io.github.qishr.cascara.schema.structure.LazySchemaNode;
import io.github.qishr.cascara.schema.structure.ObjectSchemaNode;
import io.github.qishr.cascara.schema.structure.ScalarSchemaNode;
import io.github.qishr.cascara.schema.structure.SchemaNode;
import io.github.qishr.cascara.schema.SchemaType;
import io.github.qishr.cascara.schema.rule.RegexRule;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;

class SchemaStructureTest {
    @Test
    void testPropertyAccess() {
        // CHANGE: Use ObjectSchemaNode so the properties are actually stored
        ObjectSchemaNode root = new ObjectSchemaNode(null);
        BaseSchemaNode child = new ScalarSchemaNode(SchemaType.BOOLEAN, null);

        root.addProperty("showToolbar", child);

        // This will now work
        assertEquals(child, root.getProperty("showToolbar"));

        // Safety check for the second assertion (getProperty returns Optional, not null)
        assertNull(root.getProperty("nonExistent"));
    }


    // @SuppressWarnings("rawtypes")
    // @Test
    // void testInternalReferenceResolution() throws SchemaException {
    //     // 1. Setup
    //     ParserService mockParser = mock(ParserService.class);
    //     // StudioContext mockContext = mock(StudioContext.class);

    //     ContentLoaderService mockLoader = mock(ContentLoaderService.class);

    //     SchemaResolver resolver = new CascaraSchemaResolver(mockParser, mockLoader);

    //     // 2. Create the Root and link it to a mock AST
    //     ObjectSchemaNode rootSchema = new ObjectSchemaNode("Root");
    //     MapAstNode rootAst = mock(MapAstNode.class);
    //     rootSchema.setOriginAst(rootAst);

    //     // 3. Mock the AST structure so resolveInternal can "find" the path
    //     // If resolveInternal uses map.get(part), we need to mock that behavior
    //     MapAstNode defsAst = mock(MapAstNode.class);
    //     MapAstNode userProfileAst = mock(MapAstNode.class);

    //     when(rootAst.get("$defs")).thenReturn(defsAst);
    //     when(defsAst.get("UserProfile")).thenReturn(userProfileAst);

    //     // 4. Test resolution: Fragment first, AstNode second
    //     String fragment = "#/$defs/UserProfile";
    //     AstNode resolvedAst = resolver.resolveFragment(fragment, rootAst);

    //     assertNotNull(resolvedAst, "Resolver should navigate the AST to find the node");
    // }

    @Test
    void testNestedObjectStructure() {
        // Create root object
        ObjectSchemaNode root = new ObjectSchemaNode(null);

        // Create a property
        BaseSchemaNode email = new ScalarSchemaNode(SchemaType.STRING, null);
        email.addRule(new RegexRule("^(.+)@(.+)$"));

        // Attach property to root
        root.addProperty("email", email);

        // Assertions
        assertNotNull(root.getProperty("email"));
        assertEquals(SchemaType.STRING, root.getProperty("email").getType());
    }

    @SuppressWarnings("rawtypes")
    @Test
    void testCircularReference() throws Exception {
        SchemaResolver mockResolver = mock(SchemaResolver.class);
        URI baseUri = URI.create("file:///workspace/user.json");

        // 1. Create the Root
        ObjectSchemaNode userSchema = new ObjectSchemaNode(null);
        MapAstNode mockAst = mock(MapAstNode.class);
        userSchema.setOriginAst(mockAst);
        userSchema.setOriginUri(baseUri);

        // 2. Create the Lazy node
        LazySchemaNode friendNode = new LazySchemaNode("#", mockResolver, userSchema, baseUri, mockAst, null, null);
        userSchema.addProperty("friend", friendNode);

        // 3. THE FIX: Update the mock to return a SchemaNode.
        // Since '#' points to the root, we return userSchema itself.
        when(mockResolver.resolve(eq("#"), any(SchemaNode.class))).thenReturn(userSchema);

        // 4. Verify
        assertEquals("#", friendNode.getRef());
        assertTrue(friendNode.isRef());
    }
}