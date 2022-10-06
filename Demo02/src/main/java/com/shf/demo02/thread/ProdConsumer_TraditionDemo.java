package com.shf.demo02.thread;


import lombok.SneakyThrows;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareData {
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    @SneakyThrows
    public void increment() {
        lock.lock();
        try {
            while (number != 0) {
    //            等待，不能生产
                condition.await();
            }
//       2. 干活
            number++;
            System.out.println(Thread.currentThread().getName() + "\t" + number);
//        3. 通知唤醒
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    @SneakyThrows
    public void decrement() {
        lock.lock();
        try {
            while (number == 0) {
    //            等待，不能生产
                condition.await();
            }
//       2. 干活
            number--;
            System.out.println(Thread.currentThread().getName() + "\t" + number);
//        3. 通知唤醒
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}

/**
 * 一个初始值为零的变量，两个线程对其交替操作。一个加1 一个减1，来5轮
 */
public class ProdConsumer_TraditionDemo {
    public static void main(String[] args) {
        ShareData shareData = new ShareData();

        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                shareData.increment();
            }
        },"AAA").start();

        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                shareData.decrement();
            }
        },"BBB").start();

        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                shareData.increment();
            }
        },"CCC").start();

        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                shareData.decrement();
            }
        },"DDD").start();
    }
}
