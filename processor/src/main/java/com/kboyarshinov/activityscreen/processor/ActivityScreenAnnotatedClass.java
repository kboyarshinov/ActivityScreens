package com.kboyarshinov.activityscreen.processor;

import com.kboyarshinov.activityscreens.annotation.ActivityScreen;
import com.squareup.javapoet.*;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.io.IOException;

/**
 * Holds the information about a class annotated with @ActivityScreen
 *
 * @author Kirill Boyarshinov
 */
public class ActivityScreenAnnotatedClass {
    private static final String SUFFIX = "Screen";
    private TypeElement annotatedClassElement;

    public ActivityScreenAnnotatedClass(TypeElement classElement) {
        this.annotatedClassElement = classElement;
        ActivityScreen annotation = classElement.getAnnotation(ActivityScreen.class);
    }

    /**
     * The original element that was annotated with @Screen
     */
    public TypeElement getTypeElement() {
        return annotatedClassElement;
    }

    /**
     * Generate the java code
     *
     * @throws IOException
     */
    public void generateCode(Elements elementUtils, Filer filer) throws IOException {
        Name activitySimpleName = annotatedClassElement.getSimpleName();
        String screenClassName = activitySimpleName + SUFFIX;

        MethodSpec openMethod = createOpenMethod(activitySimpleName, false);
        MethodSpec openForResultMethod = createOpenMethod(activitySimpleName, true);

        MethodSpec privateConstructor = MethodSpec.constructorBuilder().
                addModifiers(Modifier.PRIVATE).build();

        TypeSpec screenClass = TypeSpec.classBuilder(screenClassName).
                addModifiers(Modifier.PUBLIC, Modifier.FINAL).
                addMethod(privateConstructor).
                addMethod(openMethod).
                addMethod(openForResultMethod).
                build();

        PackageElement pkg = elementUtils.getPackageOf(annotatedClassElement);

        String packageName = pkg.isUnnamed() ? "" : pkg.getQualifiedName().toString();
        JavaFile javaFile = JavaFile.builder(packageName, screenClass).indent("    ").build();

        javaFile.writeTo(filer);
    }

    private static MethodSpec createOpenMethod(Name activitySimpleName, boolean forResult) {
        ClassName activityClassName = ClassName.get("android.app", "Activity");
        ClassName intentClassName = ClassName.get("android.content", "Intent");

        MethodSpec.Builder openMethodBuilder = MethodSpec.methodBuilder(forResult ? "openForResult" : "open").
                addModifiers(Modifier.PUBLIC, Modifier.STATIC).
                returns(void.class).
                addParameter(activityClassName, "activity");
        openMethodBuilder.addStatement("$T intent = new $T(activity, $L.class)", intentClassName, intentClassName, activitySimpleName);
        if (forResult) {
            openMethodBuilder.addParameter(TypeName.INT, "requestCode");
            openMethodBuilder.addStatement("activity.startActivityForResult(intent, requestCode)");
        } else {
            openMethodBuilder.addStatement("activity.startActivity(intent)");
        }
        return openMethodBuilder.build();
    }
}
