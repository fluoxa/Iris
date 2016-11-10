package de.baleipzig.iris.ui.view.recognition;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import de.baleipzig.iris.ui.language.LanguageHandler;
import de.baleipzig.iris.ui.presenter.recognition.RecognitionPresenter;
import de.baleipzig.iris.ui.service.recognition.IRecognitionService;
import de.baleipzig.iris.ui.view.base.BaseSearchNNView;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = RecognitionView.VIEW_NAME)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecognitionView extends BaseSearchNNView<RecognitionPresenter> implements IRecognitionView {
    public static final String VIEW_NAME = "";

    private final ApplicationContext context;

    @Getter
    private final LanguageHandler languageHandler;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        super.enter(viewChangeEvent);
    }

    @PostConstruct
    private void init() {
        createLayout();

        presenter = new RecognitionPresenter(this, (IRecognitionService)  context.getBean("recognitionService"));
        presenter.init();
    }

    private void createLayout() {


        VerticalLayout recognitionLayout = new VerticalLayout();


        VerticalLayout additionalInfoLayout = new VerticalLayout();
        for (int i = 0; i < 5; i++) {
            additionalInfoLayout.addComponent(new Button(i + ""));
        }

        Panel additionalInfoPanel = new Panel();
        additionalInfoPanel.setContent(additionalInfoLayout);

        AbsoluteLayout minimizableLayout = new AbsoluteLayout();
        minimizableLayout

        HorizontalLayout recognitionMainLayout = new HorizontalLayout();


        setBodyContent(new Label("RecognitionView"));
    }
}
