package com.kboyarshinov.activityscreen.processor;

import com.kboyarshinov.activityscreens.annotation.ActivityScreen;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

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
        Name simpleName = annotatedClassElement.getSimpleName();
        String screenClassName = simpleName + SUFFIX;

        ClassName contextClassName = ClassName.get("android.content", "Context");
        ClassName intentClassName = ClassName.get("android.content", "Intent");
        MethodSpec openMethod = MethodSpec.methodBuilder("open").
                addModifiers(Modifier.PUBLIC, Modifier.STATIC).
                returns(void.class).
                addParameter(contextClassName, "context").
                addStatement("$T intent = new $T(context, $L.class)", intentClassName, intentClassName, simpleName).
                addStatement("context.startActivity(intent)").
                build();

        MethodSpec privateConstructor = MethodSpec.constructorBuilder().
                addModifiers(Modifier.PRIVATE).build();

        TypeSpec screenClass = TypeSpec.classBuilder(screenClassName).
                addModifiers(Modifier.PUBLIC, Modifier.FINAL).
                addMethod(privateConstructor).
                addMethod(openMethod).
                build();

        PackageElement pkg = elementUtils.getPackageOf(annotatedClassElement);

        String packageName = pkg.isUnnamed() ? "" : pkg.getQualifiedName().toString();
        JavaFile javaFile = JavaFile.builder(packageName, screenClass).build();

        javaFile.writeTo(filer);
    }
}
