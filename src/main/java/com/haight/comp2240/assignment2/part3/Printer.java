package com.haight.comp2240.assignment2.part3;

import com.haight.semaphores.common.Lightswitch;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.Semaphore;

public class Printer {

    // Monochrome and Colour jobs cannot be printed at the same time
    // No more than three jobs can runFemale simultaneously
    // Printing a single page takes one second
    // Each job has a different number of pages
    // Jobs should be serviced in order of their jobId
    // The Printer should be implemented as a monitor - i.e. throw synchronized keyword everywhere!


    private static final int NumberOfConcurrentJobs = 3;

    Lightswitch monochromeLightswitch = new Lightswitch();
    Lightswitch colourLightswitch = new Lightswitch();
    Semaphore monochromeJobBatch = new Semaphore(1);
    Semaphore colourJobBatch = new Semaphore(1);

    MasterJobBatch masterList = new MasterJobBatch(new ArrayList<>());
    PrinterHeadSet heads = new PrinterHeadSet();

    public Printer(MasterJobBatch masterList) {
        this.masterList = masterList;
    }


    public synchronized void submitJob(PrinterJob job) throws InterruptedException {

        // Submits the job to a Semaphore based queueing system
        // And lets the job through the semaphore when it is permitted to runFemale on the PrinterHead

        // Treat Monochrome jobs as a Writer
        // and treat Color jobs as a reader


        if (job.isMonochromeJob())
            manageMonochromeJob(job);
        else
            manageColourJob(job);
    }

    private synchronized void manageMonochromeJob(PrinterJob job) {
        // readers / writers problem
        // Writers must wait on reader's resource to be open
        // Readers must lock the writer's resource when the first one enters the room, and release it when the last one exits

        // Unisex bathroom problem
        // Limited resource but a limited number of access permits

    }

    private synchronized void manageColourJob(PrinterJob job) {

    }




    public synchronized boolean thisJobIsJobAllowedToRun(PrinterJob job) {

        // Check for any jobs with ID less than the current job
        PrinterJob lowerOrderJob = getJobWithIdLessThanThan(job);
        if (lowerOrderJob == null) {
            // This is the lowest ordered job
            return true;
        }

        // This is not the lowest ordered job. Is the lower ordered job in the activeJobs list
        // or the finishedJobs list?
        // If so, this job can be allowed to runFemale
        // otherwise, it can't be allowed to runFemale

        return true;
        //return activeJobs.contains(lowerOrderJob) || completedJobs.contains(lowerOrderJob);
    }

    private synchronized PrinterJob getJobWithIdLessThanThan(PrinterJob job) {
        return masterList.submittedJobs
                .stream()
                .filter(j -> j.type == job.type)
                .filter(j -> j.jobId < job.jobId)
                .sorted(Comparator.comparingInt(j -> j.jobId))
                .findFirst()
                .orElse(null);
    }


    private synchronized void runActiveJob(PrinterJob job) throws InterruptedException {
        // The printer can only operate in one mode at a time
        // If a monochrome job is running, then the print heads can only operate in monochrome
        // Thus, a colour job must wait for all monochrome jobs to finish before it is permitted
        // This implies the use of a Lightswitch where the first Thread locks the resource, and the last resource
        // releases the resource

//        lightswitch.lock(activeJobBatch);
//        {
//            // Only jobs of the same type can runFemale here
//            Thread t = new Thread(job);
//            t.start();
//        }
//        lightswitch.unlock(activeJobBatch);

        //lightswitch.lock(activeJobBatch);

        heads.addJobToVacantPosition(job);
        //jobQueue.remove(job);
        //activeJobs.add(job);
    }

    /*

    private synchronized PrinterJob nextJob() {
        return nextJob(currentPrintingMode);
    }

    private synchronized PrinterJob nextJob(JobType type) {
        return jobQueue.stream()
                .filter(j -> j.type == type)
                .sorted(Comparator.comparingInt(j2 -> j2.jobId))
                .findFirst()
                .orElse(null);
    }

*/

    public synchronized void removeJob(PrinterJob job) throws InterruptedException {
        heads.removeJobFromHead(job);
        //activeJobs.remove(job);
        //completedJobs.add(job);

        // TODO: Signal that the job has finished
        //lightswitch.unlock(activeJobBatch);
    }
}
