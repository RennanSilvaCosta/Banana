package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.enums.LauchRecurrence;

import java.net.URL;
import java.util.ResourceBundle;

import static util.Helper.formatDecimal;

public class ControllerDialogRepeatLaunch implements Initializable {

    @FXML
    JFXComboBox<Integer> comboBoxNumberParcel = new JFXComboBox<Integer>();

    @FXML
    JFXComboBox<String> comboBoxRecorrenciasParcel = new JFXComboBox<String>();

    @FXML
    JFXButton btnFinalizarRepeat;

    @FXML
    Label txtInfo, txtInfo2, txtValueParcel, txtValue;

    double launchValue, valueParcel;

    int numberParcel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeComboBoxNumberParcel();
        initializeComboBoxRecorrenciasParcel();

        btnFinalizarRepeat.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                setLaunch();
            }
        });
    }

    public int setLaunch() {
        close();
        if (comboBoxNumberParcel.getSelectionModel().getSelectedItem() != null) {
            return comboBoxNumberParcel.getSelectionModel().getSelectedItem();
        }
        return 0;
    }

    public void getInfoValue(double value) {
        launchValue = value;
        txtValue.setText(formatDecimal(launchValue));
    }

    public void setInfoParcel() {
        numberParcel = comboBoxNumberParcel.getSelectionModel().getSelectedItem();
        valueParcel = launchValue / numberParcel;
        txtValueParcel.setText("Serão lançadas " + numberParcel + " parcelas de " + formatDecimal(valueParcel).trim());
    }

    private void initializeComboBoxRecorrenciasParcel() {
        comboBoxRecorrenciasParcel.getItems().add(String.valueOf(LauchRecurrence.ANUAL));
        comboBoxRecorrenciasParcel.getItems().add(String.valueOf(LauchRecurrence.SEMESTRAL));
        comboBoxRecorrenciasParcel.getItems().add(String.valueOf(LauchRecurrence.TRIMESTRAL));
        comboBoxRecorrenciasParcel.getItems().add(String.valueOf(LauchRecurrence.BIMESTRAL));
        comboBoxRecorrenciasParcel.getItems().add(String.valueOf(LauchRecurrence.MENSAL));
    }

    private void initializeComboBoxNumberParcel() {
        for (int x = 2; x <= 480; x++) {
            comboBoxNumberParcel.getItems().add(x);
        }
    }

    private void close() {
        Stage stage = (Stage) btnFinalizarRepeat.getScene().getWindow();
        stage.close();
    }

}
