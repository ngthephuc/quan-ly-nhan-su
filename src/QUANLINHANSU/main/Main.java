package QUANLINHANSU.main;

import QUANLINHANSU.util.SessionManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader;

        if (SessionManager.isLoggedIn()) {
            loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
        } else {
            loader = new FXMLLoader(getClass().getResource("/fxml/scene_login.fxml"));
        }

        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("Quản Lý Nhân Sự");
        stage.setResizable(false);
        stage.show();








//        FXMLLoader loader = new FXMLLoader(
//                getClass().getResource("/fxml/MainView.fxml")
//        );
//
//        Scene scene = new Scene(loader.load());
//
//        stage.setTitle("Quản Lý Nhân Sự");
//        stage.setScene(scene);
//        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}