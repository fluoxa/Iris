package de.baleipzig.iris.ui.presenter.base;

import java.util.function.Supplier;

public abstract class BasePresenter{

    public void init() {
    }

    public void runEventAsynchronously(Supplier<Void> call) {

        Runnable r = () -> call.get();
        (new Thread(r)).start();
    }

    public void changeLanguage() {
        //do something
    }
}
