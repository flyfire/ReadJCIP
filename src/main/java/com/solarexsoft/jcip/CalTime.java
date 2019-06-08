package com.solarexsoft.jcip;

import sun.jvm.hotspot.oops.FieldType;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * Created by houruhou on 2019/6/8.
 * Desc:
 */
public class CalTime {
    public static long time(Executor executor, int concurrency, final Runnable action) throws InterruptedException {
        final CountDownLatch ready = new CountDownLatch(concurrency);
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch done = new CountDownLatch(concurrency);
        for (int i = 0; i < concurrency; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    ready.countDown(); // tell timer we're ready
                    try {
                        start.await(); // wait till peers are ready
                        action.run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        done.countDown(); // tell timer we're done
                    }
                }
            });
        }
        ready.await(); // wait for all workers to be ready
        long startNanos = System.nanoTime();
        start.countDown(); // and they are off, fire
        done.await(); // wait for all workers to finish
        return System.nanoTime() - startNanos;
    }

    /*
    void test() {
        synchronized (obj) {
            while(<condition does not hold>) {
                obj.wait(); // releases lock, and reacquires on wakeup
            }
            // preform action appropriate to condition
        }
    }
    */
    /*
    private volatile FieldType field;
    FieldType getField() {
        FieldType result = field;
        if (result == null) {
            synchronized (this) {
                result = field;
                if (result == null) {
                    field = result = computeFieldValue();
                }
            }
        }
        return result;
    }*/
    /*
    private volatile FieldType field;
    FieldType getField() {
        FieldType result = field;
        if (result == null) {
            field = result = computeFieldValue();
        }
        return result;
    }
    */
}
