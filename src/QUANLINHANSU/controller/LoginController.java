package QUANLINHANSU.controller;

import QUANLINHANSU.model.TaiKhoan;
import QUANLINHANSU.service.TaiKhoanService;
import QUANLINHANSU.util.JPAUtil;
import QUANLINHANSU.util.SessionManager;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import jakarta.persistence.EntityManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class LoginController {

    @FXML
    private Button buttonreset;

    @FXML
    private Button buttonsigin;

    @FXML
    private PasswordField passwordfiel;

    @FXML
    private TextField textfield;

    @FXML
    void Resetform(ActionEvent event) {
        textfield.setText(null);
        passwordfiel.setText(null);
    }

    private TaiKhoanService service = new TaiKhoanService();

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void Siginform(ActionEvent event) {
        String tk = textfield.getText().trim();;
        String mk = passwordfiel.getText().trim();;

        var user = service.login(tk, mk);

        if (user != null) {
            try {
                // ✅ lưu session
                SessionManager.saveSession(true);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
                Scene scene = new Scene(loader.load());

                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Sai tài khoản hoặc mật khẩu!");
//            System.out.println("Sai tài khoản hoặc mật khẩu!");
        }
    }
}
