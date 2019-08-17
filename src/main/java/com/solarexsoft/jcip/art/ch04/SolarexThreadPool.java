package com.solarexsoft.jcip.art.ch04;

/**
 * Created by houruhou on 2019/8/17.
 * Desc:
 */
public interface SolarexThreadPool<Job extends Runnable> {
    void execute(Job job);

    void shutdown();

    void addWorkers(int num); // 增加工作者线程

    void removeWorkers(int num); //减少工作者线程

    int getJobSize(); // 得到正在等待执行的任务数量
}
