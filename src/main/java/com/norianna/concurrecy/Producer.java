package com.norianna.concurrecy;

public class Producer {
    private final Buffer buffer;
    private int counter;

    public Producer(Buffer pb){
        this.buffer = pb;
    }

    public void run() throws InterruptedException {
        while(!Thread.interrupted()){
            buffer.insert(counter++);
        }
    }
}
