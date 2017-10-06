package com.haight.semaphores.counter;

import java.util.concurrent.Semaphore;

public class Program {

    // Defines some classes that will be used in this scenario
    //  - An incrementer - a class that will increment the counter
    //  - An decrementer - a class that will decrement the counter
    //  - An reader      - a class that will read      the counter
    // Look up the "Strategy Pattern" for more info

    private interface IStrategy { void runStrategy(); }

    private class Incrementer implements IStrategy {
        private Counter counter;
        public Incrementer(Counter counter) { this.counter = counter; }

        @Override
        public void runStrategy() { counter.increment(); }
    }

    private class Decrementer implements IStrategy {
        private Counter counter;
        public Decrementer(Counter counter) { this.counter = counter; }

        @Override
        public void runStrategy() { counter.decrement(); }
    }

    private class Reader implements IStrategy {
        private Counter counter;
        public Reader(Counter counter) { this.counter = counter; }

        @Override
        public void runStrategy() { System.out.println("Counter Value: " + counter.value()); }
    }







    public static void main(String[] args) {
        Program p = new Program();
        p.run();
    }






    // An unprotected counter. Any thread can modify this and change it's state at a given time
    private Counter counter = new Counter();

    // A mutual exclusion "turnstile" that allows fair (non-starvation) mutually exclusive
    // access to the protected resource (the counter)
    // Any access too the protected resource (the counter) must be wrapped in two calls
    // to the semaphore (acquire and release) to ensure mutually exclusive access
    // (i.e. that only one thread can access - whether it be reading or writing)
    // at a given time
    private Semaphore counterMutex = new Semaphore(1, true);


    public void run() {
        // Thread to increment the counter
        spawn(new Incrementer(counter));


        // Thread to decrement the counter
        // This thread will compete with the incrementer
        spawn(new Decrementer(counter));


        // Thread to read and report the counter's value
        // This thread will compete with BOTH the incrementer and decrementer
        spawn(new Reader(counter));
    }

    private void spawn(IStrategy strategy) {
        new Thread( () -> {
            try {
                while (true) {
                    counterMutex.acquire();
                    {
                        strategy.runStrategy();
                    }
                    counterMutex.release();
                }
            }
            catch (InterruptedException e) { e.printStackTrace(); }
        }).start();
    }

}
