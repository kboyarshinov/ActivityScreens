package com.kboyarshinov.activityscreen.processor.typechecks;

import com.kboyarshinov.activityscreen.processor.Argument;
import com.squareup.javapoet.TypeName;

import javax.lang.model.type.TypeMirror;
import java.io.Serializable;

/**
 * @author Kirill Boyarshinov
 */
public final class SerializableTypeCheck implements TypeCheck {
    @Override
    public boolean check(TypeMirror typeMirror, TypeElements typeElements) {
        TypeMirror serializable = typeElements.getElement(Serializable.class.getName()).asType();
        return typeElements.isAssignable(typeMirror, serializable);
    }

    @Override
    public Argument toArgument(String name, String key, TypeName typeName) {
        String serializable = "Serializable";
        Argument argument = new Argument(name, key, serializable, typeName);
        argument.setRequiresCast(true);
        return argument;
    }
}
