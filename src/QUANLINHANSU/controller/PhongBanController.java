package QUANLINHANSU.controller;

import QUANLINHANSU.model.NhanVien;
import QUANLINHANSU.model.PhongBan;
import QUANLINHANSU.service.NhanVienService;
import QUANLINHANSU.service.PhongBanService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PhongBanController implements Initializable {

    // ===== Bảng =====
    @FXML private TableView<PhongBanRow> tablePhongBan;
    @FXML private TableColumn<PhongBanRow, String> colMaPb;
    @FXML private TableColumn<PhongBanRow, String> colTenPb;
    @FXML private TableColumn<PhongBanRow, String> colHeSoLuong;
    @FXML private TableColumn<PhongBanRow, String> colTruongPhong;
    @FXML private TableColumn<PhongBanRow, String> colSoThanhVien;

    // ===== Form =====
    @FXML private TextField txtMaPb;
    @FXML private TextField txtTenPb;
    @FXML private TextField txtHeSoLuong;

    // ===== Btn =======

    @FXML private Button btnThemnv;
    @FXML private Button btnXoaPB;

    private final PhongBanService phongBanService = new PhongBanService();
    private final NhanVienService nhanVienService = new NhanVienService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupColumns();
        loadTable();
    }

    // ===================== SETUP =====================
    private void setupColumns() {
        colMaPb.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getPhongBan().getMaPb()));
        colTenPb.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getPhongBan().getTenPb()));
        colHeSoLuong.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getPhongBan().getHeSoLuong() != null
                        ? String.valueOf(c.getValue().getPhongBan().getHeSoLuong()) : ""));
        colTruongPhong.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getTruongPhong()));
        colSoThanhVien.setCellValueFactory(c ->
                new SimpleStringProperty(String.valueOf(c.getValue().getSoThanhVien())));
    }
//tp
private void loadTable() {
    try {
        List<PhongBan> ds = phongBanService.layTatCa();

        List<PhongBanRow> rows = ds.stream().map(pb -> {
            long soTV = nhanVienService.demNhanVienTheoPhongBan(pb.getMaPb());

            String tenTruong = pb.getTruongPhong() != null
                    ? pb.getTruongPhong().getHoTen()
                    : "(Chưa có)";

            return new PhongBanRow(pb, tenTruong, soTV);
        }).collect(Collectors.toList());

        tablePhongBan.setItems(FXCollections.observableArrayList(rows));
    } catch (Exception e) {
        e.printStackTrace();
    }
}
//
//    private void loadTable() {
//        try {
//            List<PhongBan> ds = phongBanService.layTatCa();
//
//            // Với mỗi phòng ban → lấy thêm Trưởng Phòng + Số Thành Viên
//            List<PhongBanRow> rows = ds.stream().map(pb -> {
//                NhanVien truong = nhanVienService.layTruongPhong(pb.getMaPb());
//                long soTV = nhanVienService.demNhanVienTheoPhongBan(pb.getMaPb());
//                String tenTruong = truong != null ? truong.getHoTen() : "(Chưa có)";
//                return new PhongBanRow(pb, tenTruong, soTV);
//            }).collect(Collectors.toList());
//
//            tablePhongBan.setItems(FXCollections.observableArrayList(rows));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    // ===================== SỰ KIỆN =====================

    /** Click vào hàng → điền vào form */
    @FXML
    public void chonPhongBan() {
        PhongBanRow row = tablePhongBan.getSelectionModel().getSelectedItem();
        if (row == null) return;
        PhongBan pb = row.getPhongBan();
        txtMaPb.setText(pb.getMaPb());
        txtMaPb.setDisable(true);
        txtTenPb.setText(pb.getTenPb());
        txtHeSoLuong.setText(pb.getHeSoLuong() != null
                ? String.valueOf(pb.getHeSoLuong()) : "");
    }

    @FXML
    public void them() {
        PhongBan pb = layDuLieuTuForm();
        if (pb == null) return;
        try {
            phongBanService.themPhongBan(pb);
            showAlert(Alert.AlertType.INFORMATION, "Thêm phòng ban thành công!");
            lamSach();
            loadTable();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    @FXML
    public void sua() {
        PhongBan pb = layDuLieuTuForm();
        if (pb == null) return;
        try {
            phongBanService.capNhatPhongBan(pb);
            showAlert(Alert.AlertType.INFORMATION, "Cập nhật phòng ban thành công!");
            lamSach();
            loadTable();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    @FXML
    public void xoa() {
        PhongBanRow row = tablePhongBan.getSelectionModel().getSelectedItem();
        if (row == null) {
            showAlert(Alert.AlertType.WARNING, "Vui lòng chọn phòng ban cần xóa!");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setHeaderText(null);
        confirm.setContentText("Xóa phòng ban '" + row.getPhongBan().getTenPb() + "'?");
        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.OK) {
                try {
                    phongBanService.xoaPhongBan(row.getPhongBan().getMaPb());
                    showAlert(Alert.AlertType.INFORMATION, "Xóa phòng ban thành công!");
                    lamSach();
                    loadTable();
                } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, e.getMessage());
                }
            }
        });
    }

    /** Xem chi tiết — hiện danh sách NV trong phòng ban */
    @FXML
    public void xemChiTiet() {
        PhongBanRow row = tablePhongBan.getSelectionModel().getSelectedItem();
        if (row == null) {
            showAlert(Alert.AlertType.WARNING, "Vui lòng chọn phòng ban!");
            return;
        }
        PhongBan pb = row.getPhongBan();
        List<NhanVien> dsNV = nhanVienService.layTheoPhongBan(pb.getMaPb());

        // Hiện dialog danh sách NV
        StringBuilder sb = new StringBuilder();
        sb.append("Phòng ban: ").append(pb.getTenPb()).append("\n");
        sb.append("Trưởng phòng: ").append(row.getTruongPhong()).append("\n");
        sb.append("Số thành viên: ").append(row.getSoThanhVien()).append("\n\n");
        sb.append("Danh sách nhân viên:\n");
        dsNV.forEach(nv -> sb.append("  - ")
                .append(nv.getMaNV()).append(" | ")
                .append(nv.getHoTen()).append(" | ")
                .append(nv.getTrangThai()).append("\n"));

        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Thông Tin Chi Tiết");
        info.setHeaderText("Phòng ban: " + pb.getTenPb());
        info.setContentText(sb.toString());
        info.getDialogPane().setMinWidth(400);
        info.showAndWait();
    }

    // ===================== HELPER =====================
    private PhongBan layDuLieuTuForm() {
        String maPb = txtMaPb.getText().trim();
        String tenPb = txtTenPb.getText().trim();
        String heSoStr = txtHeSoLuong.getText().trim();

        if (maPb.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Mã phòng ban không được để trống!");
            return null;
        }
        if (tenPb.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Tên phòng ban không được để trống!");
            return null;
        }
        double heSo = 1.0;
        if (!heSoStr.isEmpty()) {
            try {
                heSo = Double.parseDouble(heSoStr);
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.WARNING, "Hệ số lương phải là số!");
                return null;
            }
        }
        return new PhongBan(maPb, tenPb, heSo);
    }

    public void lamSach() {
        txtMaPb.clear();
        txtMaPb.setDisable(false);
        txtTenPb.clear();
        txtHeSoLuong.clear();
        tablePhongBan.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // ===================== INNER CLASS — Row wrapper =====================
    /**
     * Wrapper gộp PhongBan + TruongPhong + SoThanhVien
     * để hiển thị đủ thông tin trong TableView
     */
    public static class PhongBanRow {
        private final PhongBan phongBan;
        private final String truongPhong;
        private final long soThanhVien;

        public PhongBanRow(PhongBan pb, String truongPhong, long soThanhVien) {
            this.phongBan = pb;
            this.truongPhong = truongPhong;
            this.soThanhVien = soThanhVien;
        }

        public PhongBan getPhongBan()   { return phongBan; }
        public String getTruongPhong()  { return truongPhong; }
        public long getSoThanhVien()    { return soThanhVien; }
    }
}