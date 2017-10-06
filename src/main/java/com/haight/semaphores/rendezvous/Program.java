package com.haight.semaphores.rendezvous;

import java.util.concurrent.Semaphore;

public class Program {

    public static void main(String[] args) {

        // Problem: establish a rendezvous mechanism such that
        // a1 < b2 and
        // b1 < a2
        //
        // e.g. a1 < b1 < b2 < a2 or
        //      a1 < b1 < a2 < b2 or
        //      b1 < a1 < b2 < a2 or
        //      b1 < a1 < a2 < b2
        //
        // From the above examples, Statement 1 in either thread should always be immediately followed
        // by Statement 1 from the other thread. After this, it does not matter which Statement 2 runs next

        // Initialise semaphores at 0 (i.e. locked) to enforce both threads to wait until
        // the other has signalled that it has arrived
        // This signalling takes the form of releasing a permit (which unlocks the semaphore)
        // and allows the other thread to resume execution
        Semaphore aArrived = new Semaphore(0);
        Semaphore bArrived = new Semaphore(0);

        new Thread(() -> {
            try {
                // Allow the first statement to run. Either thread can run this initially
                System.out.println("Thread A: Statement 1");

                // Notify to Thread B that Thread A has arrived
                aArrived.release();

                // Wait for Thread B to run Statement 1 before continuing
                bArrived.acquire();

                // Thread B has arrived. Either thread can now run statement 2
                System.out.println("Thread A: Statement 2");
            } catch (InterruptedException e) { e.printStackTrace(); }
        }).start();

        new Thread(() -> {
            try {
                // Allow the first statement to run. Either thread can run this initially
                System.out.println("Thread B: Statement 1");

                // Notify to Thread A that Thread B has arrived
                bArrived.release();

                // Wait for Thread A to run Statement 1 before continuing
                aArrived.acquire();


                System.out.println("Thread B: Statement 2");
            } catch (InterruptedException e) { e.printStackTrace(); }
        }).start();


    }


}


