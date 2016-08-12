package com.segunfamisa.icicle.processor;

class StringUtils {

    public static boolean isEmpty(String s) {
        return s == null || s.length() < 1 || s.equalsIgnoreCase("null");
    }
}
