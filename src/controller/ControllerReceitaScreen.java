package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import helper.CurrencyField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ControllerReceitaScreen implements Initializable {

    @FXML
    CurrencyField txtValorReceita = new CurrencyField();

    @FXML
    JFXComboBox<Label> comboBoxCategoriasReceita = new JFXComboBox<Label>();

    @FXML
    Label labelCategorias;

    @FXML
    JFXButton btnRepetirReceita, btnAnexarReceita;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeComboboxCategorias();
        initializeButtons();
    }

    private void initializeComboboxCategorias() {
        Map<String, String> categorias = new HashMap<>();

        categorias.put("Alimentação", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\addreceita\\combobox\\icon_alimentacao_24px.png");
        categorias.put("Assinaturas e serviços", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\addreceita\\combobox\\icon_assinatura_servicos_24px.png");
        categorias.put("Bares e restaurantes", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\addreceita\\combobox\\icon_bares_restaurantes_24px.png");
        categorias.put("Casa", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\addreceita\\combobox\\icon_casa_24px.png");
        categorias.put("Compras", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\addreceita\\combobox\\icon_compras_24px.png");
        categorias.put("Cuidados pessoais", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\addreceita\\combobox\\icon_cuidados_pessoais_24px.png");
        categorias.put("Dívidas e empréstimos", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\addreceita\\combobox\\icon_dividas_emprestimos_24px.png");
        categorias.put("Educação", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\addreceita\\combobox\\icon_educacao_24px.png");
        categorias.put("Família e filhos", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\addreceita\\combobox\\icon_familia_24px.png");
        categorias.put("Impostos e Taxas", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\addreceita\\combobox\\icon_impostos_24px.png");
        categorias.put("Investimentos", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\addreceita\\combobox\\icon_investimentos_24px.png");
        categorias.put("Lazer e hobbies", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\addreceita\\combobox\\icon_lazer_24px.png");
        categorias.put("Mercado", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\addreceita\\combobox\\icon_mercado_24px.png");
        categorias.put("Outros", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\addreceita\\combobox\\icon_outros_24px.png");
        categorias.put("Pets", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\addreceita\\combobox\\icon_pets_24px.png");
        categorias.put("Presentes ou doações", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\addreceita\\combobox\\icon_presente_24px.png");
        categorias.put("Roupas", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\addreceita\\combobox\\icon_roupas_24px.png");
        categorias.put("Saúde", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\addreceita\\combobox\\icon_saude_24px.png");
        categorias.put("Trabalho", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\addreceita\\combobox\\icon_trabalho_24px.png");
        categorias.put("Transporte", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\addreceita\\combobox\\icon_transporte_24px.png");
        categorias.put("Viagem", "C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\addreceita\\combobox\\icon_viagem_24px.png");

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

    private void initializeButtons() {
        try {
            btnAnexarReceita.setGraphic(new ImageView(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\addreceita\\icon_receita_anexar.png"))));
            btnRepetirReceita.setGraphic(new ImageView(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\addreceita\\icon_receita_repetir.png"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
