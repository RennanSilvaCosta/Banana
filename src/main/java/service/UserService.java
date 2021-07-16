package service;

import model.Usuario;
import repository.UserRepository;

import java.util.List;

public class UserService {

    private UserRepository repository = new UserRepository();

    public void createNewUser(Usuario user) {

        List<Usuario> usuarioList = repository.thisEmailExist(user.getEmail());

        if (usuarioList.isEmpty()) {
            user.setSaldo(0.0);
            repository.saveUser(user);
        } else {
            System.out.println("O email informado já existe!");
        }

    }


}
