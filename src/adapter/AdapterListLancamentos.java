package adapter;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.Lancamento;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AdapterListLancamentos extends ListCell<Lancamento> {

    private FXMLLoader mLLoader;

    @FXML
    private GridPane gridPane;

    @FXML
    Label txtTitle, txtDescription, txtValue;

    @FXML
    JFXButton btnEditar, btnExcluir, btnAnexar;

    @FXML
    ImageView imgCategory;

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

            txtTitle.setText(lancamento.getTitle());
            txtDescription.setText(lancamento.getDescription());
            txtValue.setText("R$" + formatDecimal(lancamento.getValue()));
            setIconCategory(lancamento);

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
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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

    public String formatDecimal(double number) {
        double epsilon = 0.004f; // 4 tenths of a cent
        if (Math.abs(Math.round(number) - number) < epsilon) {
            return String.format("%10.0f", number); // sdb
        } else {
            return String.format("%10.2f", number); // dj_segfault
        }
    }

}
