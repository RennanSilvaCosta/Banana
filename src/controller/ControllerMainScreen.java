package controller;

import adapter.AdapterListLancamentos;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import dao.SQL;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Lancamento;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerMainScreen implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    JFXButton btnAddDespesa, btnAddReceita = new JFXButton();

    @FXML
    Label txtTotalReceita, txtTotalDespesa, txtTotalSaldo;

    @FXML
    JFXListView<Lancamento> listViewLancamentos = new JFXListView<>();

    List<Lancamento> lancamentos = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initializeListLancamentos();

        btnAddReceita.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                loadNewViewAndCloseOld("/view/ReceitaScreen.fxml", null);
            }
        });

        btnAddDespesa.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                loadNewViewAndCloseOld("/view/DespesaScreen.fxml", null);
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

    private void initializeListLancamentos() {
        try {
            lancamentos.clear();
            listViewLancamentos.getItems().clear();
            lancamentos = SQL.getAllLancamentos();
            for (Lancamento lanc : lancamentos) {
                listViewLancamentos.getItems().add(lanc);
                listViewLancamentos.setCellFactory(lancamento -> new AdapterListLancamentos());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    public void refreshList() {
        initializeListLancamentos();
    }
}
