package QUANLINHANSU.model;

public class NhanVienFullTime extends NhanVien {
    private double luongThang;

    public NhanVienFullTime(String maNV, String tenNV, int tuoi, double luongThang) {
        super(maNV, tenNV, tuoi, luongThang);
        this.luongThang = luongThang;
    }

    @Override
    public void hienThi(){
        System.out.println(maNV + " - " + tenNV + " - FullTime - " + luongThang);
    }
}
