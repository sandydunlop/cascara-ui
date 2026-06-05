package io.github.qishr.cascara.ui.schema;

import java.lang.annotation.*;

// TODO: Should this not be elsewhere?
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Hidden {
    public static final String UI_HIDDEN = "ui-hidden";
}