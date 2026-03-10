package QUANLINHANSU.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "DuAn")
public class Du_An {

    @Id
    @Column(name = "MaDA")
    private String maDA;

    @Column(name = "TenDA")
    private String tenDA;

    @Column(name = "KinhPhi")
    private double kinhPhi;

    @Column(name = "NgayBatDau")
    private LocalDate ngayBatDau;

    @Column(name = "NgayKetThuc")
    private LocalDate ngayKetThuc;

    public Du_An(String p1, String cHIEN, int i, int i1, LocalDate localDate, LocalDate date) {
    }

    public Du_An(String maDA, String tenDA, double kinhPhi,
                 LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        this.maDA = maDA;
        this.tenDA = tenDA;
        this.kinhPhi = kinhPhi;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getMaDA() {
        return maDA;
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
}