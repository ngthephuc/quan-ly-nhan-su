package QUANLINHANSU.model;


import java.time.LocalDate;

public class BoNhiem {

    private BoNhiemId id;
    private NhanVien nhanVien;
    private ChucVu chucVu;
    private LocalDate denNgay;
    private String quyetDinhSo;

    // Constructors, Getters và Setters

    public BoNhiem() {
    }

    public BoNhiem(BoNhiemId id, NhanVien nhanVien, ChucVu chucVu, LocalDate denNgay, String quyetDinhSo) {
        this.id = id;
        this.nhanVien = nhanVien;
        this.chucVu = chucVu;
        this.denNgay = denNgay;
        this.quyetDinhSo = quyetDinhSo;
    }

    public BoNhiemId getId() {
        return id;
    }

    public void setId(BoNhiemId id) {
        this.id = id;
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
        return id != null ? id.getTuNgay() : null;
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
