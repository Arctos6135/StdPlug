package com.arctos6135.stdplug.widgets;

import java.util.List;

import com.arctos6135.stdplug.api.StdPlugWidgets;
import com.arctos6135.stdplug.util.MJPEGStreamViewerTask;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

@Description(name = StdPlugWidgets.MJPEG_STREAM_VIEWER, dataTypes = {
        String.class }, summary = "Displays an MJPEG video stream.")
@ParametrizedController("MJPEGStreamViewerWidget.fxml")
public class MJPEGStreamViewerWidget extends SimpleAnnotatedWidget<String> {

    protected BooleanProperty keepAspectRatio = new SimpleBooleanProperty(true);

    protected BooleanProperty showStats = new SimpleBooleanProperty(true);

    protected BooleanProperty startStream = new SimpleBooleanProperty(false);

    private MJPEGStreamViewerTask task = null;

    private GraphicsContext gc;

    private ChangeListener<Image> frameListener;
    private ChangeListener<String> statListener;
    private ChangeListener<Boolean> startStreamListener;

    @FXML
    private Pane root;

    @FXML
    private StackPane imgParentPane;

    @FXML
    private ResizableCanvas canvas;

    @FXML
    private Label fpsLabel;

    @FXML
    private Label bandwidthLabel;

    @FXML
    private TextField fpsField;

    @FXML
    private TextField bandwidthField;

    @FXML
    private Button toggleWorkerButton;

    /**
     * Draws an image onto the canvas. Respects aspect ratio options.
     * 
     * @param img The image to draw
     */
    private void drawImage(Image img) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        // If keep aspect ratio is not set then just draw directly
        if (!keepAspectRatio.get()) {
            gc.drawImage(img, 0, 0, canvas.getWidth(), canvas.getHeight());
        } else {

            double aspectRatio = img.getWidth() / img.getHeight();
            // If the image was stretched to fit the height, how wide it would be
            double scaledWidth = canvas.getHeight() * aspectRatio;

            // The image would be too wide, so scale it so that it only fits the width
            if (scaledWidth > canvas.getWidth()) {
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
            if (task != null && task.getValue() != null) {
                drawImage(task.getValue());
            } else {
                drawImage(MJPEGStreamViewerTask.NO_CONNECTION_IMG);
            }
        };
        canvas.widthProperty().addListener(resizeListener);
        canvas.heightProperty().addListener(resizeListener);
        keepAspectRatio.addListener(resizeListener);

        
        // Bind the managed property to the visible property so they're updated at once
        // So that when the node is hidden it also doesn't take up space
        fpsLabel.managedProperty().bind(fpsLabel.visibleProperty());
        fpsField.managedProperty().bind(fpsField.visibleProperty());
        bandwidthLabel.managedProperty().bind(bandwidthLabel.visibleProperty());
        bandwidthField.managedProperty().bind(bandwidthField.visibleProperty());
        toggleWorkerButton.managedProperty().bind(toggleWorkerButton.visibleProperty());
        // Add a listener to show/hide them
        showStats.addListener((observable, oldValue, newValue) -> {
            fpsLabel.setVisible(newValue);
            fpsField.setVisible(newValue);
            bandwidthLabel.setVisible(newValue);
            bandwidthField.setVisible(newValue);
            toggleWorkerButton.setVisible(newValue);
        });

        // Update the background thread's stream URL with the property
        dataProperty().addListener((observable, oldValue, newValue) -> {
            if(task != null) {
                task.updateStreamURL(newValue);
            }
        });

        frameListener = (observable, oldValue, newValue) -> {
            drawImage(newValue);
        };

        statListener = (observable, oldValue, newValue) -> {
            String[] stats = newValue.split(",");
            fpsField.setText(stats[0]);
            bandwidthField.setText(stats[1]);
        };

        // Add a listener to the start stream property
        startStreamListener = (observable, oldValue, newValue) -> {
            // If set to true and task is not running or set to false and task is running, toggle it
            if((newValue && task == null) || (!newValue && task != null)) {
                toggleWorker();
            }
        };
        startStream.addListener(startStreamListener);
    }

    @FXML
    private void toggleWorker() {
        if(task == null) {
            toggleWorkerButton.setText("Stop");
            // Set the start stream property
            // Unhook the listener first
            startStream.removeListener(startStreamListener);
            startStream.set(true);
            startStream.addListener(startStreamListener);

            // Create worker
            task = new MJPEGStreamViewerTask(dataOrDefault.get());
            // Listen for new frames and draw them
            task.valueProperty().addListener(frameListener);
            // Listen for stats updates
            task.messageProperty().addListener(statListener);
            // Start worker
            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        }
        else {
            if(!task.cancel()) {
                toggleWorkerButton.setText("Error");
                System.err.println("Error: Stream viewer task cannot be cancelled!");
            }
            else {
                toggleWorkerButton.setText("Start");
                startStream.removeListener(startStreamListener);
                startStream.set(false);
                startStream.addListener(startStreamListener);
                task.valueProperty().removeListener(frameListener);
                task.messageProperty().removeListener(statListener);
                task = null;

                drawImage(MJPEGStreamViewerTask.NO_CONNECTION_IMG);
                fpsField.setText("N/A");
                bandwidthField.setText("N/A");
            }
        }
    }

    @Override
    public List<Group> getSettings() {
        return List.of(
                Group.of("Stream", Setting.of("MJPEG Server URL", dataProperty(), String.class),
                        Setting.of("Keep Aspect Ratio", keepAspectRatio, Boolean.class),
                        Setting.of("Start Stream", startStream, Boolean.class)),
                Group.of("Appearance", Setting.of("Show Stats", showStats, Boolean.class)));
    }

    @Override
    public Pane getView() {
        return root;
    }
}
