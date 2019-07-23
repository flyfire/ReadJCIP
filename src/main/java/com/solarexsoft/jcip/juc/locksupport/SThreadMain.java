package com.solarexsoft.jcip.juc.locksupport;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by houruhou on 2019/7/23.
 * Desc:
 */
public class SThreadMain {
    public static void main(String[] args) {
        SThread sThread = new SThread(Thread.currentThread());
        sThread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main before park");
        LockSupport.park("solarex-boundary");
        System.out.println("main after park");
    }
}
