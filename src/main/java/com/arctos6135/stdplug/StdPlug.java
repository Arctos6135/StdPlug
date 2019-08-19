package com.arctos6135.stdplug;

import java.util.List;

import com.arctos6135.stdplug.widgets.ImageWidget;

import edu.wpi.first.shuffleboard.api.plugin.Description;
import edu.wpi.first.shuffleboard.api.plugin.Plugin;
import edu.wpi.first.shuffleboard.api.widget.ComponentType;
import edu.wpi.first.shuffleboard.api.widget.WidgetType;

@Description(group = "com.arctos6135", name = "StdPlug", version = "0.1.0", summary = "Arctos 6135 Standard Shuffleboard Plugin")
public class StdPlug extends Plugin {

    @Override
    @SuppressWarnings("rawtypes")
    public List<ComponentType> getComponents() {
        return List.of(
            WidgetType.forAnnotatedWidget(ImageWidget.class)
        );
    }
}
