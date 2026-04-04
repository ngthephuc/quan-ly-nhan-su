package QUANLINHANSU.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "BangLuong")
public class SaLary implements Serializable {

    @Id
    @Column(name = "MaPhieuLuong", columnDefinition = "NVARCHAR(50)")
    private String maPhieuLuong;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MaNV" ,columnDefinition = "NVARCHAR(50)" )
    private NhanVien nhanVien;

    @Column(name = "luongCoBan")
    private double luongCoBan;

    @Column(name = "phuCapChucVu")
    private double phuCapChucVu;

    @Column(name = "soNgayCong")
    private double soNgayCong;

    @Column(name = "tongLuongThucNhan")
    private double tongLuongThucNhan;

    public SaLary() {}

    // Getter và Setter

    public String getMaPhieuLuong() {
        return maPhieuLuong;
    }

    public void setMaPhieuLuong(String maPhieuLuong) {
        this.maPhieuLuong = maPhieuLuong;
    }

    public NhanVien getNhanVien() { return nhanVien; }
    public void setNhanVien(NhanVien nhanVien) { this.nhanVien = nhanVien; }
    public double getLuongCoBan() { return luongCoBan; }
    public void setLuongCoBan(double luongCoBan) { this.luongCoBan = luongCoBan; }
    public double getPhuCapChucVu() { return phuCapChucVu; }
    public void setPhuCapChucVu(double phuCapChucVu) { this.phuCapChucVu = phuCapChucVu; }
    public double getSoNgayCong() { return soNgayCong; }
    public void setSoNgayCong(double soNgayCong) { this.soNgayCong = soNgayCong; }
    public double getTongLuongThucNhan() { return tongLuongThucNhan; }
    public void setTongLuongThucNhan(double tongLuongThucNhan) { this.tongLuongThucNhan = tongLuongThucNhan; }
}