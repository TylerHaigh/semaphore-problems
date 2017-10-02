package com.haight.comp2240.farmers.part1;

import java.util.concurrent.Semaphore;

public class Bridge {

    private NeonSign sign = new NeonSign();
    private Semaphore lock = new Semaphore(1);

    private static final int StepsToCrossBridge = 20;
    private static final int SleepTimeoutMilliseconds = 100;

    public void enterBridge(Farmer f) throws InterruptedException {
        System.out.println(f.toString() + ": Arrived at bridge. Waiting to cross");
        lock.acquire();
        System.out.println(f.toString() + ": Ready to cross bridge");
    }

    public void crossBridge(Farmer f) throws InterruptedException {
        for (int i = 0; i < StepsToCrossBridge; i += 5) {
            System.out.println("Crossing Bridge. " + i + " steps of " + StepsToCrossBridge);
            Thread.sleep(SleepTimeoutMilliseconds);
        }
    }

    public void exitBridge(Farmer f) {
        System.out.println(f.toString() + ": Finished crossing bridge");

        sign.incrementCount();
        System.out.println(sign.toString());

        lock.release();
        System.out.println(f.toString() + ": Released semaphore");
    }

    public boolean bridgeIsOccupied() {
        return lock.availablePermits() <= 0;
    }


}


