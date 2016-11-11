package de.baleipzig.iris.ui.view.neuralnetconfig;

import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import de.baleipzig.iris.ui.language.LanguageHandler;
import de.baleipzig.iris.ui.presenter.neuralnetconfig.NeuralNetConfigPresenter;
import de.baleipzig.iris.ui.service.neuralnetconfig.INeuralNetConfigService;
import de.baleipzig.iris.ui.view.base.BaseSearchNNView;
import de.baleipzig.iris.ui.viewmodel.neuralnetconfig.NeuralNetConfigViewModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = NeuralNetConfigView.VIEW_NAME)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NeuralNetConfigView extends BaseSearchNNView<NeuralNetConfigPresenter> implements INeuralNetConfigView {

    //region -- member --

    public static final String VIEW_NAME = "neuralnetconfig";

    private final ApplicationContext context;
    @Getter
    private final LanguageHandler languageHandler;

    //endregion

    //region -- methods --

    @PostConstruct
    void init() {

        presenter = new NeuralNetConfigPresenter(this, (INeuralNetConfigService) context.getBean("neuralNetConfigService"));
        presenter.init();
    }

    @Override
    public void bindTrainingsConfiguration(NeuralNetConfigViewModel viewModel) {

    }

    @Override
    public void updateNeuralNetStructure(String neuralNetStructure) {

    }

    //endregion
}