package com.norianna.concurrecy;

import java.util.ArrayDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private ArrayDeque<Integer> store;
    private final int capacity;
    public static final int POISON_PILL = -1;
    private int activeProducers;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public Buffer(int capacity, int producers){
        this.capacity = capacity;
        this.activeProducers = producers;
        store = new ArrayDeque<Integer>();
    }

    public void insert(int value) throws InterruptedException {
        lock.lock();
        try {
            if (value == POISON_PILL && activeProducers > 1) {
                disableProducer();
                return;
            }

            while(full()) {
                notFull.await();
            }

            store.addLast(value);
            System.out.println(store);
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public int pop() throws InterruptedException {
        lock.lock();
        try {
            while(empty()) {
                notEmpty.await();
            }
            int value = store.removeFirst();
            if(value == POISON_PILL) store.add(POISON_PILL);
            System.out.println(store);
            notFull.signalAll();
            return value;
        } finally {
            lock.unlock();
        }
    }

    private boolean full() {
        return store.size() == capacity;
    }

    private boolean empty() {
        return store.isEmpty();
    }

    private void disableProducer() {
        activeProducers--;
    }
}
