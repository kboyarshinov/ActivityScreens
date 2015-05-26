package com.kboyarshinov.activityscreen.processor.typechecks;

import com.kboyarshinov.activityscreen.processor.Argument;
import com.kboyarshinov.activityscreen.processor.args.ParcelableArrayArgument;
import com.squareup.javapoet.TypeName;

import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeMirror;

/**
 * @author Kirill Boyarshinov
 */
public abstract class ArrayTypeCheck implements TypeCheck {
    private final String typeClassName;

    public ArrayTypeCheck(String typeClassName) {
        this.typeClassName = typeClassName;
    }

    @Override
    public boolean check(TypeMirror typeMirror, TypeElements typeElements) {
        TypeMirror elementMirror = typeElements.getElement(typeClassName).asType();
        ArrayType arrayType = typeElements.getArrayType(elementMirror);
        return typeElements.isAssignable(typeMirror, arrayType);
    }

    @Override
    public Argument toArgument(String name, String key, TypeName typeName) {
        String operation = operationPut();
        return new ParcelableArrayArgument(name, key, operation, typeName);
    }

    protected abstract String operationPut();
}
