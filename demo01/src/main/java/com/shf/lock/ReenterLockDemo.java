package com.shf.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Phone implements Runnable{ // 资源类
    public synchronized void sendSMS() {
        System.out.println(Thread.currentThread().getName() + "\t invoked sendSMS()");
        sendEmail();
    }

    public synchronized void sendEmail() {
        System.out.println(Thread.currentThread().getName() + "\t #### invoked sendEmail()");
    }

    //    ============================================================================
    Lock lock = new ReentrantLock();

    @Override
    public void run() {
        get();
    }

    public void get() {
        lock.lock();
        lock.lock();  // 加几次锁，就解几次锁
        try {
            System.out.println(Thread.currentThread().getName()+"\t invoke get()");
            set();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
            lock.unlock();
        }
    }

    public void set() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t invoke set()");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}

/**
 * 可重入锁（也叫递归锁）
 * <p>
 * 指的是同一线程外函数获得锁之后，内层递归函数能获得该锁的代码
 * 在同一个线程在外层方法获取锁的时候，在进入内层方法会自动获取锁
 * <p>
 * 也就是说，线程可以进入任何一个它已经拥有的锁所同步着的代码块
 * <p>
 * 1. synchronized就是一个典型的可重入锁
 *      t1	 invoked sendSMS()          t1线程在外层方法获取锁的时候
 *      t1	 #### invoked sendEmail()   t1在进入内层方法会自动获取锁
 *      t2	 invoked sendSMS()
 *      t2	 #### invoked sendEmail()
 *
 * 2.ReentrantLock 也是可重入锁
 *      t3	 invoke get()
 *      t3	 invoke set()
 *      t4	 invoke get()
 *      t4	 invoke set()
 */
public class ReenterLockDemo {
    public static void main(String[] args) throws InterruptedException {
        Phone phone = new Phone();

        new Thread(() -> {
            phone.sendSMS();
        }, "t1").start();

        new Thread(() -> {
            phone.sendSMS();
        }, "t2").start();

        TimeUnit.SECONDS.sleep(1);
        System.out.println("===============================================");

        Thread t3 = new Thread(phone,"t3");
        Thread t4 = new Thread(phone,"t4");
        t3.start();
        t4.start();
    }
}
