package com.shf.volatileJMM;

/**
 *  计算机在执行程序的时候，为了提高篇性能，编译器和处理器的常常会对指令做重排，一般分以下3种：
 *
 *  1. 单线程环境里面确保程序最终执行结果和代码顺序执行的结果一致
 *
 *  2. 处理器在进行重排序时必须要考虑指令之间的【数据依赖性】
 *
 *  3.多线程环境中线程交替执行，由于编译器优化重排的存在，两个线程中使用的变量能否保证一致性是无法确定的，结果无法预测
 *
 */
public class ReSortSeqDemo {
    int a = 0;
    boolean flag = false;

    public void method1(){
//        多线程环境中线程交替执行，由于编译器优化重排的存在，
//        两个线程中使用的变量能否保证一致性是无法确定的，结果无法预测
        a=1;
        flag=true;
    }

    public void method2(){
        if (flag){
            a=a+5;
            System.out.println("********retValue:"+a);
        }
    }
}
