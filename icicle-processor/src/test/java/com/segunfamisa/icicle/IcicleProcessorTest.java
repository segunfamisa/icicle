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
                        "import java.io.Serializable;" +
                        "import org.junit.Test;" +
                        "public class SampleActivity {" +
                        "@Freeze Integer name;" +
                        "@Freeze int age = 5;" +
                        "@Freeze String count;" +
                        "@Freeze Bundle args;" +
                        "@Freeze ArrayList<CharSequence> strings;" +
                        "@Freeze HashMap<String, String> map;" +
                        "@Freeze Seria serializableItem;" +
                        "public SampleActivity() {" +
                        "age = 3;" +
                        "}" +
                        "private class Seria implements Serializable {" +
                        "int j = 3;" +
                        "}" +
                        "}");

        assert_().about(javaSource())
                .that(sampleActivity)
                .processedWith(new IcicleProcessor())
                .compilesWithoutError();
    }
}
