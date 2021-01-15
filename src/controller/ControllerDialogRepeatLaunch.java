package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import model.Lancamento;
import model.enums.LauchRecurrence;

import java.net.URL;
import java.util.ResourceBundle;

import static util.Helper.formatDecimal;

public class ControllerDialogRepeatLaunch implements Initializable {

    @FXML
    JFXComboBox<String> comboBoxRecorrencias = new JFXComboBox<String>();

    @FXML
    JFXComboBox<Integer> comboBoxNumberParcel = new JFXComboBox<Integer>();

    @FXML
    JFXComboBox<String> comboBoxRecorrenciasParcel = new JFXComboBox<String>();

    @FXML
    JFXRadioButton radioButtonFixed, radioButtonParcel;

    @FXML
    JFXButton btnFinalizarRepeat;

    @FXML
    Label txtInfo, txtInfo2, txtNumberParcel, txtValueParcel, txtValue;

    final ToggleGroup group = new ToggleGroup();

    double launchValue;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initializeComboBoxRecorrencias();
        initializeComboBoxNumberParcel();
        initializeComboBoxRecorrenciasParcel();

        radioButtonFixed.setToggleGroup(group);
        radioButtonParcel.setToggleGroup(group);

        btnFinalizarRepeat.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });


    }

    public void getInfoValue(double value) {
        launchValue = value;
        txtValue.setText("R$:" + formatDecimal(launchValue));
    }

    public void teste2() {
        int numberParcel = comboBoxNumberParcel.getSelectionModel().getSelectedItem();
        double valueParcel = launchValue / numberParcel;
        txtNumberParcel.setText("Serão lançadas " + numberParcel + " parcelas de ");
        txtValueParcel.setText("R$:" + formatDecimal(valueParcel));
    }

    public void teste() {
        if (radioButtonParcel.isSelected()) {
            comboBoxRecorrencias.setVisible(false);
            comboBoxNumberParcel.setVisible(true);
            comboBoxRecorrenciasParcel.setVisible(true);
            txtInfo.setText("Parcelas: ");
            txtInfo2.setText(("Recorrencia:"));
        }
        if (radioButtonFixed.isSelected()) {
            comboBoxRecorrencias.setVisible(true);
            comboBoxNumberParcel.setVisible(false);
            comboBoxRecorrenciasParcel.setVisible(false);
            txtInfo.setText("Recorrencia:");
            txtInfo2.setText("");
            txtNumberParcel.setText("");
            txtValueParcel.setText("");
        }
    }


    private void initializeComboBoxRecorrenciasParcel() {
        comboBoxRecorrenciasParcel.getItems().add(String.valueOf(LauchRecurrence.ANUAL));
        comboBoxRecorrenciasParcel.getItems().add(String.valueOf(LauchRecurrence.SEMESTRAL));
        comboBoxRecorrenciasParcel.getItems().add(String.valueOf(LauchRecurrence.TRIMESTRAL));
        comboBoxRecorrenciasParcel.getItems().add(String.valueOf(LauchRecurrence.BIMESTRAL));
        comboBoxRecorrenciasParcel.getItems().add(String.valueOf(LauchRecurrence.MENSAL));
    }

    private void initializeComboBoxNumberParcel() {
        for (int x = 2; x <= 480; x++){
            comboBoxNumberParcel.getItems().add(x);
        }
    }

    private void initializeComboBoxRecorrencias() {
        comboBoxRecorrencias.getItems().add(String.valueOf(LauchRecurrence.ANUAL));
        comboBoxRecorrencias.getItems().add(String.valueOf(LauchRecurrence.SEMESTRAL));
        comboBoxRecorrencias.getItems().add(String.valueOf(LauchRecurrence.TRIMESTRAL));
        comboBoxRecorrencias.getItems().add(String.valueOf(LauchRecurrence.BIMESTRAL));
        comboBoxRecorrencias.getItems().add(String.valueOf(LauchRecurrence.MENSAL));
    }


}
