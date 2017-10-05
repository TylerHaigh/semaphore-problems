package com.haight.concurrency.interfaces;

public interface LimitedResource<T> {
    void put(T item);
    T take();
}


