package de.baleipzig.iris.ui.components;

import com.vaadin.shared.MouseEventDetails;
import com.vaadin.ui.CssLayout;
import org.vaadin.hezamu.canvas.Canvas;

public class DrawingLayout extends CssLayout {

    private final Canvas canvas = new Canvas();

    private boolean mousePressed = false;

    private int previousX;
    private int previousY;

    public DrawingLayout() {

        canvas.setSizeFull();

        canvas.addMouseMoveListener(this::handleMouseEvent);
        canvas.addMouseDownListener(() -> {
            mousePressed = true;
        });
        canvas.addMouseUpListener(() -> mousePressed=false);

        addComponent(canvas);
        setSizeFull();
    }

    public void clear() {
        canvas.clear();
    }

    public void setSize(double size) {

        canvas.setStrokeStyle(0,0,0);
        canvas.setLineWidth(size/20);
        canvas.setLineCap("round");
    }

    private void handleMouseEvent(MouseEventDetails mouseEventDetails) {

        int x = mouseEventDetails.getRelativeX();
        int y = mouseEventDetails.getRelativeY();

        if(mousePressed) {
            canvas.beginPath();
            canvas.moveTo(previousX, previousY);
            canvas.lineTo(x, y);
            canvas.stroke();
            canvas.closePath();
        }
        previousX = x;
        previousY = y;
    }
}