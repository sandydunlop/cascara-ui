package io.github.qishr.cascara.ui.schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OptionConstraint {
    public static final String UI_OPTION_PROVIDER = "ui-option-provider";
    public static final String NAME = "name";
    public static final String PARAMETER = "parameter";
    // public static final String LABEL_FIELD = "labelField";
    String provider() default "";
    String parameter() default "";
    // String labelField() default "";
}