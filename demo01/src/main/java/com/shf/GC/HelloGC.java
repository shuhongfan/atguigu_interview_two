package com.shf.GC;

import lombok.SneakyThrows;

public class HelloGC {
    @SneakyThrows
    public static void main(String[] args) {
        System.out.println("********** HelloGC");

//        Thread.sleep(Integer.MAX_VALUE);
//        byte[] byteArray = new byte[50 * 1024 * 1024];

    }
}
/**
 * -XX:+PrintGCDetails -XX:MetaspaceSize=128m -XX:MaxTenuringThreshold=15
 * <p>
 * 1. jsp -l
 * 2. jinfo -flag PrintGCDetails 15052
 * <p>
 * <p>
 * -Xms  1/64
 * -Xmx  1/4
 * <p>
 * -XX:+PrintFlagsInitial
 * -XX:+PrintFlagsFinal
 * -XX:+PrintFlagsFinal -version
 * -XX:+PrintCommandLineFlags
 *
 * -Xms128m
 * -Xmx4096m
 * -Xss1024k
 * -XX:MetaspaceSize=512m
 * -XX:+PrintCommandLineFlags
 * -XX:+PrintGCDetails
 * -XX:+UseSerialGC
 *
 * -XX:SurvivorRatio=8
 * eden:from:to  伊甸园与幸存者0/1区占比
 * 8:1:1
 *
 *-XX:NewRatio=4
 * new:tenured  新生代和老年代占比
 * 1:4
 *
 * java -XX:+PrintCommandLineFlags -version
 */
