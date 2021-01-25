package controller;

import adapter.AdapterListLancamentos;
import animatefx.animation.FadeIn;
import animatefx.animation.SlideInDown;
import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideInRight;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import dao.SQL;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import model.Lancamento;
import model.enums.LaunchType;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import static util.Helper.formatDecimal;
import static util.Helper.teste;

public class ControllerMainScreen implements Initializable {

    @FXML
    JFXButton btnAddDespesa, btnAddReceita, btnClose = new JFXButton();

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

        try {
            btnAddReceita.setGraphic(new ImageView(new Image( new FileInputStream("C:/Users/renna/IdeaProjects/banana/src/icons/icon_seta_cima.png"))));
            btnAddReceita.setGraphicTextGap(-5);
            btnAddDespesa.setGraphic(new ImageView(new Image( new FileInputStream("C:/Users/renna/IdeaProjects/banana/src/icons/icon_seta_baixo.png"))));
            btnAddDespesa.setGraphicTextGap(-5);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

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

        btnClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        teste(LaunchType.DESPESA);

    }

    private void setInfoValues() {
        new FadeIn(txtTotalReceita).play();
        new FadeIn(txtTotalDespesa).play();
        new FadeIn(txtTotalSaldo).play();
        new SlideInDown(txtTotalReceita).setSpeed(1).play();
        new SlideInDown(txtTotalDespesa).setSpeed(1).play();
        new SlideInDown(txtTotalSaldo).setSpeed(1).play();
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
        new FadeIn(listViewLancamentos).setSpeed(2).play();
    }

    private void goBackDate() {
        monthSelected--;
        if (monthSelected < 1) {
            monthSelected = 12;
            yearSelected--;
            setDate(monthSelected);
        }
        setDate(monthSelected);
        new SlideInLeft(txtDate).setSpeed(2).play();
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
        new SlideInRight(txtDate).setSpeed(2).play();
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

    @FXML
    public void openDialogAddReceita() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/DialogReceita.fxml"));
            DialogPane dialogPane = fxmlLoader.load();
            Dialog<ButtonType> dialog = new Dialog<>();

            dialog.initStyle(StageStyle.UNIFIED);
            dialog.initModality(Modality.APPLICATION_MODAL);
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
