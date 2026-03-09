package QUANLINHANSU.model;

public class NhanVienPartTime extends NhanVien{
    private int gioLam;
    private double luongGio;

    public NhanVienPartTime(String maNV, String tenNV, int tuoi, double luong, int gioLam, double luongGio) {
        super(maNV, tenNV, tuoi, luong);
        this.gioLam = gioLam;
        this.luongGio = luongGio;
    }
    @Override
    public void hienThi(){
        double luong = gioLam * luongGio;
        System.out.println(maNV + " - " + tenNV + " - PartTime - " + luong);
    }

    public int getGioLam() {
        return gioLam;
    }

    public void setGioLam(int gioLam) {
        this.gioLam = gioLam;
    }

    @Override
    public String toString() {
        return "NhanVienPartTime{" +
                "gioLam=" + gioLam +
                ", luongGio=" + luongGio +
                '}';
    }

}
