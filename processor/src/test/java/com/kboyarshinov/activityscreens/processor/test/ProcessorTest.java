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

    @Test
    public void emptyActivity() {
        ASSERT.about(javaSource()).
            that(emptyActivity).
            processedWith(new ActivityScreenProcessor()).
            compilesWithoutError().
            and().
            generatesSources(emptyActivityScreen);
    }
}
