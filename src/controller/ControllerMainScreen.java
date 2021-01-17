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

    int monthSelected = Calendar.getInstance().get((Calendar.MONTH)) + 1;
    int yearSelected = Calendar.getInstance().get(Calendar.YEAR);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setDate();
        initializeListLancamentos();
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
        txtTotalReceita.setText(formatDecimal(totalReceita).trim());
        txtTotalDespesa.setText(formatDecimal(totalDespesa).trim());
        txtTotalSaldo.setText(formatDecimal(totalSaldo).trim());

    }

    public void initializeListLancamentos() {
        try {
            lancamentos.clear();
            listViewLancamentos.getItems().clear();
            lancamentos = SQL.getLaunchByMonth(monthSelected, yearSelected);
            for (Lancamento lanc : lancamentos) {
                listViewLancamentos.getItems().add(lanc);
                listViewLancamentos.setCellFactory(lancamento -> new AdapterListLancamentos());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        new FadeIn(listViewLancamentos).play();
    }

    private void goBackDate() {
        monthSelected--;
        if (monthSelected < 1) {
            monthSelected = 12;
            yearSelected--;
            setDate(monthSelected);
        }
        setDate(monthSelected);
        initializeListLancamentos();
        setInfoValues();
    }

    private void advanceDate() {
        monthSelected++;
        if (monthSelected > 12) {
            monthSelected = 1;
            yearSelected++;
            setDate(monthSelected);
        }
        setDate(monthSelected);
        initializeListLancamentos();
        setInfoValues();
    }

    private void setDate(int month) {
        new FadeIn(txtDate).play();

        switch (month) {
            case 1:
                txtDate.setText("JANEIRO " + yearSelected);
                break;

            case 2:
                txtDate.setText("FEVEREIRO " + yearSelected);
                break;

            case 3:
                txtDate.setText("MARÇO " + yearSelected);
                break;

            case 4:
                txtDate.setText("ABRIL " + yearSelected);
                break;

            case 5:
                txtDate.setText("MAIO " + yearSelected);
                break;

            case 6:
                txtDate.setText("JUNHO " + yearSelected);
                break;

            case 7:
                txtDate.setText("JULHO " + yearSelected);
                break;

            case 8:
                txtDate.setText("AGOSTO " + yearSelected);
                break;

            case 9:
                txtDate.setText("SETEMBRO " + yearSelected);
                break;

            case 10:
                txtDate.setText("OUTUBRO " + yearSelected);
                break;

            case 11:
                txtDate.setText("NOVEMBRO " + yearSelected);
                break;

            case 12:
                txtDate.setText("DEZEMBRO " + yearSelected);
                break;
        }
    }

    private void setDate() {
        new FadeIn(txtDate).play();
        switch (monthSelected) {

            case 1:
                txtDate.setText("JANEIRO " + yearSelected);
                break;

            case 2:
                txtDate.setText("FEVEREIRO " + yearSelected);
                break;

            case 3:
                txtDate.setText("MARÇO " + yearSelected);
                break;

            case 4:
                txtDate.setText("ABRIL " + yearSelected);
                break;

            case 5:
                txtDate.setText("MAIO " + yearSelected);
                break;

            case 6:
                txtDate.setText("JUNHO " + yearSelected);
                break;

            case 7:
                txtDate.setText("JULHO " + yearSelected);
                break;

            case 8:
                txtDate.setText("AGOSTO " + yearSelected);
                break;

            case 9:
                txtDate.setText("SETEMBRO " + yearSelected);
                break;

            case 10:
                txtDate.setText("OUTUBRO " + yearSelected);
                break;

            case 11:
                txtDate.setText("NOVEMBRO " + yearSelected);
                break;

            case 12:
                txtDate.setText("DEZEMBRO " + yearSelected);
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
