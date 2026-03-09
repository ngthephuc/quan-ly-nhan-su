package QUANLINHANSU.service;

import QUANLINHANSU.model.ChucVu;

import java.util.ArrayList;

public class ChucVuService implements IManager<ChucVu>{
    ArrayList<ChucVu> cv = new ArrayList<>();
    @Override
    public void them(ChucVu chucVu) {
        cv.add(chucVu);

    }

    @Override
    public void xoa(String id) {
        for (int i = 0; i < cv.size(); i++) {

            if (cv.get(i).getMaCV().equals(id)) {
                cv.remove(i);
                break;
            }

        }

    }

    @Override
    public void hienThi() {
        for (ChucVu CV : cv){
            CV.hienThi();
        }

    }

    @Override
    public ChucVu tim(String id) {
        return null;
    }

    @Override
    public void sua(String id, ChucVu chucVu) {

    }
}
