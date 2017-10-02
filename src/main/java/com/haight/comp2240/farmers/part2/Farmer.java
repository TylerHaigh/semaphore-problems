package com.haight.comp2240.farmers.part2;

import com.haight.comp2240.farmers.common.Island;

public class Farmer implements Runnable {


    public int id;
    public Island currentIsland;
    public Island initialIsland;

    public Bridge bridge;

    public Farmer(int id, Island initialIsland, Bridge bridge) {
        this.id = id;
        this.initialIsland = initialIsland;
        this.currentIsland = initialIsland;
        this.bridge = bridge;
    }

    @Override
    public String toString() {
        return initialIsland.code() + "_Farmer_" + id;
    }


    public void run() {
        try {
            bridge.enterBridge(this);
            bridge.crossBridge(this);
            bridge.exitBridge(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


