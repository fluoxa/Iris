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
@SpringView(name = IRecognitionView.VIEW_NAME)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecognitionView extends BaseSearchNNView<RecognitionPresenter> implements IRecognitionView {

    private final ApplicationContext context;

    @Getter
    private final LanguageHandler languageHandler;


    private final Button toggleInfoButton = new Button();

    private final AbsoluteLayout minimizedLayout = new AbsoluteLayout();
    private final AbsoluteLayout maximizedLayout = new AbsoluteLayout();

    private boolean infoVisible = true;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        super.enter(viewChangeEvent);
    }

    @PostConstruct
    private void init() {
        createLayout();


        presenter = new RecognitionPresenter(this, (IRecognitionService)  context.getBean("recognitionService"));
        presenter.init();

        initListeners();
    }

    private void createLayout() {


        VerticalLayout recognitionLayout = new VerticalLayout();
        recognitionLayout.setMargin(true);


        VerticalLayout infoLayout = new VerticalLayout();
        infoLayout.setMargin(true);
        for (int i = 0; i < 5; i++) {
            infoLayout.addComponent(new Button(i + ""));
        }


        toggleInfoButton.setCaptionAsHtml(true);
        toggleInfoButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        toggleInfoButton.addStyleName("iris-toggle-info-button");
        toggleInfoPanelVisibility(true);

        Panel infoPanel = new Panel();
        infoPanel.setSizeFull();
        infoPanel.addStyleName(ValoTheme.PANEL_BORDERLESS);
        infoPanel.setContent(infoLayout);


        HorizontalLayout minimizableLayout = new HorizontalLayout();
        minimizableLayout.addStyleName("iris-minimizable-layout");

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

    private void initMinimizedLayout() {
        //minimizableLayout.addComponent(toggleInfoButton, "top: 50%; left: 2px;");
        //minimizableLayout.addComponent(infoLayout, "top: 0px; left: 0px;");
    }

    private void createContentOfMinimizedLayout() {
    }

    private void initListeners() {
        toggleInfoButton.addClickListener(e -> toggleInfoPanelVisibility(!infoVisible));
    }

    private void toggleInfoPanelVisibility(boolean show) {
        infoVisible = show;
        if(show) {
            toggleInfoButton.setCaption(FontAwesome.ANGLE_DOUBLE_RIGHT.getHtml());
        } else {
            toggleInfoButton.setCaption(FontAwesome.ANGLE_DOUBLE_LEFT.getHtml());
        }


    }
}
