package com.kboyarshinov.activityscreen.processor.typechecks;

import com.kboyarshinov.activityscreen.processor.Argument;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;

/**
 * @author Kirill Boyarshinov
 */
public class WildcardArrayListTypeCheck implements TypeCheck {

    private final String typeClassName;
    private final String operation;

    public WildcardArrayListTypeCheck(String typeClassName, String operation) {
        this.typeClassName = typeClassName;
        this.operation = operation;
    }

    @Override
    public boolean check(TypeMirror typeMirror, TypeElements typeElements) {
        TypeElement arrayListType = typeElements.getElement(ArrayList.class.getName());
        TypeMirror type = typeElements.getElement(typeClassName).asType();
        TypeMirror declaredType = typeElements.getDeclaredType(arrayListType, typeElements.getWildCardType(type, null));
        return typeElements.isAssignable(typeMirror, declaredType);
    }

    @Override
    public Argument toArgument(String name, String key, TypeName typeName) {
        return new Argument(name, key, operation, operation, typeName);
    }
}
