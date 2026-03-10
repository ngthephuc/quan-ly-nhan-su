package QUANLINHANSU.model;

import java.time.LocalDate;
import java.util.Objects;

public class HopDong {
    private String maHD;
    private String loaiHD;
    private LocalDate ngayKi;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;

    private double luongCoBan;

    private NhanVien nhanVien;

    public HopDong() {
    }

    public HopDong(String maHD, String loaiHD, LocalDate ngayKi, LocalDate ngayBatDau, LocalDate ngayKetThuc, double luongCoBan, NhanVien nhanVien) {
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
        if (o == null || getClass() != o.getClass()) return false;
        HopDong hopDong = (HopDong) o;
        return Double.compare(luongCoBan, hopDong.luongCoBan) == 0 && Objects.equals(maHD, hopDong.maHD) && Objects.equals(loaiHD, hopDong.loaiHD) && Objects.equals(ngayKi, hopDong.ngayKi) && Objects.equals(ngayBatDau, hopDong.ngayBatDau) && Objects.equals(ngayKetThuc, hopDong.ngayKetThuc) && Objects.equals(nhanVien, hopDong.nhanVien);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maHD, loaiHD, ngayKi, ngayBatDau, ngayKetThuc, luongCoBan, nhanVien);
    }
}
