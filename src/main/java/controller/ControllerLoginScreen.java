package controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerLoginScreen implements Initializable {

    @FXML
    JFXButton btnClose;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void exitSystem() {
        System.exit(0);
    }
}
