package com.shf.callable;

import lombok.SneakyThrows;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * Runnable无返回值，不抛异常，run方法
 */
class MyThread implements Runnable {

    @Override
    public void run() {

    }
}

/**
 * Callable有返回值，抛异常，call方法
 */
class MyThread2 implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println("********* come in Callable");
        TimeUnit.SECONDS.sleep(3);
        return 1024;
    }
}

public class CallableDemo {
    @SneakyThrows
    public static void main(String[] args) {
//        public FutureTask(Callable<V> callable) {
        FutureTask<Integer> futureTask = new FutureTask<Integer>(new MyThread2());

        Thread t1 = new Thread(futureTask, "AA");
        t1.start();

        Thread t2 = new Thread(futureTask, "BB"); // 会复用，使用同一个futureTask
        t2.start();

        int result01 = 100;

//        要求获得Callable线程的计算结果，如果没有计算完成就去强求，会导致阻塞，值计算完成
        while (!futureTask.isDone()){  // 自旋锁

        }
        int result02 = futureTask.get(); // 建议放在最后

        System.out.println("********* result: "+(result01+result02));
    }
}
