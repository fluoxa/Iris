package de.baleipzig.iris.ui.presenter.neuralnetconfig;

import com.vaadin.ui.UI;
import de.baleipzig.iris.model.neuralnet.neuralnet.NeuralNetMetaData;
import de.baleipzig.iris.ui.helper.UrlHelper;
import de.baleipzig.iris.ui.presenter.base.BaseSearchNNPresenter;
import de.baleipzig.iris.ui.service.neuralnetconfig.INeuralNetConfigService;
import de.baleipzig.iris.ui.view.neuralnetconfig.INeuralNetConfigView;
import de.baleipzig.iris.ui.view.training.ITrainingView;
import de.baleipzig.iris.ui.viewmodel.neuralnetconfig.NeuralNetConfigViewModel;

import java.util.HashMap;
import java.util.Map;


public class NeuralNetConfigPresenter extends BaseSearchNNPresenter<INeuralNetConfigView, INeuralNetConfigService> {

    private NeuralNetConfigViewModel model = new NeuralNetConfigViewModel();

    public NeuralNetConfigPresenter(INeuralNetConfigView view, INeuralNetConfigService service) {

        super(view, service);
    }

    @Override
    public void init() {

        super.init();
        initViewModel();
        bindViewModelToView();
    }

    @Override
    public void handleSelection(NeuralNetMetaData metaData) {

        model.setSelectedNeuralNetId(metaData.getId());
        model.setNeuralNet(service.getNeuralNetWorker().load(metaData.getId()));
        model.setDescription(metaData.getDescription());
        model.setName(metaData.getName());
        model.setNetStructure(service.getNeuralNetWorker().toJson(model.getNeuralNet()));

        UI.getCurrent().access(() -> view.update(model));
    }

    public  Void navigateToTrainingView() {

        Map<String, String> parameters = new HashMap<>(1);
        parameters.put("uuid", model.getSelectedNeuralNetId().toString());

        UI.getCurrent().getNavigator().navigateTo(UrlHelper.getUrl(ITrainingView.VIEW_NAME, parameters));
        return null;
    }

    private void bindViewModelToView() {

        view.bindTrainingsConfiguration(model);
    }

    private void initViewModel() {

        model.setName("");
        model.setNetStructure("");
        model.setDescription("");
        model.setNeuralNet(null);
        model.setSelectedNeuralNetId(null);
    }
}
