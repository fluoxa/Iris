package de.baleipzig.iris.ui.view.base;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.baleipzig.iris.model.neuralnet.neuralnet.NeuralNetMetaData;
import de.baleipzig.iris.ui.presenter.base.BaseSearchNNPresenter;

import javax.annotation.PostConstruct;
import java.util.List;

public abstract class BaseSearchNNView<P extends BaseSearchNNPresenter> extends BaseView<P> implements IBaseSearchNNView<P> {

    private final HorizontalLayout contentLayout = new HorizontalLayout();

    private final TextField searchTextField = new TextField();
    private final Button searchButton = new Button();
    private final Panel searchResultPanel = new Panel();
    private Table searchResultTable;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        presenter.searchAllNeuralNets();
    }

    @PostConstruct
    private void init() {
        createLayout();
        addListeners();
    }

    private void createLayout() {
        searchButton.setCaptionAsHtml(true);
        searchButton.setCaption(FontAwesome.SEARCH.getHtml());
        searchTextField.setWidth("100%");

        HorizontalLayout textFieldAndButtonLayout = new HorizontalLayout();

        textFieldAndButtonLayout.setWidth("100%");
        textFieldAndButtonLayout.setSpacing(true);

        textFieldAndButtonLayout.addComponent(searchTextField);
        textFieldAndButtonLayout.addComponent(searchButton);
        textFieldAndButtonLayout.setExpandRatio(searchTextField, 1);
        textFieldAndButtonLayout.setExpandRatio(searchButton, 0);

        searchResultPanel.setSizeFull();

        VerticalLayout searchAndResultLayout = new VerticalLayout();
        searchAndResultLayout.setHeight("100%");
        searchAndResultLayout.setWidth("300px");
        searchAndResultLayout.setSpacing(true);
        searchAndResultLayout.addStyleName("iris-search-result-layout");
        searchAndResultLayout.setMargin(new MarginInfo(false, true, false, false));

        searchAndResultLayout.addComponent(textFieldAndButtonLayout);
        searchAndResultLayout.addComponent(searchResultPanel);
        searchAndResultLayout.setExpandRatio(textFieldAndButtonLayout, 0);
        searchAndResultLayout.setExpandRatio(searchResultPanel, 1);

        contentLayout.setMargin(true);
        contentLayout.setSizeFull();

        Panel contentPanel = new Panel();
        contentPanel.setSizeFull();
        contentPanel.setContent(contentLayout);

        HorizontalLayout searchAndContentLayout = new HorizontalLayout();
        searchAndContentLayout.setSizeFull();
        searchAndContentLayout.addComponent(searchAndResultLayout);
        searchAndContentLayout.addComponent(contentPanel);
        searchAndContentLayout.setExpandRatio(searchAndResultLayout, 0);
        searchAndContentLayout.setExpandRatio(contentPanel, 4);

        super.setBodyContent(searchAndContentLayout);
    }

    private void addListeners() {
        searchButton.addClickListener(e -> presenter.searchNeuralNets(searchTextField.getValue()));

        searchTextField.addFocusListener(e -> toggleSearchButtonClickListener(true));
    }

    private void toggleSearchButtonClickListener(boolean activated) {
        if (activated) {
            searchButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        } else {
            searchButton.removeClickShortcut();
        }
    }

    @Override
    public void setSearchResult(List<NeuralNetMetaData> neuralNetMetaDatas) {
        BeanItemContainer<NeuralNetMetaData> resultAsContainer = new BeanItemContainer<>(NeuralNetMetaData.class, neuralNetMetaDatas);

        searchResultTable = new Table();
        searchResultTable.setContainerDataSource(resultAsContainer);
        searchResultTable.setVisibleColumns("name");
        searchResultTable.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
        searchResultTable.setSelectable(true);
        searchResultTable.setImmediate(true);
        searchResultTable.setSizeFull();
        searchResultTable.addStyleName(ValoTheme.TABLE_BORDERLESS);
        searchResultTable.addValueChangeListener(e -> presenter.handleSelection((NeuralNetMetaData) searchResultTable.getValue()));
        searchResultPanel.setContent(searchResultTable);
    }

    @Override
    public void lockSearchResultTable(boolean isLocked) {

        if(searchResultTable != null ) {
            searchResultTable.setEnabled(!isLocked);
        }
    }

    @Override
    public void setBodyContentLayoutMargin(boolean enabled) {
        contentLayout.setMargin(enabled);
    }

    @Override
    protected void setBodyContent(Component content) {
        contentLayout.removeAllComponents();
        contentLayout.addComponent(content);
    }


}
