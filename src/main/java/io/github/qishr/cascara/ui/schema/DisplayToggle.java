package io.github.qishr.cascara.ui.schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DisplayToggle {
    public static final String UI_DISPLAY_TOGGLE = "ui-display-toggle";
    boolean value() default false;
}