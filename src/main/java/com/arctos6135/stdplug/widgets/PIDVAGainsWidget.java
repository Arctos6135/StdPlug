package com.arctos6135.stdplug.widgets;

import com.arctos6135.stdplug.api.StdPlugWidgets;
import com.arctos6135.stdplug.data.PIDVAData;

import edu.wpi.first.shuffleboard.api.components.NumberField;
import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

@Description(name = StdPlugWidgets.PIDVA_GAINS, dataTypes = {
        PIDVAData.class }, summary = "Displays a set of PIDVA Gains.")
@ParametrizedController("PIDVAGainsWidget.fxml")
public class PIDVAGainsWidget extends SimpleAnnotatedWidget<PIDVAData> {

    @FXML
    private Pane root;

    @FXML
    private NumberField pField;

    @FXML
    private NumberField iField;

    @FXML
    private NumberField dField;

    @FXML
    private NumberField vField;

    @FXML
    private NumberField aField;

    @FXML
    private void initialize() {
        // Add listener to the data property so that the displayed values are updated
        // when the data is updated
        dataProperty().addListener((observable, oldValue, newValue) -> {
            pField.setNumber(newValue.kP);
            iField.setNumber(newValue.kI);
            dField.setNumber(newValue.kD);
            vField.setNumber(newValue.kV);
            aField.setNumber(newValue.kA);
        });

        // Add listener to the focused property of each field to trigger an action event
        // when they lose focus
        // This is so that the setX() methods get called when the text box loses focus,
        // as opposed to only when ENTER is pressed.
        pField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                pField.getOnAction().handle(new ActionEvent(this, pField));
            }
        });
        iField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                iField.getOnAction().handle(new ActionEvent(this, iField));
            }
        });
        dField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                dField.getOnAction().handle(new ActionEvent(this, dField));
            }
        });
        vField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                vField.getOnAction().handle(new ActionEvent(this, vField));
            }
        });
        aField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                aField.getOnAction().handle(new ActionEvent(this, aField));
            }
        });
    }

    @FXML
    private void setP() {
        PIDVAData data = new PIDVAData(pField.getNumber(), dataOrDefault.get().kI, dataOrDefault.get().kD, dataOrDefault.get().kV, dataOrDefault.get().kA);
        setData(data);
    }

    @FXML
    private void setI() {
        PIDVAData data = new PIDVAData(dataOrDefault.get().kP, iField.getNumber(), dataOrDefault.get().kD, dataOrDefault.get().kV, dataOrDefault.get().kA);
        setData(data);
    }

    @FXML
    private void setD() {
        PIDVAData data = new PIDVAData(dataOrDefault.get().kP, dataOrDefault.get().kI, dField.getNumber(), dataOrDefault.get().kV, dataOrDefault.get().kA);
        setData(data);
    }

    @FXML
    private void setV() {
        PIDVAData data = new PIDVAData(dataOrDefault.get().kP, dataOrDefault.get().kI, dataOrDefault.get().kD, vField.getNumber(), dataOrDefault.get().kA);
        setData(data);
    }

    @FXML
    private void setA() {
        PIDVAData data = new PIDVAData(dataOrDefault.get().kP, dataOrDefault.get().kI, dataOrDefault.get().kD, dataOrDefault.get().kV, aField.getNumber());
        setData(data);
    }

    @Override
    public Pane getView() {
        return root;
    }

}
