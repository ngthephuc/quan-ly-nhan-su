package QUANLINHANSU.repository;

import QUANLINHANSU.model.ThamGia;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;

public class ThamGiaRepository extends BaseRepository<ThamGia> {

    public ThamGiaRepository() {
        super(ThamGia.class);
    }


    public boolean kiemTraNhanVienDaThamGia(EntityManager em, String maNV, String maDA) {
        try {
            String jpql = "SELECT COUNT(tg) FROM ThamGia tg " +
                    "WHERE tg.nhanVien.maNV = :maNV AND tg.duAn.maDA = :maDA";

            Long count = em.createQuery(jpql, Long.class)
                    .setParameter("maNV", maNV)
                    .setParameter("maDA", maDA)
                    .getSingleResult();

            return count > 0; // Trả về true nếu đã tham gia rồi
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public ThamGia timThamGia(EntityManager em, String maNV, String maDA) {
        try {
            return em.createQuery(
                            "SELECT t FROM ThamGia t WHERE t.nhanVien.maNV = :maNV AND t.duAn.maDA = :maDA", ThamGia.class)
                    .setParameter("maNV", maNV)
                    .setParameter("maDA", maDA)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // Không tìm thấy thì trả về null
        }
    }
}