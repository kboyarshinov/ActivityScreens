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

    private JavaFileObject emptyActivity = JavaFileObjects.forResource("types/EmptyActivity.java");
    private JavaFileObject emptyActivityScreen = JavaFileObjects.forResource("types/EmptyActivityScreen.java");

    private JavaFileObject nonActivity = JavaFileObjects.forResource("errors/NonActivity.java");
    private JavaFileObject privateActivity = JavaFileObjects.forResource("errors/PrivateActivity.java");
    private JavaFileObject abstractActivity = JavaFileObjects.forResource("errors/AbstractActivity.java");
    private JavaFileObject interfaceClass = JavaFileObjects.forResource("errors/Interface.java");
    private JavaFileObject finalFieldActivity = JavaFileObjects.forResource("errors/FinalFieldActivity.java");
    private JavaFileObject staticFieldActivity = JavaFileObjects.forResource("errors/StaticFieldActivity.java");
    private JavaFileObject privateFieldActivity = JavaFileObjects.forResource("errors/PrivateFieldActivity.java");
    private JavaFileObject protectedFieldActivity = JavaFileObjects.forResource("errors/ProtectedFieldActivity.java");
    private JavaFileObject privateStaticFinalFieldActivity = JavaFileObjects.forResource("errors/PrivateStaticFinalFieldActivity.java");
    private JavaFileObject duplicateKeysFieldsActivity = JavaFileObjects.forResource("errors/DuplicateKeysFieldsActivity.java");
    private JavaFileObject differentKeyAndNameActivity = JavaFileObjects.forResource("misc/KeyAndNameActivity.java");
    private JavaFileObject differentKeyAndNameActivityScreen = JavaFileObjects.forResource("misc/KeyAndNameActivityScreen.java");

    private JavaFileObject primitiveTypesActivity = JavaFileObjects.forResource("types/PrimitiveTypesActivity.java");
    private JavaFileObject primitiveTypesActivityScreen = JavaFileObjects.forResource("types/PrimitiveTypesActivityScreen.java");
    private JavaFileObject primitiveArraysActivity = JavaFileObjects.forResource("types/PrimitiveArraysActivity.java");
    private JavaFileObject primitiveArraysActivityScreen = JavaFileObjects.forResource("types/PrimitiveArraysActivityScreen.java");
    private JavaFileObject primitiveWrappersActivity = JavaFileObjects.forResource("types/PrimitiveWrappersActivity.java");
    private JavaFileObject primitiveWrappersActivityScreen = JavaFileObjects.forResource("types/PrimitiveWrappersActivityScreen.java");
    private JavaFileObject classesActivity = JavaFileObjects.forResource("types/ClassesActivity.java");
    private JavaFileObject classesActivityScreen = JavaFileObjects.forResource("types/ClassesActivityScreen.java");

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
    public void primitiveTypes() {
        ASSERT.about(javaSource()).
            that(primitiveTypesActivity).
            processedWith(new ActivityScreenProcessor()).
            compilesWithoutError().
            and().
            generatesSources(primitiveTypesActivityScreen);
    }

    @Test
    public void primitiveArrays() {
        ASSERT.about(javaSource()).
                that(primitiveArraysActivity).
                processedWith(new ActivityScreenProcessor()).
                compilesWithoutError().
                and().
                generatesSources(primitiveArraysActivityScreen);
    }

    @Test
    public void primitiveWrappers() {
        ASSERT.about(javaSource()).
                that(primitiveWrappersActivity).
                processedWith(new ActivityScreenProcessor()).
                compilesWithoutError().
                and().
                generatesSources(primitiveWrappersActivityScreen);
    }

    @Test
    public void classes() {
        ASSERT.about(javaSource()).
                that(classesActivity).
                processedWith(new ActivityScreenProcessor()).
                compilesWithoutError().
                and().
                generatesSources(classesActivityScreen);
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
    public void duplicateKeys() {
        ASSERT.about(javaSource()).
                that(duplicateKeysFieldsActivity).
                processedWith(new ActivityScreenProcessor()).
                failsToCompile();
    }

    @Test
    public void differentKeyAndName() {
        ASSERT.about(javaSource()).
                that(differentKeyAndNameActivity).
                processedWith(new ActivityScreenProcessor()).
                compilesWithoutError().
                and().
                generatesSources(differentKeyAndNameActivityScreen);
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
