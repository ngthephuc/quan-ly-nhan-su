package QUANLINHANSU.main;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/NhanVien.fxml")
        );

        Scene scene = new Scene(loader.load());

        stage.setTitle("Test UI");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {

//        EntityManagerFactory emf =
//                Persistence.createEntityManagerFactory("QuanLyNhanSuPU");
//        System.out.println("Ket noi thanh cong!");

        launch();
    }
}