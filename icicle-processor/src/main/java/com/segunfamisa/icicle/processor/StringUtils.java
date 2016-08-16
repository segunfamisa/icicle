package com.segunfamisa.icicle.processor;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

class StringUtils {

    static boolean isEmpty(String s) {
        return s == null || s.length() < 1 || s.equalsIgnoreCase("null");
    }

    static String getClassName(TypeElement type, String packageName) {
        int packageLen = packageName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLen).replace('.', '$');
    }

    static String getPackageName(Elements elementUtils, TypeElement type) {
        return elementUtils.getPackageOf(type).getQualifiedName().toString();
    }
}
