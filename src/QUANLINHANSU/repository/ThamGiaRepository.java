package QUANLINHANSU.repository;

import QUANLINHANSU.model.ThamGia;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ThamGiaRepository extends BaseRepository<ThamGia> {

    public ThamGiaRepository() {
        super(ThamGia.class);
    }

    // lấy theo dự án
    public List<ThamGia> layTheoDuAn(EntityManager em, String maDA) {
        return em.createQuery(
                        "SELECT tg FROM ThamGia tg WHERE tg.maDA = :maDA",
                        ThamGia.class)
                .setParameter("maDA", maDA)
                .getResultList();
    }

    // lấy theo nhân viên
    public List<ThamGia> layTheoNhanVien(EntityManager em, String maNV) {
        return em.createQuery(
                        "SELECT tg FROM ThamGia tg WHERE tg.nhanVien.maNV = :maNV",
                        ThamGia.class)
                .setParameter("maNV", maNV)
                .getResultList();
    }
}