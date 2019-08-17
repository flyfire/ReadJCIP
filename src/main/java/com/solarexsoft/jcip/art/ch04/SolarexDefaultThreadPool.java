package com.solarexsoft.jcip.art.ch04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by houruhou on 2019/8/17.
 * Desc:
 */
public class SolarexDefaultThreadPool<Job extends Runnable> implements SolarexThreadPool<Job> {

    private static final int MAX_WORKER_NUMBERS = 10;
    private static final int DEFAULT_WORKER_NUMBERS = 5;
    private static final int MIN_WORKER_NUMBERS = 1;
    private final LinkedList<Job> jobs = new LinkedList<>();
    private final List<Worker> workers = Collections.synchronizedList(new ArrayList<>());

    private int workerNum = DEFAULT_WORKER_NUMBERS; // 工作者线程的数量
    private AtomicLong threadNum = new AtomicLong(); // 线程编号生成

    public SolarexDefaultThreadPool() {
        initializeWorkers(DEFAULT_WORKER_NUMBERS);
    }

    public SolarexDefaultThreadPool(int num) {
        workerNum = num > MAX_WORKER_NUMBERS ? MAX_WORKER_NUMBERS : num < MIN_WORKER_NUMBERS ? MIN_WORKER_NUMBERS : num;
        initializeWorkers(workerNum);
    }

    private void initializeWorkers(int num) {
        for (int i = 0; i < num; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            Thread thread = new Thread(worker, "Solarex-Worker-" + threadNum.getAndIncrement());
            thread.start();
        }
    }

    class Worker implements Runnable {
        private volatile boolean running = true;

        @Override
        public void run() {
            while (running) {
                Job job = null;
                synchronized (jobs) {
                    while (jobs.isEmpty()) {
                        try {
                            jobs.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    job = jobs.removeFirst();
                }
                if (job != null) {
                    try {
                        job.run();
                    } catch (Exception ex) {

                    }
                }
            }
        }

        public void shutdown() {
            running = false;
        }
    }
    @Override
    public void execute(Job job) {
        if (job != null) {
            synchronized (jobs) {
                jobs.addLast(job);
                jobs.notify();
            }
        }
    }

    @Override
    public void shutdown() {
        for (Worker worker : workers) {
            worker.shutdown();
        }
    }

    @Override
    public void addWorkers(int num) {
        synchronized (jobs) {
            if (num + this.workerNum > MAX_WORKER_NUMBERS) {
                num = MAX_WORKER_NUMBERS - this.workerNum;
            }
            initializeWorkers(num);
            workerNum += num;
        }
    }

    @Override
    public void removeWorkers(int num) {
        synchronized (jobs) {
            if (num > this.workerNum) {
                throw new IllegalArgumentException("beyond workernum");
            }
            int count = 0;
            while (count < num) {
                Worker worker = workers.get(count);
                if (workers.remove(worker)) {
                    worker.shutdown();
                    count++;
                }
            }
            this.workerNum -= count;
        }
    }

    @Override
    public int getJobSize() {
        return jobs.size();
    }
}
