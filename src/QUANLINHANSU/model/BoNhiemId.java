package QUANLINHANSU.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class BoNhiemId implements Serializable {

    @Column(name = "MaNV",columnDefinition = "NVARCHAR(50)")
    private String maNV;

    @Column(name = "MaCV",columnDefinition = "NVARCHAR(50)")
    private String maCV;

    @Column(name = "TuNgay")
    private LocalDate tuNgay;

    public BoNhiemId() {}

    public BoNhiemId(String maNV, String maCV, LocalDate tuNgay) {
        this.maNV = maNV;
        this.maCV = maCV;
        this.tuNgay = tuNgay;
    }

    public String getMaNV() {
        return maNV;
    }

    public String getMaCV() {
        return maCV;
    }

    public LocalDate getTuNgay() {
        return tuNgay;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public void setMaCV(String maCV) {
        this.maCV = maCV;
    }

    public void setTuNgay(LocalDate tuNgay) {
        this.tuNgay = tuNgay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoNhiemId)) return false;
        BoNhiemId that = (BoNhiemId) o;
        return Objects.equals(maNV, that.maNV) &&
                Objects.equals(maCV, that.maCV) &&
                Objects.equals(tuNgay, that.tuNgay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maNV, maCV, tuNgay);
    }
}