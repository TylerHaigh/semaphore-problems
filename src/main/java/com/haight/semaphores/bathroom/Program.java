package com.haight.semaphores.bathroom;

public class Program {

    private static final int FemaleCount = 9;
    private static final int MaleCount = 9;

    public static void main(String[] args) {

        Bathroom b = new Bathroom();

        PersonSpawner femaleSpawner = new PersonSpawner(Gender.Female, FemaleCount, b);
        PersonSpawner maleSpawner = new PersonSpawner(Gender.Male, MaleCount, b);

        Thread ft = new Thread(femaleSpawner);
        Thread mt = new Thread(maleSpawner);

        ft.start();
        mt.start();
    }


}
