package com.norianna.concurrecy;

public class Consumer {
    private final Buffer buffer;

    public Consumer(Buffer pb){
        this.buffer = pb;
    }

    public void run() throws InterruptedException {
        while(!Thread.interrupted()){
            buffer.pop();
        }
    }
}
