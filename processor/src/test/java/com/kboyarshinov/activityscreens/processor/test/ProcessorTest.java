package com.kboyarshinov.activityscreens.processor.test;

import com.google.testing.compile.JavaFileObjects;
import com.kboyarshinov.activityscreen.processor.ActivityScreenProcessor;
import org.junit.Test;

import javax.tools.JavaFileObject;

import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
import static org.truth0.Truth.ASSERT;

/**
 * @author Kirill Boyarshinov
 */
public class ProcessorTest {

    private JavaFileObject emptyActivity = JavaFileObjects.forResource("errors/EmptyActivity.java");
    private JavaFileObject emptyActivityScreen = JavaFileObjects.forResource("errors/EmptyActivityScreen.java");
    private JavaFileObject nonActivity = JavaFileObjects.forResource("errors/NonActivity.java");
    private JavaFileObject privateActivity = JavaFileObjects.forResource("errors/PrivateActivity.java");
    private JavaFileObject abstractActivity = JavaFileObjects.forResource("errors/AbstractActivity.java");
    private JavaFileObject interfaceClass = JavaFileObjects.forResource("errors/Interface.java");
    private JavaFileObject argFieldInNonActivity = JavaFileObjects.forResource("errors/ArgFieldInNonActivity.java");
    private JavaFileObject finalFieldActivity = JavaFileObjects.forResource("errors/FinalFieldActivity.java");
    private JavaFileObject staticFieldActivity = JavaFileObjects.forResource("errors/StaticFieldActivity.java");
    private JavaFileObject privateFieldActivity = JavaFileObjects.forResource("errors/PrivateFieldActivity.java");
    private JavaFileObject protectedFieldActivity = JavaFileObjects.forResource("errors/ProtectedFieldActivity.java");
    private JavaFileObject privateStaticFinalFieldActivity = JavaFileObjects.forResource("errors/PrivateStaticFinalFieldActivity.java");

    @Test
    public void emptyActivity() {
        ASSERT.about(javaSource()).
            that(emptyActivity).
            processedWith(new ActivityScreenProcessor()).
            compilesWithoutError().
            and().
            generatesSources(emptyActivityScreen);
    }

    @Test
    public void nonActivity() {
        ASSERT.about(javaSource()).
            that(nonActivity).
            processedWith(new ActivityScreenProcessor()).
            failsToCompile();
    }

    @Test
    public void privateActivity() {
        ASSERT.about(javaSource()).
                that(privateActivity).
                processedWith(new ActivityScreenProcessor()).
                failsToCompile();
    }

    @Test
    public void abstractActivity() {
        ASSERT.about(javaSource()).
                that(abstractActivity).
                processedWith(new ActivityScreenProcessor()).
                failsToCompile();
    }

    @Test
    public void interfaceClass() {
        ASSERT.about(javaSource()).
                that(interfaceClass).
                processedWith(new ActivityScreenProcessor()).
                failsToCompile();
    }

    @Test
    public void ArgFieldInNonActivity() {
        ASSERT.about(javaSource()).
                that(argFieldInNonActivity).
                processedWith(new ActivityScreenProcessor()).
                failsToCompile();
    }

    @Test
    public void validFieldsCheck() {
        ASSERT.about(javaSource()).
                that(finalFieldActivity).
                processedWith(new ActivityScreenProcessor()).
                failsToCompile();

        ASSERT.about(javaSource()).
                that(privateFieldActivity).
                processedWith(new ActivityScreenProcessor()).
                failsToCompile();

        ASSERT.about(javaSource()).
                that(staticFieldActivity).
                processedWith(new ActivityScreenProcessor()).
                failsToCompile();

        ASSERT.about(javaSource()).
                that(protectedFieldActivity).
                processedWith(new ActivityScreenProcessor()).
                failsToCompile();

        ASSERT.about(javaSource()).
                that(privateStaticFinalFieldActivity).
                processedWith(new ActivityScreenProcessor()).
                failsToCompile();
    }
}
