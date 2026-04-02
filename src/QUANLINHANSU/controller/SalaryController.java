package QUANLINHANSU.controller;

import QUANLINHANSU.model.SaLary;
import QUANLINHANSU.model.NhanVien;
import QUANLINHANSU.model.ChucVu;
import QUANLINHANSU.service.SalaryService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.time.LocalDate;
import java.util.List;

public class SalaryController {

    @FXML private TextField txtMaNV, txtLuongCB, txtPhuCap, txtSoNgayCong, txtPhongBan, txtHeSoLuong;
    @FXML private Label lblHoTen, lblLuong;
    @FXML private TableView<SaLary> tblSalary;
    @FXML private TableColumn<SaLary, String> colID, colHoTen, colPhongBan;
    @FXML private TableColumn<SaLary, Double> colChamCong, colLuong;

    private final SalaryService salaryService = new SalaryService();

    @FXML
    public void initialize() {
        // 1. Ánh xạ cột ID (Mã NV)
        colID.setCellValueFactory(new PropertyValueFactory<>("maNV"));

        // 2. Ánh xạ Họ tên
        colHoTen.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getNhanVien() != null ? cell.getValue().getNhanVien().getHoTen() : "N/A"));

        // 3. CHIẾN THUẬT: Hiển thị Chức vụ (từ Bổ nhiệm) + Phòng ban lên TableView
        colPhongBan.setCellValueFactory(cell -> {
            NhanVien nv = cell.getValue().getNhanVien();
            if (nv != null) {
                String tenCV = (nv.getChucVuHienTai() != null) ? nv.getChucVuHienTai().getTenCV() : "Chưa CV";
                String tenPB = (nv.getPhongBan() != null) ? nv.getPhongBan().getTenPb() : "Chưa PB";
                return new SimpleStringProperty(tenCV + " - " + tenPB);
            }
            return new SimpleStringProperty("N/A");
        });

        colChamCong.setCellValueFactory(new PropertyValueFactory<>("soNgayCong"));
        colLuong.setCellValueFactory(new PropertyValueFactory<>("tongLuongThucNhan"));

        // 4. Định dạng tiền tệ cho cột Lương
        colLuong.setCellFactory(column -> new TableCell<SaLary, Double>() {
            @Override protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setText(null);
                else setText(String.format("%,.0f", item));
            }
        });

        showSalaryData();
    }

    private void updateUI(SaLary s) {
        if (s == null) return;
        txtMaNV.setText(s.getMaNV());
        txtLuongCB.setText(String.format("%.0f", s.getLuongCoBan()));

        // Lấy phụ cấp từ bản ghi lương, nếu = 0 thì thử lấy từ chức vụ hiện tại
        double phuCapVal = s.getPhuCapChucVu();
        if (phuCapVal == 0 && s.getNhanVien() != null && s.getNhanVien().getChucVuHienTai() != null) {
            phuCapVal = s.getNhanVien().getChucVuHienTai().getPhuCap();
        }
        txtPhuCap.setText(String.format("%.0f", phuCapVal));

        txtSoNgayCong.setText(String.valueOf(s.getSoNgayCong()));
        lblLuong.setText(String.format("%,.0f VNĐ", s.getTongLuongThucNhan()));

        if (s.getNhanVien() != null) {
            NhanVien nv = s.getNhanVien();
            // Hiển thị tên kèm chức vụ cho chuyên nghiệp
            String chucVuStr = (nv.getChucVuHienTai() != null) ? " [" + nv.getChucVuHienTai().getTenCV() + "]" : "";
            lblHoTen.setText(nv.getHoTen() + chucVuStr);

            if (nv.getPhongBan() != null) {
                txtPhongBan.setText(nv.getPhongBan().getTenPb());
                txtHeSoLuong.setText(String.valueOf(nv.getPhongBan().getHeSoLuong()));
            }
        }
    }

    @FXML
    void handleCalculateSalary(ActionEvent event) {
        String maNV = txtMaNV.getText().trim();
        if (maNV.isEmpty()) return;
        try {
            // Tính toán và lưu
            salaryService.tinhVaLuuTuDong(maNV, LocalDate.now().getMonthValue(), LocalDate.now().getYear());

            // Reload bảng và cập nhật Form
            showSalaryData();
            updateUI(salaryService.timTheoMa(maNV));

            new Alert(Alert.AlertType.INFORMATION, "Tính lương thành công!").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Lỗi: " + e.getMessage()).show();
        }
    }

    @FXML
    void handleTableClick(MouseEvent event) {
        SaLary selected = tblSalary.getSelectionModel().getSelectedItem();
        if (selected != null) {
            updateUI(selected);
        }
    }

    public void showSalaryData() {
        try {
            List<SaLary> list = salaryService.layTatCa();
            tblSalary.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    void handleClearForm(ActionEvent event) {
        txtMaNV.clear(); txtLuongCB.clear(); txtPhuCap.clear(); txtSoNgayCong.clear();
        txtPhongBan.clear(); txtHeSoLuong.clear();
        lblHoTen.setText("---");
        lblLuong.setText("0 VNĐ");
        tblSalary.getSelectionModel().clearSelection();
    }
}