package com.shf.demo02.thread;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Exception in thread "16" Exception in thread "21" Exception in thread "19" java.util.ConcurrentModificationException
 */
public class ContainerNoSafeDemo {
    public static void main(String[] args) {
//        List<Object> list = Collections.synchronizedList(new ArrayList<>());
        List<Object> list = new CopyOnWriteArrayList<>();
        CopyOnWriteArraySet<Object> set = new CopyOnWriteArraySet<>();
        ConcurrentHashMap<String, String> stringStringConcurrentHashMap = new ConcurrentHashMap<>();

        for (int i = 1; i <= 30; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            },String.valueOf(i)).start();
        }
    }
}
