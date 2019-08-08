package com.solarexsoft.jcip.ch14;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by houruhou on 2019/8/8.
 * Desc:
 */
public class ConditionBoundedBuffer<T> {
    protected final Lock lock = new ReentrantLock();
    // 条件谓词：notFull (count < items.length)
    private final Condition notFull = lock.newCondition();
    // 条件谓词：notEmpty (count>0)
    private final Condition notEmpty = lock.newCondition();
    private final T[] items;
    private int head,tail,count;

    public ConditionBoundedBuffer(int limit) {
        items = (T[]) new Object[limit];
    }

    // 阻塞并直到： notFull
    public void put(T x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                notFull.await();
            }
            items[tail] = x;
            if (++tail == items.length) {
                tail = 0;
            }
            count++;
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }
    // 阻塞并直到： notEmpty
    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            T x = items[head];
            items[head] = null;
            if (++head == items.length) {
                head = 0;
            }
            count--;
            notFull.signalAll();
            return x;
        } finally {
            lock.unlock();
        }
    }
}
