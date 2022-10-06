package com.shf.demo02.thread;


import java.util.concurrent.locks.ReentrantLock;

class Phone implements Runnable{
    public synchronized void sendMSM() {
        System.out.println(Thread.currentThread().getId()+"\t invoked sendSmS");
        sendEmail();
    }

    public synchronized void sendEmail() {
        System.out.println(Thread.currentThread().getId()+"\t invoked sendEmail");
    }

    ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        get();
    }

    private void get() {
        lock.lock();
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t invoke get lock");
            set();
        } finally {
            lock.unlock();
            lock.unlock();
        }
    }

    private void set() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t invoke set lock");
        } finally {
            lock.unlock();
        }
    }

}
public class ReentrantLockDemo {
    public static void main(String[] args) {
        Phone phone = new Phone();

        new Thread(()->{
            phone.sendMSM();
        },"t1").start();

        new Thread(()->{
            phone.sendMSM();
        },"t2").start();


        new Thread(phone,"t3").start();
        new Thread(phone,"t4").start();

    }
}
