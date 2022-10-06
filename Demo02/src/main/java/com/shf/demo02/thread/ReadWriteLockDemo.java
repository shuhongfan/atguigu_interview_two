package com.shf.demo02.thread;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class MyCache{
    private volatile Map<String, Object> map = new HashMap<>();
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void put(String key, Object value) {
        lock.writeLock().lock();
        System.out.println(Thread.currentThread().getName()+"\t 正在写入"+key);
        try {
            TimeUnit.MICROSECONDS.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        map.put(key, value);
        System.out.println(Thread.currentThread().getName()+"\t 写入完成"+key);
        lock.writeLock().unlock();
    }

    public void get(String key) {
        lock.readLock().lock();
        System.out.println(Thread.currentThread().getName()+"\t 正在读取："+key);
        try {
            TimeUnit.MICROSECONDS.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Object result = map.get(key);
        System.out.println(Thread.currentThread().getName()+"\t 读取完成："+result);
        lock.readLock().unlock();
    }
}

public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        for (int i = 1; i <= 5; i++) {
            final int tempInt = i;

            new Thread(()->{
                myCache.put(tempInt+"",tempInt+"");
            }).start();
        }

        for (int i = 1; i <= 5; i++) {
            final int tempInt = i;

            new Thread(()->{
                myCache.get(tempInt+"");
            }).start();
        }
    }
}
