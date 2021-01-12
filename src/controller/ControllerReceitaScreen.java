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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

public class ControllerReceitaScreen implements Initializable {

    @FXML
    CurrencyField txtValorReceita = new CurrencyField();

    @FXML
    JFXComboBox<Label> comboBoxCategoriasReceita = new JFXComboBox<Label>();

    @FXML
    Label labelCategorias, txtErrorTitleReceita, txtErrorDataReceita, txtErrorValorReceita, txtErrorCategoriaReceita;

    @FXML
    JFXButton btnRepetirReceita, btnAnexarReceita, btnSaveReceita;

    @FXML
    TextField txtTituloReceita, txtDescricaoReceita, txtObservacaoReceita;

    @FXML
    DatePicker txtDataReceita;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeComboboxCategorias();
        initializeButtons();

        btnSaveReceita.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                saveReceita();
            }
        });

    }

    private void initializeButtons() {
        try {
            btnAnexarReceita.setGraphic(new ImageView(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\addreceita\\icon_receita_anexar.png"))));
            btnRepetirReceita.setGraphic(new ImageView(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\addreceita\\icon_receita_repetir.png"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveReceita() {
        try {
            Lancamento lancamento = new Lancamento();
            lancamento.setTitle(txtTituloReceita.getText());
            lancamento.setDescription(txtDescricaoReceita.getText());
            lancamento.setNote(txtObservacaoReceita.getText());
            lancamento.setValue(txtValorReceita.getAmount());
            lancamento.setDate(txtDataReceita.getValue());
            lancamento.setType(LaunchType.RECEITA);
            lancamento.setRecurrence(LauchRecurrence.SEM_RECORRENCIA);
            lancamento.setParcelas(0);
            SQL.saveReceita(lancamento);

            close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void initializeComboboxCategorias() {
        Map<String, String> categorias = new HashMap<>();

        categorias.put("Emprestimos", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\addreceita\\combobox\\icon_emprestimos_24px.png");
        categorias.put("Salario", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\addreceita\\combobox\\icon_salario_24px.png");
        categorias.put("Investimentos", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\addreceita\\combobox\\icon_investimentos_24px.png");
        categorias.put("Outras receitas", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\addreceita\\combobox\\icon_outras_receitas_24px.png");

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
