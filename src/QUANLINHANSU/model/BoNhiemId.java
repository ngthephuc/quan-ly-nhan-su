package QUANLINHANSU.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class BoNhiemId implements Serializable {

    private String maNV;
    private String maCV;
    private LocalDate tuNgay;

    public BoNhiemId() {}

    public BoNhiemId(String maNV, String maCV, LocalDate tuNgay) {
        this.maNV = maNV;
        this.maCV = maCV;
        this.tuNgay = tuNgay;
    }
    //geter and setter


    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMaCV() {
        return maCV;
    }

    public void setMaCV(String maCV) {
        this.maCV = maCV;
    }

    public LocalDate getTuNgay() {
        return tuNgay;
    }

    public void setTuNgay(LocalDate tuNgay) {
        this.tuNgay = tuNgay;
    }

    // equals + hashCode
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BoNhiemId boNhiemId = (BoNhiemId) o;
        return Objects.equals(maNV, boNhiemId.maNV) && Objects.equals(maCV, boNhiemId.maCV) && Objects.equals(tuNgay, boNhiemId.tuNgay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maNV, maCV, tuNgay);
    }


}