package QUANLINHANSU.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "HopDong")
public class HopDong {

    @Id
    @Column(name = "MaHD")
    private String maHD;

    @Column(name = "LoaiHD")
    private String loaiHD;

    @Column(name = "NgayKi")
    private LocalDate ngayKi;

    @Column(name = "NgayBatDau")
    private LocalDate ngayBatDau;

    @Column(name = "NgayKetThuc")
    private LocalDate ngayKetThuc;

    @Column(name = "LuongCoBan")
    private double luongCoBan;

    @ManyToOne
    @JoinColumn(name = "MaNV")
    private NhanVien nhanVien;

    public HopDong() {
    }

    public HopDong(String maHD, String loaiHD, LocalDate ngayKi,
                   LocalDate ngayBatDau, LocalDate ngayKetThuc,
                   double luongCoBan, NhanVien nhanVien) {
        this.maHD = maHD;
        this.loaiHD = loaiHD;
        this.ngayKi = ngayKi;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.luongCoBan = luongCoBan;
        this.nhanVien = nhanVien;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getLoaiHD() {
        return loaiHD;
    }

    public void setLoaiHD(String loaiHD) {
        this.loaiHD = loaiHD;
    }

    public LocalDate getNgayKi() {
        return ngayKi;
    }

    public void setNgayKi(LocalDate ngayKi) {
        this.ngayKi = ngayKi;
    }

    public LocalDate getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(LocalDate ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public LocalDate getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(LocalDate ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public double getLuongCoBan() {
        return luongCoBan;
    }

    public void setLuongCoBan(double luongCoBan) {
        this.luongCoBan = luongCoBan;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HopDong)) return false;
        HopDong hopDong = (HopDong) o;
        return Objects.equals(maHD, hopDong.maHD);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maHD);
    }
}