package de.baleipzig.iris.common;

import java.util.HashMap;
import java.util.Map;

public class PerformanceLog {

    private final static Map<String, Long> nanos = new HashMap<>();

    public static void start(String name) {
        synchronized (nanos) {
            nanos.put(name, System.nanoTime());
        }
    }

    public static void stop(String name) {

        double end = System.nanoTime();

        synchronized (nanos) {
            if(nanos.containsKey(name)) {
                System.out.println(name + ": " + (end - nanos.get(name)));
            } else {
                System.out.println("key not found");
            }
        }
    }
}
