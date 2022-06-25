package com.shf.CountDownLatch;

import lombok.SneakyThrows;

import java.util.concurrent.CountDownLatch;

/**
 * 让一些线程阻塞直到另外一些完成后才被唤醒  做减法
 *
 * CountDownLatch主要有两个方法,当一个或多个线程调用await方法时,调用线程会被阻塞.
 * 其他线程调用countDown方法计数器减1(调用countDown方法时线程不会阻塞),
 * 当计数器的值变为0,因调用await方法被阻塞的线程会被唤醒,继续执行
 */
public class CountDownLatchDemo {
    @SneakyThrows
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 1; i <= 6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"国，被灭");
                countDownLatch.countDown();
            }, CountryEnum.forEach_countryEnum(i).getRetMessage()).start();
        }

        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"\t *****秦帝国，一统天下");
        System.out.println();
        System.out.println(CountryEnum.ONE);
        System.out.println(CountryEnum.ONE.getRetCode());
        System.out.println(CountryEnum.ONE.getRetMessage());
    }

    @SneakyThrows
    public static void closeDoor(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 1; i <= 6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t 上完自习，离开教室");
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }

        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"\t ########### 班长最后关灯走人");
    }
}
