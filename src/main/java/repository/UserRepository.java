package repository;

import model.Usuario;

import javax.persistence.*;
import java.util.List;

public class UserRepository {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("banana");

    public List<Usuario> thisEmailExist(String email) throws NoResultException {
        //JPQl
        EntityManager entityManager = emf.createEntityManager();

        String jpql = "select u from Usuario u where email = :email";

        TypedQuery<Usuario> query = entityManager.createQuery(jpql, Usuario.class).setParameter("email", email);
        List<Usuario> users = query.getResultList();
        entityManager.close();
        return users;
    }

    public void saveUser(Usuario user) {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }


}
