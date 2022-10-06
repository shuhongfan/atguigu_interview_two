package com.shf.demo02.thread;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 *  在多线程领域，所谓阻塞，在某些情况下会挂起线程（既阻塞），一旦条件满足，被挂起的线程又会被唤醒
 *
 *  为什么我们需要BlockingQueue
 *  好处是我们不需要关系上面时候需要阻塞线程，上面时候需要唤醒先才，因为这一切BlockingQueue都给你一手包办了
 *
 *  在Concurrent包发布以前，在多线程环境下，我们每个程序员都必须去自己控制这些细节，尤其还要兼顾效率和线程安全，
 *  这会给我吗的程序带来不小的复杂度
 */
public class BlockingQueueDemo {
    public static void main(String[] args) {
        ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(3);
        System.out.println(blockingQueue.offer("A"));
        System.out.println(blockingQueue.offer("B"));
        System.out.println(blockingQueue.offer("C"));
        System.out.println(blockingQueue.offer("X"));



        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
    }
}
