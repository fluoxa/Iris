package de.baleipzig.iris.ui.view.recognition;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
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

    public static final String TOGGLE_AREA_WIDTH = "16px";
    public static final String INFO_AREA_LABEL_WIDTH = "60px";

    private final ApplicationContext context;

    @Getter
    private final LanguageHandler languageHandler;

    private final VerticalLayout recognitionLayout = new VerticalLayout();

    private final HorizontalLayout minimizableLayout = new HorizontalLayout();
    private final HorizontalLayout minimizedLayout = new HorizontalLayout();
    private final HorizontalLayout maximizedLayout = new HorizontalLayout();

    private final HorizontalLayout recognitionMainLayout = new HorizontalLayout();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        super.enter(viewChangeEvent);
    }

    @PostConstruct
    private void init() {

        createLayout();

        toggleInfoPanelVisibility(true);

        presenter = new RecognitionPresenter(this, (IRecognitionService)  context.getBean("recognitionService"));
        presenter.init();
    }

    private void createLayout() {

        recognitionLayout.setMargin(true);

        minimizableLayout.addStyleName("iris-minimizable-layout");
        minimizableLayout.addComponents(minimizedLayout, maximizedLayout);

        initMinimizedLayout();
        initMaximizedLayout();

        recognitionMainLayout.addComponents(recognitionLayout, minimizableLayout);

        recognitionMainLayout.setSizeFull();

        setBodyContent(recognitionMainLayout);
        setBodyContentLayoutMargin(false);
    }

    private void initMinimizedLayout() {

        Button button = createToggleButton(FontAwesome.ANGLE_DOUBLE_LEFT.getHtml());
        button.addClickListener(e -> toggleInfoPanelVisibility(true));
        minimizedLayout.setSizeFull();
        minimizedLayout.addComponent(button);
        minimizedLayout.setComponentAlignment(button, Alignment.MIDDLE_CENTER);
    }

    private void initMaximizedLayout() {

        Button button = createToggleButton(FontAwesome.ANGLE_DOUBLE_RIGHT.getHtml());
        button.addClickListener(e -> toggleInfoPanelVisibility(false));

        HorizontalLayout leftLayout = new HorizontalLayout();
        leftLayout.setWidth(TOGGLE_AREA_WIDTH);
        leftLayout.setHeight("100%");
        leftLayout.addComponent(button);
        leftLayout.setComponentAlignment(button, Alignment.MIDDLE_CENTER);

        Label propertiesLabel = new Label("Eigenschaften:");

        TextField nameTextField = new TextField();
        TextArea infoTextArea = new TextArea();

        VerticalLayout infoLayout = new VerticalLayout();
        infoLayout.addStyleName("iris-info-layout");
        infoLayout.setMargin(new MarginInfo(true, true, true, false));
        infoLayout.setSpacing(true);

        infoLayout.addComponent(propertiesLabel);
        infoLayout.addComponent(createAttributeLayout("Name:", nameTextField));
        infoLayout.addComponent(createAttributeLayout("Info:", infoTextArea));

        Panel infoPanel = new Panel();
        infoPanel.setSizeFull();
        infoPanel.addStyleName(ValoTheme.PANEL_BORDERLESS);
        infoPanel.setContent(infoLayout);

        HorizontalLayout rightLayout = new HorizontalLayout();
        rightLayout.setSizeFull();
        rightLayout.addComponent(infoPanel);

        maximizedLayout.setSizeFull();
        maximizedLayout.addComponent(leftLayout);
        maximizedLayout.addComponent(rightLayout);
        maximizedLayout.setExpandRatio(leftLayout, 0);
        maximizedLayout.setExpandRatio(rightLayout, 1);
    }

    private Button createToggleButton(String caption) {

        Button button = new Button();
        button.setCaptionAsHtml(true);
        button.setCaption(caption);
        button.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        button.addStyleName("iris-toggle-info-button");
        return button;
    }

    private HorizontalLayout createAttributeLayout(String caption, AbstractTextField abstractTextField) {
        Label label = new Label(caption);
        label.setWidth(INFO_AREA_LABEL_WIDTH);
        label.addStyleName("iris-info-attribute-label");

        abstractTextField.setWidth("100%");
        abstractTextField.setValue(caption);
        abstractTextField.setEnabled(false);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidth("100%");
        horizontalLayout.setSpacing(true);
        horizontalLayout.addComponents(label, abstractTextField);
        horizontalLayout.setExpandRatio(label, 0);
        horizontalLayout.setExpandRatio(abstractTextField, 1);
        return horizontalLayout;
    }

    private void toggleInfoPanelVisibility(boolean show) {

        minimizedLayout.setVisible(!show);
        maximizedLayout.setVisible(show);

        if(show) {
            minimizableLayout.setSizeFull();
            recognitionMainLayout.setExpandRatio(recognitionLayout, 3);
            recognitionMainLayout.setExpandRatio(minimizableLayout, 1);

        } else {
            minimizableLayout.setHeight("100%");
            minimizableLayout.setWidth("16px");
            recognitionMainLayout.setExpandRatio(recognitionLayout, 1);
            recognitionMainLayout.setExpandRatio(minimizableLayout, 0);
        }
    }
}
