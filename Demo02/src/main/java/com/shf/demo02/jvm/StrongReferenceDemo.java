package com.shf.demo02.jvm;

public class StrongReferenceDemo {
    public static void main(String[] args) {
        Object obj1 = new Object();
        Object obj2 = obj1;
        obj1 = null;

        System.gc();

        System.out.println(obj2);
    }
}