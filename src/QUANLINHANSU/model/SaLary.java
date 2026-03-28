package QUANLINHANSU.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "BangLuong")
public class SaLary implements Serializable {

    @Id
    @Column(name = "MaNV")
    private String maNV;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "MaNV")
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

    // CONSTRUCTOR CHIẾN THUẬT: Gán ID ngay lập tức để tránh Null Identifier
    public SaLary(NhanVien nv) {
        this.nhanVien = nv;
        if (nv != null) {
            this.maNV = nv.getMaNV();
        }
    }

    // Getter và Setter
    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }
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