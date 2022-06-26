package com.shf.ArrayListNotSafe;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 集合类不安全的问题  java.util.ConcurrentModificationException并发修改异常
 *  解决方法：
 *      1. Collections.synchronizedList  /  Collections.synchronizedSet /  Collections.synchronizedMap
 *      2. new CopyOnWriteArrayList<>() /  new  CopyOnWriteArraySet<>() / new ConcurrentHashMap<>()
 *      3. new Vector<>();
 *
 *
 */
public class ContainerNotSafeDemo {
    public static void main(String[] args) {
//        Map<String, String> map = new HashMap<>();
//        Map<String, String> map = Collections.synchronizedMap(new HashMap<>());
        Map<String, String> map = new ConcurrentHashMap<>();

        for (int i = 1; i <= 30; i++) {
            new Thread(()->{
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 8));
                System.out.println(map);
            },String.valueOf(i)).start();
        }
    }

    public static void setNotSafe(String[] args) {
//        Set<String> set = Collections.synchronizedSet(new HashSet<>());
        Set<String> set = new CopyOnWriteArraySet<>();
        /**
         *     public CopyOnWriteArraySet() {
         *         al = new CopyOnWriteArrayList<E>();
         *     }
         */
        for (int i = 1; i <= 30; i++) {
            new Thread(()->{
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(set);
            },String.valueOf(i)).start();
        }


//        hashSet底层是hashMap
        new HashSet<>().add("a");
        /**
         *     public HashSet() {
         *         map = new HashMap<>();
         *     }
         */

    }


    public static void listNotSafe(String[] args) {
//        构造一个初始容量为 10 的空列表。
//        ArrayList<Integer> integerArrayList = new ArrayList<>();
//        integerArrayList.add(1);

//        List<String> list = Arrays.asList("a", "b", "c");
//        list.forEach(System.out::println);


        /**
         * 1. 故障现象 java.util.ConcurrentModificationException
         *
         * 2. 导致原因
         *
         * 3. 解决方案
         *      3.1 new Vector<>();
         *      3.2 Collections.synchronizedList(new ArrayList<>());  Collections辅助工具类
         *      3.3 new CopyOnWriteArrayList<>()
         * 4. 优化建议（同样的错误不犯第二次）
         *
         */

//        List<String> list = new Vector<>();  //  Vector线程安全
//        List<String> list = Collections.synchronizedList(new ArrayList<>()); // 构建线程安全的ArrayList
        List<String> list = new CopyOnWriteArrayList<>();

        for (int i = 1; i <= 3; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            },String.valueOf(i)).start();
        }



        /**
         * 写时复制  CopyOnWriteArrayList
         * 写时复制 copyOnWrite 容器即写时复制的容器 往容器添加元素的时候,不直接往当前容器object[]添加,而是先将当前容器object[]进行
         *      * copy 复制出一个新的object[] newElements 然后向新容器object[] newElements 里面添加元素 添加元素后,
         *      * 再将原容器的引用指向新的容器 setArray(newElements);
         *      * 这样的好处是可以对copyOnWrite容器进行并发的读,而不需要加锁 因为当前容器不会添加任何容器.所以copyOnwrite容器也是一种
         *      * 读写分离的思想,读和写不同的容器.
         *
         *   public boolean add(E e) {
         *         final ReentrantLock lock = this.lock;
         *         lock.lock();
         *         try {
         *             Object[] elements = getArray();
         *             int len = elements.length;
         *             Object[] newElements = Arrays.copyOf(elements, len + 1);
         *             newElements[len] = e;
         *             setArray(newElements);
         *             return true;
         *         } finally {
         *             lock.unlock();
         *         }
         *     }
         *
         */
    }
}
