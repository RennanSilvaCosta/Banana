package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.stage.Stage;
import model.Lancamento;
import model.enums.LauchRecurrence;
import model.enums.LaunchType;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static util.Helper.formatDecimal;

public class ControllerReceitaScreen implements Initializable {

    @FXML
    CurrencyField txtValorReceita = new CurrencyField();

    @FXML
    JFXComboBox<Label> comboBoxCategoriasReceita = new JFXComboBox<>();

    @FXML
    Label labelCategorias, txtErrorTitleReceita, txtErrorDataReceita, txtErrorValorReceita, txtErrorCategoriaReceita, txtInfoRepeat;

    @FXML
    JFXButton btnRepetirReceita, btnAnexarReceita, btnSaveReceita;

    @FXML
    TextField txtTituloReceita, txtDescricaoReceita, txtObservacaoReceita;

    @FXML
    DatePicker txtDataReceita;

    Lancamento lancamento = new Lancamento();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeComboboxCategorias();
        initializeButtons();

        btnSaveReceita.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                saveLaunch();
            }
        });

        btnRepetirReceita.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                repeatLaunch();
            }
        });

    }

    private void initializeButtons() {
        try {
            btnAnexarReceita.setGraphic(new ImageView(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\receita\\icon_receita_anexar.png"))));
            btnRepetirReceita.setGraphic(new ImageView(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\receita\\icon_receita_repetir.png"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void repeatLaunch() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/DialogRepeatLaunch.fxml"));
            DialogPane dialogPane = fxmlLoader.load();

            ControllerDialogRepeatLaunch controller = fxmlLoader.getController();
            controller.getInfoValue(txtValorReceita.getAmount());

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Repetir lançamento");
            dialog.showAndWait();

            lancamento = controller.setLaunch();
            setInforRecurrence();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setInforRecurrence() {
        if (lancamento.isFixed()) {
            txtInfoRepeat.setText("Lançamento configurado como fixo. Com recorrencia " + lancamento.getRecurrence());
        } else {
            txtInfoRepeat.setText(lancamento.getTotalParcelas() + " parcelas de R$: " + formatDecimal(lancamento.getValue()));
        }
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
        lancamento.setTitle(txtTituloReceita.getText());
        lancamento.setDescription(txtDescricaoReceita.getText());
        lancamento.setNote(txtObservacaoReceita.getText());
        lancamento.setValue(txtValorReceita.getAmount());
        lancamento.setMonth(txtDataReceita.getValue().getMonthValue());
        lancamento.setYear(txtDataReceita.getValue().getYear());
        lancamento.setType(LaunchType.RECEITA);
        lancamento.setRecurrence(LauchRecurrence.SEM_RECORRENCIA);
        lancamento.setParcelas(0);
        lancamento.setTotalParcelas(0);
        lancamento.setCategory(comboBoxCategoriasReceita.getSelectionModel().getSelectedItem().getText());
        SQL.saveLauch(lancamento);
        close();
    }

    private void saveReceitaFixed() throws SQLException {
        lancamento.setTitle(txtTituloReceita.getText());
        lancamento.setDescription(txtDescricaoReceita.getText());
        lancamento.setNote(txtObservacaoReceita.getText());
        lancamento.setType(LaunchType.RECEITA);
        lancamento.setCategory(comboBoxCategoriasReceita.getSelectionModel().getSelectedItem().getText());
        lancamento.setMonth(txtDataReceita.getValue().getMonthValue());
        lancamento.setYear(txtDataReceita.getValue().getYear());
        lancamento.setParcelas(0);
        lancamento.setTotalParcelas(0);
        int month = txtDataReceita.getValue().getMonthValue();
        int year = 1;

        for (int cont = 0; cont < 12; cont++) {
            if (month > 12) {
                month = 1;
                lancamento.setYear(txtDataReceita.getValue().getYear() + year);
                lancamento.setMonth(month);
                SQL.saveLauch(lancamento);
                month++;
                year++;
            } else {
                lancamento.setMonth(month);
                lancamento.setYear(txtDataReceita.getValue().getYear() + (year - 1));
                SQL.saveLauch(lancamento);
                month++;
            }
        }
        close();
    }

    private void saveReceitaParcelada() throws SQLException {
        lancamento.setValue(txtValorReceita.getAmount() / lancamento.getTotalParcelas());
        lancamento.setTitle(txtTituloReceita.getText());
        lancamento.setDescription(txtDescricaoReceita.getText());
        lancamento.setNote(txtObservacaoReceita.getText());
        lancamento.setType(LaunchType.RECEITA);
        lancamento.setCategory(comboBoxCategoriasReceita.getSelectionModel().getSelectedItem().getText());
        lancamento.setMonth(txtDataReceita.getValue().getMonthValue());
        lancamento.setYear(txtDataReceita.getValue().getYear());
        int month = txtDataReceita.getValue().getMonthValue();
        int year = 1;
        int parcelas = 1;
        for (int cont = 0; cont < lancamento.getTotalParcelas(); cont++) {
            if (month > 12) {
                month = 1;
                lancamento.setYear(txtDataReceita.getValue().getYear() + year);
                lancamento.setMonth(month);
                lancamento.setParcelas(parcelas);
                SQL.saveLauch(lancamento);
                month++;
                year++;
                parcelas++;
            } else {
                lancamento.setMonth(month);
                lancamento.setYear(txtDataReceita.getValue().getYear() + (year - 1));
                lancamento.setParcelas(parcelas);
                SQL.saveLauch(lancamento);
                month++;
                parcelas++;
            }
        }
        close();
    }

    private void initializeComboboxCategorias() {
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
                comboBoxCategoriasReceita.getItems().add(labelCategorias);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        Stage stage = (Stage) btnSaveReceita.getScene().getWindow();
        stage.close();
    }

}
