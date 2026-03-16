package QUANLINHANSU.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "DuAn")

public class Du_An {
    @Id
    @Column(name = "MaDuAn")
    private String maDA;
    @Column(name = "TenDuAn")
    private String tenDA;
    @Column(name = "KinhPhi")
    private double kinhPhi;
    @Column(name = "Ngaybatdau")
    private LocalDate ngayBatDau;
    @Column(name = "Ngayketthuc")
    private LocalDate ngayKetThuc;
    @OneToMany(mappedBy = "duAn")
    private List<ThamGia> danhSachThamGia;
    public Du_An() {
    }

    public Du_An(String maDA, String tenDA, double kinhPhi, LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        this.maDA = maDA;
        this.tenDA = tenDA;
        this.kinhPhi = kinhPhi;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getMaDA() {
        return maDA;
    }

    public List<ThamGia> getDanhSachThamGia() {
        return danhSachThamGia;
    }

    public void setDanhSachThamGia(List<ThamGia> danhSachThamGia) {
        this.danhSachThamGia = danhSachThamGia;
    }

    public void setMaDA(String maDA) {
        this.maDA = maDA;
    }

    public String getTenDA() {
        return tenDA;
    }

    public void setTenDA(String tenDA) {
        this.tenDA = tenDA;
    }

    public double getKinhPhi() {
        return kinhPhi;
    }

    public void setKinhPhi(double kinhPhi) {
        this.kinhPhi = kinhPhi;
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

    @Override
    public String toString() {
        return "DuAn{" +
                "maDA='" + maDA + '\'' +
                ", tenDA='" + tenDA + '\'' +
                ", kinhPhi=" + kinhPhi +
                ", ngayBatDau='" + ngayBatDau + '\'' +
                ", ngayKetThuc='" + ngayKetThuc + '\'' +
                '}';
    }
}
