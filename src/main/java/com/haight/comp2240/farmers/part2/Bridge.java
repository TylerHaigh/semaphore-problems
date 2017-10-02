package com.haight.comp2240.farmers.part2;

import com.haight.comp2240.farmers.common.Barrier;
import com.haight.comp2240.farmers.common.Barrier2;
import com.haight.comp2240.farmers.common.Island;
import com.haight.comp2240.farmers.common.NeonSign;

import java.util.concurrent.Semaphore;

public class Bridge {

    private final NeonSign sign = new NeonSign();
    private final Semaphore bridgeLock = new Semaphore(NumberOfFarmersCrossingTogether);

    private static final int StepsToCrossBridge = 20;
    private static final int SleepTimeoutMilliseconds = 100;
    private static final int NumberOfFarmersCrossingTogether = 2;

    private final Semaphore islandLock = new Semaphore(1);
    private Island islandPermittedToCross = Island.Undefined;

    private final Barrier2 northBarrier = new Barrier2(NumberOfFarmersCrossingTogether);
    private final Barrier2 southBarrier = new Barrier2(NumberOfFarmersCrossingTogether);


    public void enterBridge(Farmer f) throws InterruptedException {
        System.out.println(f.toString() + ": Arrived at bridge. Waiting to cross");

        // Add to queue (Optional)
        // Check if queue size is valid
        // Wake up farmers or put to sleep
        // Acquire semaphore

        enterBarrier(f.currentIsland, f.currentIsland == Island.North ? northBarrier : southBarrier);

        bridgeLock.acquire();
        System.out.println(f.toString() + ": Ready to cross bridge");
    }


    private void enterBarrier(Island currentIsland, Barrier2 barrier) throws InterruptedException {
        barrier.enter();

        // At this point, at least 2 farmers are in the queue and are ready to cross
        // Lock the Island and only permit the north (or south) farmers to cross

        islandLock.acquire();
        {
            while (islandPermittedToCross != Island.Undefined && islandPermittedToCross != currentIsland) {
                islandLock.release();
                synchronized(this) { wait(); }
                islandLock.acquire();
            }

            System.out.println(currentIsland + " has monopoly of the bridge");
            islandPermittedToCross = currentIsland;
        }
        islandLock.release();
    }

    public void crossBridge(Farmer f) throws InterruptedException {
        for (int i = 0; i < StepsToCrossBridge; i += 5) {
            System.out.println("Crossing Bridge. " + i + " steps of " + StepsToCrossBridge);
            Thread.sleep(SleepTimeoutMilliseconds);
        }
    }

    public void exitBridge(Farmer f) throws InterruptedException {
        System.out.println(f.toString() + ": Finished crossing bridge");

        sign.incrementCount();
        System.out.println(sign.toString());

        bridgeLock.release();
        System.out.println(f.toString() + ": Released bridge semaphore");

        exitBarrier(f.currentIsland == Island.North ? northBarrier : southBarrier);
    }

    private void exitBarrier(Barrier2 barrier) throws InterruptedException {
        System.out.println("About to exit barrier. Waiting to rendezvous");
        barrier.exit();
        System.out.println("Exited barrier");

        // All threads have rendezvoused here
        // This means that all farmers have crossed the bridge and the island lock can be released
        islandLock.acquire();
        {
            islandPermittedToCross = Island.Undefined;
            System.out.println("Releasing monopoly of the bridge");
            synchronized (this) { notifyAll(); }
        }
        islandLock.release();
    }





}


