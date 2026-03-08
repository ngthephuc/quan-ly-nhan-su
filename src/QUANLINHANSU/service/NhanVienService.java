package QUANLINHANSU.service;

import QUANLINHANSU.model.NhanVien;

import java.util.ArrayList;

public class NhanVienService implements IManager<NhanVien> {
    ArrayList<NhanVien> ds=new ArrayList<>();


    @Override
    public void them(NhanVien nv) {
        ds.add(nv);
    }

    @Override
    public void xoa(String id) {
        ds.removeIf(nv -> nv.getMaNV().equals(id));
    }

    @Override
    public void hienThi() {
        for (NhanVien nv : ds) {
            nv.hienThi();
        }
    }

    @Override
    public NhanVien tim(String id) {
        for(NhanVien nv : ds){
            if(nv.getMaNV().equals(id)){
                return nv;
            }
        }
        return null;
    }


}
