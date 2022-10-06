package com.shf.demo02.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABADemo {
    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 1);


    public static void main(String[] args) {
        new Thread(() -> {
            atomicReference.compareAndSet(100, 101);
            atomicReference.compareAndSet(101, 100);
        }, "t1").start();

        new Thread(() -> {
//            暂停1秒钟t2线程，保证上面的t1线程完成了一次ABA操作
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(atomicReference.compareAndSet(100, 123) + "\t" + atomicReference.get());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t2").start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t 第1次版本号：" + stamp);

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(Thread.currentThread().getName() + "\t 第2次版本号：" + atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(100, 101, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);

            System.out.println(Thread.currentThread().getName() + "\t 第3次版本号：" + atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101, 100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
        },"t3").start();

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t 第1次版本号：" + stamp);

            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            boolean res = atomicStampedReference.compareAndSet(100, 2022,stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName() + "\t 修改成功否：" + res + "\t 当前最新版本号为：" + stamp);
            System.out.println(Thread.currentThread().getName()+"\t当前实际最新值："+atomicStampedReference.getReference());
        },"t4").start();
    }
}
