package com.segunfamisa.icicle;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import java.util.LinkedHashMap;
import java.util.Map;

public final class Icicle {

    public static final String SUFFIX = "$$Icicle";
    public static final String ANDROID_PREFIX = "android.";
    public static final String JAVA_PREFIX = "java.";
    private static final IIcicleDelegate NO_OP = null;
    private static final Map<Class<?>, IIcicleDelegate> ICICLES = new LinkedHashMap<>();

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
            logd("Looking for icicle for: " + targetClass.getName());
        }

        IIcicleDelegate icicle = findIcicleDeleteForClass(targetClass);
        if (outPersistentState != null) {
            // TODO: 11/08/2016 handle outpersistent state
        }

        if (icicle != null) {
            icicle.freeze(target, outState);
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
        IIcicleDelegate icicle = ICICLES.get(cls);
        if (icicle == null) {
            String className = cls.getName();
            if (className.startsWith(ANDROID_PREFIX) || className.startsWith(JAVA_PREFIX)) {
                logd("Android or Java class detected...exiting");
                return NO_OP;
            }
            try {
                Class icicleClass = Class.forName(className + SUFFIX);
                icicle = (IIcicleDelegate) icicleClass.newInstance();
            } catch (ClassNotFoundException e) {
                loge("Not found...trying superclass");
                icicle = findIcicleDeleteForClass(cls.getSuperclass());
            } catch (InstantiationException ie) {
                loge(ie.getMessage());
            } catch (IllegalAccessException iae) {
                loge(iae.getMessage());
            }
        }

        return icicle;
    }

    private static void logd(String message) {
        if (debug) {
            Log.d(TAG, message);
        }
    }

    private static void loge(String message) {
        if (debug) {
            Log.e(TAG, message);
        }
    }
}
