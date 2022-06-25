package com.shf.BlockingQueue;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * 同步队列：  不存储元素的阻塞队列,也即是单个元素的队列.
 */
public class SynchronousQueueDemo {
    public static void main(String[] args) {
        SynchronousQueue<String> blockingQueue = new SynchronousQueue<>();

        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "\t put a");
                blockingQueue.put("a");
                System.out.println(Thread.currentThread().getName() + "\t put b");
                blockingQueue.put("b");
                System.out.println(Thread.currentThread().getName() + "\t put c");
                blockingQueue.put("c");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "AAA").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + "\t get " + blockingQueue.take());
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + "\t get " + blockingQueue.take());
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + "\t get " + blockingQueue.take());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "BBB").start();
    }
}
