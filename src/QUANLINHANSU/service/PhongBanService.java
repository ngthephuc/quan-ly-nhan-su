package QUANLINHANSU.service;

import QUANLINHANSU.model.PhongBan;

import java.util.ArrayList;

public class PhongBanService implements IManager<PhongBan>{
    ArrayList<PhongBan> pb = new ArrayList<>();


    @Override
    public void them(PhongBan PB) {
        pb.add(PB);

    }

    @Override
    public void xoa(String id) {
        for (int i = 0; i < pb.size(); i++) {

            if (pb.get(i).getMaPb().equals(id)) {
                pb.remove(i);
                break;
            }

        }

    }

    @Override
    public void hienThi() {
        for (PhongBan PB : pb) {
            PB.hienThi();
        }

    }

    @Override
    public PhongBan tim(String id) {
        for (int i = 0; i < pb.size(); i++) {
            if (pb.get(i).getMaPb().equals(id)) {
                return pb.get(i);
            }

        }
        return null;
    }

    @Override
    public void sua(String id, PhongBan phongBan) {
        for (int i = 0; i < pb.size(); i++) {

            if (pb.get(i).getMaPb().equals(id)) {

                pb.get(i).setMaPb(phongBan.getMaPb());
                pb.get(i).setTenPb(phongBan.getTenPb());
                pb.get(i).setHeSoLuong(phongBan.getHeSoLuong());

                break;
            }

        }

    }




}
