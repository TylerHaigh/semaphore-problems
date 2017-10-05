package com.haight.concurrency.signchanger;


public class Program {

    public static void main(String[] args) {
        NeonSign sign = new NeonSign();

        for (int i = 0; i < 10; i++) {
            SignChanger sc = new SignChanger(sign);
            new Thread(sc, sc.toString()).start();
        }
    }

}
