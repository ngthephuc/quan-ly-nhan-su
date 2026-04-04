package QUANLINHANSU.controller;

import QUANLINHANSU.model.Cham_Cong;
import QUANLINHANSU.model.NhanVien;
import QUANLINHANSU.service.Cham_CongService;
import QUANLINHANSU.service.NhanVienService;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ChamCongController implements Initializable {

    // ===== Tab 1 — Đơn lẻ =====
    @FXML private TableView<Cham_Cong> tableDonLe;
    @FXML private TableColumn<Cham_Cong, String> colDLMaNV;
    @FXML private TableColumn<Cham_Cong, String> colDLHoTen;
    @FXML private TableColumn<Cham_Cong, String> colDLNgay;
    @FXML private TableColumn<Cham_Cong, String> colDLGioVao;
    @FXML private TableColumn<Cham_Cong, String> colDLGioRa;

    @FXML private ComboBox<NhanVien> cbNhanVienDL;
    @FXML private DatePicker dpNgayDL;
    @FXML private TextField txtGioVao;
    @FXML private TextField txtGioRa;

    // ===== Tab 2 — Hàng loạt =====
    @FXML private TableView<NhanVienCheck> tableHangLoat;
    @FXML private TableColumn<NhanVienCheck, Boolean> colHLChon;
    @FXML private TableColumn<NhanVienCheck, String> colHLMaNV;
    @FXML private TableColumn<NhanVienCheck, String> colHLHoTen;
    @FXML private TableColumn<NhanVienCheck, String> colHLPB;
    @FXML private DatePicker dpNgayHL;
    @FXML private TextField txtGioVaoHL;
    @FXML private TextField txtGioRaHL;

    // ===== Tab 3 — Lịch sử =====
    @FXML private TableView<Cham_Cong> tableLichSu;
    @FXML private TableColumn<Cham_Cong, String> colLSNgay;
    @FXML private TableColumn<Cham_Cong, String> colLSGioVao;
    @FXML private TableColumn<Cham_Cong, String> colLSGioRa;
    @FXML private TableColumn<Cham_Cong, String> colLSSoGio;

    @FXML private ComboBox<NhanVien> cbNhanVienLS;
    @FXML private ComboBox<Integer> cbThang;
    @FXML private TextField txtNam;
    @FXML private Label lblNgayCong;
    @FXML private Label lblTongGio;

    private final Cham_CongService chamCongService = new Cham_CongService();
    private final NhanVienService nhanVienService = new NhanVienService();
    private final ObservableList<NhanVienCheck> dsChamHangLoat = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTab1();
        setupTab2();
        setupTab3();
        loadTableDonLe();
    }

    // ===================== TAB 1 — ĐƠN LẺ =====================
    private void setupTab1() {
        // id.ngay và id.maNV thay vì ngay và maNV trực tiếp
        colDLMaNV.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getId().getMaNV()));
        colDLHoTen.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getNhanVien().getHoTen()));
        colDLNgay.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getId().getNgay().toString()));
        colDLGioVao.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getGioVao() != null
                        ? c.getValue().getGioVao().toString() : ""));
        colDLGioRa.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getGioRa() != null
                        ? c.getValue().getGioRa().toString() : ""));
        setupComboBoxNV(cbNhanVienDL);
        dpNgayDL.setValue(LocalDate.now());
    }

    private void loadTableDonLe() {
        try {
            List<Cham_Cong> ds = chamCongService.layTheoNgay(LocalDate.now());
            tableDonLe.setItems(FXCollections.observableArrayList(ds));
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML public void chonBanGhi() {
        Cham_Cong cc = tableDonLe.getSelectionModel().getSelectedItem();
        if (cc == null) return;
        cbNhanVienDL.getItems().stream()
                .filter(nv -> nv.getMaNV().equals(cc.getId().getMaNV()))
                .findFirst().ifPresent(cbNhanVienDL::setValue);
        dpNgayDL.setValue(cc.getId().getNgay());
        txtGioVao.setText(cc.getGioVao() != null ? cc.getGioVao().toString() : "");
        txtGioRa.setText(cc.getGioRa() != null ? cc.getGioRa().toString() : "");
    }

    @FXML public void luuDonLe() {
        NhanVien nv = cbNhanVienDL.getValue();
        if (nv == null) { showAlert(Alert.AlertType.WARNING, "Vui lòng chọn nhân viên!"); return; }
        if (dpNgayDL.getValue() == null) { showAlert(Alert.AlertType.WARNING, "Vui lòng chọn ngày!"); return; }
        if (txtGioVao.getText().isBlank() || txtGioRa.getText().isBlank()) {
            showAlert(Alert.AlertType.WARNING, "Vui lòng nhập giờ vào và giờ ra!"); return;
        }
        try {
            LocalTime gioVao = LocalTime.parse(txtGioVao.getText().trim());
            LocalTime gioRa  = LocalTime.parse(txtGioRa.getText().trim());
            chamCongService.chamCong(nv.getMaNV(), dpNgayDL.getValue(), gioVao, gioRa);
            showAlert(Alert.AlertType.INFORMATION, "Chấm công thành công!");
            lamSachDonLe();
            loadTableDonLe();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    @FXML public void xoaDonLe() {
        Cham_Cong cc = tableDonLe.getSelectionModel().getSelectedItem();
        if (cc == null) { showAlert(Alert.AlertType.WARNING, "Vui lòng chọn bản ghi cần xóa!"); return; }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setHeaderText(null);
        confirm.setContentText("Xóa bản ghi chấm công ngày " + cc.getId().getNgay() + "?");
        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.OK) {
                try {
                    chamCongService.xoa(cc.getId());
                    showAlert(Alert.AlertType.INFORMATION, "Xóa thành công!");
                    loadTableDonLe();
                } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, e.getMessage());
                }
            }
        });
    }

    @FXML public void lamSachDonLe() {
        cbNhanVienDL.setValue(null);
        dpNgayDL.setValue(LocalDate.now());
        txtGioVao.clear();
        txtGioRa.clear();
        tableDonLe.getSelectionModel().clearSelection();
    }

    // ===================== TAB 2 — HÀNG LOẠT =====================
    private void setupTab2() {
        colHLChon.setCellValueFactory(c -> c.getValue().chonProperty());
        colHLChon.setCellFactory(CheckBoxTableCell.forTableColumn(colHLChon));
        tableHangLoat.setEditable(true);

        colHLMaNV.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getNhanVien().getMaNV()));
        colHLHoTen.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getNhanVien().getHoTen()));
        colHLPB.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getNhanVien().getPhongBan() != null
                        ? c.getValue().getNhanVien().getPhongBan().getTenPb() : ""));

        dpNgayHL.setValue(LocalDate.now());
        txtGioVaoHL.setText("08:00");
        txtGioRaHL.setText("17:30");

        nhanVienService.layDangLam()
                .forEach(nv -> dsChamHangLoat.add(new NhanVienCheck(nv)));
        tableHangLoat.setItems(dsChamHangLoat);
    }

    @FXML public void chonTatCa()   { dsChamHangLoat.forEach(i -> i.setChon(true)); }
    @FXML public void boChonTatCa() { dsChamHangLoat.forEach(i -> i.setChon(false)); }

    @FXML public void luuHangLoat() {
        if (dpNgayHL.getValue() == null) { showAlert(Alert.AlertType.WARNING, "Vui lòng chọn ngày!"); return; }
        if (txtGioVaoHL.getText().isBlank() || txtGioRaHL.getText().isBlank()) {
            showAlert(Alert.AlertType.WARNING, "Vui lòng nhập giờ vào và giờ ra!"); return;
        }
        List<NhanVien> dsChon = dsChamHangLoat.stream()
                .filter(NhanVienCheck::isChon)
                .map(NhanVienCheck::getNhanVien).collect(Collectors.toList());
        if (dsChon.isEmpty()) { showAlert(Alert.AlertType.WARNING, "Chưa chọn nhân viên nào!"); return; }
        try {
            LocalTime gioVao = LocalTime.parse(txtGioVaoHL.getText().trim());
            LocalTime gioRa  = LocalTime.parse(txtGioRaHL.getText().trim());
            int soLuong = chamCongService.chamCongHangLoat(dsChon, dpNgayHL.getValue(), gioVao, gioRa);
            showAlert(Alert.AlertType.INFORMATION,
                    "Đã chấm công cho " + soLuong + "/" + dsChon.size() + " nhân viên!");
            boChonTatCa();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    // ===================== TAB 3 — LỊCH SỬ =====================
    private void setupTab3() {
        colLSNgay.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getId().getNgay().toString()));
        colLSGioVao.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getGioVao() != null
                        ? c.getValue().getGioVao().toString() : ""));
        colLSGioRa.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getGioRa() != null
                        ? c.getValue().getGioRa().toString() : ""));
        colLSSoGio.setCellValueFactory(c ->
                new SimpleStringProperty());
        setupComboBoxNV(cbNhanVienLS);
        cbThang.setItems(FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11,12));
        cbThang.setValue(LocalDate.now().getMonthValue());
        txtNam.setText(String.valueOf(LocalDate.now().getYear()));
    }

    @FXML public void xemLichSu() {
        NhanVien nv = cbNhanVienLS.getValue();
        if (nv == null) { showAlert(Alert.AlertType.WARNING, "Vui lòng chọn nhân viên!"); return; }
        try {
            int thang = cbThang.getValue();
            int nam   = Integer.parseInt(txtNam.getText().trim());

            List<Cham_Cong> ds = chamCongService.layTheoNhanVienVaThang(nv.getMaNV(), thang, nam);
            tableLichSu.setItems(FXCollections.observableArrayList(ds));

            long ngayCong   = chamCongService.demNgayCong(nv.getMaNV(), thang, nam);
            lblNgayCong.setText(ngayCong + " ngày");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    // ===================== HELPER =====================
    private void setupComboBoxNV(ComboBox<NhanVien> cb) {
        cb.setItems(FXCollections.observableArrayList(nhanVienService.layDangLam()));
        cb.setCellFactory(lv -> new ListCell<>() {
            @Override protected void updateItem(NhanVien nv, boolean empty) {
                super.updateItem(nv, empty);
                setText(empty || nv == null ? null : nv.getMaNV() + " - " + nv.getHoTen());
            }
        });
        cb.setButtonCell(new ListCell<>() {
            @Override protected void updateItem(NhanVien nv, boolean empty) {
                super.updateItem(nv, empty);
                setText(empty || nv == null ? null : nv.getMaNV() + " - " + nv.getHoTen());
            }
        });
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // ===================== INNER CLASS =====================
    public static class NhanVienCheck {
        private final NhanVien nhanVien;
        private final SimpleBooleanProperty chon = new SimpleBooleanProperty(false);

        public NhanVienCheck(NhanVien nv) { this.nhanVien = nv; }
        public NhanVien getNhanVien()     { return nhanVien; }
        public boolean isChon()           { return chon.get(); }
        public void setChon(boolean val)  { chon.set(val); }
        public SimpleBooleanProperty chonProperty() { return chon; }
    }
}