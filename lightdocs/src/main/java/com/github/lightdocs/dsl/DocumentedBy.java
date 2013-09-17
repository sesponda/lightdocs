package com.github.lightdocs.dsl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER, ElementType.METHOD, ElementType.TYPE })
public @interface DocumentedBy {
    /**
     * Should point to a valid resource containing a DSL documentation script.
     * The resource will be loaded using the current thread Classloader.
     * Prefixes file:// and http:// are also supported.
     */
    String value() default "";
}
