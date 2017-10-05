package com.haight.semaphores.common;

import java.util.LinkedList;
import java.util.Queue;

public class CountingSemaphore {

    // Limited Resource (But we'll change the API slightly)
    // Allows n "Permits" or "Resources"
    // "Taking"  or "Consuming" or "Acquire" a permit, decrements the number of remaining resource permits
    // "Putting" or "Producing" or "Releasing" a permit, increments the number of remaining resource permits
    // Maintain a queue of waiting threads, waiting to access the semaphore

    private int remainingResources;
    private Queue<Thread> waitingThreads = new LinkedList<Thread>();


    public synchronized boolean outOfResources() { return remainingResources <= 0; }


    public CountingSemaphore(int initialResources) {
        remainingResources = initialResources;
    }

    public synchronized void acquire() throws InterruptedException { acquire(1); }

    public synchronized void acquire(int numResources) throws InterruptedException {

        while (outOfResources() || queueIsOccupied() || !enoughResourcesForAcquisition(numResources) )
        {
            if (currentThreadIsFirstInQueue() && enoughResourcesForAcquisition(numResources)) {
                //System.out.println( System.currentTimeMillis() + " " + Thread.currentThread().getName() + ": The semaphore is available for me");
                break;
            }

            if (!currentThreadIsEnqueued()) {

                /*
                System.out.println(
                        System.currentTimeMillis() + " " + Thread.currentThread().getName() +
                        (queueIsOccupied() ? ": There are " + waitingThreads.size() + " waiting threads ahead of me" : ": Semaphore is out of resources" ) +
                                ". Waiting..."
                );
                */

                waitingThreads.add(Thread.currentThread());
            } else {
                //System.out.println(System.currentTimeMillis() + " " + Thread.currentThread().getName() + ": Semaphore is available but I am not at the head of the queue. Continue Waiting...");
            }

            wait();
        }

        if (currentThreadIsFirstInQueue())
            waitingThreads.remove();

        decrementResources(numResources);
    }


    private synchronized void decrementResources(int numResources) {
        remainingResources -= numResources;
        //System.out.println( System.currentTimeMillis() + " " + Thread.currentThread().getName() +  ": Consuming " + numResources + " resources. " + remainingResources + " remaining...");
    }


    private synchronized boolean queueIsOccupied() { return waitingThreads.size() > 0; }
    private synchronized boolean currentThreadIsEnqueued() { return  queueIsOccupied() && waitingThreads.contains(Thread.currentThread()); }
    private synchronized boolean currentThreadIsFirstInQueue() { return currentThreadIsEnqueued() && waitingThreads.peek().getId() == Thread.currentThread().getId(); }
    private synchronized boolean enoughResourcesForAcquisition(int numResources) { return remainingResources - numResources >= 0; }


    public synchronized void release() { release(1); }
    public synchronized void release(int numResources) {
        incrementResources(numResources);
    }

    private synchronized void incrementResources(int numResources) {
        remainingResources += numResources;
        //System.out.println(System.currentTimeMillis() + " " + Thread.currentThread().getName() + ": Releasing " + numResources + " resources. " + remainingResources + " remaining...");
        notifyAll();
    }




}
