package de.baleipzig.iris.ui.view.training;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import de.baleipzig.iris.ui.presenter.training.TrainingPresenter;
import de.baleipzig.iris.ui.service.training.ITrainingService;
import de.baleipzig.iris.ui.view.base.BaseSearchNNView;
import de.baleipzig.iris.ui.viewmodel.training.TrainingViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = TrainingView.VIEW_NAME)
public class TrainingView extends BaseSearchNNView<TrainingPresenter> implements ITrainingView {
    public static final String VIEW_NAME = "training";

    @Autowired
    private ApplicationContext context;

    private TextField learningRateField;
    private TextField trainingCyclesField;
    private TextField miniBadgeSizeField;
    private TextField trainingSetSizeField;
    private TextArea infoTextArea;
    private Button startTraining;
    private Button stopTraining;
    private Button saveNeuralNet;
    private Button resetNeuralNet;
    private Button configNeuralNet;

    //endregion

    // region -- methods --

    @PostConstruct
    void init() {

        setupElements();
        setupListeners();
        setupLayout();

        presenter = new TrainingPresenter(this, (ITrainingService) context.getBean("trainingService"));
        presenter.init();
    }

    private void setupElements() {

        learningRateField = new TextField();
        trainingCyclesField = new TextField();
        trainingSetSizeField = new TextField();
        miniBadgeSizeField = new TextField();

        startTraining = new Button("start Training");
        stopTraining = new Button("stop Training");
        saveNeuralNet = new Button("save Neural Net");
        resetNeuralNet = new Button("reset Training");
        configNeuralNet = new Button("config Neural Net");

        infoTextArea = new TextArea();
    }

    private void setupListeners() {

        startTraining.addClickListener(e -> presenter.startTraining());
    }

    private void setupLayout() {

        GridLayout settingLayout = new GridLayout(4,2);
        settingLayout.setSpacing(true);
        Label learningRateLabel = new Label("Learning Rate:");
        settingLayout.addComponent(learningRateLabel);
        settingLayout.addComponent(learningRateField);

        Label trainingCyclesRateLabel = new Label("Training Cycles:");
        settingLayout.addComponent(trainingCyclesRateLabel);
        settingLayout.addComponent(trainingCyclesField);

        Label trainingSetSizeLabel = new Label("Number of TRaining Pictures:");
        settingLayout.addComponent(trainingSetSizeLabel);
        settingLayout.addComponent(trainingSetSizeField);

        Label miniBadgeSizeLabel = new Label("Number of MiniBadge Pictures:");
        settingLayout.addComponent(miniBadgeSizeLabel);
        settingLayout.addComponent(miniBadgeSizeField);

        HorizontalLayout buttonLine = new HorizontalLayout();
        buttonLine.addComponent(configNeuralNet);
        buttonLine.addComponent(saveNeuralNet);
        buttonLine.addComponent(resetNeuralNet);
        buttonLine.addComponent(startTraining);
        buttonLine.addComponent(stopTraining);
        buttonLine.setSpacing(true);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSpacing(true);

        verticalLayout.addComponent(new Label("Settings:"));
        verticalLayout.addComponent(settingLayout);
        verticalLayout.addComponent(new Label("Infos:"));

        infoTextArea.setWidth(100, Unit.PERCENTAGE);
        verticalLayout.addComponent(infoTextArea);
        verticalLayout.addComponent(buttonLine);

        this.setBodyContent(verticalLayout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

        super.enter(viewChangeEvent);
    }

    public void bindTrainingsConfiguration(TrainingViewModel trainingViewModel) {

        BeanFieldGroup<TrainingViewModel> group = new BeanFieldGroup<>(TrainingViewModel.class);
        group.setItemDataSource(trainingViewModel);
        group.bind(learningRateField, "learningRate");
        group.bind(miniBadgeSizeField, "miniBadgeSize");
        group.bind(trainingCyclesField, "trainingCycles");
        group.bind(trainingSetSizeField, "trainingSetSize");
        group.bind(infoTextArea, "infoText");
        group.setBuffered(false);
    }
    //endregion
}
