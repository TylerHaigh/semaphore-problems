package com.haight.comp2240.assignment2.part3;

public class PrinterJob implements Runnable, Comparable<PrinterJob> {

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

    public void printPages() throws InterruptedException {
        for(int i = 0; i < pagesToPrint; i++) {
            System.out.println("Printing page " + (i + 1) + " of " + pagesToPrint + "...");
            Thread.sleep(TimeToPrintPageMillis);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrinterJob that = (PrinterJob) o;

        if (jobId != that.jobId) return false;
        if (pagesToPrint != that.pagesToPrint) return false;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        int result = jobId;
        result = 31 * result + type.hashCode();
        result = 31 * result + pagesToPrint;
        return result;
    }

    @Override
    public void run() {

        try
        {
            printer.submitJob(this);
            printPages();
            printer.removeJob(this);
        }
        catch (InterruptedException e) { e.printStackTrace(); }
    }

    @Override
    public int compareTo(PrinterJob o) {
        return Integer.compare(this.jobId, o.jobId);
    }
}

