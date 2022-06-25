package com.shf.volatileJMM;

/**
 * DCL + volatile 单例模式
 */
public class SingletonDemo {
    private static volatile SingletonDemo instance = null;// 加入 volatile 禁止指令重排

    private SingletonDemo(){
        System.out.println(Thread.currentThread().getName()+"\t 我是构造器方法");
    }

    /**
     * 方式一： 加synchronized  不推荐
     * public static synchronized SingletonDemo getInstance(){
     *
     * 方式二：DCL  存在指令重排问题,必须加入volatile禁止指令重排
     * DCL （Double check Lock 双端检锁机制）
     *      原因在于某一个线程执行到第一次检测,读取到instance不为null时,instance的引用对象可能没有完成初始化
     *
     *      instance=new SingletonDemo(); 可以分为一下3步完成
     *      1. memory=allocate（） 分配对象内存空间
     *      2. instance（memory） 初始化对象
     *      3. instance = money  设置instance指向刚分配的内存地址，此时instance！=null
     *      步骤2和步骤3不存在数据依赖关系，因此指令重排优化是允许的
     * @return
     */
    public static SingletonDemo getInstance(){
        if (instance==null){
            synchronized (SingletonDemo.class){
                if (instance==null){
                    instance=new SingletonDemo();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
//        单线程（main线程的操作动作...）
//        System.out.println(SingletonDemo.getInstance()==SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance()==SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance()==SingletonDemo.getInstance());

//        并发多线程之后，情况发送很大的变化
        for (int i = 1; i <= 10; i++) {
            new Thread(()->{
                SingletonDemo.getInstance();
            },String.valueOf(i)).start();
        }
    }
}
