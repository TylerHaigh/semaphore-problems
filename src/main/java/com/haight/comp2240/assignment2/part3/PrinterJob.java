package com.haight.comp2240.assignment2.part3;

public class PrinterJob implements Runnable {

    public int jobId;
    public JobType type;
    public int pagesToPrint;
    public Printer printer;

    public static final int TimeToPrintPageMillis = 1000;

    public PrinterJob(int jobId, int pagesToPrint, JobType type, Printer printer)
    {
        this.jobId = jobId;
        this.pagesToPrint = pagesToPrint;
        this.type = type;
        this.printer = printer;
    }


    public boolean isMonochromeJob() { return type == JobType.Monochrome; }
    public boolean isColourJob() { return type == JobType.Colour; }

    @Override
    public String toString() {
        return "(" + type.code() + jobId + ")";
    }

    public void printPages() throws InterruptedException {
        for(int i = 0; i < pagesToPrint; i++) {
            System.out.println(toString() + ": Printing page " + (i + 1) + " of " + pagesToPrint + "...");
            Thread.sleep(TimeToPrintPageMillis);
        }
    }


    @Override
    public void run() {
        try { printer.submitJob(this); }
        catch (InterruptedException e) { e.printStackTrace(); }
    }
}

