package com.kboyarshinov.activityscreens.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to indicate classes that are screens
 *
 * @author Kirill Boyarshinov
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface ActivityScreen {

}
