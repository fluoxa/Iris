package de.baleipzig.iris.ui.components;

import com.vaadin.shared.MouseEventDetails;
import com.vaadin.ui.CssLayout;
import de.baleipzig.iris.ui.helper.ImageDrawer;
import lombok.Setter;
import org.vaadin.hezamu.canvas.Canvas;

import java.awt.image.BufferedImage;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DrawingLayout extends CssLayout {

    public static final int DEFAULT_SIZE = 28;
    public static final float STROKE_DIVIDER = 15;
    private final ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
    private final Canvas canvas = new Canvas();
    private Future<?> updateFuture;
    @Setter
    private ImageChangedListener imageChangedListener;
    private ImageDrawer drawer = new ImageDrawer(DEFAULT_SIZE, DEFAULT_SIZE / STROKE_DIVIDER);
    private int size = DEFAULT_SIZE;
    private int previousX;
    private int previousY;
    private boolean mousePressed = false;

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

        drawer = new ImageDrawer(size, size / STROKE_DIVIDER);
        canvas.clear();
        scheduleExecutor();
    }

    public void setSize(double size) {

        this.size = ((Double) size).intValue();
        clear();
        canvas.setStrokeStyle(0,0,0);
        canvas.setLineWidth(size / STROKE_DIVIDER);
        canvas.setLineCap("round");

    }

    private void handleMouseEvent(MouseEventDetails mouseEventDetails) {

        int x = mouseEventDetails.getRelativeX();
        int y = mouseEventDetails.getRelativeY();

        if(mousePressed) {
            scheduleExecutor();
            canvas.beginPath();
            canvas.moveTo(previousX, previousY);
            canvas.lineTo(x, y);
            canvas.stroke();
            canvas.closePath();

            drawer.drawLine(previousX, previousY, x, y);
        }

        previousX = x;
        previousY = y;
    }

    private void scheduleExecutor() {

        if (updateFuture != null && !updateFuture.isCancelled()) {
            updateFuture.cancel(false);
        }
        updateFuture = executorService.schedule(this::notifyListener, 500, TimeUnit.MILLISECONDS);
    }

    public void notifyListener() {

        if (imageChangedListener != null) {
            imageChangedListener.imageChangeEvent(drawer.getImage());
        }
    }

    public interface ImageChangedListener {
        void imageChangeEvent(BufferedImage image);
    }

    public BufferedImage getCurrentDrawnImage() {
        return drawer.getImage();
    }
}