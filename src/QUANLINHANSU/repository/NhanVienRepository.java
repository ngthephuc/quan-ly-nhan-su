package QUANLINHANSU.repository;

import QUANLINHANSU.model.NhanVien;
import jakarta.persistence.EntityManager;

import java.util.List;

public class NhanVienRepository extends BaseRepository<NhanVien> {

    public NhanVienRepository(Class<NhanVien> entityClass) {
        super(entityClass);
    }

    public NhanVienRepository() {
        super(NhanVien.class);
    }

    public List<NhanVien> timTheoTen(EntityManager em, String hoTen) {
        return em.createQuery(
                        "SELECT nv FROM NhanVien nv WHERE LOWER(nv.hoTen) LIKE LOWER(:ten)", NhanVien.class)
                .setParameter("ten", "%" + hoTen + "%")
                .getResultList();
    }

    public List<NhanVien> layTheoPhongBan(EntityManager em, String maPb) {
        return em.createQuery(
                        "SELECT nv FROM NhanVien nv WHERE nv.phongBan.maPb = :maPb", NhanVien.class)
                .setParameter("maPb", maPb)
                .getResultList();
    }

    public List<NhanVien> layTheoTrangThai(EntityManager em, String trangThai) {
        return em.createQuery(
                        "SELECT nv FROM NhanVien nv WHERE nv.trangThai = :tt", NhanVien.class)
                .setParameter("tt", trangThai)
                .getResultList();
    }

    public NhanVien timTheoCCCD(EntityManager em, String cccd) {
        List<NhanVien> result = em.createQuery(
                        "SELECT nv FROM NhanVien nv WHERE nv.cccd = :cccd", NhanVien.class)
                .setParameter("cccd", cccd)
                .getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    public long demNhanVienDangLam(EntityManager em) {
        return em.createQuery(
                        "SELECT COUNT(nv) FROM NhanVien nv WHERE LOWER( nv.trangThai) = :trangThai", Long.class)
                .setParameter("trangThai", "Đang làm")
                .getSingleResult();
    }

    public NhanVien layTruongPhong(EntityManager em, String maPb) {
        List<NhanVien> result = em.createQuery(
                        "SELECT b.nhanVien FROM BoNhiem b " +
                                "WHERE b.nhanVien.phongBan.maPb = :maPb " +
                                "AND LOWER(b.chucVu.maCV) LIKE '%TP%' " +
                                "AND b.denNgay IS NULL", NhanVien.class)
                .setParameter("maPb", maPb)
                .getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    public long demNhanVienTheoPhongBan(EntityManager em, String maPb) {
        return em.createQuery(
                        "SELECT COUNT(nv) FROM NhanVien nv " +
                                "WHERE nv.phongBan.maPb = :maPb AND LOWER(nv.trangThai) = :trangThai", Long.class)
                .setParameter("maPb", maPb)
                .setParameter("trangThai", "Đang làm")
                .getSingleResult();
    }

}
