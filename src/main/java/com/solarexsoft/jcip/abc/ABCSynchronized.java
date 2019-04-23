package com.solarexsoft.jcip.abc;

/**
 * Created by houruhou on 2019/4/23.
 * Desc: 三个线程交替打印ABC
 */
public class ABCSynchronized {
    static class ABCRunnnable implements Runnable{
        String name;
        Object prev;
        Object self;

        ABCRunnnable(String name, Object prev, Object self) {
            this.name = name;
            this.prev = prev;
            this.self = self;
        }

        @Override
        public void run() {
            int count = 10;
            int i = 0;
            while (i < count) {
                synchronized (prev) {
                    synchronized (self) {
                        System.out.print(name);
                        self.notifyAll();
                    }
                    i++;
                    if (i == count) {
                        prev.notifyAll();
                    } else {
                        try {
                            prev.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Object A = new Object();
        Object B = new Object();
        Object C = new Object();
        Thread t1 = new Thread(new ABCRunnnable("A", C, A), "T1");
        Thread t2 = new Thread(new ABCRunnnable("B", A, B), "T2");
        Thread t3 = new Thread(new ABCRunnnable("C", B, C), "T3");
        t1.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t3.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
