package de.baleipzig.iris.logic.neuralnettrainer.result;

import lombok.Data;

@Data
public class TestResult extends Result {

    private double errorRate;

    public TestResult(boolean success, double errorRate) {

        super(success);
        this.errorRate = errorRate;
    }
}
