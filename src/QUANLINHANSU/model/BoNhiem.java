package QUANLINHANSU.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="BoNhiem")
public class BoNhiem {

    @EmbeddedId
    private BoNhiemId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("maNV")
    @JoinColumn(name = "MaNV",columnDefinition = "NVARCHAR(50)")
    private NhanVien nhanVien;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("maCV")
    @JoinColumn(name = "MaCV",columnDefinition = "NVARCHAR(50)")
    private ChucVu chucVu;

    @Column(name = "DenNgay")
    private LocalDate denNgay;

    @Column(name = "QuyetDinhSo",columnDefinition = "NVARCHAR(255)")
    private String quyetDinhSo;

    public BoNhiem() {
    }

    public BoNhiem(BoNhiemId id, NhanVien nhanVien, ChucVu chucVu
                  , LocalDate denNgay, String quyetDinhSo) {
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