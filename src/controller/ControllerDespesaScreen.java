package controller;

import animatefx.animation.Shake;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import dao.SQL;
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
import model.Launch;
import model.Prestacao;
import model.enums.LauchRecurrence;
import model.enums.LaunchType;
import validator.LaunchValidator;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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

    Launch lancamento = new Launch();
    LaunchValidator validator = new LaunchValidator();

    LocalDate dateRefe = LocalDate.now();

    int parcelNumber = 0;
    boolean paid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initializeComboboxCategorias();

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

    private void saveExpense() throws SQLException {
        lancamento.setTitle(editTextTitleExpense.getText());
        lancamento.setValue(editTextValueExpense.getAmount());
        lancamento.setPaid(paid);
        lancamento.setDate(dateRefe);
        lancamento.setType(LaunchType.DESPESA);
        lancamento.setRecurrence(LauchRecurrence.SEM_RECORRENCIA);
        lancamento.setCategory(cbCategoryExpense.getSelectionModel().getSelectedItem().getText());
        SQL.saveLaunch(lancamento);
        close();
    }

    private void saveExpenseFixed() throws SQLException {
        lancamento.setTitle(editTextTitleExpense.getText());
        lancamento.setPaid(paid);
        lancamento.setType(LaunchType.DESPESA);
        lancamento.setRecurrence(LauchRecurrence.MENSAL);
        lancamento.setCategory(cbCategoryExpense.getSelectionModel().getSelectedItem().getText());
        lancamento.setValue(editTextValueExpense.getAmount());

        for (int x = 0; x < 12; x++) {
            lancamento.setDate(dateRefe.plusMonths(x));
            SQL.saveLaunch(lancamento);
            lancamento.setPaid(false);
        }
        close();
    }

    private void saveExpenseParcelada() throws SQLException {

        lancamento.setValue(editTextValueExpense.getAmount());
        lancamento.setTitle(editTextTitleExpense.getText());
        lancamento.setType(LaunchType.DESPESA);
        lancamento.setRecurrence(LauchRecurrence.MENSAL);
        lancamento.setCategory(cbCategoryExpense.getSelectionModel().getSelectedItem().getText());
        lancamento.setDate(dateRefe);
        lancamento.setParcel(true);

        double valuePrestacao = editTextValueExpense.getAmount() / parcelNumber;

        SQL.saveLaunch(lancamento);

        int idLastLaunch = SQL.getIdLastLaunch();

        Prestacao prestacao = new Prestacao();
        prestacao.setPaidPrestacao(paid);

        for (int x = 0; x < parcelNumber; x++) {
            prestacao.setDatePrestacao(dateRefe.plusMonths(x));
            prestacao.setValuePrestacao(valuePrestacao);
            prestacao.setIdLancamento(idLastLaunch);
            SQL.saveParcel(prestacao);

        }
        close();
    }

    @FXML
    public void statusPay() {
        if (checkBoxExpensePaid.isSelected()) {
            lbStatusPay.setText("Pago");
            paid = true;
        } else {
            lbStatusPay.setText("Não pago");
            paid = false;
        }
    }

    @FXML
    public void statusFixed() {
        lancamento.setFixed(checkBoxExpenseFixed.isSelected());
        checkBoxExpenseRepeat.setSelected(false);
    }

    @FXML
    public void isRepeat() {
        if (checkBoxExpenseRepeat.isSelected()) {
            checkBoxExpenseFixed.setSelected(false);
            lancamento.setFixed(false);
            lancamento.setParcel(true);
            repeatLaunch();
        } else {
            lbRepeatStatusExpense.setText("Repetir");
        }
    }

    private void repeatLaunch() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/DialogRepeatLaunch.fxml"));
            DialogPane dialogPane = fxmlLoader.load();

            ControllerDialogRepeatLaunch controller = fxmlLoader.getController();
            controller.getInfoValue(editTextValueExpense.getAmount());

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Repetir lançamento");
            dialog.showAndWait();

            parcelNumber = controller.setLaunch();

        } catch (IOException e) {
            e.printStackTrace();
        }
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
        Double value = editTextValueExpense.getAmount();
        String description = editTextTitleExpense.getText();
        Label category = cbCategoryExpense.getSelectionModel().getSelectedItem();

        List<StandardError> errors = validator.launchIsValid(value, description, category);

        if (!errors.isEmpty()) {
            setErrorMessage(errors);
        } else {
            try {
                if (lancamento.isFixed()) {
                    saveExpenseFixed();
                } else if (lancamento.isParcel()) {
                    saveExpenseParcelada();
                } else {
                    saveExpense();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
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
