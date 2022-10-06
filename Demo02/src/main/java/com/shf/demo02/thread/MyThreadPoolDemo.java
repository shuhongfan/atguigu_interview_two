package com.shf.demo02.thread;

import java.util.concurrent.*;

/**
 * 继承Thread类
 * 实现Runnable接口，无异常，无返回值
 * 实现Callable接口，有异常，有返回值
 * 线程池
 */
public class MyThreadPoolDemo {
    public static void main(String[] args) {
//        ExecutorService threadPool = Executors.newFixedThreadPool(5); // 一池五线程
//        ExecutorService threadPool = Executors.newSingleThreadExecutor(); // 一池一线程
//        ExecutorService threadPool = Executors.newCachedThreadPool(); // 一池N线程

//        ThreadPoolExecutor

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                2,
                5,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy());

        try {
            for (int i = 0; i < 9; i++) {
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"\t 办理业务");
                });
            }
        } finally {
            threadPool.shutdown();
        }

    }
}
