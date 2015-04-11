package com.kboyarshinov.activityscreen.processor;

import com.google.auto.service.AutoService;
import com.kboyarshinov.activityscreens.annotation.ActivityArg;
import com.kboyarshinov.activityscreens.annotation.ActivityScreen;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Annotation Processor for @ActivityScreen and @ActivityArg annotations
 *
 * @author Kirill Boyarshinov
 */
@AutoService(Processor.class)
public class ActivityScreenProcessor extends AbstractProcessor {
    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;
    private Messager messager;
    private CodeGenerator codeGenerator;

    private HashMap<Name, ActivityScreenAnnotatedClass> activityClasses = new HashMap<Name, ActivityScreenAnnotatedClass>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
        codeGenerator = new CodeGenerator(elementUtils, filer);
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
    private boolean isValidActivityClass(TypeElement classElement) {

        // Check Activity inheritance
        if (!isInheritsActivity(classElement)) {
            error(classElement, "%s can only be used on activities, but %s is not a subclass of activity.",
                    ActivityScreen.class.getSimpleName(), classElement.getQualifiedName());
            return false;
        }
        
        // Check if it's public
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

        return true;
    }

    private boolean isInheritsActivity(TypeElement classElement) {
        TypeElement activityType = elementUtils.getTypeElement("android.app.Activity");
        return activityType != null && typeUtils.isSubtype(classElement.asType(), activityType.asType());
    }

    private boolean isValidField(Element annotatedElement) {
        return !annotatedElement.getModifiers().contains(Modifier.FINAL)
                || !annotatedElement.getModifiers().contains(Modifier.STATIC)
                || !annotatedElement.getModifiers().contains(Modifier.PRIVATE)
                || !annotatedElement.getModifiers().contains(Modifier.PROTECTED);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(ActivityScreen.class)) {
            if (annotatedElement.getKind() != ElementKind.CLASS) {
                error(annotatedElement, "Only classes can be annotated with @%s", ActivityScreen.class.getSimpleName());
                return true;
            }
            TypeElement typeElement = (TypeElement) annotatedElement;
            Name activityName = typeElement.getQualifiedName();

            if (!activityClasses.containsKey(activityName)) { // check if already processed
                if (!isValidActivityClass(typeElement)) {
                    return true;
                }
                activityClasses.put(activityName, new ActivityScreenAnnotatedClass(typeElement));
            }

            // process fields with @ActivityArg annotation
            List<? extends Element> enclosedElements = elementUtils.getAllMembers(typeElement);
            for (Element enclosedElement : enclosedElements) {
                ActivityArg activityArg = enclosedElement.getAnnotation(ActivityArg.class);
                if (activityArg == null) {
                    continue;
                }
                if (enclosedElement.getKind() != ElementKind.FIELD) {
                    error(enclosedElement, "Only field can be annotated with @%s", ActivityArg.class.getSimpleName());
                    continue;
                }
                if (!isValidField(enclosedElement)) {
                    error(enclosedElement, "@ActivityArg fields must not be private, protected, final or static (%s.%s)",
                            activityName, enclosedElement);
                    continue;
                }

                ActivityScreenAnnotatedClass activityScreenAnnotatedClass = activityClasses.get(activityName);
                ActivityArgAnnotatedField annotatedField = new ActivityArgAnnotatedField(enclosedElement);
                if (activityScreenAnnotatedClass.containsBundleKey(annotatedField)) {
                    //  key for bundle is already in use
                    ActivityArgAnnotatedField otherField = activityScreenAnnotatedClass.getFieldByKey(annotatedField.getKey());
                    error(annotatedField.getElement(),
                            "The bundle key '%s' for field %s in %s is already used by another field %s)",
                            annotatedField.getKey(), annotatedField.getVariableName(),
                            activityName, otherField.getVariableName());
                    return true;
                }
                activityScreenAnnotatedClass.addFieldClass(annotatedField);
            }
        }

        try {
            codeGenerator.generate(activityClasses.values());
        } catch (IOException e) {
            error(null, e.getMessage());
        }

        activityClasses.clear();
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

    /**
     * Prints warning message
     *
     * @param e The element which has caused the error. Can be null
     * @param msg The error message
     * @param args if the error message contains %s, %d etc. placeholders this arguments will be used
     * to
     * replace them
     */
    public void warn(Element e, String msg, Object... args) {
        messager.printMessage(Diagnostic.Kind.WARNING, String.format(msg, args), e);
    }
}
