package com.shf.demo02.thread;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 原子引用
 */
public class AtomicReferenceDemo {
    public static void main(String[] args) {
        AtomicReference<User> atomicReference = new AtomicReference<>();

        User zs = new User("zs", 22);
        User li4 = new User("li4", 25);

        atomicReference.set(zs);

        System.out.println(atomicReference.compareAndSet(zs,li4)+"\t"+atomicReference.get().toString());
        System.out.println(atomicReference.compareAndSet(zs,li4)+"\t"+atomicReference.get().toString());
    }
}

@Getter
@ToString
@AllArgsConstructor
class User {
    String userName;
    int age;
}
