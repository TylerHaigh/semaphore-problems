package com.haight.semaphores.common;

public class Counter {

    private int cnt = 0;

    public Counter() { cnt = 0; }
    public Counter(int initVal) { cnt = initVal; }

    public void increment() { cnt++; }
    public void decrement() { cnt--; }
    public void reset() { cnt = 0; }
    public int value() { return cnt; }


}
