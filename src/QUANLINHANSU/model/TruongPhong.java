package QUANLINHANSU.model;

public class TruongPhong extends NhanVienFullTime {
    private double troCapChucVu;

    public TruongPhong(String maNV, String tenNV, int tuoi, double luongCoBan, double troCapChucVu) {
        super(maNV, tenNV, tuoi, luongCoBan);
        this.troCapChucVu = troCapChucVu;
    }

    public double getTroCapChucVu() {
        return troCapChucVu;
    }

    public void setTroCapChucVu(double troCapChucVu) {
        this.troCapChucVu = troCapChucVu;
    }

    @Override
    public String toString() {
        return "TruongPhong{" +
                "troCapChucVu=" + troCapChucVu +
                '}';
    }

    @Override
    public void hienThi() {
        super.hienThi();
    }
}



