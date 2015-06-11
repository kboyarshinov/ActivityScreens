package com.kboyarshinov.activityscreen.processor;

import com.kboyarshinov.activityscreen.processor.typechecks.*;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Kirill Boyarshinov
 */
public class Argument {
    private final String name;
    private final String key;
    private final String operationGet;
    private final TypeName typeName;
    private final String operationPut;

    private boolean requiresCast;

    public Argument(String name, String key, String operationGet, String operationPut, TypeName typeName) {
        this.name = name;
        this.key = key;
        this.operationGet = operationGet;
        this.operationPut = operationPut;
        this.typeName = typeName;
    }

    public void setRequiresCast(boolean requiresCast) {
        this.requiresCast = requiresCast;
    }

    public String name() {
        return name;
    }

    public String operationGet() {
        return operationGet;
    }

    public TypeName typeName() {
        return typeName;
    }

    public String key() {
        return key;
    }

    public void generatePutMethod(MethodSpec.Builder builder) {
        builder.addStatement("bundle.put$L($S, $L)", operationPut, key, name);
    }

    public void generateGetMethod(MethodSpec.Builder builder) {
        if (!requiresCast) {
            builder.addStatement("activity.$L = bundle.get$L($S)", name, operationGet, key);
        } else {
            builder.addStatement("activity.$L = ($T) bundle.get$L($S)", name, typeName, operationGet, key);
        }
    }

    public FieldSpec asField(Modifier... modifiers) {
        return FieldSpec.builder(typeName, name, modifiers).build();
    }

    public ParameterSpec asParameter() {
        return ParameterSpec.builder(typeName, name).build();
    }


    private static HashMap<String, String> simpleOperations = new HashMap<String, String>(32);
    private static List<TypeCheck> typeChecks = new ArrayList<TypeCheck>();

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
        simpleOperations.put("java.io.Serializable", "Serializable");

        typeChecks.add(new ParcelableTypeCheck());
        typeChecks.add(new WildcardArrayListTypeCheck("android.os.Parcelable", "ParcelableArrayList"));
        typeChecks.add(new TypedArrayListTypeCheck(String.class.getName(), "StringArrayList"));
        typeChecks.add(new TypedArrayListTypeCheck(Integer.class.getName(), "IntegerArrayList"));
        typeChecks.add(new TypedArrayListTypeCheck(CharSequence.class.getName(), "CharSequenceArrayList"));
        typeChecks.add(new ArrayTypeCheck("android.os.Parcelable") {
            @Override
            protected String operationPut() {
                return "ParcelableArray";
            }
        });
        typeChecks.add(new SerializableTypeCheck());
    }

    public static Argument from(ActivityArgAnnotatedField field, TypeElements typeElements) throws UnsupportedTypeException {
        String key = field.getKey();
        String name = field.getName();
        Element element = field.getElement();
        TypeMirror typeMirror = element.asType();
        TypeName typeName = TypeName.get(typeMirror);

        String rawType = field.getType();
        String operation = simpleOperations.get(rawType);
        if (operation != null)
            return new Argument(name, key, operation, operation, typeName);

        for (TypeCheck typeCheck : typeChecks) {
            if (typeCheck.check(typeMirror, typeElements))
                return typeCheck.toArgument(name, key, typeName);
        }

        throw new UnsupportedTypeException(element);
    }
}
