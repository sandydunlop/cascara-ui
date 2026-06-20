package io.github.qishr.cascara.ui.form;

import io.github.qishr.cascara.schema.structure.SchemaNode;
import io.github.qishr.cascara.schema.util.ValidationResult;

public interface FieldValidator {
    ValidationResult performValidation(Object value, String path, SchemaNode schema);
}
