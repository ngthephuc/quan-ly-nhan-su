package QUANLINHANSU.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "ChucVu")
public class ChucVu {

    @Id
    @Column(name = "MaCV" ,columnDefinition = "NVARCHAR(50)")
    private String maCV;

    @Column(name = "TenCV",columnDefinition = "NVARCHAR(255)")
    private String tenCV;

    @Column(name = "PhuCap")
    private Double phuCap;

    @OneToMany(mappedBy = "chucVu")
    private List<BoNhiem> danhSachBoNhiem;

    public ChucVu() {
    }

    public ChucVu(String maCV, String tenCV, Double phuCap) {
        this.maCV = maCV;
        this.tenCV = tenCV;
        this.phuCap = phuCap;
    }

    public String getMaCV() {
        return maCV;
    }

    public void setMaCV(String maCV) {
        this.maCV = maCV;
    }

    public String getTenCV() {
        return tenCV;
    }

    public void setTenCV(String tenCV) {
        this.tenCV = tenCV;
    }

    public Double getPhuCap() {
        return phuCap;
    }

    public void setPhuCap(Double phuCap) {
        this.phuCap = phuCap;
    }

    public List<BoNhiem> getDanhSachBoNhiem() {
        return danhSachBoNhiem;
    }

    public void setDanhSachBoNhiem(List<BoNhiem> danhSachBoNhiem) {
        this.danhSachBoNhiem = danhSachBoNhiem;
    }
}