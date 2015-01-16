package com.kboyarshinov.activityscreen.processor;

import com.kboyarshinov.activityscreens.annotation.Screen;
import com.squareup.javawriter.JavaWriter;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.EnumSet;

/**
 * Holds the information about a class annotated with @Screen
 *
 * @author Kirill Boyarshinov
 */
public class ScreenAnnotatedClass {
    private static final String SUFFIX = "Screen";
    private TypeElement annotatedClassElement;

    public ScreenAnnotatedClass(TypeElement classElement) {
        this.annotatedClassElement = classElement;
        Screen annotation = classElement.getAnnotation(Screen.class);
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

        String factoryClassName = annotatedClassElement.getSimpleName() + SUFFIX;

        JavaFileObject jfo = filer.createSourceFile(annotatedClassElement.getQualifiedName() + SUFFIX);
        Writer writer = jfo.openWriter();
        JavaWriter jw = new JavaWriter(writer);

        // Write package
        PackageElement pkg = elementUtils.getPackageOf(annotatedClassElement);
        if (!pkg.isUnnamed()) {
            jw.emitPackage(pkg.getQualifiedName().toString());
        } else {
            jw.emitPackage("");
        }

        jw.emitImports("android.content.Context", "android.content.Intent");
        jw.emitEmptyLine();

        jw.beginType(factoryClassName, "class", EnumSet.of(Modifier.PUBLIC));
        jw.emitEmptyLine();
        jw.beginMethod("void", "open", EnumSet.of(Modifier.PUBLIC, Modifier.STATIC), "Context", "context");

        jw.emitField("Intent", "intent", EnumSet.noneOf(Modifier.class), "new Intent(context, " + annotatedClassElement.getSimpleName() + ".class)");
        jw.emitStatement("context.startActivity(intent)");

        jw.endMethod();
        jw.endType();
        jw.close();
    }
}
