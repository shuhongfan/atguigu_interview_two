package com.shf.volatileJMM;

public class T1 {
    volatile int n = 0;

    public void add() {
        n++;
    }

}
