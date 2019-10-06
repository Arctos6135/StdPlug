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
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

@Description(name = StdPlugWidgets.MJPEG_STREAM_VIEWER, dataTypes = {
        String.class }, summary = "Displays an MJPEG video stream.")
@ParametrizedController("MJPEGStreamViewerWidget.fxml")
public class MJPEGStreamViewerWidget extends SimpleAnnotatedWidget<String> {

    protected BooleanProperty keepAspectRatio = new SimpleBooleanProperty(true);

    private MJPEGStreamViewerThread bgThread;

    private GraphicsContext gc;

    @FXML
    private Pane root;

    @FXML
    private StackPane imgParentPane;

    @FXML
    private ResizableCanvas canvas;

    @FXML
    private TextField fpsField;

    @FXML
    private TextField mbpsField;

    /**
     * Draws an image onto the canvas. Respects aspect ratio options.
     * @param img The image to draw
     */
    private void drawImage(Image img) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        // If keep aspect ratio is not set then just draw directly
        if(!keepAspectRatio.get()) {
            gc.drawImage(img, 0, 0, canvas.getWidth(), canvas.getHeight());
        }
        else {

            double aspectRatio = img.getWidth() / img.getHeight();
            // If the image was stretched to fit the height, how wide it would be
            double scaledWidth = canvas.getHeight() * aspectRatio;

            // The image would be too wide, so scale it so that it only fits the width
            if(scaledWidth > canvas.getWidth()) {
                double scaledHeight = canvas.getWidth() / aspectRatio;
                
                // Center it
                gc.drawImage(img, 0, (canvas.getHeight() - scaledHeight) / 2, canvas.getWidth(), scaledHeight);
            }
            // The image would fit, so just use the calculated scaled width
            else {
                gc.drawImage(img, (canvas.getWidth() - scaledWidth) / 2, 0, scaledWidth, canvas.getHeight());
            }
        }
    }

    @FXML
    private void initialize() {
        gc = canvas.getGraphicsContext2D();

        // Set the minimum size
        // This is required, otherwise it won't resize properly when getting smaller
        imgParentPane.setMinSize(0, 0);
        // Set up the resizable canvas
        canvas.widthProperty().bind(imgParentPane.widthProperty());
        canvas.heightProperty().bind(imgParentPane.heightProperty());

        // Add a change listener to the size of the canvas and aspect ratio
        ChangeListener<Object> resizeListener = (observable, oldValue, newValue) -> {
            // If the settings change, redraw the image
            if(bgThread != null && bgThread.getImageProperty().get() != null) {
                drawImage(bgThread.getImageProperty().get());
            }
            else {
                drawImage(MJPEGStreamViewerThread.NO_CONNECTION_IMG);
            }
        };
        canvas.widthProperty().addListener(resizeListener);
        canvas.heightProperty().addListener(resizeListener);
        keepAspectRatio.addListener(resizeListener);

        // Initialize the background thread
        bgThread = new MJPEGStreamViewerThread(dataProperty().get());

        // Update the background thread's stream URL with the property
        dataProperty().addListener((observable, oldValue, newValue) -> {
            bgThread.updateStreamURL(newValue);
        });
        // Listen for new frames and draw them
        bgThread.getImageProperty().addListener((observable, oldValue, newValue) -> {
            drawImage(newValue);
        });
        // Listen for FPS and Mbps updates
        bgThread.getFPSProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() == -1) {
                fpsField.setText("N/A");
            }
            else {
                fpsField.setText(newValue.toString());
            }
        });
        bgThread.getMbpsProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() == -1) {
                mbpsField.setText("N/A");
            }
            else {
                mbpsField.setText(newValue.toString());
            }
        });
        bgThread.start();
    }

    @Override
    public List<Group> getSettings() {
        return List.of(Group.of("Stream", Setting.of("MJPEG Server URL", dataProperty(), String.class),
                Setting.of("Keep Aspect Ratio", keepAspectRatio, Boolean.class)));
    }

    @Override
    public Pane getView() {
        return root;
    }
}
