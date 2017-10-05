package com.haight.semaphores.bathroom;

public class Female extends Person {

    public Female(Bathroom b) { super(b); }

    @Override
    public void run() {
        try { bathroom.runFemale(this); }
        catch (InterruptedException e) { e.printStackTrace(); }
    }

    @Override
    public void routine() throws InterruptedException {

        for (int i = 0; i < DurationToRunFor; i++) {
            System.out.println("Female in the bathroom...");
            Thread.sleep(SleepTimeout);
        }

        System.out.println("Finished!");
    }
}
