package validator;

import exception.StandardError;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class LaunchValidator {

    List<StandardError> errorsList = new ArrayList<>();

    public List<StandardError> launchIsValid(Double value, String description, Label category) {
        errorsList.clear();
        launchValueIsValid(value);
        launchDescriptionIsValid(description);
        launchCategoryIsValid(category);
        return errorsList;
    }


    private void launchValueIsValid(Double value) {
        if (value <= 0) {
            errorsList.add(new StandardError("value", "O valor deve ser maior do que 0"));
        }
    }

    private void launchDescriptionIsValid(String description) {
        if (description.equals("") || description.length() <= 3) {
            errorsList.add(new StandardError("description", "A descrição deve ter no minimo 3 caracteres"));
        }
    }

    private void launchCategoryIsValid(Label category) {
        if (category == null || category.getText().equals("")) {
            errorsList.add(new StandardError("category", "Escolha uma categoria!"));
        }
    }

}
