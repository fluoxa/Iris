package de.baleipzig.iris.ui.view.neuralnetconfig;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
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
@SpringView(name = INeuralNetConfigView.VIEW_NAME)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NeuralNetConfigView extends BaseSearchNNView<NeuralNetConfigPresenter> implements INeuralNetConfigView {

    //region -- member --

    private final ApplicationContext context;
    @Getter
    private final LanguageHandler languageHandler;

    private TextArea descriptionTextArea = new TextArea();
    private TextField nameTextField = new TextField();
    private Button trainNeuralNet = new Button();
    private Button saveNeuralNet = new Button();
    private Button resetNeuralNet = new Button();
    private Button createNeuralNet = new Button();

    private TabSheet neuralNetEditor = new TabSheet();
    private TextArea jsonEditor = new TextArea();

    //endregion

    //region -- methods --

    @PostConstruct
    void init() {

        setupElements();
        setupLayout();
        setupListeners();

        presenter = new NeuralNetConfigPresenter(this, (INeuralNetConfigService) context.getBean("neuralNetConfigService"));
        presenter.init();
    }

    @Override
    public void bindTrainingsConfiguration(NeuralNetConfigViewModel viewModel) {

        BeanFieldGroup<NeuralNetConfigViewModel> group = new BeanFieldGroup<>(NeuralNetConfigViewModel.class);
        group.setItemDataSource(viewModel);

        bindNeuralNetConfigViewModelToView(group);

        group.setBuffered(false);
    }

    @Override
    public void update(NeuralNetConfigViewModel model) {

        nameTextField.setValue(model.getName());
        descriptionTextArea.setValue(model.getDescription());
        jsonEditor.setValue(model.getNetStructure());
    }

    private void setupElements(){

        trainNeuralNet.setCaption("train");
        saveNeuralNet.setCaption("save");
        resetNeuralNet.setCaption("reset");
        createNeuralNet.setCaption("new");
    }

    private void setupLayout(){

        Label settingsLabel = new Label("Settings:");
        GridLayout settingsGrid = new GridLayout(2,2);
        settingsGrid.setSpacing(true);
        Label nameLabel = new Label("Name:");
        Label descriptionLabel = new Label("Description:");
        settingsGrid.addComponent(nameLabel);
        settingsGrid.addComponent(nameTextField);
        settingsGrid.addComponent(descriptionLabel);
        settingsGrid.addComponent(descriptionTextArea);

        VerticalLayout leftColumn = new VerticalLayout();
        leftColumn.setSpacing(true);
        leftColumn.addComponent(settingsLabel);
        leftColumn.addComponent(settingsGrid);

        VerticalLayout rightColumn = new VerticalLayout();
        rightColumn.setSpacing(true);
        jsonEditor.setStyleName("iris-json-editor");
        VerticalLayout jsonEditorTab = new VerticalLayout(jsonEditor);
        jsonEditorTab.setSpacing(true);
        neuralNetEditor.addTab(jsonEditorTab, "Json Editor");
        VerticalLayout autoCreaterTab = new VerticalLayout();
        autoCreaterTab.setSpacing(true);
        neuralNetEditor.addTab(autoCreaterTab, "Auto Creator");
        rightColumn.addComponent(neuralNetEditor);

        HorizontalLayout totalSettingLayout = new HorizontalLayout();
        totalSettingLayout.setSpacing(true);
        totalSettingLayout.addComponent(leftColumn);
        totalSettingLayout.addComponent(rightColumn);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        buttonLayout.addComponent(createNeuralNet);
        buttonLayout.addComponent(trainNeuralNet);
        buttonLayout.addComponent(resetNeuralNet);
        buttonLayout.addComponent(saveNeuralNet);

        VerticalLayout totalLayout = new VerticalLayout();
        totalLayout.setSizeFull();
        totalLayout.setSpacing(true);
        totalLayout.addComponent(totalSettingLayout);
        totalLayout.addComponent(buttonLayout);

        this.setBodyContent(totalLayout);
    }

    private void setupListeners() {

        trainNeuralNet.addClickListener(e -> presenter.navigateToTrainingView());
    }

    private void  bindNeuralNetConfigViewModelToView(BeanFieldGroup<NeuralNetConfigViewModel> group) {

        group.bind(nameTextField, "name");
        group.bind(descriptionTextArea, "description");
        group.bind(jsonEditor, "netStructure");
    }

    //endregion
}