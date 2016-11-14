package de.baleipzig.iris.ui.view.base;

import de.baleipzig.iris.model.neuralnet.neuralnet.NeuralNetMetaData;
import de.baleipzig.iris.ui.presenter.base.BaseSearchNNPresenter;

import java.util.List;
import java.util.UUID;

public interface IBaseSearchNNView<P extends BaseSearchNNPresenter> extends IBaseView<P> {
    void setSearchResult(List<NeuralNetMetaData> neuralNetMetaDatas);
    void lockSearchResultTable(boolean isLocked);
    void selectSearchListItem(UUID neuralNetId);
}
