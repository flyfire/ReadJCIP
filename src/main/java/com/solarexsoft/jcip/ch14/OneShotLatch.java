package com.solarexsoft.jcip.ch14;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Created by houruhou on 2019/8/8.
 * Desc:
 */
public class OneShotLatch {
    private class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected int tryAcquireShared(int arg) {
            return (getState() == 1) ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            setState(1);
            return true;
        }
    }

    private final Sync sync = new Sync();

    public void await() throws InterruptedException {
        sync.acquireSharedInterruptibly(0);
    }

    public void signal() {
        sync.releaseShared(0);
    }
}

    /*
    boolean acquire() throws InterruptedException {
        while (当前状态不允许获取操作) {
            if (需要阻塞获取请求) {
                如果当前线程不在等待队列中，则将其插入等待队列
                阻塞当前线程
            } else {
                返回失败
            }
        }
        可能更新同步器的状态
        如果线程位于等待队列中，则将其移除队列
        返回成功
    }

    void release() {
        更新同步器的状态
        if(新的状态允许某个被阻塞的线程获取成功) {
            解除等待队列中一个或多个线程的阻塞状态
        }
    }
    */
