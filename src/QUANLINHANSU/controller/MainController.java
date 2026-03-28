package QUANLINHANSU.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML private StackPane content;
    @FXML private Label lblRole;

    @FXML private Button butHome, butNhanVien, butPhongBan, butDuAn, butLuong;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 1. Vào thẳng Dashboard ngay khi mở App
        switchHome();

        // 2. Hiện tên Admin mặc định (vì chưa làm phần User)
        if (lblRole != null) {
            lblRole.setText("Xin chào, Admin");
        }
    }

    // --- CÁC HÀM CHUYỂN VIEW (Bấm nút nào nạp file đó) ---
    @FXML public void switchHome() { loadView("/fxml/DashboardView.fxml"); }
    @FXML public void switchEmployee() { loadView("/fxml/NhanVienView.fxml"); }
    @FXML public void switchPhongBan() { loadView("/fxml/PhongBanView.fxml"); }
    @FXML public void switchDuAn() { loadView("/fxml/DuAnView.fxml"); }
    @FXML public void switchLuong() { loadView("/fxml/Salary.fxml"); }

    @FXML
    public void logout() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Bạn có chắc muốn thoát?", ButtonType.YES, ButtonType.NO);
        confirm.setTitle("Xác nhận");
        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.YES) Platform.exit();
        });
    }

    // ===================== HÀM NẠP GIAO DIỆN TỔNG LỰC =====================
    private void loadView(String fxmlPath) {
        try {
            System.out.println(">>> Đang nạp: " + fxmlPath);
            URL resource = getClass().getResource(fxmlPath);
            if (resource == null) {
                throw new IOException("Không tìm thấy file: " + fxmlPath);
            }

            Node view = FXMLLoader.load(resource);
            content.getChildren().setAll(view);

        } catch (IOException e) {
            System.err.println("Lỗi nạp View: " + e.getMessage());
            Label errorLabel = new Label("Lỗi: " + fxmlPath + " chưa sẵn sàng!");
            errorLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-padding: 20;");
            content.getChildren().setAll(errorLabel);
        }
    }

    // ===================== HÀM CHẠY THẲNG APP (Dùng để Test) =====================
    public static void main(String[] args) {
        javafx.application.Application.launch(Launcher.class, args);
    }

    public static class Launcher extends javafx.application.Application {
        @Override
        public void start(Stage stage) throws Exception {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainView.fxml"));
            stage.setTitle("Quản Lý Nhân Sự - UTC");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }
}