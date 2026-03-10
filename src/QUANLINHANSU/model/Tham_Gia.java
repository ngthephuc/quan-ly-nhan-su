package QUANLINHANSU.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ThamGia")
public class Tham_Gia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "MaNV")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "MaDA")
    private Du_An duAn;

    @Column(name = "VaiTro")
    private String vaiTro;

    @Column(name = "SoGio")
    private int soGio;

    public Tham_Gia() {
    }

    public Tham_Gia(NhanVien nhanVien, Du_An duAn, String vaiTro, int soGio) {
        this.nhanVien = nhanVien;
        this.duAn = duAn;
        this.vaiTro = vaiTro;
        this.soGio = soGio;
    }

    public int getId() {
        return id;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public Du_An getDuAn() {
        return duAn;
    }

    public void setDuAn(Du_An duAn) {
        this.duAn = duAn;
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