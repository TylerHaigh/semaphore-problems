package com.haight.semaphores.bathroom;

public class Male extends Person {

    public Male(Bathroom b) { super(b); }

    @Override
    public void run() {
        try { bathroom.runMale(this); }
        catch (InterruptedException e) { e.printStackTrace(); }
    }

    @Override
    public void routine() throws InterruptedException {
        for (int i = 0; i < DurationToRunFor; i++) {
            System.out.println("Male in the bathroom...");
            Thread.sleep(SleepTimeout);
        }

        System.out.println("Finished!");
    }
}
