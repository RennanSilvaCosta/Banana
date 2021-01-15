package controller;

import adapter.AdapterListLancamentos;
import animatefx.animation.FadeIn;
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
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Lancamento;
import model.enums.LaunchType;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import static util.Helper.formatDecimal;

public class ControllerMainScreen implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    Button btnTeste;

    @FXML
    JFXButton btnAddDespesa, btnAddReceita = new JFXButton();

    @FXML
    Label txtTotalReceita, txtTotalDespesa, txtTotalSaldo, txtDate;

    @FXML
    JFXListView<Lancamento> listViewLancamentos = new JFXListView<>();

    List<Lancamento> lancamentos = new ArrayList<>();

    @FXML
    ImageView imgAvancaMes, imgRetrocedeMes;

    int monthSelected = Calendar.getInstance().get(Calendar.MONTH);
    int yearSelected = Calendar.getInstance().get(Calendar.YEAR);
    ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initializeListLancamentos();
        setDate();
        setInfoValues();

        btnAddReceita.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                openDialogAddReceita();
            }
        });

        btnAddDespesa.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                openDialogAddDespesa();
            }
        });

        imgAvancaMes.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                advanceDate();
            }
        });

        imgRetrocedeMes.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                goBackDate();
            }
        });

        setInfoValues();

    }

    private void setInfoValues() {
        new FadeIn(txtTotalReceita).play();
        new FadeIn(txtTotalDespesa).play();
        new FadeIn(txtTotalSaldo).play();
        double totalReceita = 0;
        double totalDespesa = 0;
        for (Lancamento lanc : lancamentos) {
            if (lanc.getType().equals(LaunchType.RECEITA)) {
                totalReceita += lanc.getValue();
            }
            if (lanc.getType().equals(LaunchType.DESPESA)) {
                totalDespesa += lanc.getValue();
            }
        }
        double totalSaldo = totalReceita - totalDespesa;
        txtTotalReceita.setText(formatDecimal(totalReceita));
        txtTotalDespesa.setText(formatDecimal(totalDespesa));
        txtTotalSaldo.setText(formatDecimal(totalSaldo));

    }

    public void initializeListLancamentos() {
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

    private void goBackDate() {
        monthSelected--;
        if (monthSelected < 0) {
            monthSelected = 11;
            yearSelected--;
            setDate(monthSelected);
        }
        setDate(monthSelected);
    }

    private void advanceDate() {
        monthSelected++;
        if (monthSelected > 11) {
            monthSelected = 0;
            yearSelected++;
            setDate(monthSelected);
        }
        setDate(monthSelected);
    }

    private void setDate(int month) {
        new FadeIn(txtDate).play();
        switch (month) {

            case 0:
                txtDate.setText("Janeiro " + yearSelected);
                break;

            case 1:
                txtDate.setText("Fevereiro " + yearSelected);
                break;

            case 2:
                txtDate.setText("Março " + yearSelected);
                break;

            case 3:
                txtDate.setText("Abril " + yearSelected);
                break;

            case 4:
                txtDate.setText("Maio " + yearSelected);
                break;

            case 5:
                txtDate.setText("Junho " + yearSelected);
                break;

            case 6:
                txtDate.setText("Julho " + yearSelected);
                break;

            case 7:
                txtDate.setText("Agosto " + yearSelected);
                break;

            case 8:
                txtDate.setText("Setembro " + yearSelected);
                break;

            case 9:
                txtDate.setText("Outubro " + yearSelected);
                break;

            case 10:
                txtDate.setText("Novembro " + yearSelected);
                break;

            case 11:
                txtDate.setText("Dezembro " + yearSelected);
                break;
        }
    }

    private void setDate() {
        new FadeIn(txtDate).play();
        switch (monthSelected) {

            case 0:
                txtDate.setText("Janeiro " + yearSelected);
                break;

            case 1:
                txtDate.setText("Fevereiro " + yearSelected);
                break;

            case 2:
                txtDate.setText("Março " + yearSelected);
                break;

            case 3:
                txtDate.setText("Abril " + yearSelected);
                break;

            case 4:
                txtDate.setText("Maio " + yearSelected);
                break;

            case 5:
                txtDate.setText("Junho " + yearSelected);
                break;

            case 6:
                txtDate.setText("Julho " + yearSelected);
                break;

            case 7:
                txtDate.setText("Agosto " + yearSelected);
                break;

            case 8:
                txtDate.setText("Setembro " + yearSelected);
                break;

            case 9:
                txtDate.setText("Outubro " + yearSelected);
                break;

            case 10:
                txtDate.setText("Novembro " + yearSelected);
                break;

            case 11:
                txtDate.setText("Dezembro " + yearSelected);
                break;
        }
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

    @FXML
    public void openDialogAddReceita() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/DialogReceita.fxml"));
            DialogPane dialogPane = fxmlLoader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Adicionar nova receita");
            dialog.showAndWait();
            initializeListLancamentos();
            setInfoValues();
            new FadeIn(listViewLancamentos).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openDialogAddDespesa() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/DialogDespesa.fxml"));
            DialogPane dialogPane = fxmlLoader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Adicionar nova despesa");
            dialog.showAndWait();
            initializeListLancamentos();
            setInfoValues();
            new FadeIn(listViewLancamentos).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
