package QUANLINHANSU.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
@Entity
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

    @OneToMany(mappedBy = "nhanVien")
    private List<BoNhiem> danhSachBoNhiem;
    public NhanVien() {
    }

    public NhanVien(String maNV, String hoTen, PhongBan phongBan, String trangThai, LocalDate ngayVaoLam, String email, String sdt, String diaChi, String cccd, String gioiTinh, LocalDate ngaySinh) {
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


    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public LocalDate getNgayVaoLam() {
        return ngayVaoLam;
    }

    public void setNgayVaoLam(LocalDate ngayVaoLam) {
        this.ngayVaoLam = ngayVaoLam;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public PhongBan getPhongBan() {
        return phongBan;
    }

    public void setPhongBan(PhongBan phongBan) {
        this.phongBan = phongBan;
    }

    public List<BoNhiem> getDanhSachBoNhiem() {
        return danhSachBoNhiem;
    }

    public void setDanhSachBoNhiem(List<BoNhiem> danhSachBoNhiem) {
        this.danhSachBoNhiem = danhSachBoNhiem;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        NhanVien nhanVien = (NhanVien) o;
        return Objects.equals(maNV, nhanVien.maNV) && Objects.equals(hoTen, nhanVien.hoTen) && Objects.equals(ngaySinh, nhanVien.ngaySinh) && Objects.equals(gioiTinh, nhanVien.gioiTinh) && Objects.equals(cccd, nhanVien.cccd) && Objects.equals(diaChi, nhanVien.diaChi) && Objects.equals(email, nhanVien.email) && Objects.equals(sdt, nhanVien.sdt) && Objects.equals(ngayVaoLam, nhanVien.ngayVaoLam) && Objects.equals(trangThai, nhanVien.trangThai) && Objects.equals(phongBan, nhanVien.phongBan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maNV, hoTen, ngaySinh, gioiTinh, cccd, diaChi, email, sdt, ngayVaoLam, trangThai, phongBan);
    }

    @Override
    public String toString() {
         return "NhanVien{" +
                "maNV='" + maNV + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", ngaySinh=" + ngaySinh +
                ", gioiTinh='" + gioiTinh + '\'' +
                ", cccd='" + cccd + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", email='" + email + '\'' +
                ", sdt='" + sdt + '\'' +
                ", ngayVaoLam=" + ngayVaoLam +
                ", trangThai='" + trangThai + '\'' +
                ", phongBan=" + phongBan +
                '}';
    }


}
