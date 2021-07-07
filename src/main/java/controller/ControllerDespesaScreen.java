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
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import validator.LaunchValidator;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ControllerDespesaScreen implements Initializable {

    @FXML
    JFXButton btnSaveExpense;

    @FXML
    ImageView imgCategory, imgDescription;

    @FXML
    CurrencyField editTextValueExpense;

    @FXML
    JFXTextField editTextTitleExpense;

    @FXML
    Label lbStatusPay, lbDateThisMonth, lbDateNextMonth, lbStatusFixedExpense, lbRepeatStatusExpense, labelCategorias, txtTitleValue;

    @FXML
    JFXCheckBox checkBoxExpensePaid, checkBoxExpenseFixed, checkBoxExpenseRepeat;

    @FXML
    DatePicker dateExpense;

    @FXML
    ComboBox<Label> cbCategoryExpense = new ComboBox<>();

    LaunchValidator validator = new LaunchValidator();
    LocalDate dateRefe = LocalDate.now();

    int parcelNumber = 0;
    boolean paid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //initializeComboboxCategorias();

        btnSaveExpense.setGraphic(new ImageView(new Image("/icons/icon_save_launch_32px.png")));
        btnSaveExpense.setGraphicTextGap(-5);

        lbDateThisMonth.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (lbDateThisMonth.getStyle().equals("-fx-background-radius: 20 20 20 20; -fx-background-color: #D9D9D9;")) {
                    lbDateThisMonth.setStyle("-fx-background-color: #D25046; -fx-text-fill: white; -fx-background-radius: 20 20 20 20;");
                    lbDateNextMonth.setStyle("-fx-background-radius: 20 20 20 20; -fx-background-color: #D9D9D9;");
                    dateExpense.setValue(null);
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
                    lbDateNextMonth.setStyle("-fx-background-color: #D25046; -fx-text-fill: white; -fx-background-radius: 20 20 20 20;");
                    lbDateThisMonth.setStyle("-fx-background-radius: 20 20 20 20; -fx-background-color: #D9D9D9;");
                    dateExpense.setValue(null);
                    lbDateNextMonth.requestFocus();
                    dateRefe = dateRefe.plusMonths(1);
                } else {
                    lbDateNextMonth.setStyle("-fx-background-radius: 20 20 20 20; -fx-background-color: #D9D9D9;");
                }
            }
        });

        dateExpense.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                lbDateThisMonth.setStyle("-fx-background-radius: 20 20 20 20; -fx-background-color: #D9D9D9;");
                lbDateNextMonth.setStyle("-fx-background-radius: 20 20 20 20; -fx-background-color: #D9D9D9;");
                dateRefe = dateExpense.getValue();
            }
        });

        btnSaveExpense.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                saveLaunch();
            }
        });
    }

    private void saveLaunch() {
        validateAndSaveLaunch();
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
    }

    private void initializeComboboxCategorias() {
        Map<String, String> categorias = new HashMap<>();

        categorias.put("Alimentação", "/icons/despesa/combobox/icon_alimentacao_24px.png");
        categorias.put("Assinaturas e serviços", "/icons/despesa/combobox/icon_assinatura_servicos_24px.png");
        categorias.put("Bares e restaurantes", "/icons/despesa/combobox/icon_bares_restaurantes_24px.png");
        categorias.put("Casa", "/icons/despesa/combobox/icon_casa_24px.png");
        categorias.put("Compras", "/icons/despesa/combobox/icon_compras_24px.png");
        categorias.put("Cuidados pessoais", "/icons/despesa/combobox/icon_cuidados_pessoais_24px.png");
        categorias.put("Dívidas e empréstimos", "/icons/despesa/combobox/icon_dividas_emprestimos_24px.png");
        categorias.put("Educação", "/icons/despesa/combobox/icon_educacao_24px.png");
        categorias.put("Família e filhos", "/icons/despesa/combobox/icon_familia_24px.png");
        categorias.put("Impostos e Taxas", "/icons/despesa/combobox/icon_impostos_24px.png");
        categorias.put("Investimentos", "/icons/despesa/combobox/icon_investimentos_24px.png");
        categorias.put("Lazer e hobbies", "/icons/despesa/combobox/icon_lazer_24px.png");
        categorias.put("Mercado", "/icons/despesa/combobox/icon_mercado_24px.png");
        categorias.put("Outros", "/icons/despesa/combobox/icon_outros_24px.png");
        categorias.put("Pets", "/icons/despesa/combobox/icon_pets_24px.png");
        categorias.put("Presentes ou doações", "/icons/despesa/combobox/icon_presente_24px.png");
        categorias.put("Roupas", "/icons/despesa/combobox/icon_roupas_24px.png");
        categorias.put("Saúde", "/icons/despesa/combobox/icon_saude_24px.png");
        categorias.put("Trabalho", "/icons/despesa/combobox/icon_trabalho_24px.png");
        categorias.put("Transporte", "/icons/despesa/combobox/icon_transporte_24px.png");
        categorias.put("Viagem", "/icons/despesa/combobox/icon_viagem_24px.png");

        for (String labelsItemList : categorias.keySet()) {
            labelCategorias = new Label(labelsItemList);
            labelCategorias.setGraphic(new ImageView(new Image(categorias.get(labelsItemList))));
            labelCategorias.setGraphicTextGap(20);
            cbCategoryExpense.getItems().add(labelCategorias);
        }
    }

    private void validateAndSaveLaunch() {
    }

    private void setErrorMessage(List<StandardError> errors) {
        for (StandardError error : errors) {
            if (error.getField().equals("value")) {
                new Shake(txtTitleValue).play();
                new Shake(editTextValueExpense).play();
            }

            if (error.getField().equals("description")) {
                new Shake(imgDescription).play();
                new Shake(editTextTitleExpense).play();
            }

            if (error.getField().equals("category")) {
                new Shake(imgCategory).play();
                new Shake(cbCategoryExpense).play();
            }
        }
    }

    public void close() {
        Stage stage = (Stage) btnSaveExpense.getScene().getWindow();
        stage.close();
    }

}
