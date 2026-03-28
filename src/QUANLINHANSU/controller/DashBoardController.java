package QUANLINHANSU.controller;

import QUANLINHANSU.model.Cham_Cong;
import QUANLINHANSU.model.Du_An;
import QUANLINHANSU.model.HopDong;
import QUANLINHANSU.service.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class DashBoardController implements Initializable {

    // ===== 4 thẻ thống kê =====
    // Khớp với fx:id trong DashboardView.fxml
    @FXML private Label lblTongNV;
    @FXML private Label lblDangLam;
    @FXML private Label lblTongPB;
    @FXML private Label lblTongDA;

    // ===== Bảng chấm công hôm nay =====
    @FXML private TableView<Cham_Cong> tableChamCongHomNay;
    @FXML private TableColumn<Cham_Cong, String> colMaNV;
    @FXML private TableColumn<Cham_Cong, String> colHoTen;
    @FXML private TableColumn<Cham_Cong, String> colGioVao;
    @FXML private TableColumn<Cham_Cong, String> colGioRa;


    private final NhanVienService nhanVienService = new NhanVienService();
    private final PhongBanService phongBanService = new PhongBanService();
    private final DuAnService duAnService = new DuAnService();
    private final Cham_CongService chamCongService = new Cham_CongService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupColumns();
        loadData();
    }

    // ===================== SETUP CỘT =====================
    private void setupColumns() {

        // Cột bảng chấm công
        colMaNV.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getNhanVien().getMaNV()));

        colHoTen.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getNhanVien().getHoTen()));

        colGioVao.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getGioVao() != null
                                ? c.getValue().getGioVao().toString() : ""));

        colGioRa.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getGioRa() != null
                                ? c.getValue().getGioRa().toString() : "Chua ra"));

    }

    // ===================== LOAD DỮ LIỆU =====================
    private void loadData() {
        try {
            // 4 thẻ thống kê
            lblTongNV.setText(String.valueOf(
                    nhanVienService.layTatCa().size()));

            lblDangLam.setText(String.valueOf(
                    nhanVienService.demNhanVienDangLam()));

            lblTongPB.setText(String.valueOf(
                    phongBanService.layTatCa().size()));

            lblTongDA.setText(String.valueOf(
                    duAnService.layTatCaDuAn().size()));

            // Bảng chấm công hôm nay
            List<Cham_Cong> ccHomNay = chamCongService.layTheoNgay(LocalDate.now());
            tableChamCongHomNay.setItems(FXCollections.observableArrayList(ccHomNay));



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
