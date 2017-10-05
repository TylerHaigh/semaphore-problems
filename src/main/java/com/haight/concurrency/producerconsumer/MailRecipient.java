package com.haight.concurrency.producerconsumer;


//public interface Consumer<T> {
//    T consume();
//}


import com.haight.concurrency.interfaces.Consumer;

public class MailRecipient implements Consumer<String>, Runnable {

    MailBox mb;
    public MailRecipient(MailBox box) { mb = box; }

    public void consume() {
        String letter = mb.take();
        System.out.format("%1$s MailRecipient: I got a letter!\n", System.currentTimeMillis());
    }

    public void run() {
        while (true)
            consume();
    }
}


