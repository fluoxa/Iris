package de.baleipzig.iris.ui.presenter;

import de.baleipzig.iris.ui.service.IService;
import de.baleipzig.iris.ui.view.IView;

public interface IPresenter<View extends IView, Service extends IService> {

    public void changeLanguage();
}
