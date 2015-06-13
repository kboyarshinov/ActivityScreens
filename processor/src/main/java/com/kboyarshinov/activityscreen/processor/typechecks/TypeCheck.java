package com.kboyarshinov.activityscreen.processor.typechecks;

import com.kboyarshinov.activityscreen.processor.Argument;
import com.squareup.javapoet.TypeName;

import javax.lang.model.type.TypeMirror;

/**
 * @author Kirill Boyarshinov
 */
public interface TypeCheck {
    boolean check(TypeMirror typeMirror, TypeElements typeElements);
    Argument toArgument(String name, String key, TypeName typeName);
}
