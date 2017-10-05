package com.haight.semaphores.bathroom;

import com.haight.semaphores.common.Lightswitch;

import java.util.concurrent.Semaphore;

public class Bathroom {


    private Semaphore roomEmpty = new Semaphore(1); // Whether the room is empty or not
    private Lightswitch maleSwitch = new Lightswitch(); // Allows men. Bars women
    private Lightswitch femaleSwitch = new Lightswitch(); // Allows women. Bars men
    private Semaphore maleResourceLimit = new Semaphore(3); // Allow 3 men
    private Semaphore femaleResourceLimit = new Semaphore(3); // Allow 3 women

    private Semaphore turnstile = new Semaphore(1); // Force non-starvation


    public void runPerson(Person p) throws InterruptedException {
        if (p instanceof Female)
            runFemale((Female)p);
        else
            runMale((Male)p);
    }

    public void runFemale(Female f) throws InterruptedException {

        System.out.println("A female has arrived at the bathroom");

        turnstile.acquire();
        femaleSwitch.lock(roomEmpty);
        turnstile.release();
        {
            femaleResourceLimit.acquire();
            {
                f.routine();
            }
            femaleResourceLimit.release();
        }
        femaleSwitch.unlock(roomEmpty);

    }

    public void runMale(Male m) throws InterruptedException {

        System.out.println("A male has arrived at the bathroom");

        turnstile.acquire();
        maleSwitch.lock(roomEmpty);
        turnstile.release();
        {
            maleResourceLimit.acquire();
            {
                m.routine();
            }
            maleResourceLimit.release();
        }
        maleSwitch.unlock(roomEmpty);
    }




}
