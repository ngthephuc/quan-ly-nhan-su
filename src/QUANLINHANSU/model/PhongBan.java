package QUANLINHANSU.model;

import QUANLINHANSU.service.BoNhiemService;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "PhongBan")
public class PhongBan {

    @Id
    @Column(name = "MaPhongBan",columnDefinition = "NVARCHAR(50)")
    private String maPb;

    @Column(name = "TenPhongBan", columnDefinition = "NVARCHAR(255)")
    private String tenPb;

    @Column(name = "HeSoLuong")
    private Double heSoLuong;
//tp sửa
    @ManyToOne
    @JoinColumn(name = "MaTruongPhong")
    private NhanVien truongPhong;
    public NhanVien getTruongPhong() {
        return truongPhong;
    }

    public void setTruongPhong(NhanVien truongPhong) {
        this.truongPhong = truongPhong;
    }
//
    @OneToMany(mappedBy = "phongBan")
    private List<NhanVien> danhSachNhanVien;

    public PhongBan() {
    }

    public PhongBan(String maPb, String tenPb, Double heSoLuong) {
        this.maPb = maPb;
        this.tenPb = tenPb;
        this.heSoLuong = heSoLuong;
    }

    public PhongBan(String maPb, String tenPb, Double heSoLuong, List<NhanVien> danhSachNhanVien) {
        this.maPb = maPb;
        this.tenPb = tenPb;
        this.heSoLuong = heSoLuong;
        this.danhSachNhanVien = danhSachNhanVien;
    }


    public List<NhanVien> getDanhSachNhanVien() {
        return danhSachNhanVien;
    }

    public void setDanhSachNhanVien(List<NhanVien> danhSachNhanVien) {
        this.danhSachNhanVien = danhSachNhanVien;
    }

    public String getMaPb() {
        return maPb;
    }

    public void setMaPb(String maPb) {
        this.maPb = maPb;
    }

    public String getTenPb() {
        return tenPb;
    }

    public void setTenPb(String tenPb) {
        this.tenPb = tenPb;
    }

    public Double getHeSoLuong() {
        return heSoLuong;
    }

    public void setHeSoLuong(Double heSoLuong) {
        this.heSoLuong = heSoLuong;
    }

    public void getTruongPhongBan() {
        BoNhiemService boNhiem = new BoNhiemService();
    }

    @Override
    public String toString() {
        return "PhongBan{" +
                "maPb='" + maPb + '\'' +
                ", tenPb='" + tenPb + '\'' +
                ", heSoLuong=" + heSoLuong +
                '}';
    }
}