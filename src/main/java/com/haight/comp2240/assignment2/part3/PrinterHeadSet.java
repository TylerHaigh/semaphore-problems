package com.haight.comp2240.assignment2.part3;

public class PrinterHeadSet {

    public static final int NumberOfPrinterHeads = 3;

    private PrinterJob[] heads = new PrinterJob[NumberOfPrinterHeads];
    private int availableSlots = NumberOfPrinterHeads;

    public int availableSlots() { return availableSlots; }
    public boolean isFull() { return availableSlots == 0; }
    public boolean isEmpty() { return availableSlots == NumberOfPrinterHeads; }


    public void addJobToVacantPosition(PrinterJob job) {
        if (isFull())
            throw new IllegalStateException("Printer heads are all busy");

        assignToFirstAvailablePrinterHead(job);
        availableSlots--;
    }

    private void assignToFirstAvailablePrinterHead(PrinterJob job) {
        for (int i = 0; i < NumberOfPrinterHeads; i++ ) {
            if (heads[i] == null) {
                heads[i] = job;
                break;
            }
        }
    }

    public void removeJobFromHead(PrinterJob job) {
        if (isEmpty())
            return; // Nothing to do

        removeFirstInstanceOfJob(job);
        availableSlots++;
    }

    private void removeFirstInstanceOfJob(PrinterJob job) {
        for (int i = 0; i < NumberOfPrinterHeads; i++ ) {
            if (heads[i] == job) {
                heads[i] = null;
                break;
            }
        }
    }


}
