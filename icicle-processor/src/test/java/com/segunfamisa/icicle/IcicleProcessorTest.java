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
                        "import org.junit.Test;" +
                        "public class SampleActivity {" +
                        "@Freeze private String name;" +
                        "@Freeze private int age;" +
                        "@Freeze(\"counts\") private String count;" +
                        "}");

        assert_().about(javaSource())
                .that(sampleActivity)
                .processedWith(new IcicleProcessor())
                .compilesWithoutError();
    }
}
