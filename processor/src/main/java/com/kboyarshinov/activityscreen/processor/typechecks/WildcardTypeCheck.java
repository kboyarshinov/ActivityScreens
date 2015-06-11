package com.kboyarshinov.activityscreen.processor.typechecks;

import com.kboyarshinov.activityscreen.processor.Argument;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * @author Kirill Boyarshinov
 */
public class WildcardTypeCheck implements TypeCheck {

    private final String typeClassName;
    private final String wildcardTypeClassName;
    private final String operation;

    public WildcardTypeCheck(String typeClassName, String wildcardTypeClassName, String operation) {
        this.typeClassName = typeClassName;
        this.wildcardTypeClassName = wildcardTypeClassName;
        this.operation = operation;
    }

    @Override
    public boolean check(TypeMirror typeMirror, TypeElements typeElements) {
        TypeElement arrayListType = typeElements.getElement(wildcardTypeClassName);
        TypeMirror type = typeElements.getElement(typeClassName).asType();
        TypeMirror declaredType = typeElements.getDeclaredType(arrayListType, typeElements.getWildCardType(type, null));
        return typeElements.isAssignable(typeMirror, declaredType);
    }

    @Override
    public Argument toArgument(String name, String key, TypeName typeName) {
        return new Argument(name, key, operation, operation, typeName);
    }
}
