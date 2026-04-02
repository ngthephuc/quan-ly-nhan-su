package QUANLINHANSU.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    // Khớp với fx:id="content" trong MainView.fxml
    @FXML private StackPane content;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Mặc định mở Trang Chủ khi khởi động
        switchHome();
    }


    @FXML public void switchHome() {
        loadView("/fxml/DashboardView.fxml");
    }
    @FXML public void switchNhanVien() {loadView("/fxml/NhanVienView.fxml");}
    @FXML public void switchPhongBan() {
        loadView("/fxml/PhongBanView.fxml");
    }
    @FXML public void switchDuAn() {
        loadView("/fxml/DuAnView.fxml");
    }
    @FXML public void switchLuong() {
        loadView("/fxml/Salary.fxml");
    }
    @FXML public void switchBoNhiem() {loadView("/fxml/BoNhiemView.fxml");}
    @FXML public void switchChamCong() {loadView("/fxml/ChamCongView.fxml");}

    // Khớp với onAction="#logout"
    @FXML public void logout() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Xác nhận");
        confirm.setHeaderText(null);
        confirm.setContentText("Bạn có chắc muốn thoát?");
        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.OK) Platform.exit();
        });
    }

    // ===================== HELPER =====================
    private void loadView(String fxmlPath) {
        try {
            Node view = FXMLLoader.load(getClass().getResource(fxmlPath));
            content.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
            javafx.scene.control.Label lbl = new javafx.scene.control.Label(
                    "Chua co man hinh: " + fxmlPath);
            lbl.setStyle("-fx-font-size: 16px; -fx-text-fill: #999;");
            content.getChildren().setAll(lbl);
        }
    }
}