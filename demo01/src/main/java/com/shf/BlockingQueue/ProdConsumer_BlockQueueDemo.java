package com.shf.BlockingQueue;


import lombok.SneakyThrows;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生产者消费者3.0版 阻塞队列版
 */
class MyResource {
    private volatile boolean FLAG = true; // 默认开启，进行生产+消费
    private AtomicInteger atomicInteger = new AtomicInteger(); // 原子整型

    BlockingQueue<String> blockingQueue = null; // 阻塞队列

    public MyResource(BlockingQueue<String> blockingQueue) { //构造方法,传接口，不传类
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    /**
     * 生产者
     */
    @SneakyThrows
    public void myProd() {
        String data = null;
        boolean retValue;
        while (FLAG) { // 是否生产
            data = atomicInteger.incrementAndGet() + "";
            retValue = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if (retValue) {
                System.out.println(Thread.currentThread().getName() + "\t 插入队列" + data + "成功");
            } else {
                System.out.println(Thread.currentThread().getName() + "\t 插入队列" + data + "失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName() + "\t大老板叫停了，表示FLAG=false,生产动作结束");
    }

    /**
     * 消费者
     */
    @SneakyThrows
    public void myConsumer() {
        String result = null;
        while (FLAG) { // 是否消费
            result = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if (null == result || result.equalsIgnoreCase("")) {
                FLAG=false;
                System.out.println(Thread.currentThread().getName()+"\t 超过2秒钟没有取到蛋糕，消费者退出");
                return;
            }
            System.out.println(Thread.currentThread().getName() + "\t消费队列" + result + "成功");
        }
    }

    /**
     * 停止消费
     */
    public void stop(){
        this.FLAG = false;
    }
}

/**
 * volatile / CAS / AtomicInteger / BlockQueue / 线程交互 / 原子引用
 */
public class ProdConsumer_BlockQueueDemo {
    @SneakyThrows
    public static void main(String[] args) {
//        MyResource myResource = new MyResource(new ArrayBlockingQueue<>(10));
        MyResource myResource = new MyResource(new LinkedBlockingQueue<>(10));

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t 生产线程启动");
            myResource.myProd();
        },"Prod").start();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t 消费线程启动");
            myResource.myConsumer();
        },"Consumer").start();

        TimeUnit.SECONDS.sleep(5);

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("5秒钟时间到，大老板main线程叫停，活动结束");

        myResource.stop();
    }
}
