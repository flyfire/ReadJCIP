package com.solarexsoft.jcip;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by houruhou on 2018/7/26.
 */
public class SafeIncrease {
    private final ConcurrentHashMap<String, Long> urlCounter = new ConcurrentHashMap<>();

    public Long getCount(String url) {
        return urlCounter.get(url);
    }

    public void increase(String url) {
        Long oldValue, newValue;
        while (true) {
            oldValue = urlCounter.get(url);
            if (oldValue == null) {
                newValue = 1l;
                if (urlCounter.putIfAbsent(url, newValue) == null) {
                    break;
                }
            } else {
                newValue = oldValue + 1;
                if (urlCounter.replace(url, oldValue, newValue)) {
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        final SafeIncrease safeIncrease = new SafeIncrease();
        int callTime = 100000;
        final String url = "http://solarex.github.io/projects";
        CountDownLatch countDownLatch = new CountDownLatch(callTime);
        for (int i = 0; i < callTime; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    safeIncrease.increase(url);
                    countDownLatch.countDown();
                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
        System.out.println("累加次数: " + safeIncrease.getCount(url));
    }
}
