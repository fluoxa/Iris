package de.baleipzig.iris.ui.helper;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class ImageDrawer {

    private final BufferedImage image;
    private final Graphics2D graphics2D;

    public ImageDrawer(int size, float strokeWidth) {

        image = new BufferedImage(size, size, TYPE_INT_RGB);

        graphics2D = image.createGraphics();

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setBackground(Color.BLACK);
        graphics2D.clearRect(0, 0, size, size);
        graphics2D.setStroke(new BasicStroke(strokeWidth,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
        graphics2D.setColor(Color.WHITE);
    }

    public void drawLine(int x0, int y0, int x1, int y1) {
        graphics2D.drawLine(x0, y0, x1, y1);
    }

    public BufferedImage getImage() {
        BufferedImage b = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics g = b.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return b;
    }
}
