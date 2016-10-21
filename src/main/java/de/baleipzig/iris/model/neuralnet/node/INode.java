package de.baleipzig.iris.model.neuralnet.node;

import de.baleipzig.iris.model.neuralnet.activationfunction.IFunctionContainer;
import de.baleipzig.iris.model.neuralnet.axon.IAxon;

import java.util.List;

public interface INode {

    List<IAxon> getParentAxons();
    void addParentAxon(IAxon axon);

    List<IAxon> getChildAxons();
    void addChildAxon(IAxon axon);

    double getError();
    void setError(double error);

    double getWeightedInput();
    void setWeightedInput(double preActivation);

    double getActivation();
    void setActivation(double state);

    double getBias();
    void setBias(double bias);

    void setActivationFunctionContainer(IFunctionContainer activationFunctionContainer);
    IFunctionContainer getActivationFunctionContainer();
}