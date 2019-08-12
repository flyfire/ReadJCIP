package com.solarexsoft.jcip.ch15;

/**
 * Created by houruhou on 2019/8/10.
 * Desc:
 */
public class CasCounter {
    private SimulatedCAS value;

    public int getValue() {
        return value.get();
    }

    public int increment() {
        int v;
        do {
            v = value.get();
        } while (v != value.compareAndSwap(v, v+1));
        return v+1;
    }
}
