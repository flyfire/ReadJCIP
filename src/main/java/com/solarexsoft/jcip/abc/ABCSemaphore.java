package com.solarexsoft.jcip.abc;

import java.util.concurrent.Semaphore;

/**
 * Created by houruhou on 2019/4/23.
 * Desc:
 */
public class ABCSemaphore {
    private static Semaphore A = new Semaphore(1);
    private static Semaphore B = new Semaphore(0);
    private static Semaphore C = new Semaphore(0);

    static class ThreadSA extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    A.acquire();
                    System.out.print("A");
                    B.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class ThreadSB extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    B.acquire();
                    System.out.print("B");
                    C.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class ThreadSC extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    C.acquire();
                    System.out.print("C");
                    A.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ThreadSA t1 = new ThreadSA();
        ThreadSB t2 = new ThreadSB();
        ThreadSC t3 = new ThreadSC();
        t1.start();
        t2.start();
        t3.start();
    }
}
