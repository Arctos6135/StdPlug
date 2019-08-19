package com.arctos6135.stdplug.widgets;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import edu.wpi.first.shuffleboard.api.prefs.Group;
import edu.wpi.first.shuffleboard.api.prefs.Setting;
import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

@Description(name = "Image", dataTypes = { String.class }, summary = "Displays an image.")
public class ImageWidget extends SimpleAnnotatedWidget<String> {

    protected Pane imgPane;
    protected ImageView view;

    protected BooleanProperty keepAspectRatio = new SimpleBooleanProperty(false);

    protected double width = 0, height = 0;
    // width / height
    // Default is NaN, which indicates that no image was loaded
    protected double aspectRatio = Double.NaN;

    // Uses the window width and height to calculate and set the image width and height
    protected void fitImage() {
        if(!keepAspectRatio.get()) {
            view.setFitWidth(width);
            view.setFitHeight(height);
        }
        else {
            // If the image was stretched to fit the height, how wide it would be
            double scaledWidth = height * aspectRatio;

            // The image would be too wide, so scale it so that it only fits the width
            if(scaledWidth > width) {
                double scaledHeight = width / aspectRatio;

                view.setFitWidth(width);
                view.setFitHeight(scaledHeight);
            }
            // The image would fit, so just use the calculated scaled width
            else {
                view.setFitWidth(scaledWidth);
                view.setFitHeight(height);
            }
        }
    }

    public ImageWidget() {
        // Create the ImageView that will hold the image
        view = new ImageView();
        // Create a BorderPane to put the ImageView in
        imgPane = new BorderPane();
        // Put the image in the center
        ((BorderPane) imgPane).setCenter(view);
        // Set the minimum size
        // This is required, otherwise it won't resize properly when getting smaller
        imgPane.setMinSize(0, 0);
        
        imgPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            width = newValue.doubleValue();
            
            if(!Double.isNaN(aspectRatio)) {
                fitImage();
            }
        });
        imgPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            height = newValue.doubleValue();

            if(!Double.isNaN(aspectRatio)) {
                fitImage();
            }
        });
        
        // When the file path is changed, load the new image
        dataOrDefault.addListener((observable, oldValue, newValue) -> {
            try {
                Image img = new Image(new FileInputStream(newValue));
                aspectRatio = img.getWidth() / img.getHeight();
                view.setImage(img);
                
                // Re-calculate image size
                fitImage();
            }
            catch(FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        // When keep aspect ratio is changed, re-calculate the image size
        keepAspectRatio.addListener((observable, oldValue, newValue) -> {
            if(!Double.isNaN(aspectRatio)) {
                fitImage();
            }
        });
    }

    @Override
    public List<Group> getSettings() {
        return List.of(
            Group.of("Image Options", 
                Setting.of("Image File Path", dataProperty(), String.class),
                Setting.of("Keep Aspect Ratio", keepAspectRatio, Boolean.class)
            )
        );
    }

    @Override
    public Pane getView() {
        return imgPane;
    }
}
