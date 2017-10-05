package com.haight.concurrency.producerconsumer;

public class Program {

    public static void main(String[] args) {
        MailBox mb = new MailBox();
        Mailman mm = new Mailman(mb);
        MailRecipient mr = new MailRecipient(mb);

//        for(int i = 0; i < 10; i++) {
//            mm.produce();
//            mr.consume();
//        }


        new Thread(mm).start();
        new Thread(mr).start();
    }

}