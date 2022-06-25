package com.shf.volatileJMM;

import java.util.concurrent.atomic.AtomicInteger;

class MyData {  //MyData.java ===> MyData.class ===> JVM字节码
    volatile int number = 0;

    public void addTO60() {
        this.number = 60;
    }

    //    请注意，此时number前面是加了volatile关键字修饰的，volatile不保证原子性
    public void addPlusPlus() {
        this.number++;
    }

    //    带原子的数字，默认值为0
    AtomicInteger atomicInteger = new AtomicInteger();
    public void addAtomic() { //    原子自增
        atomicInteger.getAndIncrement();
    }
}

/**
 * volatile是Java虚拟机提供的轻量级的同步机制 保证可见性、不保证原子性、禁止指令重拍
 *
 * 1.验证volatile的可见性
 * 1.1 假如int number = 0; number变量之前根本没有添加volatile关键字修饰符，没有可见性
 * 1.2 添加了volatile，可以解决可见性问题
 *
 * 2.验证volatile不保证原子性
 * 2.1 原子性指的是什么意思？
 * 不可分割，完整性，也即某个线程正在做某个具体业务，中间不可以被加塞或者被分割。需要整体完整
 * 要么同时成功，要么同时失败。
 *
 * 2.2 volatile不保证原子性的案例演示
 *
 * 2.3 why 为什么无法保证原子性
 *
 * 2.4 如何解决原子性？
 *      * 加async
 *      * 使我们的JUC下的AtomicInteger
 */
public class VolatileDemo {
    public static void main(String[] args) throws InterruptedException {
        MyData myData = new MyData(); // 资源类

        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 1000; j++) {
                    myData.addPlusPlus();
                    myData.addAtomic(); // 原子类
                }
            }, String.valueOf(i)).start();
        }

//        需要等待上面20个线程全部都计算完成后，再用main线程取得最终的结果值看是多少？
//        TimeUnit.SECONDS.sleep(5);
        while (Thread.activeCount() > 2) {  // 线程数量大于2
            Thread.yield(); // 礼让线程
        }
        System.out.println(Thread.currentThread().getName() + "\t int type, finally number value：" + myData.number);
        System.out.println(Thread.currentThread().getName() + "\t atomicInteger type, finally number value：" + myData.atomicInteger);
    }

    //    volatile 可以保证可见性，及时通知其他线程，主物理内存的值已经被修改
    public static void seeOKByVolatile(String[] args) {
        MyData myData = new MyData(); // 资源类

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in");
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            myData.addTO60();
            System.out.println(Thread.currentThread().getName() + "\t updated number value:" + myData.number);
        }, "AAA").start();

//        第二个线程就是我们的main线程
        while (myData.number == 0) {
//            main线程就一直在这里循环等待，直到number的值不再等于0

        }
        System.out.println(Thread.currentThread().getName() + "\t miss is over");
    }
}
