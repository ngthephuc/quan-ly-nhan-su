package QUANLINHANSU.repository;

import QUANLINHANSU.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.List;

public class SalaryRepository extends BaseRepository<SaLary> {
    public SalaryRepository() { super(SaLary.class); }

    public NhanVien timNhanVienKemChiTiet(EntityManager em, String maNV) {
        try {
            String jpql = "SELECT n FROM NhanVien n LEFT JOIN FETCH n.hopDong LEFT JOIN FETCH n.phongBan WHERE n.maNV = :ma";
            return em.createQuery(jpql, NhanVien.class).setParameter("ma", maNV).getSingleResult();
        } catch (NoResultException e) { return null; }
    }

    public double layPhuCapMoiNhat(EntityManager em, String maNV) {
        try {
            String jpql = "SELECT bn.chucVu.phuCap FROM BoNhiem bn WHERE bn.nhanVien.maNV = :ma ORDER BY bn.id.tuNgay DESC";
            List<Double> results = em.createQuery(jpql, Double.class).setParameter("ma", maNV).setMaxResults(1).getResultList();
            return (results.isEmpty() || results.get(0) == null) ? 0.0 : results.get(0);
        } catch (Exception e) { return 0.0; }
    }

    // HÀM QUAN TRỌNG: Đếm số ngày công thực tế trong Database
    public double tinhTongCongTuDb(EntityManager em, String maNV) {
        try {
            // Giả sử mỗi dòng trong bảng ChamCong là 1 ngày làm việc
            String jpql = "SELECT COUNT(cc) FROM ChamCong cc WHERE cc.nhanVien.maNV = :ma";
            Long count = em.createQuery(jpql, Long.class)
                    .setParameter("ma", maNV)
                    .getSingleResult();
            return (count != null) ? count.doubleValue() : 0.0;
        } catch (Exception e) { return 0.0; }
    }

    public List<SaLary> layTatCaKemChiTiet(EntityManager em) {
        return em.createQuery("SELECT s FROM SaLary s JOIN FETCH s.nhanVien n LEFT JOIN FETCH n.phongBan", SaLary.class).getResultList();
    }

    public List<SaLary> layLichSuTinhLuong(EntityManager em , String maNV) {
        return em.createQuery("SELECT l FROM SaLary l WHERE l.nhanVien.maNV = :maNV", SaLary.class)
                .setParameter("maNV", maNV)
                .getResultList();
    }
}