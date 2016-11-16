package de.baleipzig.iris.ui.components;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Image;

public class DigitImageLayout extends CssLayout {

    private static String DIGIT_0_PATH = "img/0.svg";
    private static String DIGIT_1_PATH = "img/1.svg";
    private static String DIGIT_2_PATH = "img/2.svg";
    private static String DIGIT_3_PATH = "img/3.svg";
    private static String DIGIT_4_PATH = "img/4.svg";
    private static String DIGIT_5_PATH = "img/5.svg";
    private static String DIGIT_6_PATH = "img/6.svg";
    private static String DIGIT_7_PATH = "img/7.svg";
    private static String DIGIT_8_PATH = "img/8.svg";
    private static String DIGIT_9_PATH = "img/9.svg";
    private static String UNKNOWN_PATH = "img/unknown.svg";

    public DigitImageLayout() {
        setSizeFull();
    }

    public void setDigit(int digit) {

        String path;

        switch (digit) {
            case 0:
                path = DIGIT_0_PATH;
                break;
            case 1:
                path = DIGIT_1_PATH;
                break;
            case 2:
                path = DIGIT_2_PATH;
                break;
            case 3:
                path = DIGIT_3_PATH;
                break;
            case 4:
                path = DIGIT_4_PATH;
                break;
            case 5:
                path = DIGIT_5_PATH;
                break;
            case 6:
                path = DIGIT_6_PATH;
                break;
            case 7:
                path = DIGIT_7_PATH;
                break;
            case 8:
                path = DIGIT_8_PATH;
                break;
            case 9:
                path = DIGIT_9_PATH;
                break;
            default:
                path = UNKNOWN_PATH;
        }

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
