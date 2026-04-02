package QUANLINHANSU.repository;

import QUANLINHANSU.model.SaLary;
import jakarta.persistence.EntityManager;
import java.util.List;

public class SalaryRepository extends BaseRepository<SaLary> {
    public SalaryRepository() {
        super(SaLary.class);
    }

    public List<SaLary> layTatCa(EntityManager em) {
        // JPQL: Truy vấn dựa trên tên Class và tên biến trong Java
        // JOIN FETCH: Gom hết dữ liệu liên quan về 1 lần (Eager Loading thủ công)
        String jpql = "SELECT DISTINCT s FROM SaLary s " +
                "JOIN FETCH s.nhanVien nv " +
                "LEFT JOIN FETCH nv.danhSachBoNhiem bn " +
                "LEFT JOIN FETCH bn.chucVu " +
                "LEFT JOIN FETCH nv.phongBan " +
                "LEFT JOIN FETCH nv.hopDong";

        return em.createQuery(jpql, SaLary.class).getResultList();
    }

    public SaLary timTheoMa(EntityManager em, String maNV) {
        String jpql = "SELECT s FROM SaLary s " +
                "JOIN FETCH s.nhanVien nv " +
                "LEFT JOIN FETCH nv.danhSachBoNhiem bn " +
                "LEFT JOIN FETCH bn.chucVu " +
                "WHERE s.maNV = :ma";
        try {
            return em.createQuery(jpql, SaLary.class)
                    .setParameter("ma", maNV)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public void xoaTheoMaNV(EntityManager em, String maNV) {
        SaLary s = em.find(SaLary.class, maNV);
        if (s != null) em.remove(s);
    }
}