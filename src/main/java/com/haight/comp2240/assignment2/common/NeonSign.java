package com.haight.comp2240.assignment2.common;

public class NeonSign {
    private int crossingCount = 0;

    public void incrementCount() { crossingCount++; }

    @Override
    public String toString() {
        return "Neon Sign: Crossing Count " + crossingCount;
    }
}
