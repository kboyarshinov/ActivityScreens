package com.kboyarshinov.activityscreen.processor;

import com.google.common.collect.Iterables;
import com.squareup.javapoet.*;
import org.apache.commons.lang3.text.WordUtils;

import javax.annotation.processing.Filer;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import java.io.IOException;
import java.util.*;

/**
 * @author Kirill Boyarshinov
 */
public final class CodeGenerator {
    private final ClassName intentClassName = ClassName.get("android.content", "Intent");
    private final ClassName activityClassName = ClassName.get("android.app", "Activity");
    private final ClassName bundleClassName = ClassName.get("android.os", "Bundle");

    private final Map<String, String> argumentTypes = new HashMap<String, String>();

    private final Elements elementUtils;

    private final Filer filer;

    public CodeGenerator(Elements elementUtils, Filer filer) {
        this.elementUtils = elementUtils;
        this.filer = filer;
        fillArguments();
    }

    private void fillArguments() {
        argumentTypes.put("int", "Int");
        argumentTypes.put("int[]", "IntArray");
        argumentTypes.put("long", "Long");
        argumentTypes.put("long[]", "LongArray");
        argumentTypes.put("double", "Double");
        argumentTypes.put("double[]", "DoubleArray");
        argumentTypes.put("short", "Short");
        argumentTypes.put("short[]", "ShortArray");
        argumentTypes.put("float", "Float");
        argumentTypes.put("float[]", "FloatArray");
        argumentTypes.put("byte", "Byte");
        argumentTypes.put("byte[]", "ByteArray");
        argumentTypes.put("boolean", "Boolean");
        argumentTypes.put("boolean[]", "BooleanArray");
        argumentTypes.put("char", "Char");
        argumentTypes.put("char[]", "CharArray");
        argumentTypes.put("java.lang.Character", "Char");
        argumentTypes.put("java.lang.Integer", "Int");
        argumentTypes.put("java.lang.Long", "Long");
        argumentTypes.put("java.lang.Double", "Double");
        argumentTypes.put("java.lang.Short", "Short");
        argumentTypes.put("java.lang.Float", "Float");
        argumentTypes.put("java.lang.Byte", "Byte");
        argumentTypes.put("java.lang.Boolean", "Boolean");
        argumentTypes.put("java.lang.String", "String");
        argumentTypes.put("java.lang.CharSequence", "CharSequence");
        argumentTypes.put("java.lang.CharSequence[]", "CharSequenceArray");
        argumentTypes.put("android.os.Bundle", "Bundle");
    }

    /**
     * Generate the java code
     *
     * @throws java.io.IOException
     */
    public void generate(Collection<ActivityScreenAnnotatedClass> annotatedClasses) throws IOException, UnsupportedTypeException {
        for (ActivityScreenAnnotatedClass annotatedClass : annotatedClasses) {
            TypeElement annotatedClassElement = annotatedClass.getTypeElement();
            TypeName activityTypeName = TypeName.get(annotatedClassElement.asType());
            Name activitySimpleName = annotatedClassElement.getSimpleName();
            String screenClassName = activitySimpleName + ActivityScreenAnnotatedClass.SUFFIX;
            PackageElement pkg = elementUtils.getPackageOf(annotatedClassElement);
            String packageName = pkg.isUnnamed() ? "" : pkg.getQualifiedName().toString();
            ClassName activityScreenClassName = ClassName.get(packageName, screenClassName);

            TypeSpec.Builder classBuilder = TypeSpec.classBuilder(screenClassName);

            // collect parameters
            Set<ActivityArgAnnotatedField> requiredFields = annotatedClass.getRequiredFields();
            Set<ActivityArgAnnotatedField> optionalFields = annotatedClass.getOptionalFields();
            List<ParameterSpec> requiredParameters = new ArrayList<ParameterSpec>(requiredFields.size());
            List<ParameterSpec> optionalParameters = new ArrayList<ParameterSpec>(optionalFields.size());
            for (ActivityArgAnnotatedField field : requiredFields) {
                requiredParameters.add(ParameterSpec.builder(parseType(field), field.getKey()).build());
            }
            for (ActivityArgAnnotatedField field : optionalFields) {
                optionalParameters.add(ParameterSpec.builder(parseType(field), field.getKey()).build());
            }

            // add fields
            for (ParameterSpec requiredParameter : requiredParameters) {
                classBuilder.addField(requiredParameter.type, requiredParameter.name, Modifier.PUBLIC, Modifier.FINAL);
            }
            for (ParameterSpec optionalParameter : optionalParameters) {
                classBuilder.addField(optionalParameter.type, optionalParameter.name, Modifier.PRIVATE);
            }

            // add constructor
            MethodSpec.Builder costructorBuilder = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC);
            for (ParameterSpec requiredParameter : requiredParameters) {
                costructorBuilder.addParameter(requiredParameter);
                costructorBuilder.addStatement("this.$L = $L", requiredParameter.name, requiredParameter.name);
            }
            MethodSpec constructor = costructorBuilder.build();
            classBuilder.addMethod(constructor);

            // add setters
            for (ParameterSpec optionalParameter : optionalParameters) {
                MethodSpec setter = MethodSpec.methodBuilder(String.format("set%s", WordUtils.capitalize(optionalParameter.name))).
                    addStatement("this.$L = $L", optionalParameter.name, optionalParameter.name).
                    addStatement("return this").
                    returns(activityScreenClassName).
                    addModifiers(Modifier.PUBLIC).build();
                classBuilder.addMethod(setter);
            }

            // add getters
            for (ParameterSpec optionalParameter : optionalParameters) {
                MethodSpec getter = MethodSpec.methodBuilder(String.format("get%s", WordUtils.capitalize(optionalParameter.name))).
                        addStatement("return $L", optionalParameter.name).
                        returns(optionalParameter.type).
                        addModifiers(Modifier.PUBLIC).build();
                classBuilder.addMethod(getter);
            }

            // add open, openForResult, create methods
            MethodSpec openMethod = generateOpenMethod(false);
            MethodSpec openForResultMethod = generateOpenMethod(true);
            MethodSpec createIntentMethod = generateToIntentMethod(activitySimpleName, requiredParameters, optionalParameters);
            classBuilder.addMethod(openMethod).
                    addMethod(openForResultMethod).
                    addMethod(createIntentMethod);

            // add inject method if needed
            if (!requiredParameters.isEmpty() || !optionalParameters.isEmpty()) {
                MethodSpec injectMethod = generateInjectMethod(annotatedClassElement, Iterables.concat(requiredParameters, optionalParameters));
                classBuilder.addMethod(injectMethod);
            }

            // write class to file
            TypeSpec screenClass = classBuilder.addModifiers(Modifier.PUBLIC, Modifier.FINAL).build();
            JavaFile javaFile = JavaFile.builder(packageName, screenClass).indent("    ").build();
            javaFile.writeTo(filer);
        }
    }

    private TypeName parseType(ActivityArgAnnotatedField field) throws UnsupportedTypeException {
        String type = field.getType();
        Element element = field.getElement();
        if (!argumentTypes.containsKey(type))
            throw new UnsupportedTypeException(element);
        if (field.isArray()) {
            return ArrayTypeName.get(element.asType());
        }
        return TypeName.get(element.asType());
    }

    private MethodSpec generateOpenMethod(boolean forResult) {
        MethodSpec.Builder openMethodBuilder = MethodSpec.methodBuilder(forResult ? "openForResult" : "open").
                addModifiers(Modifier.PUBLIC).
                returns(void.class).
                addParameter(activityClassName, "activity");
        openMethodBuilder.addStatement("Intent intent = toIntent(activity)");
        if (forResult) {
            openMethodBuilder.addParameter(TypeName.INT, "requestCode");
            openMethodBuilder.addStatement("activity.startActivityForResult(intent, requestCode)");
        } else {
            openMethodBuilder.addStatement("activity.startActivity(intent)");
        }
        return openMethodBuilder.build();
    }

    private MethodSpec generateToIntentMethod(Name activitySimpleName, Iterable<ParameterSpec> requiredParameters, Iterable<ParameterSpec> optionalParameters) {
        MethodSpec.Builder createIntentBuilder = MethodSpec.methodBuilder("toIntent").
                addModifiers(Modifier.PUBLIC).
                addParameter(activityClassName, "activity").
                returns(intentClassName);
        createIntentBuilder.addStatement("$T intent = new $T(activity, $L.class)", intentClassName, intentClassName, activitySimpleName);
        for (ParameterSpec parameter : requiredParameters) {
            createIntentBuilder.addStatement("intent.putExtra($S, $L)", parameter.name, parameter.name);
        }
        for (ParameterSpec parameter : optionalParameters) {
            boolean primitive = parameter.type.isPrimitive();
            if (!primitive)
                createIntentBuilder.beginControlFlow("if ($L != null)", parameter.name);
            createIntentBuilder.addStatement("intent.putExtra($S, $L)", parameter.name, parameter.name);
            if (!primitive)
                createIntentBuilder.endControlFlow();
        }
        createIntentBuilder.addStatement("return intent");
        return createIntentBuilder.build();
    }

    private MethodSpec generateInjectMethod(TypeElement annotatedClassElement, Iterable<ParameterSpec> parameters) {
        MethodSpec.Builder injectBuilder = MethodSpec.methodBuilder("inject").
                addModifiers(Modifier.PUBLIC, Modifier.STATIC).
                addParameter(TypeName.get(annotatedClassElement.asType()), "activity").
                returns(void.class);
        injectBuilder.addStatement("$T bundle = activity.getIntent().getExtras()", bundleClassName);
        for (ParameterSpec parameter : parameters) {
            injectBuilder.addStatement("activity.$L = bundle.get$L($S)", parameter.name, getArgumentTypeString(parameter), parameter.name);
        }
        return injectBuilder.build();
    }

    private String getArgumentTypeString(ParameterSpec parameterSpec) {
        return argumentTypes.get(parameterSpec.type.toString());
    }
}
