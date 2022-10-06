package com.shf.demo02.thread;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

public class DeadLockDemo {
    public static void main(String[] args) {
        String lockA = "LockA";
        String lockB = "LockB";

        new Thread(new HoldLockThread(lockA,lockB),"ThreadAAA").start();
        new Thread(new HoldLockThread(lockB,lockA),"ThreadBBB").start();
    }
}

class HoldLockThread implements Runnable {
    private String LockA;
    private String LockB;

    public HoldLockThread(String lockA, String lockB) {
        LockA = lockA;
        LockB = lockB;
    }

    @SneakyThrows
    @Override
    public void run() {
        synchronized (LockA) {
            System.out.println(Thread.currentThread().getName()+"\t 自己持有："+LockA+"\t尝试获得："+LockB);
            TimeUnit.SECONDS.sleep(2);

            synchronized (LockB) {
                System.out.println(Thread.currentThread().getName()+"\t 自己持有："+LockB+"\t尝试获得："+LockA);
            }
        }
    }
}
