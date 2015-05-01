package com.kboyarshinov.activityscreen.processor;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.HashMap;

/**
 * @author Kirill Boyarshinov
 */
public class Argument {
    private final String name;
    private final String operation;
    private final TypeName typeName;

    public Argument(String name, String operation, TypeName typeName) {
        this.name = name;
        this.operation = operation;
        this.typeName = typeName;
    }

    public String getName() {
        return name;
    }

    public String getOperation() {
        return operation;
    }

    public TypeName getTypeName() {
        return typeName;
    }

    public void generatePutMethod(MethodSpec.Builder builder) {
        builder.addStatement("intent.putExtra($S, $L)", name, name);
    }

    public void generateGetMethod(MethodSpec.Builder builder) {
        builder.addStatement("activity.$L = bundle.get$L($S)", name, operation, name);
    }

    public FieldSpec asField(Modifier... modifiers) {
        return FieldSpec.builder(typeName, name, modifiers).build();
    }

    public ParameterSpec asParameter() {
        return ParameterSpec.builder(typeName, name).build();
    }

    private static HashMap<String, String> operationsForType = new HashMap<String, String>();

    static {
        operationsForType.put("int", "Int");
        operationsForType.put("int[]", "IntArray");
        operationsForType.put("long", "Long");
        operationsForType.put("long[]", "LongArray");
        operationsForType.put("double", "Double");
        operationsForType.put("double[]", "DoubleArray");
        operationsForType.put("short", "Short");
        operationsForType.put("short[]", "ShortArray");
        operationsForType.put("float", "Float");
        operationsForType.put("float[]", "FloatArray");
        operationsForType.put("byte", "Byte");
        operationsForType.put("byte[]", "ByteArray");
        operationsForType.put("boolean", "Boolean");
        operationsForType.put("boolean[]", "BooleanArray");
        operationsForType.put("char", "Char");
        operationsForType.put("char[]", "CharArray");
        operationsForType.put("java.lang.Character", "Char");
        operationsForType.put("java.lang.Integer", "Int");
        operationsForType.put("java.lang.Long", "Long");
        operationsForType.put("java.lang.Double", "Double");
        operationsForType.put("java.lang.Short", "Short");
        operationsForType.put("java.lang.Float", "Float");
        operationsForType.put("java.lang.Byte", "Byte");
        operationsForType.put("java.lang.Boolean", "Boolean");
        operationsForType.put("java.lang.String", "String");
        operationsForType.put("java.lang.CharSequence", "CharSequence");
        operationsForType.put("java.lang.CharSequence[]", "CharSequenceArray");
        operationsForType.put("android.os.Bundle", "Bundle");
        operationsForType.put("android.os.Parcelable", "Parcelable");
        operationsForType.put("android.os.Parcelable[]", "ParcelableArray");
    }

    public static Argument from(ActivityArgAnnotatedField field, Elements elementUtils, Types typeUtils) throws UnsupportedTypeException {
        String name = field.getKey();
        String rawType = field.getType();
        Element element = field.getElement();
        String operation = operationsForType.get(rawType);
        TypeMirror typeMirror = element.asType();
        if (operation == null) {
            TypeMirror parcelableType = elementUtils.getTypeElement("android.os.Parcelable").asType();
            if (typeUtils.isAssignable(typeMirror, parcelableType)) {
                operation = "Parcelable";
            } else {
                throw new UnsupportedTypeException(element);
            }
        }
        TypeName typeName = TypeName.get(typeMirror);
        return new Argument(name, operation, typeName);
    }
}
