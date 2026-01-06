package com.norianna.concurrecy;

import java.util.concurrent.StructuredTaskScope;

public class Main {
    static void main() throws InterruptedException {
        var buffer = new Buffer(5);

        var producer = new Producer(buffer);
        var consumer = new Consumer(buffer);

        try (var scope = StructuredTaskScope.open()){
            scope.fork(() -> {
                producer.run();
                return null;
            });

            scope.fork(() -> {
                consumer.run();
                return null;
            });

            scope.join();

        } catch (StructuredTaskScope.FailedException e) {
            System.err.println("El sistema se ha detenido: " + e.getMessage());
        }
    }
}
