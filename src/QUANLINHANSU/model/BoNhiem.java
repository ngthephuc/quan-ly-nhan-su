package QUANLINHANSU.model;


import java.time.LocalDate;

public class BoNhiem {

    private NhanVien nhanVien;
    private ChucVu chucVu;
    private LocalDate tuNgay;
    private LocalDate denNgay;
    private String quyetDinhSo;

    // Constructors, Getters và Setters

    public BoNhiem(NhanVien nhanVien, ChucVu chucVu, LocalDate tuNgay, LocalDate denNgay, String quyetDinhSo) {
        this.nhanVien = nhanVien;
        this.chucVu = chucVu;
        this.tuNgay = tuNgay;
        this.denNgay = denNgay;
        this.quyetDinhSo = quyetDinhSo;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public ChucVu getChucVu() {
        return chucVu;
    }

    public void setChucVu(ChucVu chucVu) {
        this.chucVu = chucVu;
    }

    public LocalDate getTuNgay() {
        return tuNgay;
    }

    public void setTuNgay(LocalDate tuNgay) {
        this.tuNgay = tuNgay;
    }

    public LocalDate getDenNgay() {
        return denNgay;
    }

    public void setDenNgay(LocalDate denNgay) {
        this.denNgay = denNgay;
    }

    public String getQuyetDinhSo() {
        return quyetDinhSo;
    }

    public void setQuyetDinhSo(String quyetDinhSo) {
        this.quyetDinhSo = quyetDinhSo;
    }
}
