package QUANLINHANSU.controller;

import QUANLINHANSU.model.BoNhiem;
import QUANLINHANSU.model.ChucVu;
import QUANLINHANSU.model.NhanVien;
import QUANLINHANSU.model.PhongBan;
import QUANLINHANSU.service.BoNhiemService;
import QUANLINHANSU.service.ChucVuService;
import QUANLINHANSU.service.NhanVienService;
import QUANLINHANSU.service.PhongBanService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class BoNhiemController implements Initializable {

    // ===== Bảng =====
    @FXML private TableView<BoNhiem> tableBoNhiem;
    @FXML private TableColumn<BoNhiem, String> colMaNV;
    @FXML private TableColumn<BoNhiem, String> colHoTen;
    @FXML private TableColumn<BoNhiem, String> colMaCV;
    @FXML private TableColumn<BoNhiem, String> colTenCV;
    @FXML private TableColumn<BoNhiem, String> colTuNgay;
    @FXML private TableColumn<BoNhiem, String> colDenNgay;
    @FXML private TableColumn<BoNhiem, String> colQuyetDinh;

    // ===== Form =====
    @FXML private TextField txtSearch;
    @FXML private ComboBox<NhanVien> cbNhanVien;
    @FXML private ComboBox<ChucVu> cbChucVu;
    @FXML private TextField txtQuyetDinh;
    @FXML private DatePicker dpTuNgay;
    @FXML private Label lblChucVuHienTai;
    @FXML private Label txtDenNgay;

    @FXML private Label heloo;

    private final BoNhiemService boNhiemService   = new BoNhiemService();
    private final NhanVienService nhanVienService = new NhanVienService();
    private final ChucVuService chucVuService = new ChucVuService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupColumns();
        setupComboBoxes();
        loadTable();
        dpTuNgay.setValue(LocalDate.now());

        // Khi chọn NV → hiển thị chức vụ hiện tại
        cbNhanVien.setOnAction(e -> hienThiChucVuHienTai());
    }

    // ===================== SETUP =====================
    private void setupColumns() {
        colMaNV.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getNhanVien().getMaNV()));
        colHoTen.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getNhanVien().getHoTen()));
        colMaCV.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getChucVu().getMaCV()));
        colTenCV.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getChucVu().getTenCV()));
        colTuNgay.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getId().getTuNgay() != null
                        ? c.getValue().getId().getTuNgay().toString() : ""));
        colDenNgay.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getDenNgay() != null
                        ? c.getValue().getDenNgay().toString() : "Đang giữ"));
        colQuyetDinh.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getQuyetDinhSo()));
    }

    private void setupComboBoxes() {
        // ComboBox Nhân Viên
        List<NhanVien> dsNV = nhanVienService.layDangLam();
        cbNhanVien.setItems(FXCollections.observableArrayList(dsNV));
        cbNhanVien.setCellFactory(lv -> new ListCell<>() {
            @Override protected void updateItem(NhanVien nv, boolean empty) {
                super.updateItem(nv, empty);
                setText(empty || nv == null ? null : nv.getMaNV() + " - " + nv.getHoTen());
            }
        });
        cbNhanVien.setButtonCell(new ListCell<>() {
            @Override protected void updateItem(NhanVien nv, boolean empty) {
                super.updateItem(nv, empty);
                setText(empty || nv == null ? null : nv.getMaNV() + " - " + nv.getHoTen());
            }
        });

        // ComboBox Chức Vụ
        List<ChucVu> dsCV = chucVuService.layTatCa();
        cbChucVu.setItems(FXCollections.observableArrayList(dsCV));
        cbChucVu.setCellFactory(lv -> new ListCell<>() {
            @Override protected void updateItem(ChucVu cv, boolean empty) {
                super.updateItem(cv, empty);
                setText(empty || cv == null ? null : cv.getMaCV() + " - " + cv.getTenCV());
            }
        });
        cbChucVu.setButtonCell(new ListCell<>() {
            @Override protected void updateItem(ChucVu cv, boolean empty) {
                super.updateItem(cv, empty);
                setText(empty || cv == null ? null : cv.getMaCV() + " - " + cv.getTenCV());
            }
        });
    }

    private void loadTable() {
        try {
            // Lấy toàn bộ lịch sử bổ nhiệm của tất cả NV đang làm
            List<NhanVien> dsNV = nhanVienService.layDangLam();
            List<BoNhiem> dsBoNhiem = dsNV.stream()
                    .flatMap(nv -> boNhiemService.layLichSuChucVu(nv.getMaNV()).stream())
                    .collect(Collectors.toList());
            tableBoNhiem.setItems(FXCollections.observableArrayList(dsBoNhiem));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===================== SỰ KIỆN =====================

    /** Khi chọn NV → hiển thị chức vụ đang giữ */
    private void hienThiChucVuHienTai() {
        NhanVien nv = cbNhanVien.getValue();
        if (nv == null) return;
        try {
            BoNhiem bn = boNhiemService.layChucVuHienTai(nv.getMaNV());
            if (bn != null) {
                lblChucVuHienTai.setText(bn.getChucVu().getMaCV()
                        + " - " + bn.getChucVu().getTenCV());
                lblChucVuHienTai.setStyle("-fx-text-fill: #2E7D32; -fx-font-weight: BOLD;");
            } else {
                lblChucVuHienTai.setText("(Chưa có chức vụ)");
                lblChucVuHienTai.setStyle("-fx-text-fill: #E65100; -fx-font-weight: BOLD;");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //tp
    @FXML
    public void boNhiem() {
        NhanVien nv = cbNhanVien.getValue();
        ChucVu cv   = cbChucVu.getValue();
        LocalDate tuNgay = dpTuNgay.getValue();
        String qd   = txtQuyetDinh.getText().trim();

        if (nv == null) { showAlert(Alert.AlertType.WARNING, "Vui lòng chọn nhân viên!"); return; }
        if (cv == null) { showAlert(Alert.AlertType.WARNING, "Vui lòng chọn chức vụ!"); return; }
        if (qd.isEmpty()) { showAlert(Alert.AlertType.WARNING, "Vui lòng nhập số quyết định!"); return; }

        try {
            // 👉 1. Bổ nhiệm như cũ
            boNhiemService.boNhiem(nv.getMaNV(), cv.getMaCV(), tuNgay, qd);

            // 👉 2. XỬ LÝ TRƯỞNG PHÒNG
            if (cv.getTenCV().equalsIgnoreCase("Trưởng phòng")) {

                PhongBan pb = nv.getPhongBan();

                if (pb != null) {
                    // Ghi đè luôn trưởng phòng cũ
                    pb.setTruongPhong(nv);

                    // Lưu lại DB
                    new PhongBanService().capNhatPhongBan(pb);
                }
            }

            showAlert(Alert.AlertType.INFORMATION, "Bổ nhiệm thành công!");
            lamSach();
            loadTable();

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, e.getMessage());
        }
    }
    //
//    @FXML
//    public void boNhiem() {
//        NhanVien nv = cbNhanVien.getValue();
//        ChucVu cv   = cbChucVu.getValue();
//        LocalDate tuNgay = dpTuNgay.getValue();
//        String qd   = txtQuyetDinh.getText().trim();
//
//        if (nv == null) { showAlert(Alert.AlertType.WARNING, "Vui lòng chọn nhân viên!"); return; }
//        if (cv == null) { showAlert(Alert.AlertType.WARNING, "Vui lòng chọn chức vụ!"); return; }
//        if (qd.isEmpty()) { showAlert(Alert.AlertType.WARNING, "Vui lòng nhập số quyết định!"); return; }
//
//        try {
//            boNhiemService.boNhiem(nv.getMaNV(), cv.getMaCV(),tuNgay,qd);
//            showAlert(Alert.AlertType.INFORMATION, "Bổ nhiệm thành công!");
//            lamSach();
//            loadTable();
//        } catch (Exception e) {
//            showAlert(Alert.AlertType.ERROR, e.getMessage());
//        }
//    }

    @FXML
    public void ketThuc() {
        NhanVien nv = cbNhanVien.getValue();
        if (nv == null) { showAlert(Alert.AlertType.WARNING, "Vui lòng chọn nhân viên!"); return; }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setHeaderText(null);
        confirm.setContentText("Kết thúc chức vụ hiện tại của " + nv.getHoTen() + "?");
        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.OK) {
                try {
                    boNhiemService.ketThucChucVu(nv.getMaNV());
                    showAlert(Alert.AlertType.INFORMATION, "Đã kết thúc chức vụ!");
                    lamSach();
                    loadTable();
                } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, e.getMessage());
                }
            }
        });
    }

    @FXML
    public void chonBoNhiem() {
        BoNhiem bn = tableBoNhiem.getSelectionModel().getSelectedItem();
        if (bn == null) return;
        // Điền NV vào ComboBox
        cbNhanVien.getItems().stream()
                .filter(nv -> nv.getMaNV().equals(bn.getNhanVien().getMaNV()))
                .findFirst().ifPresent(cbNhanVien::setValue);
        txtQuyetDinh.setText(bn.getQuyetDinhSo());
        hienThiChucVuHienTai();
    }

    @FXML
    public void timKiem() {
        String keyword = txtSearch.getText().trim();
        if (keyword.isEmpty()) { loadTable(); return; }
        try {
            List<NhanVien> dsNV = nhanVienService.timTheoTen(keyword);
            List<BoNhiem> dsBoNhiem = dsNV.stream()
                    .flatMap(nv -> boNhiemService.layLichSuChucVu(nv.getMaNV()).stream())
                    .collect(Collectors.toList());
            tableBoNhiem.setItems(FXCollections.observableArrayList(dsBoNhiem));
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    @FXML
    public void lamSach() {
        cbNhanVien.setValue(null);
        cbChucVu.setValue(null);
        txtQuyetDinh.clear();
        dpTuNgay.setValue(LocalDate.now());
        lblChucVuHienTai.setText("(Chưa có)");
        tableBoNhiem.getSelectionModel().clearSelection();

    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
