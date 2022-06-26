package com.shf.Error;

import java.util.ArrayList;

/**
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
 * Exception in thread "main" java.lang.OutOfMemoryError: GC overhead limit exceeded
 *
 * GC回收时间过长时会抛出OutOfNemroyError。过长的定义是，超过98%的时间用来做GC并且回收了不到%的堆内存
 * 连续多次GC都只回收了不到2%的极端情况下才会抛出。
 *
 * 假如不抛出GC overhead limit错误会发生什么情况呢?
 * 那就是GC清理的这么点内存很快会再次填满，迫使GC再次执行．这样就形成恶性循环，CPU使用率一意是100%,i而GC却没有任何成果
 */
public class GCOverHeadErrorDemo {
    public static void main(String[] args) {
        int i=0;
        ArrayList<String> list = new ArrayList<>();

        try {
            while (true){
                list.add(String.valueOf(++i).intern());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("*************** i:"+i);
        }
    }
}
