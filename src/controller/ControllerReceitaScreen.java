package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import dao.SQL;
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
import model.Lancamento;
import model.enums.LauchRecurrence;
import model.enums.LaunchType;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static util.Helper.formatDecimal;

public class ControllerReceitaScreen implements Initializable {

    @FXML
    JFXButton btnSaveReceita;

    @FXML
    CurrencyField editTextValueIncome;

    @FXML
    JFXTextField editTextDescriptionIncome;

    @FXML
    Label lbStatusPay, lbDateThisMonth, lbDateNextMonth, lbStatusFixedIncome, lbRepeatStatusIncome, labelCategorias;

    @FXML
    JFXCheckBox checkBoxIncomePaid, checkBoxIncomeFixed, checkBoxIncomeRepeat;

    @FXML
    DatePicker dateIncome;

    @FXML
    ComboBox<Label> cbCategoryIncome = new ComboBox<>();

    Lancamento lancamento = new Lancamento();
    LocalDate dateRefe = LocalDate.now();

    boolean paid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initializeComboBoxCategory();

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

    private void saveLaunch() {
        try {
            if (lancamento.isFixed()) {
                saveReceitaFixed();
            } else if (lancamento.getTotalParcelas() != null && lancamento.getTotalParcelas() > 0) {
                saveReceitaParcelada();
            } else {
                saveReceita();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void saveReceita() throws SQLException {
        lancamento.setTitle(editTextDescriptionIncome.getText());
        lancamento.setValue(editTextValueIncome.getAmount());
        lancamento.setPaid(paid);
        lancamento.setType(LaunchType.RECEITA);
        lancamento.setRecurrence(LauchRecurrence.SEM_RECORRENCIA);
        lancamento.setDate(dateRefe);
        lancamento.setParcelas(0);
        lancamento.setTotalParcelas(0);
        lancamento.setCategory(cbCategoryIncome.getSelectionModel().getSelectedItem().getText());
        SQL.saveLauch(lancamento);
        close();
    }

    private void saveReceitaFixed() throws SQLException {
        lancamento.setTitle(editTextDescriptionIncome.getText());
        lancamento.setPaid(paid);
        lancamento.setType(LaunchType.RECEITA);
        lancamento.setRecurrence(LauchRecurrence.SEM_RECORRENCIA);
        lancamento.setCategory(cbCategoryIncome.getSelectionModel().getSelectedItem().getText());
        lancamento.setParcelas(0);
        lancamento.setTotalParcelas(0);
        lancamento.setValue(editTextValueIncome.getAmount());

        for (int x = 0; x < 12; x++) {
            lancamento.setDate(dateRefe.plusMonths(x));
            SQL.saveLauch(lancamento);
            lancamento.setPaid(false);
        }

        close();
    }

    private void saveReceitaParcelada() throws SQLException {
        lancamento.setValue(editTextValueIncome.getAmount() / lancamento.getTotalParcelas());
        lancamento.setTitle(editTextDescriptionIncome.getText());
        lancamento.setPaid(paid);
        lancamento.setType(LaunchType.RECEITA);
        lancamento.setCategory(cbCategoryIncome.getSelectionModel().getSelectedItem().getText());
        int auxParcel = 1;

        for (int x = 0; x < lancamento.getTotalParcelas(); x++) {
            lancamento.setDate(dateRefe.plusMonths(x));
            lancamento.setParcelas(auxParcel);
            SQL.saveLauch(lancamento);
            lancamento.setPaid(false);
            auxParcel++;
        }

        close();
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
        if (checkBoxIncomePaid.isSelected()) {
            lbStatusPay.setText("Recebido");
            paid = true;
        } else {
            lbStatusPay.setText("Não recebido");
            paid = false;
        }
    }

    @FXML
    public void statusFixed() {
        lancamento.setFixed(checkBoxIncomeFixed.isSelected());
        checkBoxIncomeRepeat.setSelected(false);
    }

    @FXML
    public void isRepeat() {
        if (checkBoxIncomeRepeat.isSelected()) {
            checkBoxIncomeFixed.setSelected(false);
            lancamento.setFixed(false);
            repeatLaunch();
        } else {
            lbRepeatStatusIncome.setText("Repetir");
        }
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

            lancamento = controller.setLaunch();

            Lancamento lanc = controller.setLaunch();
            if (lanc != null) {
                lancamento.setTotalParcelas(lanc.getTotalParcelas());
                lancamento.setRecurrence(lanc.getRecurrence());
                lancamento.setValue(lanc.getValue());
                lbRepeatStatusIncome.setText(lancamento.getTotalParcelas() + " parcelas de " + formatDecimal(lancamento.getValue()).trim());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        Stage stage = (Stage) btnSaveReceita.getScene().getWindow();
        stage.close();
    }
}
