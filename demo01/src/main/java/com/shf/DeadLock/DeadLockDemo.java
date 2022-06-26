package com.shf.DeadLock;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

class HoldLockThread implements Runnable {
    private String lockA;
    private String lockB;

    public HoldLockThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @SneakyThrows
    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "\t 自己持有:" + lockA + "\t 尝试获取：" + lockB);

            TimeUnit.SECONDS.sleep(2);

            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t 自己持有:" + lockB + "\t 尝试获取：" + lockA);
            }
        }
    }
}

/**
 * 死锁是指两个或两个以上的进程在执行过程中，因争夺资源而造成的一种互相等待的现象，
 * 若无外力干涉那它们都将无法推进下去
 * <p>
 * 解决
 * jps命令定位进程号
 * jstack查找到死锁
 */
public class DeadLockDemo {
    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";

        new Thread(new HoldLockThread(lockA, lockB), "ThreadAAA").start();
        new Thread(new HoldLockThread(lockB, lockA), "ThreadBBB").start();
    }
}
