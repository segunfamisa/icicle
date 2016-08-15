package com.segunfamisa.icicle;

import com.google.testing.compile.JavaFileObjects;
import com.segunfamisa.icicle.processor.IcicleProcessor;

import org.junit.Test;
import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assert_;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

public final class IcicleProcessorTest {


    @Test
    public void testProcessor() {
        JavaFileObject sampleActivity = JavaFileObjects.forSourceString("com.segunfamisa.sample.SampleActivity",
                "package com.segunfamisa.sample;" +
                        "import com.segunfamisa.icicle.annotations.Freeze;" +
                        "import android.content.Intent;" +
                        "import android.os.Bundle;" +
                        "import java.util.*;" +
                        "import org.junit.Test;" +
                        "public class SampleActivity {" +
                        "@Freeze private Integer name;" +
                        "@Freeze private int age = 5;" +
                        "@Freeze private String count;" +
                        "@Freeze Bundle args;" +
                        "@Freeze ArrayList<CharSequence> strings;" +
                        "public SampleActivity() {" +
                        "age = 3;" +
                        "}" +
                        "}");

        assert_().about(javaSource())
                .that(sampleActivity)
                .processedWith(new IcicleProcessor())
                .compilesWithoutError();
    }
}
