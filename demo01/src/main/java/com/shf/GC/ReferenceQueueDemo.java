package com.shf.GC;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class ReferenceQueueDemo {
    public static void main(String[] args) {
        Object o1 = new Object();

//        引用队列
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();

//        虚引用
        WeakReference<Object> weakReference = new WeakReference<>(o1, referenceQueue);

        System.out.println(o1);
        System.out.println(weakReference.get());
        System.out.println(referenceQueue.poll());

        System.out.println("======================================");
        o1 = null;
        System.gc();


        System.out.println(o1);
        System.out.println(weakReference.get());
        System.out.println(referenceQueue.poll());

    }
}
