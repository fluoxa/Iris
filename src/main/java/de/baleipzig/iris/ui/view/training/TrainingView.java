package de.baleipzig.iris.ui.view.training;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
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
@SpringView(name = ITrainingView.VIEW_NAME)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TrainingView extends BaseSearchNNView<TrainingPresenter> implements ITrainingView {

    //region -- member --

    private final ApplicationContext context;
    @Getter
    private final LanguageHandler languageHandler;

    private Button stopTraining = new Button();
    private TextField learningRateField = new TextField();
    private TextField trainingCyclesField = new TextField();
    private TextField trainingSetSizeField = new TextField();
    private TextField miniBadgeSizeField = new TextField();
    private TextArea infoTextArea = new TextArea();

    private final ProgressBar oneCycleProgressBar = new ProgressBar();
    private final ProgressBar overallTrainingProgressBar = new ProgressBar();

    private HorizontalLayout buttonLine;

    //endregion

    // region -- methods --

    @PostConstruct
    void init() {

        setupLayout();

        presenter = new TrainingPresenter(this, (ITrainingService) context.getBean("trainingService"));
        presenter.init();
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

    @Override
    public void updateTrainingProgress(TrainingViewModel model) {

        UI.getCurrent().access(() -> {
            overallTrainingProgressBar.setValue((float) model.getOverallTrainingProgress());
            oneCycleProgressBar.setValue((float) model.getCycleProgress());
        });
    }

    private void bindTrainingViewModelToView(BeanFieldGroup<TrainingViewModel> group) {

        group.bind(learningRateField, "learningRate");
        group.bind(miniBadgeSizeField, "miniBadgeSize");
        group.bind(trainingCyclesField, "trainingCycles");
        group.bind(trainingSetSizeField, "trainingSetSize");
    }

    private void setupLayout() {

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();

        VerticalLayout scrollableVerticalLayout = new VerticalLayout();
        scrollableVerticalLayout.setSpacing(true);

        scrollableVerticalLayout.addComponent(new Label(languageHandler.getTranslation("training.view.parameterlabel")));
        scrollableVerticalLayout.addComponent(getSettingLayout());
        scrollableVerticalLayout.addComponent(new Label(languageHandler.getTranslation("training.view.infolabel")));

        infoTextArea.setSizeFull();
        infoTextArea.setReadOnly(true);
        infoTextArea.addStyleName("iris-info-textarea");
        scrollableVerticalLayout.addComponent(infoTextArea);

        scrollableVerticalLayout.addComponent(new Label(languageHandler.getTranslation("training.view.overalltrainingprogress")));
        overallTrainingProgressBar.addStyleName("iris-progressbar-training");
        scrollableVerticalLayout.addComponent(overallTrainingProgressBar);
        scrollableVerticalLayout.addComponent(new Label(languageHandler.getTranslation("training.view.onecycleprogress")));
        oneCycleProgressBar.addStyleName("iris-progressbar-training");
        scrollableVerticalLayout.addComponent(oneCycleProgressBar);

        scrollableVerticalLayout.setWidth(100, Unit.PERCENTAGE);

        Panel scrollPanel = new Panel();
        scrollPanel.addStyleName(ValoTheme.PANEL_BORDERLESS);
        scrollPanel.setSizeFull();

        scrollPanel.setContent(scrollableVerticalLayout);

        AbstractOrderedLayout buttonLine = getButtonLine();

        verticalLayout.addComponents(scrollPanel, buttonLine);
        verticalLayout.setExpandRatio(scrollPanel,1);
        verticalLayout.setExpandRatio(buttonLine, 0);

        this.setBodyContent(verticalLayout);
    }

    private GridLayout getSettingLayout() {

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

        return settingLayout;
    }

    private HorizontalLayout getButtonLine() {

        buttonLine = new HorizontalLayout();

        Button startTraining = new Button(languageHandler.getTranslation("training.view.starttraining"));
        Button saveNeuralNet = new Button(languageHandler.getTranslation("training.view.saveneuralnet"));
        Button resetNeuralNet = new Button(languageHandler.getTranslation("training.view.resetneuralnet"));
        Button configNeuralNet = new Button(languageHandler.getTranslation("training.view.configneuralnet"));
        stopTraining.setCaption(languageHandler.getTranslation("training.view.stoptraining"));

        startTraining.addClickListener(e -> presenter.runEventAsynchronously(presenter::startTraining));
        stopTraining.addClickListener(e -> presenter.runEventAsynchronously(presenter::stopTraining));
        resetNeuralNet.addClickListener(e -> presenter.resetNeuralNet());
        saveNeuralNet.addClickListener(e -> presenter.saveNeuralNet());
        configNeuralNet.addClickListener(e -> presenter.navigateToConfigView());

        stopTraining.setEnabled(false);
        buttonLine.addComponent(configNeuralNet);
        buttonLine.addComponent(saveNeuralNet);
        buttonLine.addComponent(resetNeuralNet);
        buttonLine.addComponent(startTraining);
        buttonLine.addComponent(stopTraining);
        buttonLine.setSpacing(true);

        return buttonLine;
    }

    //endregion
}