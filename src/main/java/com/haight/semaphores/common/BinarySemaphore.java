package com.haight.semaphores.common;

public class BinarySemaphore {

    private static final int MAX_RESOURCES = 1;

    private CountingSemaphore sem = new CountingSemaphore(MAX_RESOURCES);

    public boolean outOfResources() { return sem.outOfResources(); }
    public void acquire() throws InterruptedException { sem.acquire(MAX_RESOURCES); }
    public void release() { sem.release(MAX_RESOURCES); }

}
