package io.github.qishr.cascara.ui.schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FileConstraint {
    public static final String X_FILE_CONSTRAINT = "x-file";
    String[] extensions() default {"*.*"};
    String initialDirectory() default "";
    boolean mustExist() default true;
    boolean absolute() default false;
}