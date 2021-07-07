package controller;

import animatefx.animation.Shake;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import exception.StandardError;
import helper.CurrencyField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.Constantes;
import validator.LaunchValidator;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ControllerReceitaScreen implements Initializable {

    @FXML
    JFXButton btnSaveReceita;

    @FXML
    CurrencyField editTextValueIncome;

    @FXML
    JFXTextField editTextTitleIncome;

    @FXML
    Label lbStatusPay, lbDateThisMonth, lbDateNextMonth, lbStatusFixedIncome, lbRepeatStatusIncome, labelCategorias, txtTitleValue, txtTitleScreen;

    @FXML
    ImageView imgDescription, imgCategory;

    @FXML
    JFXCheckBox checkBoxIncomePaid, checkBoxIncomeFixed, checkBoxIncomeRepeat;

    @FXML
    DatePicker dateIncome;

    @FXML
    ComboBox<Label> cbCategoryIncome = new ComboBox<>();

    LaunchValidator validator = new LaunchValidator();

    LocalDate dateRefe = LocalDate.now();

    boolean paid;

    int parcelNumber = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initializeComboBoxCategory();

        btnSaveReceita.setGraphic(new ImageView(new Image("/icons/icon_save_launch_32px.png")));
        btnSaveReceita.setGraphicTextGap(-5);

        lbDateThisMonth.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (lbDateThisMonth.getStyle().equals("-fx-background-radius: 20 20 20 20; -fx-background-color: #D9D9D9;")) {
                    lbDateThisMonth.setStyle("-fx-background-color: #64B86C; -fx-text-fill: white; -fx-background-radius: 20 20 20 20;");
                    lbDateNextMonth.setStyle("-fx-background-radius: 20 20 20 20; -fx-background-color: #D9D9D9;");
                    dateIncome.setValue(null);
                    lbDateThisMonth.requestFocus();
                    dateRefe = LocalDate.now();
                } else {
                    lbDateThisMonth.setStyle("-fx-background-radius: 20 20 20 20; -fx-background-color: #D9D9D9;");
                }
            }
        });

        lbDateNextMonth.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (lbDateNextMonth.getStyle().equals("-fx-background-radius: 20 20 20 20; -fx-background-color: #D9D9D9;")) {
                    lbDateNextMonth.setStyle("-fx-background-color: #64B86C; -fx-text-fill: white; -fx-background-radius: 20 20 20 20;");
                    lbDateThisMonth.setStyle("-fx-background-radius: 20 20 20 20; -fx-background-color: #D9D9D9;");
                    dateIncome.setValue(null);
                    lbDateNextMonth.requestFocus();
                    dateRefe = dateRefe.plusMonths(1);
                } else {
                    lbDateNextMonth.setStyle("-fx-background-radius: 20 20 20 20; -fx-background-color: #D9D9D9;");
                }
            }
        });

        dateIncome.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                lbDateThisMonth.setStyle("-fx-background-radius: 20 20 20 20; -fx-background-color: #D9D9D9;");
                lbDateNextMonth.setStyle("-fx-background-radius: 20 20 20 20; -fx-background-color: #D9D9D9;");
                dateRefe = dateIncome.getValue();
            }
        });

        btnSaveReceita.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                saveLaunch();
            }
        });
    }

    /*public void teste(Launch l) {
        txtTitleScreen.setText("Editar receita");
        lancamento = l;
        editTextValueIncome.setAmount(l.getValue());
        checkBoxIncomePaid.setSelected(l.isFixed());
        editTextDescriptionIncome.setText(l.getTitle());
        checkBoxIncomeFixed.setSelected(l.isFixed());
        dateRefe = l.getDate();
        if (l.getTotalParcelas() > 0) {
            checkBoxIncomeRepeat.setSelected(true);
        }
        statusPay();
        statusFixed();
        isRepeat();
    }*/

    private void saveLaunch() {
        validateAndSaveLaunch();
    }

    private void validateAndSaveLaunch() {
    }

    private void updateReceita() throws SQLException {
    }

    private void saveReceita() throws SQLException {
        setCategory();
        setPaid();
        close();
    }

    private void saveReceitaFixed() throws SQLException {

    }

    private void saveReceitaParcelada() throws SQLException {
    }

    private void initializeComboBoxCategory() {
        Map<String, String> categorias = new HashMap<>();

        categorias.put("Emprestimos", "/icons/receita/combobox/icon_emprestimos_24px.png");
        categorias.put("Salario", "/icons/receita/combobox/icon_salario_24px.png");
        categorias.put("Investimentos", "/icons/receita/combobox/icon_investimentos_24px.png");
        categorias.put("Outras receitas", "/icons/receita/combobox/icon_outras_receitas_24px.png");

        for (String labelsItemList : categorias.keySet()) {
            labelCategorias = new Label(labelsItemList);
            labelCategorias.setGraphic(new ImageView(new Image(categorias.get(labelsItemList))));
            labelCategorias.setGraphicTextGap(20);
            cbCategoryIncome.getItems().add(labelCategorias);
        }
    }

    @FXML
    public void statusPay() {
    }

    @FXML
    public void statusFixed() {
    }

    @FXML
    public void isRepeat() {

    }

    private void repeatLaunch() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/DialogRepeatLaunch.fxml"));
            DialogPane dialogPane = fxmlLoader.load();

            ControllerDialogRepeatLaunch controller = fxmlLoader.getController();
            controller.getInfoValue(editTextValueIncome.getAmount());

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Repetir lançamento");
            dialog.showAndWait();

            parcelNumber = controller.setLaunch();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setErrorMessage(List<StandardError> errors) {
        for (StandardError error : errors) {
            if (error.getField().equals("value")) {
                new Shake(txtTitleValue).play();
                new Shake(editTextValueIncome).play();
            }

            if (error.getField().equals("description")) {
                new Shake(imgDescription).play();
                new Shake(editTextTitleIncome).play();
            }

            if (error.getField().equals("category")) {
                new Shake(imgCategory).play();
                new Shake(cbCategoryIncome).play();
            }
        }
    }

    private void setCategory() {
    }

    private void setPaid() {
    }

    public void close() {
        Stage stage = (Stage) btnSaveReceita.getScene().getWindow();
        stage.close();
    }
}
