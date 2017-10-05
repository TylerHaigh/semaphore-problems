package com.haight.comp2240.assignment2.part3;

import java.util.ArrayList;
import java.util.List;

public class MasterJobBatch {

    // Models the input file whereby n jobs are to be submitted to the printer.
    // The printer needs to know exactly what is to be submitted
    // because when the threaded jobs are submitted, there is no way to guarantee that job
    // n will be submitted after job n-1. Likewise, for a given job n, it could be the last
    // job to arrive at the printer. The printer will not know of the existence of n until such time
    // and would otherwise believe that n+1 is the lowest order job and start running


    public int numberOfJobsSubmitted = 0;
    public List<PrinterJob> submittedJobs = new ArrayList<>();

    public MasterJobBatch(List<PrinterJob> printerJobs) {
        submittedJobs = printerJobs;
        numberOfJobsSubmitted = printerJobs.size();
    }

    public void removeJob(PrinterJob job) { submittedJobs.remove(job); }
    public int remainingJobs() { return submittedJobs.size(); }

}
