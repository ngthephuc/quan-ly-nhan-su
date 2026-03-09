package QUANLINHANSU.service;

import QUANLINHANSU.model.NhanVien;
import QUANLINHANSU.model.PhongBan;

import java.util.ArrayList;

public class NhanVienService implements IManager<NhanVien> {

    // Danh sách lưu nhân viên
    ArrayList<NhanVien> ds = new ArrayList<>();

    // ================= THÊM NHÂN VIÊN =================
    @Override
    public void them(NhanVien nv) {
        ds.add(nv);
    }


    // ================= XOÁ NHÂN VIÊN =================
    @Override
    public void xoa(String id) {

        for (int i = 0; i < ds.size(); i++) {

            if (ds.get(i).getMaNV().equals(id)) {
                ds.remove(i);
                break;
            }

        }

    }

    // ================= HIỂN THỊ DANH SÁCH =================
    @Override
    public void hienThi() {

        for (NhanVien nv : ds) {
            nv.hienThi();
        }

    }

    // ================= TÌM NHÂN VIÊN =================
    @Override
    public NhanVien tim(String id) {

        for (int i = 0; i < ds.size(); i++) {

            if (ds.get(i).getMaNV().equals(id)) {
                return ds.get(i);
            }

        }

        return null;

    }

    // ================= SỬA NHÂN VIÊN =================
    @Override
    public void sua(String id, NhanVien nvMoi) {

        for (int i = 0; i < ds.size(); i++) {

            if (ds.get(i).getMaNV().equals(id)) {

                ds.get(i).setTenNV(nvMoi.getTenNV());
                ds.get(i).setTuoi(nvMoi.getTuoi());
                ds.get(i).setLuong(nvMoi.getLuong());

                break;
            }

        }

    }

}