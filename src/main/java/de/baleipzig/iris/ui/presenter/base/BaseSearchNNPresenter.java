package de.baleipzig.iris.ui.presenter.base;

import de.baleipzig.iris.model.neuralnet.neuralnet.NeuralNetMetaData;
import de.baleipzig.iris.ui.service.base.IBaseSearchNNService;
import de.baleipzig.iris.ui.view.base.IBaseSearchNNView;

import java.util.List;


public abstract class BaseSearchNNPresenter<V extends IBaseSearchNNView, S extends IBaseSearchNNService> extends BasePresenter<V, S> {

    public BaseSearchNNPresenter(V view, S service) {
        super(view, service);
    }

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
