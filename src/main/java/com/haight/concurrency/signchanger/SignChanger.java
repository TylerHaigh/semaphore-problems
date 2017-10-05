package com.haight.concurrency.signchanger;

import java.util.Random;

public class SignChanger implements Runnable {

    private static int nextId = 0;

    private Random rand = new Random();
    private int thisId;

    private NeonSign sign;
    public SignChanger(NeonSign sign) {
        this.sign = sign;
        thisId = nextId++;
    }

    @Override
    public String toString() {
        return String.format("SignChanger_%1$s", thisId);
    }

    public void run() {

        while(true) {
            try {

                double randVal = rand.nextDouble();

                if (randVal > 0.5) sign.increment();
                else sign.decrement();

                System.out.println("The value is now: " + sign.value());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
