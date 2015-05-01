package com.kboyarshinov.activityscreen.processor;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

/**
 * @author Kirill Boyarshinov
 */
public class Argument {
    public final String name;
    public final ArgumentType type;
    public final TypeName typeName;

    public Argument(String name, ArgumentType type, TypeName typeName) {
        this.name = name;
        this.type = type;
        this.typeName = typeName;
    }

    public FieldSpec asField(Modifier... modifiers) {
        return FieldSpec.builder(typeName, name, modifiers).build();
    }

    public ParameterSpec asParameter() {
        return ParameterSpec.builder(typeName, name).build();
    }

    public static Argument from(ActivityArgAnnotatedField field) throws UnsupportedTypeException {
        String name = field.getKey();
        String rawType = field.getType();
        Element element = field.getElement();
        ArgumentType type = ArgumentType.from(rawType);
        if (type == null) {
            throw new UnsupportedTypeException(element);
        }
        TypeName typeName = TypeName.get(element.asType());
        return new Argument(name, type, typeName);
    }
}
