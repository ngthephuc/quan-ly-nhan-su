package QUANLINHANSU.controller;

import QUANLINHANSU.model.SaLary;
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

    // // --- KHAI BÁO CÁC THÀNH PHẦN GIAO DIỆN (@FXML) ---
    // // Các ô nhập liệu và hiển thị thông tin dạng văn bản
    @FXML private TextField txtMaNV; // // Ô nhập Mã nhân viên
    @FXML private TextField txtLuongCB; // // Hiển thị Lương cơ bản (từ Hợp đồng)
    @FXML private TextField txtPhuCap; // // Hiển thị Phụ cấp (từ Chức vụ)
    @FXML private TextField txtSoNgayCong; // // Hiển thị Số ngày làm việc thực tế
    @FXML private TextField txtPhongBan; // // Hiển thị tên Phòng ban của nhân viên
    @FXML private TextField txtHeSoLuong; // // Hiển thị Hệ số lương tương ứng của phòng ban

    // // Các thành phần hiển thị thông tin dạng nhãn (Label)
    @FXML private Label lblHoTen; // // Nhãn hiển thị Họ và tên nhân viên
    @FXML private Label lblLuong; // // Nhãn hiển thị Tổng tiền lương thực nhận (màu đỏ, nổi bật)

    // // Thành phần bảng và các cột dữ liệu
    @FXML private TableView<SaLary> tblSalary; // // Bảng danh sách bảng lương
    @FXML private TableColumn<SaLary, String> colID, colHoTen, colPhongBan; // // Các cột kiểu chữ (Mã NV, Tên, Phòng)
    @FXML private TableColumn<SaLary, Double> colChamCong, colLuong; // // Các cột kiểu số (Công, Tiền lương)

    // // Đối tượng xử lý nghiệp vụ (Service) để tương tác với Database
    private final SalaryService salaryService = new SalaryService();

    // // --- HÀM KHỞI TẠO (initialize) ---
    // // Tự động chạy khi file FXML được nạp lên màn hình
    @FXML
    public void initialize() {
        // // 1. Cấu hình ánh xạ dữ liệu (Mapping) cho từng cột trong TableView
        colID.setCellValueFactory(new PropertyValueFactory<>("maNV"));

        // // Lấy Họ tên thông qua mối quan hệ liên kết với thực thể NhanVien
        colHoTen.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getNhanVien() != null ? cell.getValue().getNhanVien().getHoTen() : "N/A"));

        // // Lấy Tên phòng ban thông qua NhanVien -> PhongBan
        colPhongBan.setCellValueFactory(cell -> new SimpleStringProperty(
                (cell.getValue().getNhanVien() != null && cell.getValue().getNhanVien().getPhongBan() != null)
                        ? cell.getValue().getNhanVien().getPhongBan().getTenPb() : "Chưa có"));

        colChamCong.setCellValueFactory(new PropertyValueFactory<>("soNgayCong"));
        colLuong.setCellValueFactory(new PropertyValueFactory<>("tongLuongThucNhan"));

        // // 2. Định dạng hiển thị tiền lương: Thêm dấu phẩy ngăn cách hàng nghìn (Ví dụ: 1,000,000)
        colLuong.setCellFactory(column -> new TableCell<SaLary, Double>() {
            @Override protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setText(null);
                else setText(String.format("%,.0f", item)); // // Định dạng kiểu số tiền Việt Nam
            }
        });

        // // 3. Tải dữ liệu từ SQL lên bảng ngay khi mở giao diện
        showSalaryData();
    }

    // // --- HÀM CẬP NHẬT GIAO DIỆN (updateUI) ---
    // // Mục đích: Đẩy dữ liệu từ đối tượng SaLary lên các ô nhập liệu phía trên màn hình
    private void updateUI(SaLary s) {
        if (s == null) return;
        txtMaNV.setText(s.getMaNV()); // // Hiện Mã nhân viên
        txtLuongCB.setText(String.format("%.0f", s.getLuongCoBan())); // // Hiện Lương CB (định dạng không số thập phân)
        txtPhuCap.setText(String.format("%.0f", s.getPhuCapChucVu())); // // Hiện Phụ cấp
        txtSoNgayCong.setText(String.valueOf(s.getSoNgayCong())); // // Hiện Số ngày công
        lblLuong.setText(String.format("%,.0f VNĐ", s.getTongLuongThucNhan())); // // Hiện tổng tiền thực nhận

        // // Nếu đối tượng SaLary có chứa thông tin NhanVien đi kèm
        if (s.getNhanVien() != null) {
            lblHoTen.setText(s.getNhanVien().getHoTen()); // // Hiện tên nhân viên
            if (s.getNhanVien().getPhongBan() != null) {
                // // Hiện Phòng ban và Hệ số lương tương ứng
                txtPhongBan.setText(s.getNhanVien().getPhongBan().getTenPb());
                txtHeSoLuong.setText(String.valueOf(s.getNhanVien().getPhongBan().getHeSoLuong()));
            }
        }
    }

    // // --- SỰ KIỆN NÚT [TÍNH LƯƠNG] ---
    @FXML
    void handleCalculateSalary(ActionEvent event) {
        String maNV = txtMaNV.getText().trim();
        if (maNV.isEmpty()) return; // // Kiểm tra nếu chưa nhập mã NV thì dừng lại
        try {
            // // 1. Gọi Service thực hiện logic tính lương (Lương gốc * Hệ số / 26 * Công + Phụ cấp)
            // // Tính theo Tháng và Năm hiện tại của hệ thống
            salaryService.tinhVaLuuTuDong(maNV, LocalDate.now().getMonthValue(), LocalDate.now().getYear());

            // // 2. Làm mới bảng dữ liệu để hiển thị kết quả vừa tính
            showSalaryData();

            // // 3. Truy vấn lại bản ghi vừa lưu để hiển thị chi tiết lên Form phía trên
            updateUI(salaryService.timTheoMa(maNV));

            // // Hiện thông báo thành công
            new Alert(Alert.AlertType.INFORMATION, "Tính lương thành công!").show();
        } catch (Exception e) {
            // // Hiện thông báo lỗi nếu có vấn đề xảy ra (ví dụ: Nhân viên chưa có hợp đồng)
            new Alert(Alert.AlertType.ERROR, "Lỗi: " + e.getMessage()).show();
        }
    }

    // // --- SỰ KIỆN CLICK CHUỘT VÀO BẢNG (handleTableClick) ---
    @FXML
    void handleTableClick(MouseEvent event) {
        // // Lấy đối tượng SaLary tại dòng mà người dùng vừa click chuột
        SaLary selected = tblSalary.getSelectionModel().getSelectedItem();
        if (selected != null) {
            updateUI(selected); // // Đẩy thông tin của bản ghi đó lên các ô nhập liệu
        }
    }

    // // --- HÀM TẢI DỮ LIỆU (showSalaryData) ---
    // // Mục đích: Lấy toàn bộ danh sách bảng lương từ Database đổ vào TableView
    public void showSalaryData() {
        try {
            List<SaLary> list = salaryService.layTatCa(); // // Gọi Service lấy danh sách
            // // Chuyển danh sách thành ObservableList để TableView có thể lắng nghe thay đổi
            tblSalary.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) { e.printStackTrace(); }
    }

    // // --- SỰ KIỆN NÚT [LÀM SẠCH] ---
    @FXML
    void handleClearForm(ActionEvent event) {
        // // Xóa trắng toàn bộ dữ liệu đang hiển thị trên các ô nhập liệu và nhãn
        txtMaNV.clear(); txtLuongCB.clear(); txtPhuCap.clear(); txtSoNgayCong.clear();
        txtPhongBan.clear(); txtHeSoLuong.clear();
        lblHoTen.setText("---");
        lblLuong.setText("0 VNĐ");

        // // Bỏ chọn dòng trong bảng TableView
        tblSalary.getSelectionModel().clearSelection();
    }
}