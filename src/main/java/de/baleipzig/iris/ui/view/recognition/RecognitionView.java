package de.baleipzig.iris.ui.view.recognition;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import de.baleipzig.iris.ui.presenter.recognition.RecognitionPresenter;
import de.baleipzig.iris.ui.service.recognition.IRecognitionService;
import de.baleipzig.iris.ui.view.base.BaseView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = RecognitionView.VIEW_NAME)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecognitionView extends BaseView<RecognitionPresenter> implements IRecognitionView {
    public static final String VIEW_NAME = "";

    private final ApplicationContext context;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }

    @PostConstruct
    void init() {
        presenter = new RecognitionPresenter(this, (IRecognitionService)  context.getBean("recognitionService"));
        addComponent(new Label("RecognitionView"));
    }
}
