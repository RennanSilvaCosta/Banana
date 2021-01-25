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

public class ControllerDespesaScreen implements Initializable {

    @FXML
    JFXButton btnSaveExpense;

    @FXML
    CurrencyField editTextValueExpense;

    @FXML
    JFXTextField editTextDescriptionExpense;

    @FXML
    Label lbStatusPay, lbDateThisMonth, lbDateNextMonth, lbStatusFixedExpense, lbRepeatStatusExpense, labelCategorias;

    @FXML
    JFXCheckBox checkBoxExpensePaid, checkBoxExpenseFixed, checkBoxExpenseRepeat;

    @FXML
    DatePicker dateExpense;

    @FXML
    ComboBox<Label> cbCategoryExpense = new ComboBox<>();

    Lancamento lancamento = new Lancamento();

    Calendar calendar = Calendar.getInstance();

    int monthLaunch = 0;
    int yearLaunch = 0;
    boolean paid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initializeComboboxCategorias();

        try {
            btnSaveExpense.setGraphic(new ImageView(new Image( new FileInputStream("C:/Users/renna/IdeaProjects/banana/src/icons/icon_save_launch_32px.png"))));
            btnSaveExpense.setGraphicTextGap(-5);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        lbDateThisMonth.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if (lbDateThisMonth.getStyle().equals("-fx-background-radius: 20 20 20 20; -fx-background-color: #D9D9D9;")) {
                    lbDateThisMonth.setStyle("-fx-background-color: #D25046; -fx-text-fill: white; -fx-background-radius: 20 20 20 20;");
                    lbDateNextMonth.setStyle("-fx-background-radius: 20 20 20 20; -fx-background-color: #D9D9D9;");
                    dateExpense.setValue(null);
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
                    lbDateNextMonth.setStyle("-fx-background-color: #D25046; -fx-text-fill: white; -fx-background-radius: 20 20 20 20;");
                    lbDateThisMonth.setStyle("-fx-background-radius: 20 20 20 20; -fx-background-color: #D9D9D9;");
                    dateExpense.setValue(null);
                    lbDateNextMonth.requestFocus();
                    monthLaunch = calendar.get(Calendar.MONTH) + 2;
                    yearLaunch = Calendar.getInstance().get(Calendar.YEAR);
                }else{
                    lbDateNextMonth.setStyle("-fx-background-radius: 20 20 20 20; -fx-background-color: #D9D9D9;");
                }
            }
        });

        dateExpense.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                lbDateThisMonth.setStyle("-fx-background-radius: 20 20 20 20; -fx-background-color: #D9D9D9;");
                lbDateNextMonth.setStyle("-fx-background-radius: 20 20 20 20; -fx-background-color: #D9D9D9;");
                monthLaunch = calendar.get(Calendar.MONTH) +1;
                yearLaunch = Calendar.getInstance().get(Calendar.YEAR);
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
        try {
            if (lancamento.isFixed()) {
                saveExpenseFixed();
            } else if (lancamento.getTotalParcelas() != null && lancamento.getTotalParcelas() > 0 ) {
                saveExpenseParcelada();
            } else {
                saveExpense();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void saveExpense() throws SQLException {
        lancamento.setTitle(editTextDescriptionExpense.getText());
        lancamento.setValue(editTextValueExpense.getAmount());
        lancamento.setPaid(paid);
        lancamento.setType(LaunchType.DESPESA);
        lancamento.setRecurrence(LauchRecurrence.SEM_RECORRENCIA);
        lancamento.setParcelas(0);
        lancamento.setTotalParcelas(0);
        lancamento.setMonth(monthLaunch);
        lancamento.setYear(yearLaunch);
        lancamento.setCategory(cbCategoryExpense.getSelectionModel().getSelectedItem().getText());
        SQL.saveLauch(lancamento);
        close();
    }

    private void saveExpenseFixed() throws SQLException {
        lancamento.setTitle(editTextDescriptionExpense.getText());
        lancamento.setPaid(paid);
        lancamento.setType(LaunchType.DESPESA);
        lancamento.setRecurrence(LauchRecurrence.SEM_RECORRENCIA);
        lancamento.setCategory(cbCategoryExpense.getSelectionModel().getSelectedItem().getText());
        lancamento.setParcelas(0);
        lancamento.setTotalParcelas(0);
        lancamento.setMonth(monthLaunch);
        lancamento.setYear(yearLaunch);
        lancamento.setValue(editTextValueExpense.getAmount());
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

    private void saveExpenseParcelada() throws SQLException {
        lancamento.setValue(editTextValueExpense.getAmount() / lancamento.getTotalParcelas());
        lancamento.setTitle(editTextDescriptionExpense.getText());
        lancamento.setPaid(paid);
        lancamento.setType(LaunchType.DESPESA);
        lancamento.setCategory(cbCategoryExpense.getSelectionModel().getSelectedItem().getText());
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

    @FXML
    public void statusPay() {
        if (checkBoxExpensePaid.isSelected()){
            lbStatusPay.setText("Recebido");
            paid = true;
        } else {
            lbStatusPay.setText("Não recebido");
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

            lancamento = controller.setLaunch();

            Lancamento lanc = controller.setLaunch();
            if (lanc != null){
                lancamento.setTotalParcelas(lanc.getTotalParcelas());
                lancamento.setRecurrence(lanc.getRecurrence());
                lancamento.setValue(lanc.getValue());
                lbRepeatStatusExpense.setText(lancamento.getTotalParcelas() + " parcelas de " + formatDecimal(lancamento.getValue()).trim());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void initializeComboboxCategorias() {
        Map<String, String> categorias = new HashMap<>();

        categorias.put("Alimentação", "C:/Users/renna/IdeaProjects/banana/src/icons/despesa/combobox/icon_alimentacao_24px.png");
        categorias.put("Assinaturas e serviços", "C:/Users/renna/IdeaProjects/banana/src/icons/despesa/combobox/icon_assinatura_servicos_24px.png");
        categorias.put("Bares e restaurantes", "C:/Users/renna/IdeaProjects/banana/src/icons/despesa/combobox/icon_bares_restaurantes_24px.png");
        categorias.put("Casa", "C:/Users/renna/IdeaProjects/banana/src/icons/despesa/combobox/icon_casa_24px.png");
        categorias.put("Compras", "C:/Users/renna/IdeaProjects/banana/src/icons/despesa/combobox/icon_compras_24px.png");
        categorias.put("Cuidados pessoais", "C:/Users/renna/IdeaProjects/banana/src/icons/despesa/combobox/icon_cuidados_pessoais_24px.png");
        categorias.put("Dívidas e empréstimos", "C:/Users/renna/IdeaProjects/banana/src/icons/despesa/combobox/icon_dividas_emprestimos_24px.png");
        categorias.put("Educação", "C:/Users/renna/IdeaProjects/banana/src/icons/despesa/combobox/icon_educacao_24px.png");
        categorias.put("Família e filhos", "C:/Users/renna/IdeaProjects/banana/src/icons/despesa/combobox/icon_familia_24px.png");
        categorias.put("Impostos e Taxas", "C:/Users/renna/IdeaProjects/banana/src/icons/despesa/combobox/icon_impostos_24px.png");
        categorias.put("Investimentos", "C:/Users/renna/IdeaProjects/banana/src/icons/despesa/combobox/icon_investimentos_24px.png");
        categorias.put("Lazer e hobbies", "C:/Users/renna/IdeaProjects/banana/src/icons/despesa/combobox/icon_lazer_24px.png");
        categorias.put("Mercado", "C:/Users/renna/IdeaProjects/banana/src/icons/despesa/combobox/icon_mercado_24px.png");
        categorias.put("Outros", "C:/Users/renna/IdeaProjects/banana/src/icons/despesa/combobox/icon_outros_24px.png");
        categorias.put("Pets", "C:/Users/renna/IdeaProjects/banana/src/icons/despesa/combobox/icon_pets_24px.png");
        categorias.put("Presentes ou doações", "C:/Users/renna/IdeaProjects/banana/src/icons/despesa/combobox/icon_presente_24px.png");
        categorias.put("Roupas", "C:/Users/renna/IdeaProjects/banana/src/icons/despesa/combobox/icon_roupas_24px.png");
        categorias.put("Saúde", "C:/Users/renna/IdeaProjects/banana/src/icons/despesa/combobox/icon_saude_24px.png");
        categorias.put("Trabalho", "C:/Users/renna/IdeaProjects/banana/src/icons/despesa/combobox/icon_trabalho_24px.png");
        categorias.put("Transporte", "C:/Users/renna/IdeaProjects/banana/src/icons/despesa/combobox/icon_transporte_24px.png");
        categorias.put("Viagem", "C:/Users/renna/IdeaProjects/banana/src/icons/despesa/combobox/icon_viagem_24px.png");

        try {
            for (String labelsItemList : categorias.keySet()) {
                labelCategorias = new Label(labelsItemList);
                labelCategorias.setGraphic(new ImageView(new Image(new FileInputStream(categorias.get(labelsItemList)))));
                labelCategorias.setGraphicTextGap(20);
                cbCategoryExpense.getItems().add(labelCategorias);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        Stage stage = (Stage) btnSaveExpense.getScene().getWindow();
        stage.close();
    }

}
