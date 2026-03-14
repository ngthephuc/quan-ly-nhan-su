package QUANLINHANSU.model;

import java.time.LocalDate;

public class Du_An {
    private String maDA;
    private String tenDA;
    private double kinhPhi;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;

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
