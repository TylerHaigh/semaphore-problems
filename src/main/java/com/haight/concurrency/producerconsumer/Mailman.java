package com.haight.concurrency.producerconsumer;

//public interface Producer<T> {
//    void produce();
//}

import com.haight.concurrency.interfaces.Producer;

public class Mailman implements Producer<String>, Runnable {

    MailBox mb;
    public Mailman(MailBox box) { mb = box; }


    public void produce() {
        mb.put("This is a letter");
        System.out.format("%1$s MailMan: I'm putting a letter in the letterbox\n", System.currentTimeMillis());
    }

    public void run() {
        while(true)
            produce();
    }
}


