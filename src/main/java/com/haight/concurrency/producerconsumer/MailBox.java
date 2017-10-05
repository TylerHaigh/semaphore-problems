package com.haight.concurrency.producerconsumer;

//public interface LimitedResource<T> {
//    void put(T item);
//    T take();
//}

import com.haight.concurrency.interfaces.LimitedResource;

public class MailBox implements LimitedResource<String> {

    private String letter;

    public boolean isEmpty() { return letter == null; }
    public boolean isFull() { return !isEmpty(); }

    public synchronized void put(String item) {

        while(isFull()) {
            try { wait(); }
            catch (InterruptedException e) { }
        }

        if (isFull())
            throw new IllegalStateException("Mailbox is not empty");

        letter = item;
        notifyAll();
    }

    public synchronized String take() {


        while (isEmpty())
        {
            try { wait(); }
            catch (InterruptedException e) { }
        }

        if (isEmpty())
            throw new IllegalStateException("Mailbox is empty");

        String tmp = letter;
        letter = null;

        notifyAll();

        return tmp;
    }
}
