package QUANLINHANSU.service;

import QUANLINHANSU.model.HopDong;
import QUANLINHANSU.repository.HopDongRepository;

import java.util.List;

public class HopDongService {
    private HopDongRepository repository;

    public HopDongService() {
        repository = new HopDongRepository();
    }

    // thêm hợp đồng
    public void themHopDong(HopDong hd) {

        if (hd.getNhanVien() == null) {
            System.out.println("Nhan vien khong hop le");
            return;
        }

        if (hd.getMaHD() == null || hd.getMaHD().isEmpty()) {
            System.out.println("Ma hop dong khong hop le");
            return;
        }

        repository.create(hd);
    }

    // lấy danh sách hợp đồng
    public List<HopDong> layDanhSach() {
        return repository.readAll();
    }

    // cập nhật
    public void capNhat(HopDong hd) {
        repository.update(hd);
    }

    // xoá
    public void xoa(String maHD) {
        repository.delete(maHD);
    }
}
