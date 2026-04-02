package QUANLINHANSU.model;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "ChamCong")
public class Cham_Cong {

    @EmbeddedId
    private ChamCongId id;

    @ManyToOne
    @MapsId("maNV")
    @JoinColumn(name = "MaNV",columnDefinition = "NVARCHAR(50)" ,foreignKey = @ForeignKey(name = "FK_CHAMCONG_NHANVIEN"))
    private NhanVien nhanVien;

    @Column(name = "GioVao")
    private LocalTime gioVao;

    @Column(name = "GioRa")
    private LocalTime gioRa;
    @Column(name = "SoCong")
    private double socong;

    public Cham_Cong() {}

    public Cham_Cong(ChamCongId id, NhanVien nhanVien, LocalTime gioVao, LocalTime gioRa,double socong) {
        this.id = id;
        this.nhanVien = nhanVien;
        this.gioVao = gioVao;
        this.gioRa = gioRa;
        this.socong=socong;
    }

    public ChamCongId getId() {
        return id;
    }

    public void setId(ChamCongId id) {
        this.id = id;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public LocalTime getGioVao() {
        return gioVao;
    }

    public void setGioVao(LocalTime gioVao) {
        this.gioVao = gioVao;
    }

    public LocalTime getGioRa() {
        return gioRa;
    }

    public void setGioRa(LocalTime gioRa) {
        this.gioRa = gioRa;
    }

    public double getSocong() {
        return socong;
    }

    public void setSocong(double socong) {
        this.socong = socong;
    }
}