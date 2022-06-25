package com.shf.BlockingQueue;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 1. 队列FIFO 先进先出
 *
 * 2. 阻塞队列
 *      2.1 阻塞队列有没有好的一面
 *      2.2 不得不阻塞，你如何管理
 *
 */
public class BlockingQueueDemo {
    /**
     * 超时
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        System.out.println(blockingQueue.offer("a",2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("b",2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("c",2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("x",2L, TimeUnit.SECONDS));

        blockingQueue.poll(2L, TimeUnit.SECONDS);
        blockingQueue.poll(2L, TimeUnit.SECONDS);
        blockingQueue.poll(2L, TimeUnit.SECONDS);
        blockingQueue.poll(2L, TimeUnit.SECONDS);
    }

    /**
     * 阻塞
     * @param args
     * @throws InterruptedException
     */
    public static void putOrTake(String[] args) throws InterruptedException {
        ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(3);
        blockingQueue.put("a");
        blockingQueue.put("b");
        blockingQueue.put("c");
//        blockingQueue.put("x");

        blockingQueue.take();
        blockingQueue.take();
        blockingQueue.take();
        blockingQueue.take();
    }

    /**
     * 特殊值
     * @param args
     */
    public static void offerOrPoll(String[] args) {
        ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(3);
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        System.out.println(blockingQueue.offer("x"));

        System.out.println(blockingQueue.peek());

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
    }

    /**
     * 抛出异常
     * @param args
     */
    public static void addOrRemove(String[] args) {
        ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(3);

        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));

//        Exception in thread "main" java.lang.IllegalStateException: Queue full
//        System.out.println(blockingQueue.add("x"));

        System.out.println(blockingQueue.element());  //   返回队首元素

        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());

//        Exception in thread "main" java.util.NoSuchElementException
//        System.out.println(blockingQueue.remove());
    }
}
