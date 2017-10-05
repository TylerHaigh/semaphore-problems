package com.haight.concurrency.signchanger;

import com.haight.semaphores.common.BinarySemaphore;

public class NeonSign {

    private BinarySemaphore sem = new BinarySemaphore();

    private int value = 0;
    private static final int DELAY_MILLI = 1000;

    public void increment() throws InterruptedException {

        sem.acquire();
        {
            System.out.println( System.currentTimeMillis() + " " +Thread.currentThread().getName() + ": I am incrementing the value");
            delay(DELAY_MILLI);
            value++;
            System.out.println( System.currentTimeMillis() + " " +Thread.currentThread().getName() + ": Done!");
        }
        sem.release();

    }

    private synchronized void delay(int milli) {
        try { wait(milli); } catch (InterruptedException e) { }
    }


    public void decrement() throws InterruptedException {
        sem.acquire();
        {
            System.out.println( System.currentTimeMillis() + " " +Thread.currentThread().getName() + ": I am decrementing the value");
            delay(DELAY_MILLI);
            value--;
            System.out.println( System.currentTimeMillis() + " " +Thread.currentThread().getName() + ": Done!");
        }
        sem.release();
    }

    public int value() { return value; }


}
