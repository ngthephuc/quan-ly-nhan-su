package QUANLINHANSU.model;

import jakarta.persistence.*;

@Entity
@Table(name = "PhongBan")
public class PhongBan {

    @Id
    @Column(name = "MaPhongBan")
    private String maPb;

    @Column(name = "TenPhongBan")
    private String tenPb;

    @Column(name = "HeSoLuong")
    private Double heSoLuong;

    public PhongBan() {
    }

    public PhongBan() {
    }

    public PhongBan(String maPb, String tenPb, Double heSoLuong) {
        this.maPb = maPb;
        this.tenPb = tenPb;
        this.heSoLuong = heSoLuong;
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

    @Override
    public String toString() {
        return "PhongBan{" +
                "maPb='" + maPb + '\'' +
                ", tenPb='" + tenPb + '\'' +
                ", heSoLuong=" + heSoLuong +
                '}';
    }
}