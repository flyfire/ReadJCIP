package com.solarexsoft.jcip.ch15;

/**
 * Created by houruhou on 2019/8/11.
 * Desc:
 */
public class DoubleCheckLocking {
    private static volatile DoubleCheckLocking dcl;

    private DoubleCheckLocking() {
    }

    public static DoubleCheckLocking getInstance() {
        if (dcl == null) {
            synchronized (DoubleCheckLocking.class) {
                if (dcl == null) {
                    dcl = new DoubleCheckLocking();
                }
            }
        }
        return dcl;
    }
}
