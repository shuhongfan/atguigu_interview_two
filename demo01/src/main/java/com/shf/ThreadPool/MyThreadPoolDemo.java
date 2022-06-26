package com.shf.ThreadPool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * 获得java多线程的方式
 * 1. 继承thread类
 * 2. 实现Runnable接口，没有返回值，不抛异常，run方法
 * 3. 实现callable接口，有返回值，会抛出异常，call方法
 * 4. 线程池
 *   线程池做的工作主要是控制运行的线程的数量,处理过程中将任务加入队列,然后在线程创建后启动这些任务,
 *   如果先生超过了最大数量,超出的数量的线程排队等候,等其他线程执行完毕,再从队列中取出任务来执行.
 *
 * 他的主要特点为:线程复用:控制最大并发数:管理线程.
 * 第一:降低资源消耗.通过重复利用自己创建的线程降低线程创建和销毁造成的消耗.
 * 第二: 提高响应速度.当任务到达时,任务可以不需要等到线程和粗昂就爱你就能立即执行.
 * 第三: 提高线程的可管理性.线程是稀缺资源,如果无限的创阿金,不仅会消耗资源,还会较低系统的稳定性,使用线程池可以进行统一分配,调优和监控.
 *
 * Java中的线程池是通过Executor框架实现的,该框架中用到了Executor,Executors,ExecutorService,ThreadPoolExecutor这几个类.
 *
 * 说说线程池的底层工作原理?
 * 1.假设一开始只有两个核心线程（既corePoolSize），请求的数量也只能两个，但在后面请求的数量越来越多，在队列（既BlockingQueue）这里等待的人爆满了
 * 2.那么maximumPool就会开启最大非核心线程数来进行处理请求的数量，但是若在BlockingQueue这里等待的人已经爆满了，最大线程数和队列都爆满了，
 * 3.那么handler就会开始拒绝其他正在大量的请求进来。
 * 4.如果后期慢慢的请求量越来越少，也即请求量的数量开始少于目前线程的数量，那么此时线程池就会开始对目前已经空余的线程进行一段时间的等待，
 * 若此时这等待的时间中，无再有更多更大量的请求量进来，也即现在来的请求数量里只需核心线程就能够处理的话，那么就会把多余的线程进行销毁，直至剩下两个核心线程（既corePoolSize）。
 *
 * 11.线程池用过吗?生产上你是如何设置合理参数
 * 等待队列也已经排满了,再也塞不下新的任务了
 * 同时,
 * 线程池的max也到达了,无法接续为新任务服务
 * 这时我们需要拒绝策略机制合理的处理这个问题.
 * AbortPolicy(默认):直接抛出RejectedException异常阻止系统正常运行
 * CallerRunPolicy:"调用者运行"一种调节机制,该策略既不会抛弃任务,也不会抛出异常,而是将某些任务回退到调用者，从而降低新任务的流量。
 * DiscardOldestPolicy:抛弃队列中等待最久的任务,然后把当前任务加入队列中尝试再次提交当前任务
 * DiscardPolicy:直接丢弃任务,不予任何处理也不抛出异常.如果允许任务丢失,这是最好的一种拒绝策略方案
 * 以上内置策略均实现了RejectExecutionHandler接口
 *
 * CPU 密集型： CPU核数 + 1个线程的线程池
 * IO 密集型： CPU核数 / （1 - 阻塞系数）  阻塞系数在 0.8-0.9之间
 */
public class MyThreadPoolDemo {
    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());

        ExecutorService threadPool = new ThreadPoolExecutor(
                2,  //1.corePoolSize:线程池中的常驻核心线程数；1.在创建了线程池后,当有请求任务来之后,就会安排池中的线程去执行请求任务,近视理解为今日当值线程 2.当线程池中的线程数目达到corePoolSize后,就会把到达的任务放入到缓存队列当中.
                5, // 2.maximumPoolSize:线程池能够容纳同时执行的最大线程数,此值大于等于1
                1L, // 3.keepAliveTime:多余的空闲线程存活时间,当空间时间达到keepAliveTime值时,多余的线程会被销毁直到只剩下corePoolSize个线程为止，默认情况下:只有当线程池中的线程数大于corePoolSize时keepAliveTime才会起作用,知道线程中的线程数不大于corepoolSIze,
                TimeUnit.SECONDS, //4.unit:keepAliveTime的单位
                new LinkedBlockingQueue<Runnable>(3), //5.workQueue:任务队列,被提交但尚未被执行的任务.
                Executors.defaultThreadFactory(),  //6.threadFactory:表示生成线程池中工作线程的线程工厂,用户创建新线程,一般用默认即可
                new ThreadPoolExecutor.DiscardPolicy()); //7.handler:拒绝策略,表示当线程队列满了并且工作线程大于等于线程池的最大显示 数(maxnumPoolSize)时如何来拒绝.

        try {
            for (int i = 1; i <= 10; i++) {
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"\t 办理业务");
                });
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            threadPool.shutdown();
        }
    }

    public static void threadPoolInit(String[] args) {
//        创建一个定长线程池,可控制线程的最大并发数,超出的线程会在队列中等待.
//        newFixedThreadPool创建的线程池corePoolSize和MaxmumPoolSize是 相等的,它使用的的LinkedBlockingQueue
        ExecutorService threadPool = Executors.newFixedThreadPool(5);// 一池五处理线程

//        创建一个单线程化的线程池,它只会用唯一的工作线程来执行任务,保证所有任务都按照指定顺序执行.
//        newSingleThreadExecutor将corePoolSize和MaxmumPoolSize都设置为1,它使用的的LinkedBlockingQueue
//        ExecutorService threadPool = Executors.newSingleThreadExecutor();//一池一处理线程

//        创建一个可缓存线程池,如果线程池长度超过处理需要,可灵活回收空闲线程,若无可回收,则创建新线程.
//        newCachedThreadPool将corePoolSize设置为0；MaxmumPoolSize设置为Integer.MAX_VALUE,
//        它使用的是SynchronousQUeue,也就是说来了任务就创建线程运行,如果线程空闲超过60秒,就销毁线程
//        ExecutorService threadPool = Executors.newCachedThreadPool();//  一池N处理线程


//        模拟10个用户来办理业务，每个用户都是一个来自外部的请求线程
        try {
            for (int i = 1; i <= 20; i++) {
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"\t 办理业务");
                });
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            threadPool.shutdown();
        }
    }
}

/**
 * 使用给定的初始参数创建一个新的ThreadPoolExecutor 。
 * 参形：
 * corePoolSize - 保留在池中的线程数，即使它们是空闲的，除非设置allowCoreThreadTimeOut
 * maximumPoolSize – 池中允许的最大线程数
 * keepAliveTime – 当线程数大于核心时，这是多余的空闲线程在终止前等待新任务的最长时间。
 * unit – keepAliveTime参数的时间单位
 * workQueue – 用于在执行任务之前保存任务的队列。此队列将仅保存由execute方法提交的Runnable任务。
 * threadFactory – 执行器创建新线程时使用的工厂
 * handler – 由于达到线程边界和队列容量而阻塞执行时使用的处理程序
 *
 * public ThreadPoolExecutor(int corePoolSize,
 *                               int maximumPoolSize,
 *                               long keepAliveTime,
 *                               TimeUnit unit,
 *                               BlockingQueue<Runnable> workQueue,
 *                               ThreadFactory threadFactory,
 *                               RejectedExecutionHandler handler) {
 *         if (corePoolSize < 0 ||
 *             maximumPoolSize <= 0 ||
 *             maximumPoolSize < corePoolSize ||
 *             keepAliveTime < 0)
 *             throw new IllegalArgumentException();
 *         if (workQueue == null || threadFactory == null || handler == null)
 *             throw new NullPointerException();
 *         this.acc = System.getSecurityManager() == null ?
 *                 null :
 *                 AccessController.getContext();
 *         this.corePoolSize = corePoolSize;
 *         this.maximumPoolSize = maximumPoolSize;
 *         this.workQueue = workQueue;
 *         this.keepAliveTime = unit.toNanos(keepAliveTime);
 *         this.threadFactory = threadFactory;
 *         this.handler = handler;
 *     }
 */