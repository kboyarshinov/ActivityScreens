package com.kboyarshinov.activityscreen.processor;

import com.squareup.javapoet.*;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
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

    private final Map<String, String> argumentTypes = new HashMap<String, String>(20);

    private final Elements elementUtils;

    private final Filer filer;

    public CodeGenerator(Elements elementUtils, Filer filer) {
        this.elementUtils = elementUtils;
        this.filer = filer;
        fillArguments();
    }

    private void fillArguments() {
        argumentTypes.put("java.lang.String", "String");
        argumentTypes.put("int", "Int");
        argumentTypes.put("java.lang.Integer", "Int");
        argumentTypes.put("long", "Long");
        argumentTypes.put("java.lang.Long", "Long");
        argumentTypes.put("double", "Double");
        argumentTypes.put("java.lang.Double", "Double");
        argumentTypes.put("short", "Short");
        argumentTypes.put("java.lang.Short", "Short");
        argumentTypes.put("float", "Float");
        argumentTypes.put("java.lang.Float", "Float");
        argumentTypes.put("byte", "Byte");
        argumentTypes.put("java.lang.Byte", "Byte");
        argumentTypes.put("boolean", "Boolean");
        argumentTypes.put("java.lang.Boolean", "Boolean");
        argumentTypes.put("char", "Char");
        argumentTypes.put("java.lang.Character", "Char");
        argumentTypes.put("java.lang.CharSequence", "CharSequence");
        argumentTypes.put("android.os.Bundle", "Bundle");
        argumentTypes.put("android.os.Parcelable", "Parcelable");
    }

    /**
     * Generate the java code
     *
     * @throws java.io.IOException
     */
    public void generate(Collection<ActivityScreenAnnotatedClass> annotatedClasses) throws IOException {
        for (ActivityScreenAnnotatedClass annotatedClass : annotatedClasses) {
            TypeElement annotatedClassElement = annotatedClass.getTypeElement();
            Name activitySimpleName = annotatedClassElement.getSimpleName();
            String screenClassName = activitySimpleName + ActivityScreenAnnotatedClass.SUFFIX;

            List<ParameterSpec> parameters = new ArrayList<ParameterSpec>(annotatedClass.getRequiredFields().size());
            Set<ActivityArgAnnotatedField> requiredFields = annotatedClass.getRequiredFields();
            for (ActivityArgAnnotatedField requiredField : requiredFields) {
                parameters.add(ParameterSpec.builder(parseType(requiredField), requiredField.getKey()).build());
            }

            MethodSpec openMethod = generateOpenMethod(false, parameters);
            MethodSpec openForResultMethod = generateOpenMethod(true, parameters);
            MethodSpec createIntentMethod = generateCreateIntentMethod(activitySimpleName, parameters);
            MethodSpec injectMethod = generateInjectMethod(annotatedClassElement, parameters);

            MethodSpec privateConstructor = MethodSpec.constructorBuilder().
                    addModifiers(Modifier.PRIVATE).build();

            TypeSpec.Builder screenBuilder = TypeSpec.classBuilder(screenClassName).
                    addModifiers(Modifier.PUBLIC, Modifier.FINAL).
                    addMethod(privateConstructor).
                    addMethod(openMethod).
                    addMethod(openForResultMethod).
                    addMethod(createIntentMethod);
            if (!parameters.isEmpty()) {
                screenBuilder.addMethod(injectMethod);
            }

            TypeSpec screenClass = screenBuilder.build();
            PackageElement pkg = elementUtils.getPackageOf(annotatedClassElement);

            String packageName = pkg.isUnnamed() ? "" : pkg.getQualifiedName().toString();
            JavaFile javaFile = JavaFile.builder(packageName, screenClass).indent("    ").build();

            javaFile.writeTo(filer);
        }
    }

    private TypeName parseType(ActivityArgAnnotatedField field) {
        if (field.isPrimitive()) {
            return TypeName.get(field.getElement().asType());
        }
        return null;
    }

    private MethodSpec generateOpenMethod(boolean forResult, List<ParameterSpec> parameters) {
        MethodSpec.Builder openMethodBuilder = MethodSpec.methodBuilder(forResult ? "openForResult" : "open").
                addModifiers(Modifier.PUBLIC, Modifier.STATIC).
                returns(void.class).
                addParameter(activityClassName, "activity");
        if (parameters.isEmpty()) {
            openMethodBuilder.addStatement("Intent intent = createIntent(activity)");
        } else {
            openMethodBuilder.addStatement("Intent intent = createIntent(activity, " + toParametersString(parameters) + ")");
        }
        if (!parameters.isEmpty()) {
            for (ParameterSpec parameter : parameters) {
                openMethodBuilder.addParameter(parameter);
            }
        }
        if (forResult) {
            openMethodBuilder.addParameter(TypeName.INT, "requestCode");
            openMethodBuilder.addStatement("activity.startActivityForResult(intent, requestCode)");
        } else {
            openMethodBuilder.addStatement("activity.startActivity(intent)");
        }
        return openMethodBuilder.build();
    }

    private String toParametersString(List<ParameterSpec> parameters) {
        StringBuilder builder = new StringBuilder();
        boolean firstParameter = true;
        for (Iterator<ParameterSpec> i = parameters.iterator(); i.hasNext();) {
            ParameterSpec parameter = i.next();
            if (!firstParameter) builder.append(", ");
                builder.append(parameter.name);
            firstParameter = false;
        }
        return builder.toString();
    }

    private MethodSpec generateCreateIntentMethod(Name activitySimpleName, List<ParameterSpec> parameters) {
        MethodSpec.Builder createIntentBuilder = MethodSpec.methodBuilder("createIntent").
                addModifiers(Modifier.PUBLIC, Modifier.STATIC).
                addParameter(activityClassName, "activity").
                returns(intentClassName);
        createIntentBuilder.addStatement("$T intent = new $T(activity, $L.class)", intentClassName, intentClassName, activitySimpleName);
        if (!parameters.isEmpty()) {
            for (ParameterSpec parameter : parameters) {
                createIntentBuilder.addParameter(parameter);
                createIntentBuilder.addStatement("intent.putExtra($S, $L)", parameter.name, parameter.name);
            }
        }
        createIntentBuilder.addStatement("return intent");
        return createIntentBuilder.build();
    }

    private MethodSpec generateInjectMethod(TypeElement annotatedClassElement, List<ParameterSpec> parameters) {
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
