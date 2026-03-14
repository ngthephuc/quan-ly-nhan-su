package QUANLINHANSU.model;

import jakarta.persistence.*;

@Entity
public class ThamGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "MaNV")
    private NhanVien nhanVien;
    @Column(name = "MaDA")
    private String maDA;
    @Column(name = "VaiTro")
    private String vaiTro;
    @Column(name = "SoGio")
    private int soGio;

    public ThamGia() {
    }

    public ThamGia(NhanVien nhanVien, String maDA, String vaiTro, int soGio) {
        this.nhanVien = nhanVien;
        this.maDA = maDA;
        this.vaiTro = vaiTro;
        this.soGio = soGio;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public String getMaDA() {
        return maDA;
    }

    public void setMaDA(String maDA) {
        this.maDA = maDA;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public int getSoGio() {
        return soGio;
    }

    public void setSoGio(int soGio) {
        this.soGio = soGio;
    }
}
