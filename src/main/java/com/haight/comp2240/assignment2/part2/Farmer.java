package com.haight.comp2240.assignment2.part2;

import com.haight.comp2240.assignment2.common.Island;

public class Farmer implements Runnable {


    private int id;
    private Island currentIsland;
    private Island initialIsland;

    private Bridge bridge;

    public Farmer(int id, Island initialIsland, Bridge bridge) {
        this.id = id;
        this.initialIsland = initialIsland;
        this.currentIsland = initialIsland;
        this.bridge = bridge;
    }

    public Island currentIsland() { return currentIsland; }

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


