package de.baleipzig.iris.ui.view.recognition;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
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
        recognitionLayout.setMargin(true);


        VerticalLayout infoLayout = new VerticalLayout();
        infoLayout.setMargin(true);
        for (int i = 0; i < 5; i++) {
            infoLayout.addComponent(new Button(i + ""));
        }

        Button toggleInfoButton = new Button();
        toggleInfoButton.setCaptionAsHtml(true);
        toggleInfoButton.setCaption(FontAwesome.ANGLE_DOUBLE_RIGHT.getHtml());
        toggleInfoButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        toggleInfoButton.addStyleName("iris-toggle-info-button");

        Panel infoPanel = new Panel();
        infoPanel.setSizeFull();
        infoPanel.addStyleName(ValoTheme.PANEL_BORDERLESS);
        infoPanel.setContent(infoLayout);


        CssLayout minimizableLayout = new CssLayout();
        minimizableLayout.addStyleName("iris-minimizable-layout");
        minimizableLayout.setHeight("100%");
        minimizableLayout.addComponents(toggleInfoButton, infoPanel);
        //minimizableLayout.setComponentAlignment(toggleInfoButton, Alignment.MIDDLE_CENTER);


        HorizontalLayout recognitionMainLayout = new HorizontalLayout();
        recognitionMainLayout.addComponents(recognitionLayout, minimizableLayout);



        recognitionMainLayout.addStyleName("bla");
        recognitionMainLayout.setSizeFull();
        recognitionMainLayout.setExpandRatio(recognitionLayout, 1);
        recognitionMainLayout.setExpandRatio(minimizableLayout, 0);

        setBodyContent(recognitionMainLayout);
        setBodyContentLayoutMargin(false);
    }
}
