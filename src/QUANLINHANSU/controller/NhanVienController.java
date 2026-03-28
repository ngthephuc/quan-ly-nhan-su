package QUANLINHANSU.controller;

import QUANLINHANSU.model.HopDong;
import QUANLINHANSU.model.NhanVien;
import QUANLINHANSU.model.PhongBan;
import QUANLINHANSU.service.HopDongService;
import QUANLINHANSU.service.NhanVienService;
import QUANLINHANSU.service.PhongBanService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class NhanVienController implements Initializable {

    // ===== Bảng =====
    @FXML private TableView<NhanVien> tableNhanVien;
    @FXML private TableColumn<NhanVien, String> colMaNV;
    @FXML private TableColumn<NhanVien, String> colHoTen;
    @FXML private TableColumn<NhanVien, String> colGioiTinh;
    @FXML private TableColumn<NhanVien, String> colNgaySinh;
    @FXML private TableColumn<NhanVien, String> colNgayVaoLam;
    @FXML private TableColumn<NhanVien, String> colTrangThai;
    @FXML private TableColumn<NhanVien, String> colPhongBan;
    @FXML private TableColumn<NhanVien, String> colHopDong;

    // ===== Form =====
    @FXML private TextField txtSearch;
    @FXML private TextField txtMaNV;
    @FXML private TextField txtHoTen;
    @FXML private ComboBox<String> cbGioiTinh;
    @FXML private DatePicker dpNgaySinh;
    @FXML private TextField txtCccd;
    @FXML private DatePicker dpNgayVaoLam;
    @FXML private ComboBox<String> cbTrangThai;
    @FXML private TextField txtSdt;
    @FXML private TextField txtDiaChi;
    @FXML private TextField txtEmail;
    @FXML private ComboBox<PhongBan> cbPhongBan;
    @FXML private ComboBox<HopDong> cbHopDong;


    private final NhanVienService nhanVienService = new NhanVienService();
    private final PhongBanService phongBanService = new PhongBanService();
    private final HopDongService hopDongService = new HopDongService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupColumns();
        setupComboBoxes();
        loadTable();
    }

    // ===================== SETUP =====================
    private void setupColumns() {
        colMaNV.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getMaNV()));
        colHoTen.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getHoTen()));
        colGioiTinh.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getGioiTinh()));
        colNgaySinh.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getNgaySinh() != null
                        ? c.getValue().getNgaySinh().toString() : ""));
        colNgayVaoLam.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getNgayVaoLam() != null
                        ? c.getValue().getNgayVaoLam().toString() : ""));
        colTrangThai.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getTrangThai()));
        colPhongBan.setCellValueFactory(c -> {
            PhongBan pb = c.getValue().getPhongBan();
            return new SimpleStringProperty(pb != null ? pb.getTenPb() : "");
        });
        colHopDong.setCellValueFactory(c -> {
            HopDong hd = c.getValue().getHopDong();
            return new SimpleStringProperty(hd != null ? hd.getLoaiHD() : "");
        });
    }

    private void setupComboBoxes() {
        cbGioiTinh.setItems(FXCollections.observableArrayList("Nam", "Nữ", "Khác"));
        cbTrangThai.setItems(FXCollections.observableArrayList("Đang làm", "Đã nghỉ"));

        // Load danh sách phòng ban từ DB
        List<PhongBan> dsPhongBan = phongBanService.layTatCa();
        cbPhongBan.setItems(FXCollections.observableArrayList(dsPhongBan));

        List<HopDong> dsHopDong = hopDongService.layTatCa();
        cbHopDong.setItems(FXCollections.observableArrayList(dsHopDong));

        // Hiển thị tên phòng ban trong ComboBox


        cbPhongBan.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(PhongBan pb, boolean empty) {
                super.updateItem(pb, empty);
                setText(empty || pb == null ? null : pb.getTenPb());  // hien thi danh sach do xuong
            }
        });
        cbPhongBan.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(PhongBan pb, boolean empty) {
                super.updateItem(pb, empty);
                setText(empty || pb == null ? null : pb.getTenPb());       // hien thi cai dc chon lên trên nút
            }
        });


        // hienj thi danh sách hop dong

        cbHopDong.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(HopDong hd, boolean empty) {
                super.updateItem(hd, empty);
                setText(empty || hd == null ? null : hd.getLoaiHD());  // hien thi danh sach do xuong
            }
        });
        cbHopDong.setButtonCell(new ListCell<>() {
            protected void updateItem(HopDong hd , boolean empty) {
                super.updateItem(hd, empty);
                setText(empty || hd == null ? null : hd.getLoaiHD());
            }
        });
    }

    private void loadTable() {
        try {
            List<NhanVien> ds = nhanVienService.layTatCa();
            tableNhanVien.setItems(FXCollections.observableArrayList(ds));
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể tải danh sách nhân viên!\n" + e.getMessage());
        }
    }

    // ===================== SỰ KIỆN =====================

    /** Click vào hàng trong bảng → điền vào form */
    @FXML
    public void chonNhanVien() {
        NhanVien nv = tableNhanVien.getSelectionModel().getSelectedItem();
        if (nv == null) return;
        txtMaNV.setText(nv.getMaNV());
        txtMaNV.setDisable(true); // Không cho sửa mã NV
        txtHoTen.setText(nv.getHoTen());
        cbGioiTinh.setValue(nv.getGioiTinh());
        dpNgaySinh.setValue(nv.getNgaySinh());
        txtCccd.setText(nv.getCccd());
        dpNgayVaoLam.setValue(nv.getNgayVaoLam());
        cbTrangThai.setValue(nv.getTrangThai());
        txtSdt.setText(nv.getSdt());
        txtDiaChi.setText(nv.getDiaChi());
        txtEmail.setText(nv.getEmail());

        // Chọn đúng phòng ban trong ComboBox
        if (nv.getPhongBan() != null) {
            cbPhongBan.getItems().stream()
                    .filter(pb -> pb.getMaPb().equals(nv.getPhongBan().getMaPb()))
                    .findFirst()
                    .ifPresent(cbPhongBan::setValue);
        }
    }

    @FXML
    public void themNV() {
        try {
            NhanVien nv = layDuLieuTuForm();
            if (nv == null) return;

            nhanVienService.themNhanVien(nv);
            showAlert(Alert.AlertType.INFORMATION, "Thành công", "Thêm nhân viên thành công!");
            lamSach();
            loadTable();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", e.getMessage());
        }
    }

    @FXML
    public void suaNV() {
        try {
            NhanVien nv = layDuLieuTuForm();
            if (nv == null) return;

            nhanVienService.capNhatNhanVien(nv);
            showAlert(Alert.AlertType.INFORMATION, "Thành công", "Cập nhật nhân viên thành công!");
            lamSach();
            loadTable();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", e.getMessage());
        }
    }

    @FXML
    public void xoaNV() {
        NhanVien nv = tableNhanVien.getSelectionModel().getSelectedItem();
        if (nv == null) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn nhân viên cần xóa!");
            return;
        }

        // Xác nhận trước khi xóa
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Xác nhận");
        confirm.setContentText("Bạn có chắc muốn cho nhân viên '" + nv.getHoTen() + "' nghỉ việc?");
        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.OK) {
                try {
                    nhanVienService.nghi(nv.getMaNV());
                    showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đã cập nhật trạng thái nghỉ việc!");
                    lamSach();
                    loadTable();
                } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, "Lỗi", e.getMessage());
                }
            }
        });
    }

    @FXML
    public void lamSach() {
        txtMaNV.clear();
        txtMaNV.setDisable(false);
        txtHoTen.clear();
        cbGioiTinh.setValue(null);
        dpNgaySinh.setValue(null);
        txtCccd.clear();
        dpNgayVaoLam.setValue(null);
        cbTrangThai.setValue(null);
        txtSdt.clear();
        txtDiaChi.clear();
        txtEmail.clear();
        cbPhongBan.setValue(null);
        tableNhanVien.getSelectionModel().clearSelection();
    }

    @FXML
    public void timKiemNV() {
        String keyword = txtSearch.getText().trim();
        try {
            List<NhanVien> ds = keyword.isEmpty()
                    ? nhanVienService.layTatCa()
                    : nhanVienService.timTheoTen(keyword);
            tableNhanVien.setItems(FXCollections.observableArrayList(ds));
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", e.getMessage());
        }
    }

    // ===================== HELPER =====================

    /** Lấy dữ liệu từ form và tạo object NhanVien */
    private NhanVien layDuLieuTuForm() {
        String maNV = txtMaNV.getText().trim();
        if (maNV.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Mã nhân viên không được để trống!");
            return null;
        }

        NhanVien nv = new NhanVien();
        nv.setMaNV(maNV);
        nv.setHoTen(txtHoTen.getText().trim());
        nv.setGioiTinh(cbGioiTinh.getValue());
        nv.setNgaySinh(dpNgaySinh.getValue());
        nv.setCccd(txtCccd.getText().trim());
        nv.setNgayVaoLam(dpNgayVaoLam.getValue());
        nv.setTrangThai(cbTrangThai.getValue());
        nv.setSdt(txtSdt.getText().trim());
        nv.setDiaChi(txtDiaChi.getText().trim());
        nv.setEmail(txtEmail.getText().trim());
        nv.setPhongBan(cbPhongBan.getValue());

        return nv;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}