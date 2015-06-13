package com.kboyarshinov.activityscreen.processor.typechecks;

import com.kboyarshinov.activityscreen.processor.Argument;
import com.kboyarshinov.activityscreen.processor.args.ParcelableArrayArgument;
import com.squareup.javapoet.TypeName;

import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeMirror;

/**
 * @author Kirill Boyarshinov
 */
public class ArrayTypeCheck implements TypeCheck {
    private final String typeClassName;
    private final String operationPut;

    public ArrayTypeCheck(String typeClassName, String operationPut) {
        this.typeClassName = typeClassName;
        this.operationPut = operationPut;
    }

    @Override
    public boolean check(TypeMirror typeMirror, TypeElements typeElements) {
        TypeMirror elementMirror = typeElements.getElement(typeClassName).asType();
        ArrayType arrayType = typeElements.getArrayType(elementMirror);
        return typeElements.isAssignable(typeMirror, arrayType);
    }

    @Override
    public Argument toArgument(String name, String key, TypeName typeName) {
        return new ParcelableArrayArgument(name, key, operationPut, typeName);
    }
}
