package de.baleipzig.iris.ui.presenter.base;

import de.baleipzig.iris.model.neuralnet.neuralnet.NeuralNetMetaData;
import de.baleipzig.iris.ui.service.base.IBaseSearchNNService;
import de.baleipzig.iris.ui.view.base.IBaseSearchNNView;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public abstract class BaseSearchNNPresenter<V extends IBaseSearchNNView, S extends IBaseSearchNNService> extends BasePresenter {
    protected final V view;
    protected final S service;

    public void init() {
        super.init();
    }

    public void searchNeuralNets(String searchTerm) {
        List<NeuralNetMetaData> neuralNetMetaData = service.getNeuralNetWorker().findAllNeuralNetMetaDataByName(searchTerm);
        view.setSearchResult(neuralNetMetaData);
    }

    public void searchAllNeuralNets() {
        List<NeuralNetMetaData> neuralNetMetaData = service.getNeuralNetWorker().findAllNeuralNetMetaDatas();
        view.setSearchResult(neuralNetMetaData);
    }

    public abstract void handleSelection(NeuralNetMetaData metaData);
}
