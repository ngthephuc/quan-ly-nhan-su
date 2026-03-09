package QUANLINHANSU.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class BoNhiemId implements Serializable {
    private String nhanVien;
    private String chucVu;
    private LocalDate tuNgay;


    public BoNhiemId() {
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BoNhiemId boNhiemId = (BoNhiemId) o;
        return Objects.equals(nhanVien, boNhiemId.nhanVien) && Objects.equals(chucVu, boNhiemId.chucVu) && Objects.equals(tuNgay, boNhiemId.tuNgay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nhanVien, chucVu, tuNgay);
    }

    public String getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(String nhanVien) {
        this.nhanVien = nhanVien;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public LocalDate getTuNgay() {
        return tuNgay;
    }

    public void setTuNgay(LocalDate tuNgay) {
        this.tuNgay = tuNgay;
    }
}
