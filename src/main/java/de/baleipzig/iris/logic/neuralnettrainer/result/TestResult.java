package de.baleipzig.iris.logic.neuralnettrainer.result;

import de.baleipzig.iris.enums.ResultType;
import lombok.Data;

@Data
public class TestResult extends Result {

    private double errorRate;

    public TestResult(ResultType success, double errorRate) {

        super(success);
        this.errorRate = errorRate;
    }
}