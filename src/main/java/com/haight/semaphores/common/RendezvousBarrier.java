package com.haight.semaphores.common;

import java.util.concurrent.Semaphore;

public class RendezvousBarrier {

    private Semaphore countMutex = new Semaphore(1);
    private Semaphore entryTurnstile = new Semaphore(0); // Initially Locked
    private Semaphore exitTurnstile  = new Semaphore(0); // Initially Locked

    private int enterCounter = 0;
    private int exitCounter = 0;
    private int barrierRequiredCount = 0;

    public RendezvousBarrier(int barrierRequiredCount) { this.barrierRequiredCount = barrierRequiredCount; }

    public void enter() throws InterruptedException {
        safeIncrement();

        // Do now allow entry into the barrier until all threads rendezvous here
        // Wait here until thread "n" signals me.
        // Thread "n" will be allowed to acquire the semaphore as well
        entryTurnstile.acquire();
    }

    private void safeIncrement() throws InterruptedException {
        // Ensure mutually exclusive access to the currentCount variable
        countMutex.acquire();
        {
            incrementCount();
        }
        countMutex.release();
    }

    private void incrementCount() {
        enterCounter++;

        if (allThreadsHaveEnteredUnsafe()) {
            entryTurnstile.release(barrierRequiredCount); // Unlock the First Turnstile and allow all threads to pass through
            enterCounter = 0;
        }
    }

    public boolean allThreadsHaveEntered() throws InterruptedException {
        boolean result;
        countMutex.acquire();
        {
            result = allThreadsHaveEnteredUnsafe();
        }
        countMutex.release();
        return result;
    }

    private boolean allThreadsHaveEnteredUnsafe() {
        return enterCounter == barrierRequiredCount;
    }





    public void exit() throws InterruptedException {
        safeDecrement();

        // Do not allow exiting of the Barrier until all threads rendezvous here
        exitTurnstile.acquire();
    }

    private void safeDecrement() throws InterruptedException {
        // Ensure mutually exclusive access to the currentCount variable
        countMutex.acquire();
        {
            decrementCount();
        }
        countMutex.release();
    }

    private void decrementCount() {
        // This is safe since is is called from safeDecrement
        exitCounter++;

        if (allThreadsHaveExitedUnsafe()) { // called in safe manner
            exitTurnstile.release(barrierRequiredCount);
            exitCounter = 0;
        }
    }

    public boolean allThreadsHaveExited() throws InterruptedException {
        boolean result;
        countMutex.acquire();
        {
            result = allThreadsHaveExitedUnsafe();
        }
        countMutex.release();
        return result;
    }

    private boolean allThreadsHaveExitedUnsafe() { return exitCounter == barrierRequiredCount; }


}
