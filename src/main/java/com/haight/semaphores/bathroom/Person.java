package com.haight.semaphores.bathroom;

public abstract class Person implements Runnable {

    protected static final int DurationToRunFor = 3;
    protected static final int SleepTimeout = 100;

    protected Bathroom bathroom;

    public Person(Bathroom bathroom) { this.bathroom = bathroom; }

    public abstract void routine() throws InterruptedException;
}
