package QUANLINHANSU.repository;

import QUANLINHANSU.model.HopDong;
import jakarta.persistence.EntityManager;

import java.util.List;

public class HopDongRepository extends BaseRepository<HopDong> {

    public HopDongRepository() {
        super(HopDong.class);
    }

    // lấy theo nhân viên
    public List<HopDong> layTheoNhanVien(EntityManager em, String maNV) {
        return em.createQuery(
                        "SELECT hd FROM HopDong hd WHERE hd.nhanVien.maNV = :maNV",
                        HopDong.class)
                .setParameter("maNV", maNV)
                .getResultList();
    }

    // lấy theo loại hợp đồng
    public List<HopDong> layTheoLoai(EntityManager em, String loai) {
        return em.createQuery(
                        "SELECT hd FROM HopDong hd WHERE hd.loaiHopDong = :loai",
                        HopDong.class)
                .setParameter("loai", loai)
                .getResultList();
    }
}