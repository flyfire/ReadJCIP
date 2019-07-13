package com.solarexsoft.jcip.reentrant;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by houruhou on 2019/7/13.
 * Desc:
 */
public class LoggingWidgetTest {

    @Test
    public void doSomething() {
        LoggingWidget widget = new LoggingWidget();
        widget.doSomething();
    }
}