package QUANLINHANSU.model;

import QUANLINHANSU.service.IManager;

public class NhanVien {
    protected String maNV;
    protected String tenNV;
    protected int tuoi;
    protected double luong;

    PhongBan phongBan;
    public NhanVien() {
    }

    public NhanVien(String maNV, String tenNV, int tuoi, double luong) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.tuoi = tuoi;
        this.luong = luong;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public int getTuoi() {
        return tuoi;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }

    public double getLuong() {
        return luong;
    }

    public void setLuong(double luong) {
        this.luong = luong;
    }

    public PhongBan getPhongBan() {
        return phongBan;
    }

    public void setPhongBan(PhongBan phongBan) {
        this.phongBan = phongBan;
    }

    public void hienThi(){
        System.out.println(maNV + " - " + tenNV + " - " + tuoi);
    }
    //ntp test
}
