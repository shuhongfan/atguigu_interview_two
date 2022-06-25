package com.shf.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁 spinlock
 *  尝试获取锁的线程不会立即阻塞，而是采用循环的方式去尝试获取锁，
 *  这样的好处是减少线程上下文切换的消耗，缺点是循环会消耗CPU
 */
public class SpinLockDemo {
    //        原子引用线程
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

//    加锁
    public void myLock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "\t come in");
//        CAS 比较并交换
        while (!atomicReference.compareAndSet(null, thread)) {

        }

    }

//    解锁
    public void myUnlock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println(Thread.currentThread().getName()+"\t invoke myUnLock");
    }

    public static void main(String[] args) throws InterruptedException {
        SpinLockDemo spinLockDemo = new SpinLockDemo();

        new Thread(()->{
            spinLockDemo.myLock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            spinLockDemo.myUnlock();
        },"AA").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(()->{
            spinLockDemo.myLock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            spinLockDemo.myUnlock();
        },"BB").start();
    }
}
