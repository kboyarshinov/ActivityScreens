package com.kboyarshinov.activityscreen.processor;

import com.squareup.javapoet.*;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kirill Boyarshinov
 */
public final class CodeGenerator {
    private static final ClassName intentClassName = ClassName.get("android.content", "Intent");
    private static final ClassName activityClassName = ClassName.get("android.app", "Activity");

    private static final Map<String, String> ARGUMENT_TYPES = new HashMap<String, String>(20);

    static {
        ARGUMENT_TYPES.put("java.lang.String", "String");
        ARGUMENT_TYPES.put("int", "Int");
        ARGUMENT_TYPES.put("java.lang.Integer", "Int");
        ARGUMENT_TYPES.put("long", "Long");
        ARGUMENT_TYPES.put("java.lang.Long", "Long");
        ARGUMENT_TYPES.put("double", "Double");
        ARGUMENT_TYPES.put("java.lang.Double", "Double");
        ARGUMENT_TYPES.put("short", "Short");
        ARGUMENT_TYPES.put("java.lang.Short", "Short");
        ARGUMENT_TYPES.put("float", "Float");
        ARGUMENT_TYPES.put("java.lang.Float", "Float");
        ARGUMENT_TYPES.put("byte", "Byte");
        ARGUMENT_TYPES.put("java.lang.Byte", "Byte");
        ARGUMENT_TYPES.put("boolean", "Boolean");
        ARGUMENT_TYPES.put("java.lang.Boolean", "Boolean");
        ARGUMENT_TYPES.put("char", "Char");
        ARGUMENT_TYPES.put("java.lang.Character", "Char");
        ARGUMENT_TYPES.put("java.lang.CharSequence", "CharSequence");
        ARGUMENT_TYPES.put("android.os.Bundle", "Bundle");
        ARGUMENT_TYPES.put("android.os.Parcelable", "Parcelable");
    }

    private final Elements elementUtils;

    private final Filer filer;

    public CodeGenerator(Elements elementUtils, Filer filer) {
        this.elementUtils = elementUtils;
        this.filer = filer;
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

            MethodSpec openMethod = generateOpenMethod(false);
            MethodSpec openForResultMethod = generateOpenMethod(true);
            MethodSpec createIntentMethod = generateCreateIntentMethod(activitySimpleName);

            MethodSpec privateConstructor = MethodSpec.constructorBuilder().
                    addModifiers(Modifier.PRIVATE).build();

            TypeSpec screenClass = TypeSpec.classBuilder(screenClassName).
                    addModifiers(Modifier.PUBLIC, Modifier.FINAL).
                    addMethod(privateConstructor).
                    addMethod(openMethod).
                    addMethod(openForResultMethod).
                    addMethod(createIntentMethod).
                    build();

            PackageElement pkg = elementUtils.getPackageOf(annotatedClassElement);

            String packageName = pkg.isUnnamed() ? "" : pkg.getQualifiedName().toString();
            JavaFile javaFile = JavaFile.builder(packageName, screenClass).indent("    ").build();

            javaFile.writeTo(filer);
        }
    }

    private static MethodSpec generateOpenMethod(boolean forResult) {
        MethodSpec.Builder openMethodBuilder = MethodSpec.methodBuilder(forResult ? "openForResult" : "open").
                addModifiers(Modifier.PUBLIC, Modifier.STATIC).
                returns(void.class).
                addParameter(activityClassName, "activity");
        openMethodBuilder.addStatement("Intent intent = createIntent(activity)");
        if (forResult) {
            openMethodBuilder.addParameter(TypeName.INT, "requestCode");
            openMethodBuilder.addStatement("activity.startActivityForResult(intent, requestCode)");
        } else {
            openMethodBuilder.addStatement("activity.startActivity(intent)");
        }
        return openMethodBuilder.build();
    }

    private static MethodSpec generateCreateIntentMethod(Name activitySimpleName) {
        MethodSpec.Builder createIntentBuilder = MethodSpec.methodBuilder("createIntent").
                addModifiers(Modifier.PUBLIC, Modifier.STATIC).
                addParameter(activityClassName, "activity").
                returns(intentClassName);
        createIntentBuilder.addStatement("$T intent = new $T(activity, $L.class)", intentClassName, intentClassName, activitySimpleName);
        createIntentBuilder.addStatement("return intent");
        return createIntentBuilder.build();
    }
}
