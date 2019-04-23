package com.solarexsoft.jcip.abc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by houruhou on 2019/4/23.
 * Desc:
 */
public class ABCCondition {
    private static Lock lock = new ReentrantLock();
    private static Condition A = lock.newCondition();
    private static Condition B = lock.newCondition();
    private static Condition C = lock.newCondition();
    private static int count = 0;

    static class ThreadCA extends Thread {
        @Override
        public void run() {
            try {
                lock.lock();
                for (int i = 0; i < 10; i++) {
                    while (count % 3 != 0) {
                        A.await();
                    }
                    count++;
                    System.out.print("A");
                    B.signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    static class ThreadCB extends Thread {
        @Override
        public void run() {
            try {
                lock.lock();
                for (int i = 0; i < 10; i++) {
                    while (count % 3 != 1) {
                        B.await();
                    }
                    count++;
                    System.out.print("B");
                    C.signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    static class ThreadCC extends Thread {
        @Override
        public void run() {
            try {
                lock.lock();
                for (int i = 0; i < 10; i++) {
                    while ( count % 3 != 2) {
                        C.await();
                    }
                    count++;
                    System.out.print("C");
                    A.signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ThreadCA ta = new ThreadCA();
        ThreadCB tb = new ThreadCB();
        ThreadCC tc = new ThreadCC();
        ta.start();
        tb.start();
        tc.start();
    }
}
