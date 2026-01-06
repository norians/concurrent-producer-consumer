package com.norianna.concurrecy;

import java.util.concurrent.StructuredTaskScope;

public class Main {
    static void main() throws InterruptedException {
        var totalProducers = 3;
        var buffer = new Buffer(5, totalProducers);
        try (var scope = StructuredTaskScope.open()){
            for(int i: new int[totalProducers]) {
                var producer = new Producer(buffer);
                var consumer = new Consumer(buffer);

                scope.fork(() -> {
                    producer.run();
                    return null;
                });

                scope.fork(() -> {
                    consumer.run();
                    return null;
                });
            }
            scope.join();

        } catch (StructuredTaskScope.FailedException e) {
            System.err.println("El sistema se ha detenido: " + e.getMessage());
        }
    }
}
