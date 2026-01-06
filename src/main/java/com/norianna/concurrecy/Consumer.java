package com.norianna.concurrecy;

public class Consumer {
    private final Buffer buffer;

    public Consumer(Buffer pb){
        this.buffer = pb;
    }

    public void run() throws InterruptedException {
        while(!Thread.interrupted()){
            if(buffer.pop() == Buffer.POISON_PILL) break;
            //Simulates the thread is busy treating the value
            Thread.sleep(100);
        }
    }
}
