package com.shf.BlockingQueue;

import lombok.SneakyThrows;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 传统版生产者和消费者
 */
class ShareData {
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    @SneakyThrows
    public void increment() {
        lock.lock();

        try {
//        1. 判断
            while (number != 0) {
    //            等待，不能生产
                condition.await();
            }

//        2. 干活
            number++;
            System.out.println(Thread.currentThread().getName() + "\t" + number);

//        3.通知唤醒
            condition.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    @SneakyThrows
    public void decrement() {
        lock.lock();

        try {
//        1. 判断
            while (number == 0) {
    //            等待，不能生产
                condition.await();
            }

//        2. 干活
            number--;
            System.out.println(Thread.currentThread().getName() + "\t" + number);

//        3.通知唤醒
            condition.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}

/**
 * 题目： 一个初始值为零的变量，两个线程对其交替操作，一个加1一个减1，来5轮
 */
public class ProdConsumer_TraditionDemo {
    public static void main(String[] args) {
        ShareData shareData = new ShareData();

        new Thread(()->{
            for (int i = 1; i <= 5; i++) {
                shareData.increment();
            }
        },"AA").start();


        new Thread(()->{
            for (int i = 1; i <= 5; i++) {
                shareData.decrement();
            }
        },"BB").start();

        new Thread(()->{
            for (int i = 1; i <= 5; i++) {
                shareData.increment();
            }
        },"CC").start();


        new Thread(()->{
            for (int i = 1; i <= 5; i++) {
                shareData.decrement();
            }
        },"DD").start();
    }
}
