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

    @Column(name = "MaNV")
    private String maNV;

    @Column(name = "Ngay")
    private LocalDate ngay;

    @Column(name = "GioVao")
    private LocalTime gioVao;

    @Column(name = "GioRa")
    private LocalTime gioRa;

    public Cham_Cong() {
    }

    public Cham_Cong(String maNV, LocalDate ngay, LocalTime gioVao, LocalTime gioRa) {
        this.maNV = maNV;
        this.ngay = ngay;
        this.gioVao = gioVao;
        this.gioRa = gioRa;
    }

    public int getId() {
        return id;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
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
}
