package com.segunfamisa.icicle;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

public final class Icicle {

    private static final String TAG = "Icicle";
    private static final boolean debug = true;

    public static void freeze(Object target, Bundle outState) {
        freeze(target, outState, null);
    }

    public static void freeze(Object target, Bundle outState, PersistableBundle outPersistentState) {
        Validate.validateNotNull(target, "target");
        Validate.validateNotNull(outState, "outState");

        Class<?> targetClass = target.getClass();

        if (debug) {
            log("Looking for icicle for: " + targetClass.getName());
        }

        IIcicleDelegate icicle = findIcicleDeleteForClass(targetClass);
        // TODO: 11/08/2016 persist normal data

        if (outPersistentState != null) {
            // TODO: 11/08/2016 handle outpersistent state
        }
    }

    public static void thaw(Object target, Bundle savedInstanceState) {
        thaw(target, savedInstanceState, null);
    }

    public static void thaw(Object target, Bundle savedInstanceState,
                            PersistableBundle persistentState) {
        Validate.validateNotNull(target, "target");
    }


    private static IIcicleDelegate findIcicleDeleteForClass(Class<?> cls) {

        return null;
    }

    private static void log(String message) {
        Log.d(TAG, message);
    }
}
