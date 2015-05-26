package com.kboyarshinov.activityscreen.processor.typechecks;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.HashMap;

/**
 * @author Kirill Boyarshinov
 */
public class TypeElements {

    private final Elements elementUtils;
    private final Types typeUtils;

    private final HashMap<String, TypeElement> elements = new HashMap<String, TypeElement>();

    public TypeElements(Elements elementUtils, Types typeUtils) {
        this.elementUtils = elementUtils;
        this.typeUtils = typeUtils;
    }

    public TypeElement getElement(String className) {
        if (elements.containsKey(className))
            return elements.get(className);
        TypeElement typeElement = elementUtils.getTypeElement(className);
        elements.put(className, typeElement);
        return typeElement;
    }

    public boolean isAssignable(TypeMirror t1, TypeMirror t2) {
        return typeUtils.isAssignable(t1, t2);
    }

    public TypeMirror getDeclaredType(TypeElement typeElement, TypeMirror... type) {
        return typeUtils.getDeclaredType(typeElement, type);
    }

    public ArrayType getArrayType(TypeMirror typeMirror) {
        return typeUtils.getArrayType(typeMirror);
    }
}
