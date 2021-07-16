package controller;

import animatefx.animation.Shake;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import exception.StandardError;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Usuario;
import service.UserService;
import validator.RegisterValidator;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerRegisterScreen implements Initializable {

    @FXML
    JFXTextField txtEmail, txtUserName;

    @FXML
    JFXButton btnCreateAccount, btnClose;

    @FXML
    Label lblUserNameError, lblUserEmailError;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnCreateAccount.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                createNewUser();
                closeWindow();
            }
        });
    }

    private void createNewUser() {
        UserService userService = new UserService();
        Usuario user = new Usuario();

        String userName = txtUserName.getText();
        String email = txtEmail.getText();

        user.setNome(userName);
        user.setEmail(email);

        if (validateFormRegister(userName, email)) {
            userService.createNewUser(user);
        }
    }

    private boolean validateFormRegister(String userName, String userEmail) {
        List<StandardError> errors = RegisterValidator.formeRegisterValidate(userName, userEmail);
        if (errors.isEmpty()) {
            return true;
        } else {
            setErrors(errors);
            return false;
        }
    }

    private void setErrors(List<StandardError> errorList) {
        lblUserNameError.setText("");
        lblUserEmailError.setText("");

        for (StandardError error : errorList) {
            if (error.getField().equals("userName")) {
                lblUserNameError.setText(error.getMessage());
                new Shake(txtUserName).play();

            } else if (error.getField().equals("userEmail")) {
                lblUserEmailError.setText(error.getMessage());
                new Shake(txtEmail).play();
            }
        }
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
}
