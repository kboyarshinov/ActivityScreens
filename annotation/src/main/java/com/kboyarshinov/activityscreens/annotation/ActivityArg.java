package com.kboyarshinov.activityscreens.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotated every field that should be a Activity argument with this annotation
 *
 * @author Kirill Boyarshinov
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface ActivityArg {
    /**
     * Specifies if the argument is required (default) or not
     * @return true if required, false otherwise
     */
    boolean required() default true;
    /**
     * Key in the arguments bundle, by default uses the field name, minus the "m" prefix.
     */
    String key() default "";
}
