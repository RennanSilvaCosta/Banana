package adapter;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import model.Lancamento;

import java.io.IOException;

public class AdapterListLancamentos extends ListCell<Lancamento> {

    private FXMLLoader mLLoader;

    @FXML
    private GridPane gridPane;

    @FXML
    Label txtTitle, txtDescription, txtValue;

    @FXML
    JFXButton btnEditar, btnExcluir, btnAnexar;

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

            txtTitle.setText(lancamento.getTitle());
            txtDescription.setText(lancamento.getDescription());
            txtValue.setText("R$" + formatDecimal(lancamento.getValue()));
            setText(null);
            setGraphic(gridPane);
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
