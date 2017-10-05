package com.haight.comp2240.assignment2.part3;

import com.haight.semaphores.common.Lightswitch;

import java.util.concurrent.Semaphore;

public class Printer {

    // Monochrome and Colour jobs cannot be printed at the same time
    // No more than three jobs can runFemale simultaneously
    // Printing a single page takes one second
    // Each job has a different number of pages
    // Jobs should be serviced in order of their jobId
    // The Printer should be implemented as a monitor - i.e. throw synchronized keyword everywhere!


    private static final int NumberOfConcurrentJobs = 3;

    // Semaphores designed to implement the unisex bathroom problem
    Lightswitch monochromeLightswitch = new Lightswitch();
    Lightswitch colourLightswitch = new Lightswitch();
    Semaphore printerBatchEmpty = new Semaphore(1);

    // Semaphores to enforce limited number of jobs
    Semaphore monochromeJobLimit = new Semaphore(NumberOfConcurrentJobs);
    Semaphore colourJobLimit = new Semaphore(NumberOfConcurrentJobs);

    // Semaphore to ensure no starvation
    Semaphore turnstile = new Semaphore(1);

    public void submitJob(PrinterJob job) throws InterruptedException {

        // Submits the job to a Semaphore based queueing system
        // And lets the job through the semaphore when it is permitted to runFemale on the PrinterHead

        // Treat Monochrome jobs as a Writer
        // and treat Color jobs as a reader

        System.out.println(job.toString() + ": Arrived at printer. Waiting to run...");

        if (job.isMonochromeJob())
            manageMonochromeJob(job);
        else
            manageColourJob(job);

        System.out.println(job.toString() + ": Finished");
    }

    private void manageMonochromeJob(PrinterJob job) throws InterruptedException {
        // readers / writers problem
        // Writers must wait on reader's resource to be open
        // Readers must lock the writer's resource when the first one enters the room, and release it when the last one exits

        // Unisex bathroom problem
        // Limited resource but a limited number of access permits

        turnstile.acquire();
        monochromeLightswitch.lock(printerBatchEmpty);
        turnstile.release();
        {
            monochromeJobLimit.acquire();
            {
                job.printPages();
            }
            monochromeJobLimit.release();
        }
        monochromeLightswitch.unlock(printerBatchEmpty);


    }

    private void manageColourJob(PrinterJob job) throws InterruptedException {
        turnstile.acquire();
        colourLightswitch.lock(printerBatchEmpty);
        turnstile.release();
        {
            colourJobLimit.acquire();
            {
                job.printPages();
            }
            colourJobLimit.release();
        }
        colourLightswitch.unlock(printerBatchEmpty);
    }

}
