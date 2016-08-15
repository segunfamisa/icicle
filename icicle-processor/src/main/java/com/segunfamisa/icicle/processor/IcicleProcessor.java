package com.segunfamisa.icicle.processor;


import com.segunfamisa.icicle.annotations.Freeze;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

public class IcicleProcessor extends AbstractProcessor {

    private Filer filer;
    private Messager messager;
    private Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        filer = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
        elementUtils = processingEnvironment.getElementUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<String>();
        annotations.add(Freeze.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
        Set<? extends Element> elements = env.getElementsAnnotatedWith(Freeze.class);
        Map<TypeElement, BindingClass> fieldBindings = new LinkedHashMap<>();
        for (Element element : elements) {
            if (element.getKind() != ElementKind.FIELD) {
                error(element, "Only Fields can be annoted with @%s", Freeze.class.getSimpleName());
                // exit
                return true;
            }
            TypeElement typeElement = (TypeElement) element.getEnclosingElement();

            // create binding class for each field
            BindingClass bindingClass = getOrCreateBinding(typeElement, fieldBindings);

            // create a field binding for each element
            bindingClass.createFieldBinding(element, Freeze.class.getSimpleName());
        }

        for (BindingClass binding : fieldBindings.values()) {
            // write each binding to filer
            try {
                binding.writeToFiler(filer);
            } catch (IOException e) {
                messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
            }
        }

        // always return false
        return false;
    }

    private BindingClass getOrCreateBinding(TypeElement enclosingElement,
                                            Map<TypeElement, BindingClass> fieldBindings) {
        BindingClass binding = fieldBindings.get(enclosingElement);
        if (binding == null) {
            String classPackage = getPackageName(enclosingElement);
            String className = getClassName(enclosingElement, classPackage) + "$$Icicle";;
            String targetClass = enclosingElement.getQualifiedName().toString();

            binding =  new BindingClass(classPackage, className, targetClass);
            fieldBindings.put(enclosingElement, binding);
        }
        return binding;
    }

    private static String getClassName(TypeElement type, String packageName) {
        int packageLen = packageName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLen).replace('.', '$');
    }

    private String getPackageName(TypeElement type) {
        return elementUtils.getPackageOf(type).getQualifiedName().toString();
    }

    private void error(Element e, String msg, Object... args) {
        messager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(msg, args),
                e);
    }
}
