package de.baleipzig.iris.ui.view.training;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import de.baleipzig.iris.ui.presenter.training.TrainingPresenter;
import de.baleipzig.iris.ui.service.training.ITrainingService;
import de.baleipzig.iris.ui.view.base.BaseSearchNNView;
import de.baleipzig.iris.ui.viewmodel.training.TrainingsConfiguration;
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

    //endregion

    // region -- methods --

    @PostConstruct
    void init() {
        createViewComponents();
        presenter = new TrainingPresenter(this, (ITrainingService) context.getBean("trainingsService"));
        presenter.init();
    }

    private void createViewComponents() {
        CssLayout layout = new CssLayout();
        Label learningRateLabel = new Label("Learning Rate");
        learningRateField = new TextField();
        layout.addComponent(learningRateLabel);
        layout.addComponent(learningRateField);

        Button startTraining = new Button("start Training");
        startTraining.addClickListener(e -> presenter.startTraining());

        layout.addComponent(startTraining);
        setBodyContent(layout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        super.enter(viewChangeEvent);
    }

    public void bindTrainingsConfiguration(TrainingsConfiguration trainingsConfiguration) {
        BeanFieldGroup<TrainingsConfiguration> group = new BeanFieldGroup<>(TrainingsConfiguration.class);
        group.setItemDataSource(trainingsConfiguration);
        group.bind(learningRateField, "learningRate");
        group.setBuffered(false);
    }
    //endregion
}
