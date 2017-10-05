package com.haight.semaphores.common;

import java.util.concurrent.Semaphore;

public class FixedLightswitch {

    // Only allows locking and unlocking of a single resource
    // Much safer as it is self contained and keeps correct count on the resource
    // but provides less readability when using the api

    private int peopleInTheRoom = 0;
    private Semaphore countMutex = new Semaphore(1);

    // when a writer comes in, the room must be empty (i.e. no readers) to ensure no readers
    // can access the data whilst the writer is making modifications
    // thus, the writers must wait until the room is empty

    private Semaphore resource;
    public FixedLightswitch(Semaphore resource) { this.resource = resource; }

    public void lock() throws InterruptedException {
        countMutex.acquire();
        {
            incrementCount(this.resource);
        }
        countMutex.release();
    }

    private void incrementCount(Semaphore resource) throws InterruptedException {
        peopleInTheRoom++;

        if (firstPersonToEnterTheRoom())
            resource.acquire(); // Lock the resource so that no writers can enter the room
    }

    private boolean firstPersonToEnterTheRoom() { return peopleInTheRoom == 1; }
    private boolean lastPersonToLeaveTheRoom()  { return peopleInTheRoom == 0; }

    public void unlock() throws InterruptedException {
        countMutex.acquire();
        {
            decrementCount(this.resource);
        }
        countMutex.release();
    }

    private void decrementCount(Semaphore resource) {
        peopleInTheRoom--;

        if (lastPersonToLeaveTheRoom())
            resource.release(); // Release the resource so that writers can enter the room
    }

}
