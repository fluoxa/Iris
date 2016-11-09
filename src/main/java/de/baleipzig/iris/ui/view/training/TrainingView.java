package de.baleipzig.iris.ui.view.training;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import de.baleipzig.iris.ui.language.LanguageHandler;
import de.baleipzig.iris.ui.presenter.training.TrainingPresenter;
import de.baleipzig.iris.ui.service.training.ITrainingService;
import de.baleipzig.iris.ui.view.base.BaseSearchNNView;
import de.baleipzig.iris.ui.viewmodel.training.TrainingViewModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = TrainingView.VIEW_NAME)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TrainingView extends BaseSearchNNView<TrainingPresenter> implements ITrainingView {
    public static final String VIEW_NAME = "training";

    private final ApplicationContext context;
    @Getter
    private final LanguageHandler languageHandler;

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

    private HorizontalLayout buttonLine;

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
        resetNeuralNet.setCaption("reset Neural Net");
        configNeuralNet.setCaption("config Neural Net");
    }

    private void setupListeners() {

        startTraining.addClickListener(e -> presenter.runEventAsynchronously(presenter::startTraining));
        stopTraining.addClickListener(e -> presenter.runEventAsynchronously(presenter::stopTraining));
        resetNeuralNet.addClickListener(e -> presenter.resetNeuralNet());
        saveNeuralNet.addClickListener(e -> presenter.saveNeuralNet());
    }

    private void setupLayout() {

        GridLayout settingLayout = new GridLayout(4,2);
        settingLayout.setSpacing(true);
        Label learningRateLabel = new Label(languageHandler.getTranslation("training.view.learningrate"));
        settingLayout.addComponent(learningRateLabel);
        settingLayout.addComponent(learningRateField);

        Label trainingCyclesRateLabel = new Label(languageHandler.getTranslation("training.view.cycles"));
        settingLayout.addComponent(trainingCyclesRateLabel);
        settingLayout.addComponent(trainingCyclesField);

        Label trainingSetSizeLabel = new Label(languageHandler.getTranslation("training.view.trainingsetsize"));
        settingLayout.addComponent(trainingSetSizeLabel);
        settingLayout.addComponent(trainingSetSizeField);

        Label miniBadgeSizeLabel = new Label(languageHandler.getTranslation("training.view.minibadgesize"));
        settingLayout.addComponent(miniBadgeSizeLabel);
        settingLayout.addComponent(miniBadgeSizeField);

        buttonLine = new HorizontalLayout();
        stopTraining.setEnabled(false);
        buttonLine.addComponent(configNeuralNet);
        buttonLine.addComponent(saveNeuralNet);
        buttonLine.addComponent(resetNeuralNet);
        buttonLine.addComponent(startTraining);
        buttonLine.addComponent(stopTraining);
        buttonLine.setSpacing(true);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSpacing(true);

        verticalLayout.addComponent(new Label(languageHandler.getTranslation("training.view.parameterlabel")));
        verticalLayout.addComponent(settingLayout);
        verticalLayout.addComponent(new Label(languageHandler.getTranslation("training.view.infolabel")));

        infoTextArea.setSizeFull();
        infoTextArea.setReadOnly(true);
        infoTextArea.addStyleName("iris-info-textarea");
        verticalLayout.addComponent(infoTextArea);
        verticalLayout.setWidth(100, Unit.PERCENTAGE);
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
        UI.getCurrent().access(() -> {
            infoTextArea.setReadOnly(false);
            infoTextArea.setValue(String.format("%s%s%s",infoTextArea.getValue(), newline, message));
            infoTextArea.setCursorPosition(infoTextArea.getValue().length());
            infoTextArea.setReadOnly(true);
        });
    }

    @Override
    public void setTrainingLock(boolean isLocked) {

        for (Component comp : buttonLine) {
            if (comp instanceof Button && comp != stopTraining) {
                comp.setEnabled(!isLocked);
            }
        }

        stopTraining.setEnabled(isLocked);

        lockSearchResultTable(isLocked);
    }

    //endregion
}