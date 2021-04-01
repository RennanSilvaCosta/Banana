package controller;

import adapter.AdapterListLancamentos;
import animatefx.animation.FadeIn;
import animatefx.animation.SlideInDown;
import animatefx.animation.SlideInLeft;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import dao.SQL;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Launch;
import model.enums.LaunchType;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static dao.SQL.getLaunchParcelsByMonth;
import static util.Helper.formatDecimal;

public class ControllerMainScreen implements Initializable {

    @FXML
    JFXButton btnAddDespesa, btnAddReceita, btnClose = new JFXButton();

    @FXML
    Label txtTotalReceita, txtTotalDespesa, txtTotalSaldo, txtDate;

    @FXML
    JFXListView<Launch> listViewLancamentos = new JFXListView<>();

    List<Launch> lancamentos = new ArrayList<>();

    @FXML
    ImageView imgAvancaMes, imgRetrocedeMes;

    LocalDate dateSelected = LocalDate.now();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDate();
        initializeListLancamentos();
        setInfoValues();

        btnAddReceita.setGraphic(new ImageView(new Image("/icons/icon_seta_cima.png")));
        btnAddReceita.setGraphicTextGap(-5);
        btnAddDespesa.setGraphic(new ImageView(new Image("/icons/icon_seta_baixo.png")));
        btnAddDespesa.setGraphicTextGap(-5);

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

    }

    public void clickItemList() {
        Launch lancamento = listViewLancamentos.getSelectionModel().getSelectedItem();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();

            if (lancamento.getType().toString().equals("RECEITA")) {

                fxmlLoader.setLocation(getClass().getResource("/view/DialogReceita.fxml"));
                DialogPane dialogPane = fxmlLoader.load();
                ControllerReceitaScreen controller = fxmlLoader.getController();

                //controller.teste(lancamento);

                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(dialogPane);
                dialog.setTitle("Editar receita");
                dialog.showAndWait();

            } else {

                fxmlLoader.setLocation(getClass().getResource("/view/DialogDespesa.fxml"));
                DialogPane dialogPane = fxmlLoader.load();
                ControllerDespesaScreen controller = fxmlLoader.getController();
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(dialogPane);
                dialog.setTitle("Editar despesa");
                dialog.showAndWait();

            }

            initializeListLancamentos();
            setInfoValues();
            new FadeIn(listViewLancamentos).play();

        } catch (IOException e) {
            e.printStackTrace();
        }

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
        for (Launch lanc : lancamentos) {
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
            lancamentos = SQL.getLaunchByMonth(dateSelected);

            for (Launch lanc : lancamentos) {
                if (lanc.isParcel()) {
                    lanc = getLaunchParcelsByMonth(lanc, dateSelected);
                    listViewLancamentos.getItems().add(lanc);
                    listViewLancamentos.setCellFactory(lancamento -> new AdapterListLancamentos());
                } else {
                    listViewLancamentos.getItems().add(lanc);
                    listViewLancamentos.setCellFactory(lancamento -> new AdapterListLancamentos());
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        new FadeIn(listViewLancamentos).setSpeed(2).play();
    }

    private void goBackDate() {
        dateSelected = dateSelected.minusMonths(1);
        setDate(dateSelected.getMonth().getValue(), dateSelected.getYear());
        new SlideInLeft(txtDate).setSpeed(2).play();
        initializeListLancamentos();
        setInfoValues();
    }

    private void advanceDate() {
        dateSelected = dateSelected.plusMonths(1);
        setDate(dateSelected.getMonth().getValue(), dateSelected.getYear());
        new SlideInLeft(txtDate).setSpeed(2).play();
        initializeListLancamentos();
        setInfoValues();
    }

    private void setDate(int month, int year) {
        new FadeIn(txtDate).play();
        switch (month) {
            case 1:
                txtDate.setText("JANEIRO " + year);
                break;

            case 2:
                txtDate.setText("FEVEREIRO " + year);
                break;

            case 3:
                txtDate.setText("MARÇO " + year);
                break;

            case 4:
                txtDate.setText("ABRIL " + year);
                break;

            case 5:
                txtDate.setText("MAIO " + year);
                break;

            case 6:
                txtDate.setText("JUNHO " + year);
                break;

            case 7:
                txtDate.setText("JULHO " + year);
                break;

            case 8:
                txtDate.setText("AGOSTO " + year);
                break;

            case 9:
                txtDate.setText("SETEMBRO " + year);
                break;

            case 10:
                txtDate.setText("OUTUBRO " + year);
                break;

            case 11:
                txtDate.setText("NOVEMBRO " + year);
                break;

            case 12:
                txtDate.setText("DEZEMBRO " + year);
                break;
        }
    }

    private void setDate() {
        new FadeIn(txtDate).play();
        switch (dateSelected.getMonth().getValue()) {
            case 1:
                txtDate.setText("JANEIRO " + dateSelected.getYear());
                break;

            case 2:
                txtDate.setText("FEVEREIRO " + dateSelected.getYear());
                break;

            case 3:
                txtDate.setText("MARÇO " + dateSelected.getYear());
                break;

            case 4:
                txtDate.setText("ABRIL " + dateSelected.getYear());
                break;

            case 5:
                txtDate.setText("MAIO " + dateSelected.getYear());
                break;

            case 6:
                txtDate.setText("JUNHO " + dateSelected.getYear());
                break;

            case 7:
                txtDate.setText("JULHO " + dateSelected.getYear());
                break;

            case 8:
                txtDate.setText("AGOSTO " + dateSelected.getYear());
                break;

            case 9:
                txtDate.setText("SETEMBRO " + dateSelected.getYear());
                break;

            case 10:
                txtDate.setText("OUTUBRO " + dateSelected.getYear());
                break;

            case 11:
                txtDate.setText("NOVEMBRO " + dateSelected.getYear());
                break;

            case 12:
                txtDate.setText("DEZEMBRO " + dateSelected.getYear());
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
