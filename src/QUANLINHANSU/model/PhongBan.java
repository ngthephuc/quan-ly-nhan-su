package QUANLINHANSU.model;

public class PhongBan {
    private String maPb;
    private String tenPb;
    private Double heSoLuong;


    public PhongBan() {
    }

    public PhongBan(String maPb, String tenPb, Double heSoLuong) {
        this.maPb = maPb;
        this.tenPb = tenPb;
        this.heSoLuong = heSoLuong;
    }

    public String getMaPb() {
        return maPb;
    }

    public String getTenPb() {
        return tenPb;
    }

    public Double getHeSoLuong() {
        return heSoLuong;

    }

    public void setMaPb(String maPb) {
        this.maPb = maPb;
    }

    public void setTenPb(String tenPb) {
        this.tenPb = tenPb;
    }

    public void setHeSoLuong(Double heSoLuong) {
        this.heSoLuong = heSoLuong;
    }

    @Override
    public String toString() {
        return "PhongBan{" +
                "maPb='" + maPb + '\'' +
                ", tenPb='" + tenPb + '\'' +
                ", heSoLuong=" + heSoLuong +
                '}';
    }

    public void hienThi() {
    }
}