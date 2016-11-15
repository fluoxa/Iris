package de.baleipzig.iris.ui.view.neuralnetconfig;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import de.baleipzig.iris.common.Dimension;
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
import java.util.ArrayList;
import java.util.List;

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
    private Button deleteNeuralNet = new Button();
    private Button generateNeuralNet = new Button();

    private TabSheet neuralNetEditor = new TabSheet();
    private TextArea jsonEditor = new TextArea();

    private List<HorizontalLayout> dimensionLayouts = new ArrayList<>();

    private final BeanFieldGroup<NeuralNetConfigViewModel> beanFieldGroup = new BeanFieldGroup<>(NeuralNetConfigViewModel.class);

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
        beanFieldGroup.setBuffered(false);
        beanFieldGroup.setItemDataSource(viewModel);

        bindNeuralNetConfigViewModelToView(beanFieldGroup);
    }

    @Override
    public void resetView() {

        jsonEditor.setValue("");
        nameTextField.setValue("");
        descriptionTextArea.setValue("");
    }

    @Override
    public void update(NeuralNetConfigViewModel viewModel) {
        beanFieldGroup.setItemDataSource(viewModel);
    }

    @Override
    public List<Dimension> getHiddenLayerDimensions( ){

        List<Dimension> dimensions = new ArrayList<>(dimensionLayouts.size());

        for (HorizontalLayout dimensionLayout : dimensionLayouts) {

            Dimension dim = new Dimension();
            int count = 0;

            for (Component component : dimensionLayout) {
                if(component instanceof TextField && count == 0) {
                    int dimX = Integer.parseInt(((TextField) component).getValue());
                    dimX = dimX > -1 ? dimX : 0;
                    dim.setX(dimX);
                    count++;
                }
                else if(component instanceof TextField && count == 1) {
                    int dimY = Integer.parseInt(((TextField) component).getValue());
                    dimY = dimY > -1 ? dimY : 0;
                    dim.setY(dimY);
                }
            }

            dimensions.add(dim);
        }

        return  dimensions;
    }

    private void setupElements(){

        trainNeuralNet.setCaption("train");
        saveNeuralNet.setCaption("save");
        resetNeuralNet.setCaption("reset");
        createNeuralNet.setCaption("new");
        deleteNeuralNet.setCaption("delete");
        generateNeuralNet.setCaption("generate");
    }

    private void setupLayout(){

        GridLayout settingsGrid = new GridLayout(2,2);
        settingsGrid.setSpacing(true);
        settingsGrid.setWidth(75, Unit.PERCENTAGE);
        settingsGrid.setHeight(100, Unit.PERCENTAGE);
        Label nameLabel = new Label("Name:");
        Label descriptionLabel = new Label("Description:");
        settingsGrid.addComponent(nameLabel);
        nameTextField.setWidth(100, Unit.PERCENTAGE);
        settingsGrid.addComponent(nameTextField);
        settingsGrid.addComponent(descriptionLabel);
        descriptionTextArea.setWidth(100, Unit.PERCENTAGE);
        settingsGrid.addComponent(descriptionTextArea);

        VerticalLayout metaDataTab = new VerticalLayout();
        metaDataTab.setSpacing(true);
        metaDataTab.setSizeFull();
        metaDataTab.addComponent(settingsGrid);

        VerticalLayout jsonEditorTab = new VerticalLayout(jsonEditor);
        jsonEditor.setSizeFull();
        jsonEditorTab.setSpacing(true);
        jsonEditorTab.setSizeFull();

        VerticalLayout autoCreaterTab = new VerticalLayout();
        autoCreaterTab.setSpacing(true);
        autoCreaterTab.setSizeFull();
        autoCreaterTab.addComponent(new Label("erzeugen eines neuen NeuralNetCores mit HiddenLayer der angegebenene Dimension:"));
        autoCreaterTab.addComponent(new Label("Hidden Layer Dimension:"));
        HorizontalLayout dimensionLayout = new HorizontalLayout(
                new Label("x-Dimension:"),
                new TextField(),
                new Label("y-Dimension:"),
                new TextField()
        );
        dimensionLayout.setSpacing(true);
        dimensionLayouts.add(dimensionLayout);
        dimensionLayouts.forEach(layout -> autoCreaterTab.addComponent(layout));
        autoCreaterTab.addComponent(generateNeuralNet);
        autoCreaterTab.setComponentAlignment(generateNeuralNet, Alignment.MIDDLE_RIGHT);

        neuralNetEditor.addTab(metaDataTab, "Meta Data Setting");
        neuralNetEditor.addTab(jsonEditorTab, "Json Editor");
        neuralNetEditor.addTab(autoCreaterTab, "Auto Creator");

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        buttonLayout.addComponent(trainNeuralNet);
        buttonLayout.addComponent(resetNeuralNet);
        buttonLayout.addComponent(saveNeuralNet);
        buttonLayout.addComponent(createNeuralNet);
        buttonLayout.addComponent(deleteNeuralNet);

        VerticalLayout totalLayout = new VerticalLayout();
        totalLayout.setSizeFull();
        totalLayout.setSpacing(true);
        totalLayout.addComponent(neuralNetEditor);
        totalLayout.addComponent(buttonLayout);

        this.setBodyContent(totalLayout);
    }

    private void setupListeners() {

        trainNeuralNet.addClickListener(e -> presenter.navigateToTrainingView());
        saveNeuralNet.addClickListener(e -> presenter.saveNeuralNet());
        createNeuralNet.addClickListener(e -> presenter.createNeuralNet());
        deleteNeuralNet.addClickListener(e -> presenter.deleteNeuralNet());
        resetNeuralNet.addClickListener(e -> presenter.resetNeuralNet());
    }

    private void  bindNeuralNetConfigViewModelToView(BeanFieldGroup<NeuralNetConfigViewModel> group) {

        group.bind(nameTextField, "name");
        group.bind(descriptionTextArea, "description");
        group.bind(jsonEditor, "netStructure");
    }

    //endregion
}