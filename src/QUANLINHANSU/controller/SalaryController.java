package QUANLINHANSU.controller;

import QUANLINHANSU.model.*;
import QUANLINHANSU.service.BoNhiemService;
import QUANLINHANSU.service.Cham_CongService;
import QUANLINHANSU.service.NhanVienService;
import QUANLINHANSU.service.SalaryService;

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

public class SalaryController implements Initializable {
    @FXML private TextField txtMaNV, txtLuongCB, txtPhuCap, txtSoNgayCong, txtPhongBan, txtHeSoLuong,txtMaPhieuLuong,txtNam;
    @FXML private Label lblHoTen, lblLuong;
    @FXML private TableView<SaLary> tblSalary;
    @FXML private TableColumn<SaLary, String> colID, colHoTen, colPhongBan, colMaPhieuLuong;
    @FXML private TableColumn<SaLary, String> colChamCong, colLuong;

    @FXML private ComboBox<NhanVien> cbNhanVien;
    @FXML private ComboBox<Integer> cbThang;


    private final SalaryService salaryService = new SalaryService();
    private final NhanVienService nhanVienService = new NhanVienService();
    private final Cham_CongService  chamCongService = new Cham_CongService();
    //tp
    private final BoNhiemService boNhiemService = new BoNhiemService();
    //
    LocalDate date = LocalDate.now();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupColums();
        setupComboBox();
        loadTable();

        cbNhanVien.setOnAction(event -> hienThiDuLieuTuongDong());
        cbThang.setValue(date.getMonthValue());
        txtNam.setText(date.getYear() + "");
    }


    private void setupColums(){
        colID.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getNhanVien().getMaNV()));
        colHoTen.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getNhanVien().getHoTen()));
        colPhongBan.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getNhanVien().getPhongBan().getTenPb()));
        colChamCong.setCellValueFactory(c ->
                new SimpleStringProperty(String.valueOf(c.getValue().getSoNgayCong())));
//        colLuong.setCellValueFactory(c ->
//                new SimpleStringProperty(String.valueOf(c.getValue().getTongLuongThucNhan())));
        //tp
        colLuong.setCellValueFactory(c -> {
            double value = c.getValue().getTongLuongThucNhan();
            return new SimpleStringProperty(String.format("%,.0f", value));
        });
        //
        colMaPhieuLuong.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getMaPhieuLuong()));
    }

    private void setupComboBox(){
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
    }

    private void loadTable() {
        try {
            // Lấy toàn bộ lịch sử đã tính lương của tất cả NV đang làm
            List<NhanVien> dsNV = nhanVienService.layDangLam();
            List<SaLary> dstinhLuong = dsNV.stream()
                    .flatMap(nv -> salaryService.layLichSuTinhLuong(nv.getMaNV()).stream())
                    .collect(Collectors.toList());
            tblSalary.setItems(FXCollections.observableArrayList(dstinhLuong));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * HÀM QUAN TRỌNG: Tự động lôi dữ liệu từ Database lên Form khi nhập mã NV
     */
    public void tinhLuong() {
        NhanVien nv = cbNhanVien.getValue();
        String maPhieu = txtMaPhieuLuong.getText();
        double soNgayCong = Double.parseDouble(txtSoNgayCong.getText().replaceAll("[^0-9.]", ""));
        if (nv == null) { showAlert(Alert.AlertType.WARNING, "Vui lòng chọn nhân viên!"); return; }

        try {
           salaryService.tinhVaLuuTuDong(nv.getMaNV(),soNgayCong,maPhieu);

            showAlert(Alert.AlertType.INFORMATION, "Tính lương thành công!");
            handleClearForm();
            loadTable();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, e.getMessage());
        }
    }





    @FXML
    public void chonSalary() {
       SaLary sa = tblSalary.getSelectionModel().getSelectedItem();
        if (sa == null) return;
        // Điền NV vào ComboBox
        cbNhanVien.getItems().stream()
                .filter(nv -> nv.getMaNV().equals(sa.getNhanVien().getMaNV()))
                .findFirst().ifPresent(cbNhanVien::setValue);
        txtPhongBan.setText(sa.getNhanVien().getPhongBan().getTenPb());
        txtHeSoLuong.setText(sa.getNhanVien().getPhongBan().getHeSoLuong().toString());
        txtLuongCB.setText(String.valueOf(sa.getLuongCoBan()));
        txtPhuCap.setText(String.format("%.0f", sa.getPhuCapChucVu()));
        txtSoNgayCong.setText(String.format("%.0f", sa.getSoNgayCong()));
//        lblLuong.setText(String.valueOf(sa.getTongLuongThucNhan()));
        //tp
        lblLuong.setText(String.format("%,.0f VNĐ", sa.getTongLuongThucNhan()));
        //
        txtMaPhieuLuong.setText(sa.getMaPhieuLuong());
    }

    @FXML
    void handleClearForm() {
        cbNhanVien.setValue(null);
        txtLuongCB.clear();
        txtPhuCap.clear();
        txtSoNgayCong.clear();
        txtPhongBan.clear();
        txtHeSoLuong.clear();
        lblLuong.setText("0 VNĐ");
        tblSalary.getSelectionModel().clearSelection();
    }


    private void hienThiDuLieuTuongDong(){
        NhanVien nv = cbNhanVien.getValue();
        if(nv == null) return;
        try{
            txtPhongBan.setText(nv.getPhongBan() != null ? nv.getPhongBan().getTenPb() : "N/A");
            txtHeSoLuong.setText(nv.getPhongBan() != null ? String.valueOf(nv.getPhongBan().getHeSoLuong()) : "1.0");
            txtLuongCB.setText(nv.getHopDong() != null ? String.format("%.0f", nv.getHopDong().getLuongCoBan()) : "0");
//            txtPhuCap.setText(String.format("%.0f",nv.getPhuCapHienTai()));
            //tp
            BoNhiem bn = boNhiemService.layChucVuHienTai(nv.getMaNV());

            if (bn != null) {
                double phuCap = bn.getChucVu().getPhuCap();
                txtPhuCap.setText(String.format("%.0f", phuCap));
            } else {
                txtPhuCap.setText("0");
            }
            //
            cbThang.setValue(LocalDate.now().getMonthValue());
            txtNam.setText(LocalDate.now().getYear()+"");
            demNgayCong(nv);
            lblLuong.setText("Đang chờ tính...");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void demNgayCong(NhanVien nv){
        try {
            int thang = cbThang.getValue();
            int nam   = Integer.parseInt(txtNam.getText().trim());
            long ngayCong   = chamCongService.demNgayCong(nv.getMaNV(), thang, nam);
            txtSoNgayCong.setText(ngayCong + " ngày");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, e.getMessage());
        }
    }
    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}