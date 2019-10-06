package com.arctos6135.stdplug.widgets;

import java.util.List;

import com.arctos6135.stdplug.api.StdPlugWidgets;
import com.arctos6135.stdplug.util.MJPEGStreamViewerThread;
import com.arctos6135.stdplug.util.ResizableCanvas;

import edu.wpi.first.shuffleboard.api.prefs.Group;
import edu.wpi.first.shuffleboard.api.prefs.Setting;
import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

@Description(name = StdPlugWidgets.MJPEG_STREAM_VIEWER, dataTypes = {
        String.class }, summary = "Displays an MJPEG video stream.")
@ParametrizedController("MJPEGStreamViewerWidget.fxml")
public class MJPEGStreamViewerWidget extends SimpleAnnotatedWidget<String> {
    
    protected BooleanProperty keepAspectRatio = new SimpleBooleanProperty(false);

    private MJPEGStreamViewerThread bgThread;

    private GraphicsContext gc;

    @FXML
    private Pane root;

    @FXML
    private StackPane imgParentPane;

    @FXML
    private ResizableCanvas img;

    @FXML
    private TextField fpsField;

    @FXML
    private TextField mbpsField;

    @FXML
    private void initialize() {
        // Set the minimum size
        // This is required, otherwise it won't resize properly when getting smaller
        imgParentPane.setMinSize(0, 0);
        // Set up the resizable canvas
        img.widthProperty().bind(imgParentPane.widthProperty());
        img.heightProperty().bind(imgParentPane.heightProperty());

        // Initialize the background thread
        bgThread = new MJPEGStreamViewerThread(dataProperty().get());
        gc = img.getGraphicsContext2D();
        // Update the background thread's stream URL with the property
        dataProperty().addListener((observable, oldValue, newValue) -> {
            bgThread.updateStreamURL(newValue);
        });

        bgThread.getImageProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                gc.drawImage(newValue, 0.0, 0.0, img.getWidth(), img.getHeight());
            }
            else {
                // TODO: Draw error image
                System.out.println("Failed to get image!");
            }
        });
        bgThread.start();
    }

    @Override
    public List<Group> getSettings() {
        return List.of(
            Group.of("Stream", 
                Setting.of("MJPEG Server URL", dataProperty(), String.class),
                Setting.of("Keep Aspect Ratio", keepAspectRatio, Boolean.class)
            )
        );
    }

    @Override
    public Pane getView() {
        return root;
    }
}
