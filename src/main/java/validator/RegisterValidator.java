package validator;

import exception.StandardError;
import util.Constantes;

import java.util.ArrayList;
import java.util.List;

public class RegisterValidator {

    public static List<StandardError> formeRegisterValidate(String userName, String userEmail) {
        List<StandardError> errorsList = new ArrayList<>();

        if (userName.length() <= 3 | userName.isEmpty()) {
            errorsList.add(new StandardError("userName", "O nome de usuario deve conter no minimo 4 caracteres!"));
        }

        if (!userEmail.matches(Constantes.regexEmail)) {
            errorsList.add(new StandardError("userEmail", "O email informado não é valido!"));
        }

        return errorsList;
    }
}
