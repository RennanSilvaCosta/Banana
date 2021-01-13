package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import dao.SQL;
import helper.CurrencyField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ControllerDespesaScreen implements Initializable {

    @FXML
    JFXComboBox<Label> comboBoxCategoryDespesa = new JFXComboBox<Label>();

    @FXML
    Label labelDespesa;

    @FXML
    CurrencyField txtValueDespesa = new CurrencyField();

    @FXML
    JFXButton btnRepetirDespesa, btnAnexarDespesa, btnSalvarDespesa;

    @FXML
    TextField txtTitleDespesa, txtDescriptionDespesa, txtObsDespesa;

    @FXML
    DatePicker txtDataDespesa;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeComboboxCategorias();
        initializeButtons();

        btnSalvarDespesa.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                saveDespesa();
            }
        });

    }

    private void initializeComboboxCategorias() {
        Map<String, String> categorias = new HashMap<>();

        categorias.put("Alimentação", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\combobox\\icon_alimentacao_24px.png");
        categorias.put("Assinaturas e serviços", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\combobox\\icon_assinatura_servicos_24px.png");
        categorias.put("Bares e restaurantes", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\combobox\\icon_bares_restaurantes_24px.png");
        categorias.put("Casa", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\combobox\\icon_casa_24px.png");
        categorias.put("Compras", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\combobox\\icon_compras_24px.png");
        categorias.put("Cuidados pessoais", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\combobox\\icon_cuidados_pessoais_24px.png");
        categorias.put("Dívidas e empréstimos", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\combobox\\icon_dividas_emprestimos_24px.png");
        categorias.put("Educação", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\combobox\\icon_educacao_24px.png");
        categorias.put("Família e filhos", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\combobox\\icon_familia_24px.png");
        categorias.put("Impostos e Taxas", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\combobox\\icon_impostos_24px.png");
        categorias.put("Investimentos", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\combobox\\icon_investimentos_24px.png");
        categorias.put("Lazer e hobbies", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\combobox\\icon_lazer_24px.png");
        categorias.put("Mercado", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\combobox\\icon_mercado_24px.png");
        categorias.put("Outros", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\combobox\\icon_outros_24px.png");
        categorias.put("Pets", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\combobox\\icon_pets_24px.png");
        categorias.put("Presentes ou doações", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\combobox\\icon_presente_24px.png");
        categorias.put("Roupas", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\combobox\\icon_roupas_24px.png");
        categorias.put("Saúde", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\combobox\\icon_saude_24px.png");
        categorias.put("Trabalho", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\combobox\\icon_trabalho_24px.png");
        categorias.put("Transporte", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\combobox\\icon_transporte_24px.png");
        categorias.put("Viagem", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\combobox\\icon_viagem_24px.png");

        try {
            for (String labelsItemList : categorias.keySet()) {
                labelDespesa = new Label(labelsItemList);
                labelDespesa.setGraphic(new ImageView(new Image(new FileInputStream(categorias.get(labelsItemList)))));
                labelDespesa.setGraphicTextGap(20);
                comboBoxCategoryDespesa.getItems().add(labelDespesa);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initializeButtons() {
        try {
            btnAnexarDespesa.setGraphic(new ImageView(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\receita\\icon_receita_anexar.png"))));
            btnRepetirDespesa.setGraphic(new ImageView(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\receita\\icon_receita_repetir.png"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveDespesa() {
        try {
            Lancamento lancamento = new Lancamento();
            lancamento.setTitle(txtTitleDespesa.getText());
            lancamento.setDescription(txtDescriptionDespesa.getText());
            lancamento.setNote(txtObsDespesa.getText());
            lancamento.setValue(txtValueDespesa.getAmount());
            lancamento.setDate(txtDataDespesa.getValue());
            lancamento.setType(LaunchType.DESPESA);
            lancamento.setRecurrence(LauchRecurrence.SEM_RECORRENCIA);
            lancamento.setParcelas(0);
            lancamento.setCategory(comboBoxCategoryDespesa.getSelectionModel().getSelectedItem().getText());
            SQL.saveLauch(lancamento);
            close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void close() {
        Stage stage = (Stage) btnSalvarDespesa.getScene().getWindow();
        stage.close();
    }

}
