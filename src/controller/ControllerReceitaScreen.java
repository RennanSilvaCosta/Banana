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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;
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

    Calendar calendar = Calendar.getInstance();

    int monthLaunch = 0;
    int yearLaunch = 0;
    boolean paid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initializeComboBoxCategory();

        try {
            btnSaveReceita.setGraphic(new ImageView(new Image( new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\icon_save_launch_32px.png"))));
            btnSaveReceita.setGraphicTextGap(-5);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        lbDateThisMonth.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if (lbDateThisMonth.getStyle().equals("-fx-background-radius: 20 20 20 20; -fx-background-color: #D9D9D9;")) {
                    lbDateThisMonth.setStyle("-fx-background-color: #64B86C; -fx-text-fill: white; -fx-background-radius: 20 20 20 20;");
                    lbDateNextMonth.setStyle("-fx-background-radius: 20 20 20 20; -fx-background-color: #D9D9D9;");
                    dateIncome.setValue(null);
                    lbDateThisMonth.requestFocus();
                    monthLaunch = calendar.get(Calendar.MONTH) +1;
                    yearLaunch = Calendar.getInstance().get(Calendar.YEAR);
                }else{
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
                    monthLaunch = calendar.get(Calendar.MONTH) + 2;
                    yearLaunch = Calendar.getInstance().get(Calendar.YEAR);
                }else{
                    lbDateNextMonth.setStyle("-fx-background-radius: 20 20 20 20; -fx-background-color: #D9D9D9;");
                }
            }
        });

        dateIncome.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                lbDateThisMonth.setStyle("-fx-background-radius: 20 20 20 20; -fx-background-color: #D9D9D9;");
                lbDateNextMonth.setStyle("-fx-background-radius: 20 20 20 20; -fx-background-color: #D9D9D9;");
                monthLaunch = calendar.get(Calendar.MONTH) +1;
                yearLaunch = Calendar.getInstance().get(Calendar.YEAR);
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
            } else if (lancamento.getTotalParcelas() != null && lancamento.getTotalParcelas() > 0 ) {
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
        lancamento.setParcelas(0);
        lancamento.setTotalParcelas(0);
        lancamento.setMonth(monthLaunch);
        lancamento.setYear(yearLaunch);
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
        lancamento.setMonth(monthLaunch);
        lancamento.setYear(yearLaunch);
        lancamento.setValue(editTextValueIncome.getAmount());
        int month = lancamento.getMonth();
        int year = 1;

        for (int cont = 0; cont < 12; cont++) {
            if (month > 12) {
                month = 1;
                lancamento.setYear(calendar.get(Calendar.YEAR) + year);
                lancamento.setMonth(month);
                SQL.saveLauch(lancamento);
                lancamento.setPaid(false);
                month++;
                year++;
            } else {
                lancamento.setMonth(month);
                lancamento.setYear(calendar.get(Calendar.YEAR) + (year - 1));
                SQL.saveLauch(lancamento);
                lancamento.setPaid(false);
                month++;
            }
        }
        close();
    }

    private void saveReceitaParcelada() throws SQLException {
        lancamento.setValue(editTextValueIncome.getAmount() / lancamento.getTotalParcelas());
        lancamento.setTitle(editTextDescriptionIncome.getText());
        lancamento.setPaid(paid);
        lancamento.setType(LaunchType.RECEITA);
        lancamento.setCategory(cbCategoryIncome.getSelectionModel().getSelectedItem().getText());
        lancamento.setMonth(monthLaunch);
        lancamento.setYear(yearLaunch);
        int month = lancamento.getMonth();
        int year = 1;
        int parcelas = 1;
        for (int cont = 0; cont < lancamento.getTotalParcelas(); cont++) {
            if (month > 12) {
                month = 1;
                lancamento.setYear(calendar.get(Calendar.YEAR) + year);
                lancamento.setMonth(month);
                lancamento.setParcelas(parcelas);
                SQL.saveLauch(lancamento);
                lancamento.setPaid(false);
                month++;
                year++;
                parcelas++;
            } else {
                lancamento.setMonth(month);
                lancamento.setYear(calendar.get(Calendar.YEAR) + (year - 1));
                lancamento.setParcelas(parcelas);
                SQL.saveLauch(lancamento);
                lancamento.setPaid(false);
                month++;
                parcelas++;
            }
        }
        close();
    }

    private void initializeComboBoxCategory() {
        Map<String, String> categorias = new HashMap<>();

        categorias.put("Emprestimos", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\receita\\combobox\\icon_emprestimos_24px.png");
        categorias.put("Salario", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\receita\\combobox\\icon_salario_24px.png");
        categorias.put("Investimentos", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\receita\\combobox\\icon_investimentos_24px.png");
        categorias.put("Outras receitas", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\receita\\combobox\\icon_outras_receitas_24px.png");

        try {
            for (String labelsItemList : categorias.keySet()) {
                labelCategorias = new Label(labelsItemList);
                labelCategorias.setGraphic(new ImageView(new Image(new FileInputStream(categorias.get(labelsItemList)))));
                labelCategorias.setGraphicTextGap(20);
                cbCategoryIncome.getItems().add(labelCategorias);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void statusPay() {
        if (checkBoxIncomePaid.isSelected()){
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
            if (lanc != null){
                lancamento.setTotalParcelas(lanc.getTotalParcelas());
                lancamento.setRecurrence(lanc.getRecurrence());
                lancamento.setValue(lanc.getValue());
                lbRepeatStatusIncome.setText(lancamento.getTotalParcelas() + " parcelas de R$: " + formatDecimal(lancamento.getValue()).trim());
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
