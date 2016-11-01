package de.baleipzig.iris.ui.presenter.base;

import de.baleipzig.iris.model.neuralnet.neuralnet.NeuralNetMetaData;
import de.baleipzig.iris.ui.service.base.IBaseSearchNNService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public abstract class BaseSearchNNPresenter<S extends IBaseSearchNNService> extends BasePresenter {
    protected final S service;

    public void init() {
        super.init();
    }

    public void searchNeuralNets(String searchTerm) {
        List<NeuralNetMetaData> neuralNetMetaDatas = service.getNeuralNetWorker().findByName(searchTerm);
        System.out.println(neuralNetMetaDatas);
    }
}
