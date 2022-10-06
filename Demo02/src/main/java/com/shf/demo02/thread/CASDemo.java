package com.shf.demo02.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS  compareAndSet  比较并交换
 */
public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);
        System.out.println(atomicInteger.compareAndSet(5, 10)+"current data is:"+atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5, 1024)+"current data is:"+atomicInteger.get());

        atomicInteger.getAndIncrement();
    }
}
