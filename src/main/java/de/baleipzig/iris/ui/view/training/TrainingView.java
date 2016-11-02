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

    private TextField learningRateField = new TextField();
    private TextField trainingCyclesField = new TextField();
    private TextField trainingSetSizeField = new TextField();
    private TextField miniBadgeSizeField = new TextField();
    private TextArea infoTextArea = new TextArea();
    private Button startTraining = new Button();
    private Button stopTraining = new Button();
    private Button saveNeuralNet = new Button();
    private Button resetNeuralNet = new Button();
    private Button configNeuralNet = new Button();

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

        startTraining.setCaption("start Training");
        stopTraining.setCaption("stop Training");
        saveNeuralNet.setCaption("save Neural Net");
        resetNeuralNet.setCaption("reset Training");
        configNeuralNet.setCaption("config Neural Net");
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

        bindTrainingViewModelToView(group);

        group.setBuffered(false);
    }

    private void bindTrainingViewModelToView(BeanFieldGroup<TrainingViewModel> group) {

        group.bind(learningRateField, "learningRate");
        group.bind(miniBadgeSizeField, "miniBadgeSize");
        group.bind(trainingCyclesField, "trainingCycles");
        group.bind(trainingSetSizeField, "trainingSetSize");
    }

    public void addInfoText(String message) {

        String newline = infoTextArea.getValue().isEmpty() ? "" : System.lineSeparator();

        infoTextArea.setValue(String.format("%s%s%s",infoTextArea.getValue(), newline, message));
    }

    //endregion
}