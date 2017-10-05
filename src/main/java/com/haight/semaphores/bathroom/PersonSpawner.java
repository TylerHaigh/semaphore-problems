package com.haight.semaphores.bathroom;

import java.util.Random;

public class PersonSpawner implements Runnable {

    private Gender gender;
    private int numberToSpawn;
    private Bathroom bathroom;

    private Random rand = new Random();

    private static final int SleepTime = 100;

    public PersonSpawner (Gender gender, int numberToSpawn, Bathroom bathroom) {
        this.gender = gender;
        this.numberToSpawn = numberToSpawn;
        this.bathroom = bathroom;
    }


    @Override
    public void run() {
        for (int i = 0; i < numberToSpawn; i++) {
            spawnPersonThread();
        }
    }

    private void spawnPersonThread() {
        Person p = makePerson();
        startThread(p);
        pause();
    }

    private Person makePerson()
    {
        Person p =  (gender == Gender.Female)
                ? new Female(bathroom)
                : new Male(bathroom);

        return p;
    }

    private void startThread(Person p) {
        Thread t = new Thread(p);
        t.start();
    }

    private void pause()
    {
        try { Thread.sleep(rand.nextInt(SleepTime)); }
        catch (InterruptedException e) { e.printStackTrace(); }
    }

}
