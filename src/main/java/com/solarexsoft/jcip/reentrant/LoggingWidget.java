package com.solarexsoft.jcip.reentrant;

/**
 * Created by houruhou on 2019/7/13.
 * Desc:
 */
public class LoggingWidget extends Widget {
    @Override
    public synchronized void doSomething() {
        System.out.println("LoggingWidget doSomething");
        super.doSomething();
    }
}
