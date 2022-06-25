package com.shf.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS是什么？  =======》  compareAndSet
 *  比较并交换
 *
 * CAS底层原理
 *      自旋锁
 *      UNSafe类
 *
 * CAS缺点：
 *      多次比较循环事件长开销很大
 *      只能保证一个共享变量的原子性
 *      引出ABA问题
 *
 *     synchronized加锁，一致性保证，并发性下降
 *     CAS不加锁，保证一致性，但是它需要多次比较，耗时时间长，开销大
 */
public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);
//        如果当前值==预期值，则自动将值设置为给定的更新值
        System.out.println(atomicInteger.compareAndSet(5,10)+"\tcurrent data:"+atomicInteger.get());

        System.out.println(atomicInteger.compareAndSet(5,2020)+"\tcurrent data:"+atomicInteger.get());
    }
}
