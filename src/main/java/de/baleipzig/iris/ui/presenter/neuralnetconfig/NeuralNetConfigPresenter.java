package de.baleipzig.iris.ui.presenter.neuralnetconfig;

import de.baleipzig.iris.enums.NeuralNetCoreType;
import de.baleipzig.iris.model.neuralnet.neuralnet.INeuralNet;
import de.baleipzig.iris.model.neuralnet.neuralnet.NeuralNetMetaData;
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

        if(metaData == null) {
            return;
        }

        model.setSelectedNeuralNetId(metaData.getId());
        model.setNeuralNet(service.getNeuralNetWorker().load(metaData.getId()));
        model.setDescription(metaData.getDescription());
        model.setName(metaData.getName());
        model.setNetStructure(service.getNeuralNetWorker().toJson(model.getNeuralNet()));
        model.setOriginalNetStructure(model.getNetStructure());
        view.update(model);
    }

    public Void saveNeuralNet() {

        if(model.getSelectedNeuralNetId() == null || model.getNeuralNet() == null) {
            return null;
        }

        INeuralNet savedNet;

        if(model.getOriginalNetStructure().equals(model.getNetStructure())) {
            savedNet = model.getNeuralNet();
        }
        else {
            savedNet = service.getNeuralNetWorker().fromJson(model.getNetStructure(), NeuralNetCoreType.TRAIN);

            if(savedNet == null ) {
                return null;
            }

            savedNet.getNeuralNetMetaData().setId(model.getSelectedNeuralNetId());
        }

        savedNet.getNeuralNetMetaData().setName(model.getName());
        savedNet.getNeuralNetMetaData().setDescription(model.getDescription());

        service.getNeuralNetWorker().save(savedNet);
        searchAllNeuralNets();
        view.selectSearchListItem(model.getSelectedNeuralNetId());

        return null;
    }

    public  Void navigateToTrainingView() {

        if(model.getSelectedNeuralNetId() != null) {
            Map<String, String> parameters = new HashMap<>(1);
            parameters.put("uuid", model.getSelectedNeuralNetId().toString());

            return this.navigateToView(ITrainingView.VIEW_NAME, parameters);
        }
        else {
            return this.navigateToView(ITrainingView.VIEW_NAME);
        }
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
