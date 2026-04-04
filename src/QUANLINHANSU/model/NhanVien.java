package QUANLINHANSU.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
@Entity
public class NhanVien {
    @Id
    @Column(name = "MaNV" ,columnDefinition = "NVARCHAR(50)")
    private String maNV;

    @Column(name = "HoTen" , columnDefinition = "NVARCHAR(255)")
    private String hoTen;

    @Column(name = "NgaySinh")
    private LocalDate ngaySinh;

    @Column(name = "GioiTinh",columnDefinition = "NVARCHAR(255)")
    private String gioiTinh;

    @Column(name = "CCCD",columnDefinition = "NVARCHAR(255)")
    private String cccd;

    @Column(name = "DiaChi",columnDefinition = "NVARCHAR(255)")
    private String diaChi;

    @Column(name = "Email",columnDefinition = "NVARCHAR(255)")
    private String email;

    @Column(name = "SDT" ,columnDefinition = "NVARCHAR(255)")
    private String sdt;

    @Column(name = "NgayVaoLam")
    private LocalDate ngayVaoLam;

    @Column(name = "TrangThai",columnDefinition = "NVARCHAR(255)")
    private String trangThai;

    @ManyToOne
    @JoinColumn(name = "MaPhongBan",columnDefinition = "NVARCHAR(50)")
    private PhongBan phongBan;

    @ManyToOne
    @JoinColumn(name = "MaHD",columnDefinition = "NVARCHAR(50)")
    private HopDong hopDong;

    @OneToMany(mappedBy = "nhanVien" ,fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<BoNhiem> danhSachBoNhiem;


    @OneToMany(mappedBy = "nhanVien",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ThamGia> danhSachThamGia;

    @OneToMany(mappedBy = "nhanVien",fetch =  FetchType.EAGER)
    private List<Cham_Cong> danhSachChamCong;

    @OneToMany(mappedBy = "nhanVien",fetch =  FetchType.EAGER, cascade = CascadeType.ALL)
    private List<SaLary> danhSachLuong;

    public NhanVien() {
    }

    public NhanVien(String maNV, String hoTen, LocalDate ngaySinh, String gioiTinh, String cccd, String diaChi, String email, String sdt, LocalDate ngayVaoLam, String trangThai, PhongBan phongBan, HopDong hopDong, List<BoNhiem> danhSachBoNhiem, List<ThamGia> danhSachThamGia, List<Cham_Cong> danhSachChamCong, List<SaLary> danhSachLuong) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.cccd = cccd;
        this.diaChi = diaChi;
        this.email = email;
        this.sdt = sdt;
        this.ngayVaoLam = ngayVaoLam;
        this.trangThai = trangThai;
        this.phongBan = phongBan;
        this.hopDong = hopDong;
        this.danhSachBoNhiem = danhSachBoNhiem;
        this.danhSachThamGia = danhSachThamGia;
        this.danhSachChamCong = danhSachChamCong;
        this.danhSachLuong = danhSachLuong;
    }

    public List<SaLary> getDanhSachLuong() {
        return danhSachLuong;
    }

    public void setDanhSachLuong(List<SaLary> danhSachLuong) {
        this.danhSachLuong = danhSachLuong;
    }

    public List<Cham_Cong> getDanhSachChamCong() {
        return danhSachChamCong;
    }

    public void setDanhSachChamCong(List<Cham_Cong> danhSachChamCong) {
        this.danhSachChamCong = danhSachChamCong;
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

    public HopDong getHopDong() {
        return hopDong;
    }

    public void setHopDong(HopDong hopDong) {
        this.hopDong = hopDong;
    }

    public List<BoNhiem> getDanhSachBoNhiem() {
        return danhSachBoNhiem;
    }

    public void setDanhSachBoNhiem(List<BoNhiem> danhSachBoNhiem) {
        this.danhSachBoNhiem = danhSachBoNhiem;
    }

    public List<ThamGia> getDanhSachThamGia() {
        return danhSachThamGia;
    }

    public void setDanhSachThamGia(List<ThamGia> danhSachThamGia) {
        this.danhSachThamGia = danhSachThamGia;
    }

    public double getPhuCapHienTai() {
        if (danhSachBoNhiem == null || danhSachBoNhiem.isEmpty()) {
            return 0;
        }

        // Logic: Tìm bản ghi bổ nhiệm đang có hiệu lực (DenNgay == null)
        for (BoNhiem bn : danhSachBoNhiem) {
            if (bn.getDenNgay() == null) {
                return bn.getChucVu().getPhuCap();
            }
        }

        // Nếu tất cả đã kết thúc, có thể lấy bản ghi có ngày gần nhất
        return 0;
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
