package com.shf.demo02.jvm;

import java.lang.ref.SoftReference;

public class SoftReferenceDemo {
    public static void softRef_Memory_Enough() {
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<Object>(o1);

        System.out.println(o1);
        System.out.println(softReference.get());

        o1 = null;
        System.gc();

        System.out.println(o1);
        System.out.println(softReference.get());
    }

    public static void main(String[] args) {
            softRef_Memory_Enough();
    }
}
