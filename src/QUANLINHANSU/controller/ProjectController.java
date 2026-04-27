package QUANLINHANSU.controller;

import QUANLINHANSU.model.Du_An;
import QUANLINHANSU.model.NhanVien;
import QUANLINHANSU.model.ThamGia;
import QUANLINHANSU.service.DuAnService;
import QUANLINHANSU.service.ThamGiaService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.net.URL;
import java.time.LocalDate;

public class ProjectController {

    // --- TableView Dự Án ---
    @FXML private TableView<Du_An> tbProject;
    @FXML private TableColumn<Du_An, String> colProjectID, colNameProject;
    @FXML private TableColumn<Du_An, Double> colBudgetProject;
    @FXML private TableColumn<Du_An, Integer> colNumMenberProject;
    @FXML private TableColumn<Du_An, LocalDate> colDateStartProject, colDateEndProject;

    // --- Form nhập liệu Dự Án (TextField) ---
    @FXML private TextField IDProject, NameProject, BudgetProject;
    @FXML private DatePicker DateStartProject, DateEndProject;

    // --- Form Nhân viên trong Dự Án (TextField) ---
//    @FXML private TextField IDEmployeeProject, IDProjectEmployee, EmployeePost;
    @FXML private TextField IDEmployeeProject, IDProjectEmployee;
    private final DuAnService duAnService = new DuAnService();
    private final ThamGiaService thamGiaService = new ThamGiaService();

    @FXML
    public void initialize() {
        // Mapping cột
        colProjectID.setCellValueFactory(new PropertyValueFactory<>("maDA"));
        colNameProject.setCellValueFactory(new PropertyValueFactory<>("tenDA"));
        colBudgetProject.setCellValueFactory(new PropertyValueFactory<>("kinhPhi"));
        colNumMenberProject.setCellValueFactory(new PropertyValueFactory<>("soNguoiThamGia"));

        if (colDateStartProject != null) colDateStartProject.setCellValueFactory(new PropertyValueFactory<>("ngayBatDau"));
        if (colDateEndProject != null) colDateEndProject.setCellValueFactory(new PropertyValueFactory<>("ngayKetThuc"));

        loadData();

        // Đổ dữ liệu lên Form khi chọn dòng trên Table
        tbProject.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> {
            if (selected != null) {
                IDProject.setText(selected.getMaDA());
                NameProject.setText(selected.getTenDA());
                BudgetProject.setText(String.valueOf(selected.getKinhPhi()));
                DateStartProject.setValue(selected.getNgayBatDau());
                DateEndProject.setValue(selected.getNgayKetThuc());
                IDProjectEmployee.setText(selected.getMaDA());
            }
        });
    }

    public void loadData() {
        try {
            tbProject.setItems(FXCollections.observableArrayList(duAnService.layTatCaKemSoLuong()));
        } catch (Exception e) { e.printStackTrace(); }
    }

    // ===================== CÁC HÀM XỬ LÝ SỰ KIỆN (EVENT HANDLERS) =====================

    // 1. Thêm dự án
    @FXML void addProject(ActionEvent event) {
        try {
            duAnService.themDuAn(getDuAnTuForm());
            hienThongBao("Thành công", "Đã thêm dự án!");
            loadData();
        } catch (Exception e) { hienThongBao("Lỗi", e.getMessage()); }
    }

    // 2. Sửa dự án
    @FXML void editProject(ActionEvent event) {
        try {
            duAnService.capNhatDuAn(getDuAnTuForm());
            hienThongBao("Thành công", "Đã cập nhật dự án!");
            loadData();
        } catch (Exception e) { hienThongBao("Lỗi", e.getMessage()); }
    }

    // 3. Xóa dự án
    @FXML void deleteProject(ActionEvent event) {
        try {
            duAnService.xoaDuAn(IDProject.getText());
            hienThongBao("Thành công", "Đã xóa dự án!");
            loadData();
        } catch (Exception e) { hienThongBao("Lỗi", e.getMessage()); }
    }

    // 4. Thêm nhân viên vào dự án (Lỗi dòng 55 cũ)
    @FXML void addProjectEmployee(ActionEvent event) {
        try {
            thamGiaService.themThamGiaChuan(getThamGiaTuForm());
            hienThongBao("Thành công", "Đã thêm nhân viên vào dự án!");
            loadData();
        } catch (Exception e) { hienThongBao("Lỗi", e.getMessage()); }
    }

    // 5. Sửa thông tin nhân viên trong dự án (FIX LỖI DÒNG 56 ÔNG VỪA BÁO)
//    @FXML void editProjectEmployee(ActionEvent event) {
//        String maNV = IDEmployeeProject.getText();
//        String maDA = IDProjectEmployee.getText();
//        String vaiTroMoi = EmployeePost.getText();
//
//        if (maNV.isEmpty() || maDA.isEmpty()) {
//            hienThongBao("Lỗi", "Vui lòng nhập Mã NV và Mã DA để sửa!");
//            return;
//        }
//
//        try {
//            // GỌI SERVICE THẬT CHỨ KHÔNG HIỆN THÔNG BÁO TÀO LAO NỮA
//            thamGiaService.capNhatVaiTro(maNV, maDA, vaiTroMoi);
//            loadData(); // Load lại bảng để cập nhật dữ liệu
//            hienThongBao("Thành công", "Đã cập nhật chức vụ mới cho nhân viên " + maNV);
//        } catch (Exception e) {
//            hienThongBao("Lỗi", e.getMessage());
//        }
//    }

    // 6. Xóa nhân viên khỏi dự án
    @FXML void deleteProjectEmployee(ActionEvent event) {
        String maNV = IDEmployeeProject.getText();
        String maDA = IDProjectEmployee.getText();

        // 1. Kiểm tra xem ông có nhập mã vào ô chưa
        if (maNV.isEmpty() || maDA.isEmpty()) {
            hienThongBao("Lỗi", "Vui lòng nhập đủ Mã NV và Mã DA ở các ô phía dưới để xóa!");
            return;
        }

        // 2. Hiện bảng xác nhận (Alert Confirmation) cho chắc ăn
        Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
        conf.setTitle("Xác nhận xóa");
        conf.setHeaderText("Bạn có chắc muốn xóa nhân viên này khỏi dự án?");
        conf.setContentText("Mã NV: " + maNV + "\nMã Dự Án: " + maDA);

        // 3. Nếu ông bấm OK thì mới tiến hành xóa
        if (conf.showAndWait().get() == ButtonType.OK) {
            try {
                // Gọi Service thật để xóa trong DB
                thamGiaService.xoaKhoiDuAn(maNV, maDA);

                // Load lại bảng chính để con số "Số thành viên" cập nhật lại
                loadData();

                hienThongBao("Thành công", "Đã xóa nhân viên " + maNV + " khỏi dự án!");

                // Xóa trắng các ô nhập liệu sau khi xóa xong cho sạch
                IDEmployeeProject.clear();
//                EmployeePost.clear();

            } catch (Exception e) {
                // Nếu xóa lỗi (ví dụ không tìm thấy bản ghi) thì báo ở đây
                hienThongBao("Lỗi", "Không thể xóa: " + e.getMessage());
            }
        }
    }

    // 7. Nút xem Chi tiết dự án
    @FXML void butTTCT(ActionEvent event) {
        Du_An s = tbProject.getSelectionModel().getSelectedItem();
        if (s == null) return;
        try {
            URL loc = getClass().getResource("/fxml/DetailedInformationProject.fxml");
            FXMLLoader loader = new FXMLLoader(loc);
            Parent root = loader.load();
            DetailedInfoProjectController ct = loader.getController();
            ct.loadLabel(s.getNgayBatDau(), s.getNgayKetThuc(), s.getKinhPhi(), s.getMaDA(), s.getTenDA());
            ct.loadNhanVienTheoDuAn(s.getMaDA());
            Stage st = new Stage(); st.setScene(new Scene(root)); st.initModality(Modality.APPLICATION_MODAL); st.show();
        } catch (Exception e) { e.printStackTrace(); }
    }

    // ===================== HÀM PHỤ TRỢ =====================

    private Du_An getDuAnTuForm() {
        return new Du_An(IDProject.getText(), NameProject.getText(),
                Double.parseDouble(BudgetProject.getText().isEmpty() ? "0" : BudgetProject.getText()),
                DateStartProject.getValue(), DateEndProject.getValue());
    }

    private ThamGia getThamGiaTuForm() {
        ThamGia tg = new ThamGia();
        NhanVien nv = new NhanVien(); nv.setMaNV(IDEmployeeProject.getText());
        Du_An da = new Du_An(); da.setMaDA(IDProjectEmployee.getText());
        tg.setNhanVien(nv); tg.setDuAn(da);
//        tg.setVaiTro(EmployeePost.getText());
        tg.setVaiTro("");
        tg.setSoGio(0);
        return tg;
    }

    private void hienThongBao(String t, String c) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(t); a.setHeaderText(null); a.setContentText(c); a.showAndWait();
    }
    private boolean checkTrongDA() {
        if (IDProject.getText().isEmpty() || NameProject.getText().isEmpty()) {
            hienThongBao("Lỗi nhập liệu", "Mã và Tên dự án không được để trống!");
            return false;
        }
        try {
            Double.parseDouble(BudgetProject.getText());
        } catch (Exception e) {
            hienThongBao("Lỗi", "Kinh phí phải là con số!");
            return false;
        }
        return true;
    }

}