package com.haight.semaphores.common;

import java.util.concurrent.Semaphore;

public class Lightswitch {

    private int peopleInTheRoom = 0;
    private Semaphore countMutex = new Semaphore(1);

    // when a writer comes in, the room must be empty (i.e. no readers) to ensure no readers
    // can access the data whilst the writer is making modifications
    // thus, the writers must wait until the room is empty

    public void lock(Semaphore resource) throws InterruptedException {
        countMutex.acquire();
        {
            incrementCount(resource);
        }
        countMutex.release();
    }

    private void incrementCount(Semaphore resource) throws InterruptedException {
        peopleInTheRoom++;

        if (firstPersonToEnterTheRoom())
            resource.acquire();
    }

    private boolean firstPersonToEnterTheRoom() { return peopleInTheRoom == 1; }
    private boolean lastPersonToLeaveTheRoom()  { return peopleInTheRoom == 0; }

    public void unlock(Semaphore resource) throws InterruptedException {
        countMutex.acquire();
        {
            decrementCount(resource);
        }
        countMutex.release();
    }

    private void decrementCount(Semaphore resource) {
        peopleInTheRoom--;

        if (lastPersonToLeaveTheRoom())
            resource.release();
    }

}
