package de.baleipzig.iris.ui.presenter.neuralnetconfig;

import com.vaadin.ui.UI;
import de.baleipzig.iris.model.neuralnet.neuralnet.NeuralNetMetaData;
import de.baleipzig.iris.ui.presenter.base.BaseSearchNNPresenter;
import de.baleipzig.iris.ui.service.neuralnetconfig.INeuralNetConfigService;
import de.baleipzig.iris.ui.view.neuralnetconfig.INeuralNetConfigView;
import de.baleipzig.iris.ui.view.training.TrainingView;
import de.baleipzig.iris.ui.viewmodel.neuralnetconfig.NeuralNetConfigViewModel;


public class NeuralNetConfigPresenter extends BaseSearchNNPresenter<INeuralNetConfigView, INeuralNetConfigService> {

    private NeuralNetConfigViewModel model = new NeuralNetConfigViewModel();

    public NeuralNetConfigPresenter(INeuralNetConfigView view, INeuralNetConfigService service) {

        super(view, service);
    }

    @Override
    public void init() {

        super.init();
    }

    @Override
    public void handleSelection(NeuralNetMetaData metaData) {

        model.setSelectedNeuralNetId(metaData.getId());
    }

    public  Void redirectToTrainingView() {

        String parameterString = model.getSelectedNeuralNetId() == null ? "" : String.format("/%s=%s", "uuid", model.getSelectedNeuralNetId());
        UI.getCurrent().getNavigator().navigateTo(String.format("%s%s", TrainingView.VIEW_NAME,parameterString));
        return null;
    }
}
