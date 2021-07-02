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
import model.Installment;
import model.LaunchType;
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

    Launch lancamento = new Launch();
    LaunchValidator validator = new LaunchValidator();

    LocalDate dateRefe = LocalDate.now();

    boolean paid;

    int parcelNumber = 0;

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
        Double value = editTextValueIncome.getAmount();
        String description = editTextTitleIncome.getText();
        Label category = cbCategoryIncome.getSelectionModel().getSelectedItem();
        List<StandardError> errors = validator.launchIsValid(value, description, category);

        if (!errors.isEmpty()) {
            setErrorMessage(errors);
        } else {

            try {
                if (lancamento.getId() != null) {
                    updateReceita();
                } else {
                    if (lancamento.isFixed()) {
                        saveReceitaFixed();
                    } else if (lancamento.isParcel()) {
                        saveReceitaParcelada();
                    } else {
                        saveReceita();
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private void updateReceita() throws SQLException {
        lancamento.setTitle(editTextTitleIncome.getText());
        lancamento.setValue(editTextValueIncome.getAmount());
        // lancamento.setPaid(paid);
        lancamento.setDate(dateRefe);
        // lancamento.setCategory(cbCategoryIncome.getSelectionModel().getSelectedItem().getText());

        if (lancamento.isFixed()) {
            for (int x = 0; x < 12; x++) {
                lancamento.setDate(dateRefe.plusMonths(x));
                SQL.saveLaunch(lancamento);
                //lancamento.setPaid(false);
            }
        }

        SQL.updateLaunch(lancamento);
        close();
    }

    private void saveReceita() throws SQLException {
        lancamento.setTitle(editTextTitleIncome.getText());
        lancamento.setValue(editTextValueIncome.getAmount());
        lancamento.setType(Constantes.TIPO_LANCAMENTO_RECEITA);
        lancamento.setRecurrence(Constantes.RECORRENCIA_LANCAMENTO_SEM_RECORRENCIA);
        lancamento.setDate(dateRefe);
        setCategory();
        setPaid();
        SQL.saveLaunch(lancamento);
        close();
    }

    private void saveReceitaFixed() throws SQLException {
        lancamento.setTitle(editTextTitleIncome.getText());
        //lancamento.setPaid(paid);
        // lancamento.setType(LaunchType.RECEITA);
        //lancamento.setRecurrence(LauchRecurrence.SEM_RECORRENCIA);
        //lancamento.setCategory(cbCategoryIncome.getSelectionModel().getSelectedItem().getText());
        lancamento.setValue(editTextValueIncome.getAmount());

        for (int x = 0; x < 12; x++) {
            lancamento.setDate(dateRefe.plusMonths(x));
            SQL.saveLaunch(lancamento);
            //lancamento.setPaid(false);
        }
        close();
    }

    private void saveReceitaParcelada() throws SQLException {
        lancamento.setValue(editTextValueIncome.getAmount());
        lancamento.setTitle(editTextTitleIncome.getText());
        lancamento.setType(Constantes.TIPO_LANCAMENTO_RECEITA);
        lancamento.setRecurrence(Constantes.RECORRENCIA_LANCAMENTO_SEM_RECORRENCIA);
        setCategory();
        lancamento.setDate(dateRefe);

        double valuePrestacao = editTextValueIncome.getAmount() / parcelNumber;

        SQL.saveLaunch(lancamento);

        int idLastLaunch = SQL.getIdLastLaunch();

        Installment installment = new Installment();
        //prestacao.setPaidPrestacao(paid);

        for (int x = 0; x < parcelNumber; x++) {
            installment.setDate(dateRefe.plusMonths(x));
            installment.setValue(valuePrestacao);
            installment.setIdLancamento(idLastLaunch);
            SQL.saveParcel(installment);
            //prestacao.setPaidPrestacao(false);

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
            lancamento.setParcel(true);
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
        String category = cbCategoryIncome.getSelectionModel().getSelectedItem().getText();
        if (category.equals("Salario")) {
            lancamento.setCategory(1);
        } else if (category.equals("Investimentos")) {
            lancamento.setCategory(2);
        } else if (category.equals("Emprestimos")) {
            lancamento.setCategory(3);
        } else if (category.equals("Outras receitas")) {
            lancamento.setCategory(4);
        }
    }

    private void setPaid() {
        if (paid) {
            lancamento.setPaid(Constantes.STATUS_PAGAMENTO_RECEBIDO);
        } else {
            lancamento.setPaid(Constantes.STATUS_PAGAMENTO_NAO_RECEBIDO);
        }
    }

    public void close() {
        Stage stage = (Stage) btnSaveReceita.getScene().getWindow();
        stage.close();
    }
}
