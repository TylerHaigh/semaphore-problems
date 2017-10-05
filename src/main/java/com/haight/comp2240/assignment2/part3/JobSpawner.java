package com.haight.comp2240.assignment2.part3;

import java.util.Random;

public class JobSpawner implements Runnable {

    private static final Random RANDOM = new Random();
    private static final int BoundOnNumberOfPagesToPrint = 5;
    private static final int DelayTimeout = 100;

    private JobType type;
    private int numberOfJobs;
    private Printer printer;
    public JobSpawner(JobType type, int numberOfJobs, Printer printer)
    {
        this.type = type;
        this.numberOfJobs = numberOfJobs;
        this.printer = printer;
    }

    public void spawnJob(int id) {
        PrinterJob job = new PrinterJob(id, RANDOM.nextInt(BoundOnNumberOfPagesToPrint), this.type, this.printer);

        Thread t = new Thread(job);
        t.start();
    }

    @Override
    public void run() {
        for(int i = 0; i < numberOfJobs; i++) {
            spawnJob(i);
            delay();
        }
    }

    private void delay() {
        try { Thread.sleep(DelayTimeout); }
        catch (InterruptedException e) { e.printStackTrace(); }
    }
}
