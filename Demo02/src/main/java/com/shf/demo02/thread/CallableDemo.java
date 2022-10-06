package com.shf.demo02.thread;

import lombok.SneakyThrows;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class CallableDemo {
    @SneakyThrows
    public static void main(String[] args) {
        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread());

        new Thread(futureTask, "AA").start();
        new Thread(futureTask, "BB").start();

        Integer res = 100;
        Integer res2 =0;
        while (!futureTask.isDone()) {
            res2 = futureTask.get();
        }

        System.out.println("****result:"+(res+res2));
    }
}

class MyThread implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println("-***** come in Callable");
        TimeUnit.SECONDS.sleep(2);
        return 1024;
    }
}

