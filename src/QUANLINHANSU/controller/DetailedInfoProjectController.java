package QUANLINHANSU.controller;

import QUANLINHANSU.model.NhanVien;
import QUANLINHANSU.service.DuAnService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.List;

public class DetailedInfoProjectController {

    @FXML private TableView<NhanVien> tbTTCTDA;
    @FXML private TableColumn<NhanVien, String> colMNV, colName, colSDT;
    @FXML private Label ttctEnd, ttctKinhPhi, ttctMDA, ttctStart, ttctTDA;

    private final DuAnService duAnService = new DuAnService();

    // HÀM ĐÓNG CỬA SỔ - ÔNG PHẢI CÓ HÀM NÀY
    @FXML
    void closeWindow(ActionEvent event) {
        // Lấy Stage từ cái nút vừa bấm
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close(); // Lệnh đóng
    }

    public void loadLabel(LocalDate start, LocalDate end, double budget, String mda, String tda) {
        if (ttctStart != null) ttctStart.setText(start != null ? start.toString() : "N/A");
        if (ttctEnd != null) ttctEnd.setText(end != null ? end.toString() : "Đang làm");
        if (ttctMDA != null) ttctMDA.setText(mda);
        if (ttctTDA != null) ttctTDA.setText(tda);
        if (ttctKinhPhi != null) ttctKinhPhi.setText(String.format("%,.0f VNĐ", budget));
    }

    public void loadNhanVienTheoDuAn(String maDA) {
        List<NhanVien> list = duAnService.layNhanVienChiTiet(maDA);
        tbTTCTDA.setItems(FXCollections.observableArrayList(list));
    }

    @FXML
    public void initialize() {
        colMNV.setCellValueFactory(new PropertyValueFactory<>("maNV"));
        colName.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
        colSDT.setCellValueFactory(new PropertyValueFactory<>("sdt"));
    }
}