package QUANLINHANSU.service;

import QUANLINHANSU.model.ThamGia;
import QUANLINHANSU.repository.ThamGiaRepository;

import java.util.List;

public class ThamGiaService {
    private ThamGiaRepository thamGiaRepository;

    public ThamGiaService() {
        thamGiaRepository = new ThamGiaRepository();
    }

    // thêm nhân viên vào dự án
    public void themThamGia(ThamGia tg) {

        // kiểm tra dữ liệu
        if (tg.getNhanVien() == null) {
            System.out.println("Nhan vien khong hop le");
            return;
        }

        if (tg.getMaDA() == null || tg.getMaDA().isEmpty()) {
            System.out.println("Ma du an khong hop le");
            return;
        }

        thamGiaRepository.create(tg);
    }

    // lấy danh sách tham gia
    public List<ThamGia> layDanhSach() {
        return thamGiaRepository.readAll();
    }

    // cập nhật
    public void capNhat(ThamGia tg) {
        thamGiaRepository.update(tg);
    }

    // xoá
    public void xoa(int id) {
        thamGiaRepository.delete(id);
    }

}
