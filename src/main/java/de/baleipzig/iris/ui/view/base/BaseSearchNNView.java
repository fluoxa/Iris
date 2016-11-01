package de.baleipzig.iris.ui.view.base;

import de.baleipzig.iris.ui.presenter.base.BaseSearchNNPresenter;

import javax.annotation.PostConstruct;

public abstract class BaseSearchNNView<P extends BaseSearchNNPresenter> extends BaseView<P> implements IBaseSearchNNView<P> {

    @PostConstruct
    private void init() {
        System.out.println("BaseSearchNNView");
    }
}
