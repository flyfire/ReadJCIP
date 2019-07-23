package com.solarexsoft.jcip.juc.locksupport;


import java.util.concurrent.locks.LockSupport;

/**
 * Created by houruhou on 2019/7/23.
 * Desc:
 */
public class SThread extends Thread {
    Thread thread;

    SThread(Thread thread) {
        this.thread = thread;
        setName("SThread");
    }

    @Override
    public void run() {
        System.out.println("thread -> " + Thread.currentThread().getName() + "before unpark");
        System.out.println("before thread name -> " + thread.getName() + ",blocker ->" + LockSupport.getBlocker(thread));
        LockSupport.unpark(thread);
        System.out.println("thread -> " + Thread.currentThread().getName() + "after unpark");
        System.out.println("after thread name -> " + thread.getName() + ",blocker ->" + LockSupport.getBlocker(thread));
    }
}
