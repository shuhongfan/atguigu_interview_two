package com.shf.volatileJMM;

public class mySort {
    public void mySort() {
        int x = 11;  // 语句1
        int y = 12; // 语句2
        x = x + 5;  // 语句3
        y = x * x;  // 语句4
    }

}

/**
 * 指令重排
 * 1234
 * 2134
 * 1324
 */