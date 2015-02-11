package com.kboyarshinov.activityscreen.processor;

import com.google.auto.service.AutoService;
import com.kboyarshinov.activityscreens.annotation.ActivityScreen;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Annotation Processor for @Screen annotation
 *
 * @author Kirill Boyarshinov
 */
@AutoService(Processor.class)
public class ActivityScreenProcessor extends AbstractProcessor {
    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<String>();
        annotations.add(ActivityScreen.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * Checks if the annotated element observes our rules
     */
    private boolean isValidClass(ActivityScreenAnnotatedClass item) {

        // Cast to TypeElement, has more type specific methods
        TypeElement classElement = item.getTypeElement();

        if (!classElement.getModifiers().contains(Modifier.PUBLIC)) {
            error(classElement, "The class %s is not public.", classElement.getQualifiedName().toString());
            return false;
        }

        // Check if it's an abstract class
        if (classElement.getModifiers().contains(Modifier.ABSTRACT)) {
            error(classElement, "The class %s is abstract. You can't annotate abstract classes with @%s.",
                    classElement.getQualifiedName().toString(), ActivityScreen.class.getSimpleName());
            return false;
        }

        // Check if it's an interface
        if (classElement.getKind().isInterface()) {
            error(classElement, "%s is interface. You can't annotate interfaces with @%s.",
                    classElement.getQualifiedName().toString(), ActivityScreen.class.getSimpleName());
            return false;
        }

        // Check Activity inheritance
        TypeElement activityType = elementUtils.getTypeElement("android.app.Activity");
        if (activityType == null || !typeUtils.isSubtype(classElement.asType(), activityType.asType())) {
            error(classElement, "%s can only be used on activities, but %s is not a subclass of activity.",
                    ActivityScreen.class.getSimpleName(), classElement.getQualifiedName());
            return false;
        }
        return true;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(ActivityScreen.class)) {
            // Check if a class has been annotated with @Factory
            if (annotatedElement.getKind() != ElementKind.CLASS) {
                error(annotatedElement, "Only classes can be annotated with @%s", ActivityScreen.class.getSimpleName());
                return true; // Exit processing
            }
            // We can cast it, because we know that it of ElementKind.CLASS
            TypeElement typeElement = (TypeElement) annotatedElement;

            ActivityScreenAnnotatedClass annotatedClass = new ActivityScreenAnnotatedClass(typeElement);
            if (!isValidClass(annotatedClass)) {
                return true; // Error message printed, exit processing
            }

            try {
                annotatedClass.generateCode(elementUtils, filer);
            } catch (IOException e) {
                error(null, e.getMessage());
            }
        }
        return false;
    }

    /**
     * Prints an error message
     *
     * @param e The element which has caused the error. Can be null
     * @param msg The error message
     * @param args if the error message contains %s, %d etc. placeholders this arguments will be used
     * to
     * replace them
     */
    public void error(Element e, String msg, Object... args) {
        messager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args), e);
    }
}
