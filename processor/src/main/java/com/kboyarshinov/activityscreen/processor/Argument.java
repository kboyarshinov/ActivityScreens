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
    private final String operation;
    private final TypeName typeName;

    private String putOperation = "Extra";

    public Argument(String name, String key, String operation, TypeName typeName) {
        this.name = name;
        this.key = key;
        this.operation = operation;
        this.typeName = typeName;
    }

    public void setPutOperation(String putOperation) {
        this.putOperation = putOperation;
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

    public String getKey() {
        return key;
    }

    public void generatePutMethod(MethodSpec.Builder builder) {
        if (putOperation == null)
            throw new NullPointerException("Put operation not set");
        builder.addStatement("intent.put$L($S, $L)", putOperation, key, name);
    }

    public void generateGetMethod(MethodSpec.Builder builder) {
        builder.addStatement("activity.$L = bundle.get$L($S)", name, operation, key);
    }

    public FieldSpec asField(Modifier... modifiers) {
        return FieldSpec.builder(typeName, name, modifiers).build();
    }

    public ParameterSpec asParameter() {
        return ParameterSpec.builder(typeName, name).build();
    }


    private static HashMap<String, String> simpleOperations = new HashMap<String, String>(31);
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

        typeChecks.add(new ParcelableTypeCheck());
        typeChecks.add(new TypedArrayListTypeCheck(String.class.getName()) {
            @Override
            protected String operationGet() {
                return "StringArrayList";
            }

            @Override
            protected String operationPut() {
                return "StringArrayListExtra";
            }
        });
        typeChecks.add(new TypedArrayListTypeCheck(Integer.class.getName()) {
            @Override
            protected String operationGet() {
                return "IntegerArrayList";
            }

            @Override
            protected String operationPut() {
                return "IntegerArrayListExtra";
            }
        });
        typeChecks.add(new TypedArrayListTypeCheck(CharSequence.class.getName()) {
            @Override
            protected String operationGet() {
                return "CharSequenceArrayList";
            }

            @Override
            protected String operationPut() {
                return "CharSequenceArrayListExtra";
            }
        });
        typeChecks.add(new ArrayTypeCheck("android.os.Parcelable") {
            @Override
            protected String operationPut() {
                return "ParcelableArray";
            }
        });
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
            return new Argument(name, key, operation, typeName);

        for (TypeCheck typeCheck : typeChecks) {
            if (typeCheck.check(typeMirror, typeElements))
                return typeCheck.toArgument(name, key, typeName);
        }

        throw new UnsupportedTypeException(element);
    }
}
