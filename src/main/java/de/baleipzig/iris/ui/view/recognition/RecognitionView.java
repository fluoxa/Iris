package de.baleipzig.iris.ui.view.recognition;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.baleipzig.iris.common.Dimension;
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

    public static final int LEFT_EXPAND_RATIO = 11;
    public static final int RIGHT_EXPAND_RATIO = 5;
    public static final int TOP_EXPAND_RATIO = 2;
    public static final int BOTTOM_EXPAND_RATIO = 1;
    public static final int MINIMIZED_WIDTH = 16;
    public static final double BETTER_SAVE_THAN_SORRY = 20;

    public static final String TOGGLE_AREA_WIDTH = "16px";
    public static final String INFO_AREA_LABEL_WIDTH = "60px";

    private final ApplicationContext context;

    @Getter
    private final LanguageHandler languageHandler;

    private final HorizontalLayout recognitionHeaderLayout = new HorizontalLayout();
    private final CssLayout captureBoundaryLayout = new CssLayout();
    private final CssLayout resultBoundaryLayout = new CssLayout();
    private final VerticalLayout recognitionLayout = new VerticalLayout();

    private final HorizontalLayout minimizableLayout = new HorizontalLayout();
    private final HorizontalLayout minimizedLayout = new HorizontalLayout();
    private final HorizontalLayout maximizedLayout = new HorizontalLayout();

    private final HorizontalLayout recognitionMainLayout = new HorizontalLayout();

    private boolean infoPanelVisible = false;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        super.enter(viewChangeEvent);
    }

    @PostConstruct
    private void init() {

        createLayout();

        infoPanelVisible = isWindowBigEnough();

        presenter = new RecognitionPresenter(this, (IRecognitionService) context.getBean("recognitionService"));
        presenter.init();

        reinitializeViewPort();
        UI.getCurrent().getPage().addBrowserWindowResizeListener(event -> reinitializeViewPort());
    }

    private void createLayout() {
        initRecognitionLayout();

        minimizableLayout.addStyleName("iris-minimizable-layout");
        minimizableLayout.addComponents(minimizedLayout, maximizedLayout);

        initMinimizedLayout();
        initMaximizedLayout();

        recognitionMainLayout.addComponents(recognitionLayout, minimizableLayout);

        recognitionMainLayout.setSizeFull();

        setBodyContent(recognitionMainLayout);
        setBodyContentLayoutMargin(false);
    }

    private void initRecognitionLayout() {

        Label captureLabel = new Label("Erfassung");
        captureLabel.addStyleName("iris-recognition-header-label");

        Label resultLabel = new Label("Resultat");
        resultLabel.addStyleName("iris-recognition-header-label");

        recognitionHeaderLayout.addComponents(captureLabel, resultLabel);
        recognitionHeaderLayout.setWidth("100%");
        recognitionHeaderLayout.setHeight("25px");

        captureBoundaryLayout.addStyleName("iris-boundary-layout");

        HorizontalLayout captureLayout = new HorizontalLayout();
        captureLayout.setSizeFull();
        captureLayout.addComponent(captureBoundaryLayout);
        captureLayout.setComponentAlignment(captureBoundaryLayout, Alignment.MIDDLE_CENTER);

        resultBoundaryLayout.addStyleName("iris-boundary-layout");
        HorizontalLayout resultLayout = new HorizontalLayout();
        resultLayout.setSizeFull();
        resultLayout.addComponent(resultBoundaryLayout);
        resultLayout.setComponentAlignment(resultBoundaryLayout, Alignment.MIDDLE_CENTER);

        HorizontalLayout captureAndResultLayout = new HorizontalLayout();
        captureAndResultLayout.addComponents(captureLayout, resultLayout);
        captureAndResultLayout.setSizeFull();

        CheckBox realTimeRecognition = new CheckBox("Echtzeitberechnung", true);

        TextArea infoTextArea = new TextArea();
        infoTextArea.setSizeFull();
        infoTextArea.setReadOnly(true);
        infoTextArea.addStyleName("iris-info-textarea");

        VerticalLayout bottomLayout = new VerticalLayout();
        bottomLayout.setSizeFull();
        bottomLayout.addComponents(realTimeRecognition, infoTextArea);
        bottomLayout.setExpandRatio(realTimeRecognition, 0);
        bottomLayout.setExpandRatio(infoTextArea, 1);

        recognitionLayout.setMargin(true);
        recognitionLayout.setSizeFull();
        recognitionLayout.setSpacing(true);
        recognitionLayout.addStyleName("iris-recognition-layout");

        recognitionLayout.addComponents(recognitionHeaderLayout, captureAndResultLayout, bottomLayout);

        recognitionLayout.setExpandRatio(recognitionHeaderLayout, 0);
        recognitionLayout.setExpandRatio(captureAndResultLayout, TOP_EXPAND_RATIO);
        recognitionLayout.setExpandRatio(bottomLayout, BOTTOM_EXPAND_RATIO);
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

    private AbstractOrderedLayout createAttributeLayout(String caption, AbstractTextField abstractTextField) {

        Label label = new Label(caption);
        label.setWidth(INFO_AREA_LABEL_WIDTH);
        label.addStyleName("iris-info-attribute-label");

        abstractTextField.setWidth("100%");
        abstractTextField.setValue(caption);
        abstractTextField.setEnabled(false);

        AbstractOrderedLayout layout = null;
        if (isWindowBigEnough()) {
            layout = new HorizontalLayout();
            layout.setWidth("100%");
            layout.setSpacing(true);
            layout.addComponents(label, abstractTextField);
            layout.setExpandRatio(label, 0);
            layout.setExpandRatio(abstractTextField, 1);
        } else {
            layout = new VerticalLayout();
            layout.setWidth("100%");
            layout.setSpacing(true);
            layout.addComponents(label, abstractTextField);
        }

        return layout;
    }

    private boolean isWindowBigEnough() {
        return UI.getCurrent().getPage().getBrowserWindowWidth() > 1280;
    }

    private void toggleInfoPanelVisibility(boolean show) {
        infoPanelVisible = show;
        reinitializeViewPort();
    }

    private void reinitializeViewPort() {
        recalculateBoundaryLayoutSize();
        showOrHideInfoPanel();
    }

    private void showOrHideInfoPanel() {
        minimizedLayout.setVisible(!infoPanelVisible);
        maximizedLayout.setVisible(infoPanelVisible);

        if (infoPanelVisible) {
            minimizableLayout.setSizeFull();
            recognitionMainLayout.setExpandRatio(recognitionLayout, LEFT_EXPAND_RATIO);
            recognitionMainLayout.setExpandRatio(minimizableLayout, RIGHT_EXPAND_RATIO);

        } else {
            minimizableLayout.setHeight("100%");
            minimizableLayout.setWidth(MINIMIZED_WIDTH, Unit.PIXELS);
            recognitionMainLayout.setExpandRatio(recognitionLayout, 1);
            recognitionMainLayout.setExpandRatio(minimizableLayout, 0);
        }
    }

    private void recalculateBoundaryLayoutSize() {
        Dimension parentBodyLayoutDimension = super.getEstimatedBodyLayoutDim();

        int recognitionLayoutHeight = parentBodyLayoutDimension.getX();
        int recognitionLayoutWidht;
        if(infoPanelVisible) {
            recognitionLayoutWidht = parentBodyLayoutDimension.getY() * LEFT_EXPAND_RATIO/(LEFT_EXPAND_RATIO + RIGHT_EXPAND_RATIO);
        } else {
            recognitionLayoutWidht = parentBodyLayoutDimension.getY() - MINIMIZED_WIDTH;
        }


        double availableHeight = (recognitionLayoutHeight - 2*MARGIN_WIDTH - recognitionHeaderLayout.getHeight()) * TOP_EXPAND_RATIO/(BOTTOM_EXPAND_RATIO + TOP_EXPAND_RATIO);
        double availableWidth = (recognitionLayoutWidht - 2*MARGIN_WIDTH) / 2;

        long availableSize = Math.round(Math.min(availableHeight, availableWidth) - BETTER_SAVE_THAN_SORRY);

        captureBoundaryLayout.setWidth(availableSize + "px");
        captureBoundaryLayout.setHeight(availableSize + "px");
        resultBoundaryLayout.setWidth(availableSize + "px");
        resultBoundaryLayout.setHeight(availableSize + "px");

        System.out.println(availableHeight + ", " + availableWidth + ": " + availableSize);
    }
}
