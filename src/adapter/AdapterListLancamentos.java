package adapter;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import model.Lancamento;
import model.enums.LaunchType;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static util.Helper.formatDecimal;

public class AdapterListLancamentos extends ListCell<Lancamento> {

    private FXMLLoader mLLoader;

    @FXML
    private GridPane gridPane;

    @FXML
    Label txtTitle, txtCategory, txtValue, txtParcel;

    @FXML
    ImageView imgCategory, img1, img2;

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

            setIconCategory(lancamento);
            setBackgroundColor(lancamento);
            isFixed(lancamento);
            isParcel(lancamento);
            isPaid(lancamento);

            txtTitle.setText(lancamento.getTitle());
            txtCategory.setText(lancamento.getCategory());
            txtValue.setText(formatDecimal(lancamento.getValue()).trim());

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
                txtValue.setTextFill(Paint.valueOf("#64B86C"));
                break;
            case DESPESA:
                gridPane.setStyle("-fx-background-color: #FDEDED;");
                txtValue.setTextFill(Paint.valueOf("#C80000"));
        }
    }

    private void isFixed(Lancamento lancamento) {
        try {
            if (lancamento.isFixed() && lancamento.getType().equals(LaunchType.RECEITA)) {
                img1.setVisible(true);
                img1.setImage(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\receita\\icon_fixed_receita.png")));
            } else if (lancamento.isFixed() && lancamento.getType().equals(LaunchType.DESPESA)) {
                img1.setVisible(true);
                img1.setImage(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\despesa\\icon_fixed_despesa.png")));
            } else {
                img1.setVisible(false);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void isParcel(Lancamento lancamento) {
        if (lancamento.getTotalParcelas() > 0) {
            txtParcel.setText(lancamento.getParcelas() + "/" + lancamento.getTotalParcelas());
        } else {
            txtParcel.setText("");
        }
    }

    private void isPaid(Lancamento lancamento) {
        try {
            if (lancamento.isPaid() && lancamento.isFixed()) {
                img2.setVisible(true);
                img2.setImage(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\banana\\src\\icons\\icon_paid_status.png")));
            } else {
                img2.setVisible(false);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
