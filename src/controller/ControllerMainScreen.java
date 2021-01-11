package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMainScreen implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    JFXButton btnAddDespesa, btnAddReceita = new JFXButton();

    @FXML
    Label txtTotalReceita, txtTotalDespesa, txtTotalSaldo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btnAddReceita.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                loadNewViewAndCloseOld("/view/ReceitaScreen.fxml", null);

            }
        });

        btnAddDespesa.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Despesa");
            }
        });

    }

    public void loadNewViewAndCloseOld(String caminho, JFXButton componente) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(caminho));
            Parent parent = fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

            if (componente != null) {
                //a partir do componenete de layout recupero a janela a ser fechada
                Stage stage2 = (Stage) componente.getScene().getWindow();
                stage2.close();
            }

            scene.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });

            scene.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
                stage.setOpacity(0.7);
            });

            scene.setOnMouseReleased(mouseEvent -> stage.setOpacity(1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
