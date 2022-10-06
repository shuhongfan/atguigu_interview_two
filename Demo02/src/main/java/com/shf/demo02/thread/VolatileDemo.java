package com.shf.demo02.thread;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * JMM
 * 可见性
 * 原子性：不可分割、完整性，也即某个线程正在做某个具体业务时，中间不可以被加塞或者被分割。需要具体完成，要么同时成功，要么同时失败
 */
public class VolatileDemo {
    public static void main(String[] args) {
//        seeOkByVolatile();

        MyData myData = new MyData();

        for (int i = 1; i <= 20; i++) {
            new Thread(()->{
                for (int j = 1; j <=1000 ; j++) {
                    myData.addPlus();
                    myData.addAtomic();
                }
            },String.valueOf(i)).start();
        }

//        需要等待上面20个线程全部计算完成后，再用main线程取得最终的结果值看是多少
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName()+"\t finally number is:"+myData.number);
        System.out.println(Thread.currentThread().getName()+"\t finally number is:"+myData.number2);
    }

    /**
     * 验证 volatile的可见性
     */
    private static void seeOkByVolatile() {
        MyData myData = new MyData();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + "\t come in");
            try {
                TimeUnit.SECONDS.sleep(3);
                myData.addTo60();
                System.out.println(Thread.currentThread().getName() + "\t update value to 60");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"AAA").start();

//        第二个线程主线程
        while (myData.number == 0) {

        }
        System.out.println(Thread.currentThread().getName()+"\t mission is done");
    }
}

class MyData{
    // 可见性，禁止指令重排
    volatile int number = 0;

    public void addTo60() {
        this.number = 60;
    }

    public void addPlus() {
        number++;
    }

    AtomicInteger number2 = new AtomicInteger(0);

    public void addAtomic() {
        number2.getAndIncrement();
    }
}
