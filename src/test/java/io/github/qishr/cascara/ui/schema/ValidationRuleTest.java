package io.github.qishr.cascara.ui.schema;

import io.github.qishr.cascara.common.lang.QuoteStyle;
import io.github.qishr.cascara.schema.structure.ArraySchemaNode;
import io.github.qishr.cascara.schema.structure.BaseSchemaNode;
import io.github.qishr.cascara.schema.structure.ObjectSchemaNode;
import io.github.qishr.cascara.schema.structure.ScalarSchemaNode;
import io.github.qishr.cascara.schema.SchemaType;
import io.github.qishr.cascara.schema.rule.MinValueRule;
import io.github.qishr.cascara.schema.rule.RegexRule;
import io.github.qishr.cascara.schema.util.ValidationResult;
import io.github.qishr.cascara.lang.yaml.ast.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidationRuleTest {

    /**
     * Helper to create a location-aware Scalar node for testing.
     */
    private YamlScalarNode createMockScalar(Object value, int line, int col) {
        YamlScalarNode node = new YamlScalarNode(line, col, String.valueOf(value), String.valueOf(value), QuoteStyle.PLAIN);
        // Create a token so the node has coordinate metadata
        // YamlToken mockToken = new YamlToken(null, String.valueOf(value), value, 0, line, col);
        // node.setStartToken(mockToken);
        return node;
    }

    @Test
    void testNestedValidation() {
        // Setup Schema: User { age: Integer(min: 18) }
        ObjectSchemaNode userSchema = new ObjectSchemaNode(null);
        BaseSchemaNode age = new ScalarSchemaNode(SchemaType.INTEGER, null);
        age.addRule(new MinValueRule(18));
        userSchema.addProperty("age", age);

        // Setup Data AST: { age: 16 } at line 5, col 10
        YamlMapNode dataNode = new YamlMapNode();
        YamlScalarNode key = createMockScalar("age", 5, 1);
        YamlScalarNode value = createMockScalar(16, 5, 10);
        dataNode.put(new YamlMapEntryNode(key, value));

        ValidationResult result = new ValidationResult();
        userSchema.validate(dataNode, "user", result);

        assertFalse(result.isValid());
        assertEquals("user.age", result.getMessages().get(0).path());
        assertEquals(5, result.getMessages().get(0).line());
        assertEquals(10, result.getMessages().get(0).column());
    }

    @Test
    void testArrayValidation() {
        // Setup Schema: tags: Array<String(regex: ^[a-z]+$)>
        ArraySchemaNode tagsSchema = new ArraySchemaNode(null);
        BaseSchemaNode tagItem = new ScalarSchemaNode(SchemaType.STRING, null);
        tagItem.addRule(new RegexRule("^[a-z]+$"));
        tagsSchema.setItemTemplate(tagItem);

        // Setup Data AST: ["valid", "INVALID123"]
        YamlSequenceNode seqNode = new YamlSequenceNode();
        seqNode.add(createMockScalar("valid", 1, 1));
        seqNode.add(createMockScalar("INVALID123", 2, 5));

        ValidationResult result = new ValidationResult();
        tagsSchema.validate(seqNode, "tags", result);

        assertFalse(result.isValid());
        // Verify we caught the second element specifically
        ValidationResult.Message error = result.getMessages().get(0);
        assertEquals("tags[1]", error.path());
        assertEquals(2, error.line());
        assertEquals(5, error.column());
    }
}
