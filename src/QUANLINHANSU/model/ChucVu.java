package QUANLINHANSU.model;

import java.util.List;

public class ChucVu {
    private String maCV;
    private String tenCV;
    private Double phuCap;

    private List<BoNhiem> danhSachBoNhiem;


    public ChucVu(String maCV, String tenCV, Double phuCap, List<BoNhiem> danhSachBoNhiem) {
        this.maCV = maCV;
        this.tenCV = tenCV;
        this.phuCap = phuCap;
        this.danhSachBoNhiem = danhSachBoNhiem;
    }

    public String getMaCV() {
        return maCV;
    }

    public void setMaCV(String maCV) {
        this.maCV = maCV;
    }

    public String getTenCV() {
        return tenCV;
    }

    public void setTenCV(String tenCV) {
        this.tenCV = tenCV;
    }

    public Double getPhuCap() {
        return phuCap;
    }

    public void setPhuCap(Double phuCap) {
        this.phuCap = phuCap;
    }

    public List<BoNhiem> getDanhSachBoNhiem() {
        return danhSachBoNhiem;
    }

    public void setDanhSachBoNhiem(List<BoNhiem> danhSachBoNhiem) {
        this.danhSachBoNhiem = danhSachBoNhiem;
    }

    @Override
    public String toString() {
        return "ChucVu{" +
                "maCV='" + maCV + '\'' +
                ", tenCV='" + tenCV + '\'' +
                ", phuCap=" + phuCap +
                ", danhSachBoNhiem=" + danhSachBoNhiem +
                '}';
    }

    public void hienThi() {
    }
}
