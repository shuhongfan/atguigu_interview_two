package com.shf.demo02.thread;


import lombok.SneakyThrows;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyResource {
    private volatile boolean FLAG = true;
    private AtomicInteger atomicInteger = new AtomicInteger();

    BlockingQueue<String> blockingQueue = null;

    public MyResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    @SneakyThrows
    public void myProd() {
        String data = null;
        boolean retValue;

        while (FLAG) {
            data = atomicInteger.incrementAndGet()+"";
            retValue = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);

            if (retValue) {
                System.out.println(Thread.currentThread().getName()+"\t 插入队列"+data+"成功");
            } else {
                System.out.println(Thread.currentThread().getName()+"\t 插入队列"+data+"失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName()+"\t 大老板叫停了，表示FLAG=FALSE，生产动作结束");
    }

    @SneakyThrows
    public void myConsumer() {
        String result = null;
        while (FLAG) {
            result = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if (null == result || result.equalsIgnoreCase("")) {
                FLAG = false;
                System.out.println(Thread.currentThread().getName()+"\t 超规格2秒钟没有去到蛋糕，消费退出");
            }

            System.out.println(Thread.currentThread().getName()+"\t 消费队列"+result+"成功");
        }
    }

    public void stop() {
        this.FLAG = false;
    }
}

public class ProdConsumer_BlockQueueDemo {
    @SneakyThrows
    public static void main(String[] args) {
        MyResource myResource = new MyResource(new ArrayBlockingQueue<>(10));

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t 生产线程启动");
            myResource.myProd();
        },"prod").start();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t 消费线程启动");
            myResource.myConsumer();
        },"consumer").start();

        TimeUnit.SECONDS.sleep(4);
        myResource.stop();

        System.out.println("---------- 活动结束");
    }
}
