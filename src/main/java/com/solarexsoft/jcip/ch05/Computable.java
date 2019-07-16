package com.solarexsoft.jcip.ch05;

/**
 * Created by houruhou on 2019/7/16.
 * Desc:
 */
public interface Computable<A, V> {
    V compute(A arg) throws InterruptedException;
}
