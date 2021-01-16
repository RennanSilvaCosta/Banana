package adapter;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import model.Lancamento;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static util.Helper.formatDecimal;

public class AdapterListLancamentos extends ListCell<Lancamento> {

    private FXMLLoader mLLoader;

    @FXML
    private GridPane gridPane;

    @FXML
    Label txtTitle, txtDescription, txtValue, txtFixed;

    @FXML
    JFXButton btnEditar, btnExcluir, btnAnexar;

    @FXML
    ImageView imgCategory, imgRecurrence;

    @Override
    protected void updateItem(Lancamento lancamento, boolean empty) {
        super.updateItem(lancamento, empty);

        if (empty || lancamento == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/view/adapter/AdapterListLancamentos.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            initializeButtons();
            setIconCategory(lancamento);
            setBackgroundColor(lancamento);
            isFixed(lancamento);
            isParcel(lancamento);

            txtTitle.setText(lancamento.getTitle());
            txtDescription.setText(lancamento.getDescription());
            txtValue.setText("R$" + formatDecimal(lancamento.getValue()));

            setText(null);
            setGraphic(gridPane);
        }
    }

    private void setIconCategory(Lancamento lancamento) {
        try {
            switch (lancamento.getCategory()) {
                case "Salario":
                    imgCategory.setImage(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\receita\\category\\icon_salario_64px.png")));
                    break;
                case "Investimentos":
                    imgCategory.setImage(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\receita\\category\\icon_investimentos_64px.png")));
                    break;
                case "Emprestimos":
                    imgCategory.setImage(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\receita\\category\\icon_emprestimos_64px.png")));
                    break;
                case "Outras receitas":
                    imgCategory.setImage(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\receita\\category\\icon_outras_receitas_64px.png")));
                    break;
                case "Alimentação":
                    imgCategory.setImage(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\category\\icon_alimentacao_64px.png")));
                    break;
                case "Bares e restaurantes":
                    imgCategory.setImage(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\category\\icon_bares_restaurantes_64px.png")));
                    break;
                case "Casa":
                    imgCategory.setImage(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\category\\icon_casa_64px.png")));
                    break;
                case "Compras":
                    imgCategory.setImage(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\category\\icon_compras_64px.png")));
                    break;
                case "Cuidados pessoais":
                    imgCategory.setImage(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\category\\icon_cuidados_pessoais_64px.png")));
                    break;
                case "Dívidas e empréstimos":
                    imgCategory.setImage(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\category\\icon_dividas_emprestimos_64px.png")));
                    break;
                case "Educação":
                    imgCategory.setImage(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\category\\icon_educacao_64px.png")));
                    break;
                case "Família e filhos":
                    imgCategory.setImage(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\category\\icon_familia_64px.png")));
                    break;
                case "Impostos e Taxas":
                    imgCategory.setImage(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\category\\icon_impostos_64px.png")));
                    break;
                case "Assinaturas e serviços":
                    imgCategory.setImage(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\category\\icon_assinatura_servico_64px.png")));
                    break;
                case "Lazer e hobbies":
                    imgCategory.setImage(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\category\\icon_lazer_64px.png")));
                    break;
                case "Mercado":
                    imgCategory.setImage(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\category\\icon_mercado_64px.png")));
                    break;
                case "Outros":
                    imgCategory.setImage(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\category\\icon_outros_64px.png")));
                    break;
                case "Pets":
                    imgCategory.setImage(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\category\\icon_pets_64px.png")));
                    break;
                case "Presentes ou doações":
                    imgCategory.setImage(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\category\\icon_presente_64px.png")));
                    break;
                case "Roupas":
                    imgCategory.setImage(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\category\\icon_roupas_64px.png")));
                    break;
                case "Saúde":
                    imgCategory.setImage(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\category\\icon_saude_64px.png")));
                    break;
                case "Trabalho":
                    imgCategory.setImage(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\category\\icon_trabalho_64px.png")));
                    break;
                case "Transporte":
                    imgCategory.setImage(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\category\\icon_transporte_64px.png")));
                    break;
                case "Viagem":
                    imgCategory.setImage(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\category\\icon_viagem_64px.png")));
                    break;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setBackgroundColor(Lancamento lancamento) {
        switch (lancamento.getType()) {
            case RECEITA:
                gridPane.setStyle("-fx-background-color: #EDFDF4;");
                txtValue.setTextFill(Paint.valueOf("#56CAA4"));
                break;
            case DESPESA:
                gridPane.setStyle("-fx-background-color: #FDEDED;");
                txtValue.setTextFill(Paint.valueOf("#E06A63"));
        }
    }

    private void initializeButtons() {
        try {
            btnEditar.setGraphic(new ImageView(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\icon_edit_24px.png"))));
            btnExcluir.setGraphic(new ImageView(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\icon_delete_24px.png"))));
            btnAnexar.setGraphic(new ImageView(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\icon_anexar_24px.png"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void isFixed(Lancamento lancamento) {
        try {
            if (lancamento.isFixed()) {
                imgRecurrence.setVisible(true);
                imgRecurrence.setImage(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\icon_repeat_32px.png")));
                txtFixed.setText("Fixa");
            } else {
                imgRecurrence.setVisible(false);
                txtFixed.setText("");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void isParcel(Lancamento lancamento) {
        try {
            if (lancamento.getTotalParcelas() > 0) {
                imgRecurrence.setVisible(true);
                imgRecurrence.setImage(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\icon_credito.png")));
                txtFixed.setText(lancamento.getParcelas() + "/" + lancamento.getTotalParcelas());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
