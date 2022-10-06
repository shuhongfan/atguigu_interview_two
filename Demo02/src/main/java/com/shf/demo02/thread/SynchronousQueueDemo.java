package com.shf.demo02.thread;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class SynchronousQueueDemo {
    public static void main(String[] args) {
        SynchronousQueue<String> queue = new SynchronousQueue<>();

        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName()+"\t put 1");
                queue.put("1");

                System.out.println(Thread.currentThread().getName()+"\t put 2");
                queue.put("2");

                System.out.println(Thread.currentThread().getName()+"\t put 3");
                queue.put("3");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"AAA").start();


        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName()+"\t "+queue.take());

                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName()+"\t "+queue.take());

                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName()+"\t "+queue.take());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"BBB").start();
    }
}
