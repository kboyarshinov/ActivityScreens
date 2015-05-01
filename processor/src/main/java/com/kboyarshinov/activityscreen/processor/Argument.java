package com.kboyarshinov.activityscreen.processor;

import com.kboyarshinov.activityscreen.processor.args.ParcelableArrayArgument;
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

    protected Argument(String name, String operation, TypeName typeName) {
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

    private static HashMap<String, String> simpleOperations = new HashMap<String, String>(31);

    static {
        simpleOperations.put("int", "Int");
        simpleOperations.put("int[]", "IntArray");
        simpleOperations.put("long", "Long");
        simpleOperations.put("long[]", "LongArray");
        simpleOperations.put("double", "Double");
        simpleOperations.put("double[]", "DoubleArray");
        simpleOperations.put("short", "Short");
        simpleOperations.put("short[]", "ShortArray");
        simpleOperations.put("float", "Float");
        simpleOperations.put("float[]", "FloatArray");
        simpleOperations.put("byte", "Byte");
        simpleOperations.put("byte[]", "ByteArray");
        simpleOperations.put("boolean", "Boolean");
        simpleOperations.put("boolean[]", "BooleanArray");
        simpleOperations.put("char", "Char");
        simpleOperations.put("char[]", "CharArray");
        simpleOperations.put("java.lang.Character", "Char");
        simpleOperations.put("java.lang.Integer", "Int");
        simpleOperations.put("java.lang.Long", "Long");
        simpleOperations.put("java.lang.Double", "Double");
        simpleOperations.put("java.lang.Short", "Short");
        simpleOperations.put("java.lang.Float", "Float");
        simpleOperations.put("java.lang.Byte", "Byte");
        simpleOperations.put("java.lang.Boolean", "Boolean");
        simpleOperations.put("java.lang.String", "String");
        simpleOperations.put("java.lang.String[]", "StringArray");
        simpleOperations.put("java.lang.CharSequence", "CharSequence");
        simpleOperations.put("java.lang.CharSequence[]", "CharSequenceArray");
        simpleOperations.put("android.os.Bundle", "Bundle");
        simpleOperations.put("android.os.Parcelable", "Parcelable");
        simpleOperations.put("android.os.Parcelable[]", "ParcelableArray");
    }

    public static Argument from(ActivityArgAnnotatedField field, Elements elementUtils, Types typeUtils) throws UnsupportedTypeException {
        String name = field.getKey();
        Element element = field.getElement();
        TypeMirror typeMirror = element.asType();
        TypeName typeName = TypeName.get(typeMirror);

        String rawType = field.getType();
        String operation = simpleOperations.get(rawType);
        if (operation != null)
            return new Argument(name, operation, typeName);

        TypeMirror parcelableType = elementUtils.getTypeElement("android.os.Parcelable").asType();
        if (typeUtils.isAssignable(typeMirror, parcelableType)) {
            operation = "Parcelable";
            return new Argument(name, operation, typeName);
        } else if (typeUtils.isAssignable(typeMirror, typeUtils.getArrayType(parcelableType))) {
            operation = "ParcelableArray";
            return new ParcelableArrayArgument(name, operation, typeName);
        }

        throw new UnsupportedTypeException(element);
    }
}
