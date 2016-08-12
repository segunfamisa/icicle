package com.segunfamisa.icicle;

class Validate {

    static void validateNotNull(Object object, String name) {
        if (object == null) {
            throw new NullPointerException("Argument " + name + " cannot be null");
        }
    }
}
