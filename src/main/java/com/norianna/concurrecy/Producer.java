package com.norianna.concurrecy;

public class Producer {
    private final Buffer buffer;
    private int counter;

    public Producer(Buffer pb){
        this.buffer = pb;
    }

    public void run() throws InterruptedException {
        while(!Thread.interrupted()){
            if(counter <= 10) {
                buffer.insert(counter++);
            } else {
                buffer.insert(Buffer.POISON_PILL);
                break;
            }
        }
    }
}
