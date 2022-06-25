package com.shf.BlockingQueue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 题目： synchronize和lock有什么区别？ 用新的lock有什么好处？请你举例说说
 * 1. 原始构成
 * synchronize是关键字属于JVM层面
 * monitorenter
 * 底层是通过monitor对象完成，其实wait / notify等方法也依赖monitor对象，
 * 只有在同步代码块或方法中才能调用wait / notify等方法
 * monitorexit
 * <p>
 * Lock 是具体类 （java.util.concurrent.locks.lock） 是api层面的锁
 * new #3 <java/util/concurrent/locks/ReentrantLock>
 * <p>
 * 2. 使用方法
 * synchronized 不需要用户手动释放锁，当synchronized代码执行完成后系统会自动让线程释放对锁的占用
 * ReentrantLock 则需要用户去手动释放锁若没有主动释放锁，就有可能导致死锁现象。
 * 需要lock()和unLock()方法配合try/finally语句块来完成
 * <p>
 * 3. 等待是否可中断
 * synchronize 不可中断，除非抛出异常后者正常运行完成
 * ReentrantLock 可中断，
 * 1） 设置超时方法 tryLock（long timeout，TimeUnit unit）
 * 2） lockInterruptibly（）放入代码块中，调用interrupt（）方法可中断
 * <p>
 * 4. 加锁是否公平
 * synchronize 非公平锁
 * ReentrantLock 两者都可以，默认非公平锁，构造方法可以传入Boolean值，true为公平锁，false为非公平锁
 * <p>
 * 5. 锁绑定多个条件Condition
 * synchronize 没有
 * ReentrantLock 用来实现分组唤醒需要唤醒的线程们，可以精确唤醒，而
 * 不是像Synchronize要么随机唤醒一个线程要么唤醒全部线程
 */

/**
 * 题目： 多线程之间按顺序调用，实现 A --->  B  ---->  C  三个线程启动，要去如下：
 * AA打印5次，BB打印10次，CC打印15次  10轮
 *
 * 锁绑定多条件condition，多线程调度和通知唤醒
 */

class ShareResource { // 资源类
    private int number = 1;  // A:1 B:2 C:3
    private Lock lock = new ReentrantLock();  // 锁
    private Condition c1 = lock.newCondition();  // 3把备用钥匙
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();


    public void print5() {
        lock.lock();
        try {
            //    1.判断
            while (number != 1) {
                c1.wait();
            }
//            2.干活
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
//            3.通知
            number = 2; // 修改标志位
            c2.signal(); // 通知一个人c2
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void print10() {
        lock.lock();
        try {
            //    1.判断
            while (number != 2) {
                c2.wait();
            }
//            2.干活
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
//            3.通知
            number = 3; // 修改标志位
            c3.signal(); // 通知一个人c3
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void print15() {
        lock.lock();
        try {
            //    1.判断
            while (number != 3) {
                c3.wait();
            }
//            2.干活
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
//            3.通知
            number = 1; // 修改标志位
            c1.signal(); // 通知一个人c1
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

}

public class SyncAndReentrantLockDemo {
    public static void main(String[] args) {
//        线程操作资源类
        ShareResource shareResource = new ShareResource();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                shareResource.print5();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                shareResource.print10();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                shareResource.print15();
            }
        }, "C").start();
    }
}