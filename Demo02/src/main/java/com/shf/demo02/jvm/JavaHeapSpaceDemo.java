package com.shf.demo02.jvm;

import java.util.Random;

public class JavaHeapSpaceDemo {
    public static void main(String[] args) {
        String str = "shf";

        /**
         * Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
         */
        while (true) {
            str += str + new Random().nextInt(111111) + new Random().nextInt(69666);
        }
    }
}
