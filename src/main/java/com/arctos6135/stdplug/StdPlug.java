package com.arctos6135.stdplug;

import java.util.List;
import java.util.Map;

import com.arctos6135.stdplug.datatypes.PIDVADPDataType;
import com.arctos6135.stdplug.datatypes.PIDVADataType;
import com.arctos6135.stdplug.widgets.ImageWidget;
import com.arctos6135.stdplug.widgets.MJPEGStreamViewerWidget;
import com.arctos6135.stdplug.widgets.PIDVAGainsWidget;

import edu.wpi.first.shuffleboard.api.data.DataType;
import edu.wpi.first.shuffleboard.api.plugin.Description;
import edu.wpi.first.shuffleboard.api.plugin.Plugin;
import edu.wpi.first.shuffleboard.api.widget.ComponentType;
import edu.wpi.first.shuffleboard.api.widget.WidgetType;

@Description(group = "com.arctos6135", name = "StdPlug", version = "0.2.0", summary = "Arctos 6135 Standard Shuffleboard Plugin")
public class StdPlug extends Plugin {

    @Override
    @SuppressWarnings("rawtypes")
    public List<DataType> getDataTypes() {
        return List.of(
            PIDVADataType.Instance,
            PIDVADPDataType.Instance
        );
    }

    @Override
    @SuppressWarnings("rawtypes")
    public List<ComponentType> getComponents() {
        return List.of(
            WidgetType.forAnnotatedWidget(ImageWidget.class),
            WidgetType.forAnnotatedWidget(PIDVAGainsWidget.class),
            WidgetType.forAnnotatedWidget(MJPEGStreamViewerWidget.class)
        );
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Map<DataType, ComponentType> getDefaultComponents() {
        return Map.of(
            PIDVADataType.Instance, WidgetType.forAnnotatedWidget(PIDVAGainsWidget.class),
            PIDVADPDataType.Instance, WidgetType.forAnnotatedWidget(PIDVAGainsWidget.class)
        );
    }
}
