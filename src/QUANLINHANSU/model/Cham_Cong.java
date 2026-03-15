package QUANLINHANSU.model;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
@Entity
@Table(name = "ChamCong")
public class Cham_Cong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "MaNV")
    private NhanVien nhanVien;

    @Column(name = "Ngay")
    private LocalDate ngay;

    @Column(name = "GioVao")
    private LocalTime gioVao;

    @Column(name = "GioRa")
    private LocalTime gioRa;

    public Cham_Cong() {
    }

    public Cham_Cong(NhanVien nhanVien, LocalDate ngay, LocalTime gioVao, LocalTime gioRa) {
        this.nhanVien = nhanVien;
        this.ngay = ngay;
        this.gioVao = gioVao;
        this.gioRa = gioRa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public LocalDate getNgay() {
        return ngay;
    }

    public void setNgay(LocalDate ngay) {
        this.ngay = ngay;
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

    @Override
    public String toString() {
        return "ChamCong{" +
                "maNV='" + nhanVien + '\'' +
                ", ngay='" + ngay + '\'' +
                ", gioVao='" + gioVao + '\'' +
                ", gioRa='" + gioRa + '\'' +
                '}';
    }
}
