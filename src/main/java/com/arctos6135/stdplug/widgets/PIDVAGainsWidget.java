package com.arctos6135.stdplug.widgets;

import com.arctos6135.stdplug.api.StdPlugWidgets;
import com.arctos6135.stdplug.data.PIDVADPData;
import com.arctos6135.stdplug.data.PIDVAData;

import edu.wpi.first.shuffleboard.api.components.NumberField;
import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

@Description(name = StdPlugWidgets.PIDVA_GAINS, dataTypes = { PIDVAData.class,
        PIDVADPData.class }, summary = "Displays a set of PIDVA or PIDVADP Gains.")
@ParametrizedController("PIDVAGainsWidget.fxml")
public class PIDVAGainsWidget extends SimpleAnnotatedWidget<Object> {

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
    private NumberField dpField;

    @FXML
    private Label dpLabel;

    private BooleanProperty showDP = new SimpleBooleanProperty(false);

    @FXML
    private void initialize() {
        // Add listener to the data property so that the displayed values are updated
        // when the data is updated
        dataProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue instanceof PIDVAData) {
                pField.setNumber(((PIDVAData) newValue).kP);
                iField.setNumber(((PIDVAData) newValue).kI);
                dField.setNumber(((PIDVAData) newValue).kD);
                vField.setNumber(((PIDVAData) newValue).kV);
                aField.setNumber(((PIDVAData) newValue).kA);

                showDP.set(false);
            } else {
                pField.setNumber(((PIDVADPData) newValue).kP);
                iField.setNumber(((PIDVADPData) newValue).kI);
                dField.setNumber(((PIDVADPData) newValue).kD);
                vField.setNumber(((PIDVADPData) newValue).kV);
                aField.setNumber(((PIDVADPData) newValue).kA);
                dpField.setNumber(((PIDVADPData) newValue).kDP);

                showDP.set(true);
            }
        });

        // Bind the visible and managed properties of the DP text field and label
        // so that when we set it to invisible it's also not managed
        dpField.managedProperty().bind(dpField.visibleProperty());
        dpLabel.managedProperty().bind(dpLabel.visibleProperty());

        // Add listener to the showDP property to hide the DP field when it is set to true
        showDP.addListener((observable, oldValue, newValue) -> {
            dpField.setVisible(newValue);
            dpLabel.setVisible(newValue);
        });
        dpField.setVisible(showDP.get());
        dpLabel.setVisible(showDP.get());

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
        dpField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                dpField.getOnAction().handle(new ActionEvent(this, dpField));
            }
        });
    }

    @FXML
    private void setP() {
        Object data;
        if (dataOrDefault.get() instanceof PIDVAData) {
            data = new PIDVAData(pField.getNumber(), ((PIDVAData) dataOrDefault.get()).kI,
                    ((PIDVAData) dataOrDefault.get()).kD, ((PIDVAData) dataOrDefault.get()).kV,
                    ((PIDVAData) dataOrDefault.get()).kA);
        } else {
            data = new PIDVADPData(pField.getNumber(), ((PIDVADPData) dataOrDefault.get()).kI,
                    ((PIDVADPData) dataOrDefault.get()).kD, ((PIDVADPData) dataOrDefault.get()).kV,
                    ((PIDVADPData) dataOrDefault.get()).kA, ((PIDVADPData) dataOrDefault.get()).kDP);
        }
        setData(data);
    }

    @FXML
    private void setI() {
        Object data;
        if (dataOrDefault.get() instanceof PIDVAData) {
            data = new PIDVAData(((PIDVAData) dataOrDefault.get()).kP, iField.getNumber(),
                    ((PIDVAData) dataOrDefault.get()).kD, ((PIDVAData) dataOrDefault.get()).kV,
                    ((PIDVAData) dataOrDefault.get()).kA);
        } else {
            data = new PIDVADPData(((PIDVADPData) dataOrDefault.get()).kP, iField.getNumber(),
                    ((PIDVADPData) dataOrDefault.get()).kD, ((PIDVADPData) dataOrDefault.get()).kV,
                    ((PIDVADPData) dataOrDefault.get()).kA, ((PIDVADPData) dataOrDefault.get()).kDP);
        }
        setData(data);
    }

    @FXML
    private void setD() {
        Object data;
        if (dataOrDefault.get() instanceof PIDVAData) {
            data = new PIDVAData(((PIDVAData) dataOrDefault.get()).kP, ((PIDVAData) dataOrDefault.get()).kI,
                    dField.getNumber(), ((PIDVAData) dataOrDefault.get()).kV, ((PIDVAData) dataOrDefault.get()).kA);
        } else {
            data = new PIDVADPData(((PIDVADPData) dataOrDefault.get()).kP, ((PIDVADPData) dataOrDefault.get()).kI,
                    dField.getNumber(), ((PIDVADPData) dataOrDefault.get()).kV, ((PIDVADPData) dataOrDefault.get()).kA,
                    ((PIDVADPData) dataOrDefault.get()).kDP);
        }
        setData(data);
    }

    @FXML
    private void setV() {
        Object data;
        if (dataOrDefault.get() instanceof PIDVAData) {
            data = new PIDVAData(((PIDVAData) dataOrDefault.get()).kP, ((PIDVAData) dataOrDefault.get()).kI,
                    ((PIDVAData) dataOrDefault.get()).kD, vField.getNumber(), ((PIDVAData) dataOrDefault.get()).kA);
        } else {
            data = new PIDVADPData(((PIDVADPData) dataOrDefault.get()).kP, ((PIDVADPData) dataOrDefault.get()).kI,
                    ((PIDVADPData) dataOrDefault.get()).kD, vField.getNumber(), ((PIDVADPData) dataOrDefault.get()).kA,
                    ((PIDVADPData) dataOrDefault.get()).kDP);
        }
        setData(data);
    }

    @FXML
    private void setA() {
        Object data;
        if (dataOrDefault.get() instanceof PIDVAData) {
            data = new PIDVAData(((PIDVAData) dataOrDefault.get()).kP, ((PIDVAData) dataOrDefault.get()).kI,
                    ((PIDVAData) dataOrDefault.get()).kD, ((PIDVAData) dataOrDefault.get()).kV, aField.getNumber());
        } else {
            data = new PIDVADPData(((PIDVADPData) dataOrDefault.get()).kP, ((PIDVADPData) dataOrDefault.get()).kI,
                    ((PIDVADPData) dataOrDefault.get()).kD, ((PIDVADPData) dataOrDefault.get()).kV,
                    aField.getNumber(), ((PIDVADPData) dataOrDefault.get()).kDP);
        }
        setData(data);
    }

    @FXML
    private void setDP() {
        Object data;
        if (dataOrDefault.get() instanceof PIDVAData) {
            return;
        } else {
            data = new PIDVADPData(((PIDVADPData) dataOrDefault.get()).kP, ((PIDVADPData) dataOrDefault.get()).kI,
                    ((PIDVADPData) dataOrDefault.get()).kD, ((PIDVADPData) dataOrDefault.get()).kV,
                    ((PIDVADPData) dataOrDefault.get()).kA, dpField.getNumber());
        }
        setData(data);
    }

    @Override
    public Pane getView() {
        return root;
    }

}
