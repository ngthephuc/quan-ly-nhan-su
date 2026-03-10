package QUANLINHANSU.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "NhanVien")
public class NhanVien {

    @Id
    @Column(name = "MaNV")
    private String maNV;

    @Column(name = "HoTen")
    private String hoTen;

    @Column(name = "NgaySinh")
    private LocalDate ngaySinh;

    @Column(name = "GioiTinh")
    private String gioiTinh;

    @Column(name = "CCCD")
    private String cccd;

    @Column(name = "DiaChi")
    private String diaChi;

    @Column(name = "Email")
    private String email;

    @Column(name = "SDT")
    private String sdt;

    @Column(name = "NgayVaoLam")
    private LocalDate ngayVaoLam;

    @Column(name = "TrangThai")
    private String trangThai;

    @ManyToOne
    @JoinColumn(name = "MaPhongBan")
    private PhongBan phongBan;

    public NhanVien() {
    }

    public NhanVien(String maNV, String hoTen, PhongBan phongBan, String trangThai,
                    LocalDate ngayVaoLam, String email, String sdt, String diaChi,
                    String cccd, String gioiTinh, LocalDate ngaySinh) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.phongBan = phongBan;
        this.trangThai = trangThai;
        this.ngayVaoLam = ngayVaoLam;
        this.email = email;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.cccd = cccd;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
    }

    // getters + setters
}