package QUANLINHANSU.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class ChamCongId implements Serializable {

    @Column(name = "MaNV",columnDefinition = "NVARCHAR(50)")
    private String maNV;

    @Column(name = "Ngay")
    private LocalDate ngay;

    public ChamCongId() {
    }

    public ChamCongId(String maNV, LocalDate ngay) {
        this.maNV = maNV;
        this.ngay = ngay;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChamCongId)) return false;
        ChamCongId that = (ChamCongId) o;
        return Objects.equals(maNV, that.maNV) &&
                Objects.equals(ngay, that.ngay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maNV, ngay);
    }
}