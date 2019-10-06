package com.arctos6135.stdplug.util;

import javafx.scene.canvas.Canvas;

public class ResizableCanvas extends Canvas {
    
    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double prefWidth(double height) {
        return getWidth();
    }

    @Override
    public double prefHeight(double width) {
        return getHeight();
    }
}
