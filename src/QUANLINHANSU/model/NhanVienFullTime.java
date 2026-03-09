package QUANLINHANSU.model;

public class NhanVienFullTime extends NhanVien {
    private double luongCoBan;

    public NhanVienFullTime(String maNV, String tenNV, int tuoi, double luongCoBan) {
        super(maNV, tenNV, tuoi, luongCoBan);
        this.luongCoBan = luongCoBan;
    }

    @Override
    public void hienThi(){
        System.out.println(maNV + " - " + tenNV + " - FullTime - " + luongCoBan);
    }

    public double getLuongCoBan() {
        return luongCoBan;
    }

    public void setLuongCoBan(double luongCoBan) {
        this.luongCoBan = luongCoBan;
    }

    @Override
    public String toString() {
        return "NhanVienFullTime{" +
                "luongCoBan=" + luongCoBan +
                '}';
    }
    //alooohhdhhh
}
