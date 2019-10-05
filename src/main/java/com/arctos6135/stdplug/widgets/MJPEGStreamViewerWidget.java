package com.arctos6135.stdplug.widgets;

import com.arctos6135.stdplug.api.StdPlugWidgets;

import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

@Description(name = StdPlugWidgets.MJPEG_STREAM_VIEWER, dataTypes = {
        String.class }, summary = "Displays an MJPEG video stream.")
@ParametrizedController("MJPEGStreamViewerWidget.fxml")
public class MJPEGStreamViewerWidget extends SimpleAnnotatedWidget<String> {

    @FXML
    private Pane root;

    @FXML
    private Canvas canvas;

    @FXML
    private TextField fpsField;

    @FXML
    private TextField mbpsField;

    @FXML
    private void initialize() {
        
    }

    @Override
    public Pane getView() {
        return root;
    }
}
