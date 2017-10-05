package com.haight.comp2240.assignment2.part3;

public class Program {

    private static final int NumberOfMonochromeJobs = 5;
    private static final int NumberOfColourJobs = 5;

    public static void main(String[] args) {

        Printer p = new Printer();

        JobSpawner mono = new JobSpawner(JobType.Monochrome, NumberOfMonochromeJobs, p);
        JobSpawner col = new JobSpawner(JobType.Colour, NumberOfColourJobs, p);

        new Thread(mono).start();
        new Thread(col).start();
    }

}
