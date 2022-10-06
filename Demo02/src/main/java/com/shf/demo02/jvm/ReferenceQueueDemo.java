package com.shf.demo02.jvm;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class ReferenceQueueDemo {
    public static void main(String[] args) {
        Object o1 = new Object();
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        WeakReference<Object> weakReference = new WeakReference<>(o1, queue);

        System.out.println(o1);
        System.out.println(weakReference.get());
        System.out.println(queue.poll());

        o1 = null;
        System.gc();

        System.out.println(o1);
        System.out.println(weakReference.get());
        System.out.println(queue.poll());
    }
}
