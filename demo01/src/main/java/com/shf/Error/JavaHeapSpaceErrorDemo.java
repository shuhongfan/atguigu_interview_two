package com.shf.Error;

/**
 * -Xms10m -Xmx10m
 * Exception in thread "main" java.lang.OutOfMemoryError: Java heap space  堆溢出
 */
public class JavaHeapSpaceErrorDemo {
    public static void main(String[] args) {
//        String str = "shuhongfan";
//
//        while (true){
//            str+=str+new Random().nextInt(11111);
//            str.intern();
//        }


        byte[] bytes = new byte[80 * 1024 * 1024];
    }
}
