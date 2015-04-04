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
    private JavaFileObject nonActivity = JavaFileObjects.forResource("types/NonActivity.java");
    private JavaFileObject privateActivity = JavaFileObjects.forResource("types/PrivateActivity.java");
    private JavaFileObject abstractActivity = JavaFileObjects.forResource("types/AbstractActivity.java");
    private JavaFileObject interfaceClass = JavaFileObjects.forResource("types/Interface.java");

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
}
