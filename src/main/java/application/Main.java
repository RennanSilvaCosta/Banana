package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Categoria;
import model.Lancamento;
import model.Parcela;
import model.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("banana");
        EntityManager em = emf.createEntityManager();

        Usuario user = em.find(Usuario.class, 1L);
        System.out.println(user.getNome());
        System.out.println(user.getEmail());

        Lancamento teste = new Lancamento();

        for (Lancamento lanc : user.getLancamentos()) {
            System.out.println(lanc.getTitulo());
            System.out.println(lanc.getValor());
            System.out.println(lanc.getDescricao());
            teste = lanc;
        }

        for (Parcela parcela : teste.getParcelas()) {
            System.out.println(parcela.getId());
            System.out.println(parcela.getValorParcela());
            System.out.println(parcela.getDateVencimento());
        }
        Categoria cat = em.find(Categoria.class, 1L);

        System.out.println(cat.getId());
        System.out.println(cat.getDescricao());

        em.close();
        emf.close();

        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.getIcons().add(new Image("/icons/icon_banana.png"));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        scene.getRoot().requestFocus();

        scene.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        scene.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
            primaryStage.setOpacity(0.7);
        });
        scene.setOnMouseReleased(mouseEvent -> primaryStage.setOpacity(1));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
