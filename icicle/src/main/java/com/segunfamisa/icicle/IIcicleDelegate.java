package com.segunfamisa.icicle;

import android.os.Bundle;

/**
 * Used for generated classes
 */
public interface IIcicleDelegate<T> {
    void freeze(final T target, Bundle outState);
    void thaw(final T target, Bundle savedInstanceState);
}
