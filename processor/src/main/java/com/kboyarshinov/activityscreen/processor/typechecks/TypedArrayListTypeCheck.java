package com.kboyarshinov.activityscreen.processor.typechecks;

import com.kboyarshinov.activityscreen.processor.Argument;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;

/**
 * @author Kirill Boyarshinov
 */
public abstract class TypedArrayListTypeCheck implements TypeCheck {
    private final String typeClassName;

    public TypedArrayListTypeCheck(String typeClassName) {
        this.typeClassName = typeClassName;
    }

    @Override
    public boolean check(TypeMirror typeMirror, TypeElements typeElements) {
        TypeElement arrayListType = typeElements.getElement(ArrayList.class.getName());
        TypeMirror type = typeElements.getElement(typeClassName).asType();
        TypeMirror declaredType = typeElements.getDeclaredType(arrayListType, type);
        return typeElements.isAssignable(typeMirror, declaredType);
    }

    @Override
    public Argument toArgument(String name, String key, TypeName typeName) {
        Argument argument = new Argument(name, key, operationGet(), typeName);
        argument.setPutOperation(operationPut());
        return argument;
    }

    protected abstract String operationGet();
    protected abstract String operationPut();
}
