package com.shf.GC;

import java.lang.ref.SoftReference;

public class SoftReferenceDemo {
    /**
     * 内存够用的时候就保留，不够用就回收
     */
    public static void softRef_Memory_Enough() {
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<Object>(o1);
        System.out.println(o1);
        System.out.println(softReference.get());

        o1=null;
        System.gc();

        System.out.println(o1);
        System.out.println(softReference.get());
    }

    /**
     *  内存不够用
     *  -Xms5m -Xmx5m -XX:+PrintGCDetails
     */
    public static void softRef_Memory_NotEnough() {
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<Object>(o1);
        System.out.println(o1);
        System.out.println(softReference.get());

        o1=null;


        try {
            byte[] bytes = new byte[30 * 1024 * 1024];
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println(o1);
            System.out.println(softReference.get());
        }
    }

    public static void main(String[] args) {
//        softRef_Memory_Enough();
        softRef_Memory_NotEnough();
    }
}
