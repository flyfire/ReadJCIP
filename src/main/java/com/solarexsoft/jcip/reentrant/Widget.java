package com.solarexsoft.jcip.reentrant;

/**
 * Created by houruhou on 2019/7/13.
 * Desc:
 */
public class Widget {
    public synchronized void doSomething() {
        System.out.println("Widget do something");
    }
}
