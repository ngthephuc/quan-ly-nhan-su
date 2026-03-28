package QUANLINHANSU.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "HopDong")
public class HopDong {

    @Id
    @Column(name = "MaHD")
    private String maHD;

    @Column(name = "LoaiHD",columnDefinition = "NVARCHAR(255)")
    private String loaiHD;

    @Column(name = "LuongCoBan")
    private double luongCoBan;



    public HopDong() {
    }

    public HopDong(String maHD, String loaiHD,
                   double luongCoBan, List<NhanVien> listNhanVien) {
        this.maHD = maHD;
        this.loaiHD = loaiHD;
        this.luongCoBan = luongCoBan;

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


    public double getLuongCoBan() {
        return luongCoBan;
    }

    public void setLuongCoBan(double luongCoBan) {
        this.luongCoBan = luongCoBan;
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