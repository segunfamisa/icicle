package com.segunfamisa.icicle.processor;

import com.segunfamisa.icicle.annotations.Freeze;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;

final class BindingClass {

    private final String classPackage;
    private final String className;
    private final String targetClass;
    private final Map<String, FieldBinding> fieldBindings;

    public BindingClass(String classPackage, String className, String targetClass) {
        this.classPackage = classPackage;
        this.className = className;
        this.targetClass = targetClass;
        fieldBindings = new HashMap<>();
    }

    void createFieldBinding(Element element, String annotationClass) {
        try {
            FieldBinding binding = FieldBinding.newInstance(element, annotationClass);
            fieldBindings.put(binding.name, binding);
        } catch (ProcessingException e) {
            e.printStackTrace();
        }
    }

    void writeToFiler(Filer filer) throws IOException {
        ClassName targetClassName = ClassName.get(classPackage, targetClass);

        TypeSpec.Builder icicle = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addTypeVariable(TypeVariableName.get("T", targetClassName))
                .addField(generateBundleField())
                .addMethod(generateFreezeMethod())
                .addMethod(generateThawMethod());

        ClassName callback = ClassName.get("com.segunfamisa.icicle", "IIcicleDelegate");
        icicle.addSuperinterface(ParameterizedTypeName.get(callback, TypeVariableName.get("T")));

        JavaFile javaFile = JavaFile.builder(classPackage, icicle.build()).build();
        javaFile.writeTo(filer);
    }

    private FieldSpec generateBundleField() {
        FieldSpec.Builder builder = FieldSpec.builder(ClassName.get("android.os", "Bundle"), "state")
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC);

        return builder.build();
    }

    private MethodSpec generateThawMethod() {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("thaw")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                //void thaw(final T target, Bundle savedInstanceState);
                .addParameter(TypeVariableName.get("T"), "target", Modifier.FINAL)
                .addParameter(ClassName.get("android.os", "Bundle"), "savedInstanceState");

        // TODO: 14/08/2016 for each field binding, retrieve the item from the bundle

        return builder.build();
    }

    private MethodSpec generateFreezeMethod() {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("freeze")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(TypeVariableName.get("T"), "target", Modifier.FINAL)
                .addParameter(ClassName.get("android.os", "Bundle"), "outState");

        // TODO: 14/08/2016 for each field binding add implementations to save details

        return builder.build();
    }

    private static class FieldBinding {
        final String name;
        final VariableElement variableElement;
        FieldBinding(Element element) {
            variableElement = (VariableElement) element;
            name = variableElement.getSimpleName().toString();
        }

        static FieldBinding newInstance(Element element, String annotationClass)
                throws ProcessingException {
            final String key;
            if (annotationClass.equals(Freeze.class.getSimpleName())) {
                final Freeze instance = element.getAnnotation(Freeze.class);
                return new FieldBinding(element);
            } else {
                throw new ProcessingException(element,
                        "This method can only be called on a @%s annotation",
                        Freeze.class.getSimpleName());
            }
        }
    }
}
