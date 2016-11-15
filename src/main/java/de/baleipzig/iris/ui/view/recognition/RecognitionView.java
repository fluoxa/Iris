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
import elemental.json.JsonArray;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = IRecognitionView.VIEW_NAME)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecognitionView extends BaseSearchNNView<RecognitionPresenter> implements IRecognitionView {

    private static String ELEMENT_ID = "RecognitionView.ElementId";

    public static final String TOGGLE_AREA_WIDTH = "16px";
    public static final String INFO_AREA_LABEL_WIDTH = "60px";

    private final ApplicationContext context;

    @Getter
    private final LanguageHandler languageHandler;

    private final CssLayout captureBoundaryLayout = new CssLayout();
    private final CssLayout resultBoundaryLayout = new CssLayout();
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
        addJavaScriptFunction();
        UI.getCurrent().getPage().addBrowserWindowResizeListener(event -> updateCaptureLayoutSize());

        createLayout();

        toggleInfoPanelVisibility(isWindowBigEnough());



        presenter = new RecognitionPresenter(this, (IRecognitionService) context.getBean("recognitionService"));
        presenter.init();
        /*
        presenter.runEventAsynchronously(() -> {
            UI.getCurrent().access(() -> {
                updateCaptureLayoutSize();
            });
            return null;
        });*/
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

        captureBoundaryLayout.addStyleName("border-test");

        HorizontalLayout captureLayout = new HorizontalLayout();
        captureLayout.setSizeFull();
        captureLayout.setId(ELEMENT_ID);
        captureLayout.addStyleName("border-test");
        captureLayout.addComponent(captureBoundaryLayout);
        captureLayout.setComponentAlignment(captureBoundaryLayout, Alignment.MIDDLE_CENTER);

        resultBoundaryLayout.addStyleName("border-test");

        HorizontalLayout resultLayout = new HorizontalLayout();
        resultLayout.setSizeFull();
        resultLayout.addStyleName("border-test");
        resultLayout.addComponent(resultBoundaryLayout);
        resultLayout.setComponentAlignment(resultBoundaryLayout, Alignment.MIDDLE_CENTER);

        HorizontalLayout captureAndResultLayout = new HorizontalLayout();
        captureAndResultLayout.addComponents(captureLayout, resultLayout);
        captureAndResultLayout.setSizeFull();
        captureAndResultLayout.setSpacing(true);

        CheckBox realTimeRecognition = new CheckBox("Echtzeitberechnung", true);

        Label textAreaLabel = new Label("Informationen");

        TextArea infoTextArea = new TextArea();
        infoTextArea.setSizeFull();
        infoTextArea.setReadOnly(true);
        infoTextArea.addStyleName("iris-info-textarea");

        recognitionLayout.setMargin(true);
        recognitionLayout.setSizeFull();
        recognitionLayout.setSpacing(true);
        recognitionLayout.addStyleName("iris-recognition-layout");

        recognitionLayout.addComponent(captureAndResultLayout);
        recognitionLayout.addComponent(realTimeRecognition);
        //recognitionLayout.addComponent(textAreaLabel);
        recognitionLayout.addComponent(infoTextArea);

        recognitionLayout.setExpandRatio(captureAndResultLayout, 2);
        recognitionLayout.setExpandRatio(realTimeRecognition, 0);
        //recognitionLayout.setExpandRatio(textAreaLabel, 0);
        recognitionLayout.setExpandRatio(infoTextArea, 1);
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

        minimizedLayout.setVisible(!show);
        maximizedLayout.setVisible(show);

        if (show) {
            minimizableLayout.setSizeFull();
            recognitionMainLayout.setExpandRatio(recognitionLayout, 11);
            recognitionMainLayout.setExpandRatio(minimizableLayout, 5);

        } else {
            minimizableLayout.setHeight("100%");
            minimizableLayout.setWidth("16px");
            recognitionMainLayout.setExpandRatio(recognitionLayout, 1);
            recognitionMainLayout.setExpandRatio(minimizableLayout, 0);
        }
    }

    private void recalculateBoundaryLayoutSize(int height, int width) {
        System.out.println(height + ", " + width);

        int size = Math.min(height, width);

        captureBoundaryLayout.setSizeUndefined();

        captureBoundaryLayout.setHeight(size, Unit.PIXELS);
        captureBoundaryLayout.setWidth(size, Unit.PIXELS);

        resultBoundaryLayout.setHeight(size, Unit.PIXELS);
        resultBoundaryLayout.setWidth(size, Unit.PIXELS);
    }

    private void addJavaScriptFunction() {
        JavaScript.getCurrent().addFunction("getHeight4" + RecognitionView.class.getSimpleName(), new JavaScriptFunction() {
            private int captureLayoutWidth;
            private int captureLayoutHeight;

            @Override
            public void call(final JsonArray arguments) {
                captureLayoutHeight = (int) arguments.getNumber(0);
                captureLayoutWidth = (int) arguments.getNumber(1);
                recalculateBoundaryLayoutSize(captureLayoutHeight, captureLayoutWidth);
            }
        });
    }


    private void updateCaptureLayoutSize() {
        JavaScript.getCurrent().execute(""
                + "var element = document.getElementById('" + ELEMENT_ID + "');"
                + "getHeight4" + RecognitionView.class.getSimpleName() + "(element.clientHeight, element.clientWidth);");
    }
}
