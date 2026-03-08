package QUANLINHANSU.model;

import QUANLINHANSU.service.IManager;

public class NhanVien {
    protected String maNV;
    protected String tenNV;
    protected int tuoi;
    protected double luong;

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

    public String getTenNV() {
        return tenNV;
    }

    public int getTuoi() {
        return tuoi;
    }

    public double getLuong() {
        return luong;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }
    public void hienThi(){
        System.out.println(maNV + " - " + tenNV + " - " + tuoi);
    }
}