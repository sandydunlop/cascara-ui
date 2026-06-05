package io.github.qishr.cascara.ui.schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ItemsEditableConstraint {
    public static final String UI_ITEMS_EDITABLE = "ui-items-editable";
    boolean value() default true;

    // TODO: Add to type analyzer
}