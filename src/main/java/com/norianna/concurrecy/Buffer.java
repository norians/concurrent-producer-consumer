package com.norianna.concurrecy;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private ArrayList<Integer> store;
    private int i, j, size;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition fullStore = lock.newCondition();
    private final Condition emptyStore = lock.newCondition();

    public Buffer(int size){
        i=j=0;
        this.size = size;
        store = new ArrayList<Integer>();
    }

    public void insert(int value) throws InterruptedException {
        lock.lock();
        try {
            while(full()) {
                fullStore.await();
                System.out.println("FULL");
            }
            store.add(value);
            System.out.println(store);
            fullStore.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public int pop() throws InterruptedException {
        lock.lock();
        try {
            while(empty()) {
                emptyStore.await();
                System.out.println("VACÍO");
            }
            int popValue = store.removeFirst();
            System.out.println(store);
            fullStore.signalAll();
            return popValue;
        } finally {
            lock.unlock();
        }
    }

    private synchronized boolean full() {
        return store.size() == size;
    }

    private synchronized boolean empty() {
        return store.isEmpty();
    }

}
