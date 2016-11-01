package de.baleipzig.iris.ui.presenter.base;

import de.baleipzig.iris.ui.service.base.IBaseSearchNNService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class BaseSearchNNPresenter<S extends IBaseSearchNNService> extends BasePresenter {
    protected final S service;

    public void init() {
        super.init();
    }
}
