package de.baleipzig.iris.ui.components;


import com.vaadin.server.ThemeResource;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Image;

import java.util.HashMap;
import java.util.Map;

public class DigitImageLayout extends CssLayout {

    static final String UNKNOWN_PATH = "img/unknown.svg";
    private static final Map<Integer, String> digitToPathMap = new HashMap();

    static {
        digitToPathMap.put(0, "img/0.svg");
        digitToPathMap.put(1, "img/1.svg");
        digitToPathMap.put(2, "img/2.svg");
        digitToPathMap.put(3, "img/3.svg");
        digitToPathMap.put(4, "img/4.svg");
        digitToPathMap.put(5, "img/5.svg");
        digitToPathMap.put(6, "img/6.svg");
        digitToPathMap.put(7, "img/7.svg");
        digitToPathMap.put(8, "img/8.svg");
        digitToPathMap.put(9, "img/9.svg");
    }

    public DigitImageLayout() {
        setSizeFull();
    }

    public void setDigit(Integer digit) {

        String path = digitToPathMap.getOrDefault(digit, UNKNOWN_PATH);

        setImage(path);
    }

    public void setUnknown() {
        setImage(UNKNOWN_PATH);
    }

    private void setImage(String path) {

        this.removeAllComponents();

        ThemeResource imageResource = new ThemeResource(path);
        Image image = new Image(null, imageResource);
        image.setSizeFull();

        this.addComponent(image);
    }
}
