package com.shf.ABA;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ABA问题的解决  AtomicStampedReference 增加版本号
 */
public class ABADemo {
    static AtomicReference<Integer> atomicReference =
            new AtomicReference<>(100);
//    版本号
    static AtomicStampedReference<Integer> atomicStampedReference =
        new AtomicStampedReference<Integer>(100, 1);


    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            atomicReference.compareAndSet(100, 101);
            atomicReference.compareAndSet(101, 100);
        }, "t1").start();

        new Thread(() -> {
//            暂停线程1秒钟t2线程，保证上面的t1线程完成了一个ABA操作
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(atomicReference.compareAndSet(100, 2022) + "\t" + atomicReference.get());
        }, "t2").start();

        TimeUnit.SECONDS.sleep(2);
        System.out.println("===========以下是ABA问题的解决，增加版本号=============");

        new Thread(()->{
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t第1次版本号:"+stamp);

            try {
                TimeUnit.SECONDS.sleep(1); // 暂停1秒钟t3线程
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            atomicStampedReference.compareAndSet(100, 101,atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName()+"\t第2次版本号:"+atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101, 100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName()+"\t第3次版本号:"+atomicStampedReference.getStamp());

        },"t3").start();

        new Thread(()->{
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t第1次版本号:"+stamp);

            try {
                TimeUnit.SECONDS.sleep(3); // 暂停3秒钟t4线程
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            boolean result = atomicStampedReference.compareAndSet(100, 2022, stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName()
                    +"\t修改是否成功:"+result+
                    "\t当前最新实际版本号："+ atomicStampedReference.getStamp());
            System.out.println(Thread.currentThread().getName()+
                    "\t当前实际最新值："+atomicStampedReference.getReference());
        },"t4").start();
    }
}
