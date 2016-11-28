package de.baleipzig.iris.common;

import java.util.HashMap;
import java.util.Map;

public class PerfomanceLog {

    private final static Map<String, Long> nanos = new HashMap<>();

    public static void start(String name) {
        synchronized (nanos) {
            nanos.put(name, System.nanoTime());
        }
    }

    public static void stop(String name) {
        synchronized (nanos) {
            if(nanos.containsKey(name)) {
                System.out.println(name + ": " + (System.nanoTime() - nanos.get(name)));
            } else {
                System.out.println("key not found");
            }
        }
    }
}
