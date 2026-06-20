package io.github.qishr.cascara.ui.schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SearchColumns {
    public static final String UI_SEARCH_COLUMNS = "ui-search-columns";
    String[] value() default {};

    // TODO: Add to type analyzer
}