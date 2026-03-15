package QUANLINHANSU.service;

import QUANLINHANSU.model.PhongBan;
import QUANLINHANSU.repository.PhongBanRepository;
import QUANLINHANSU.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class PhongBanService {
    private final PhongBanRepository phongBanRepo = new PhongBanRepository();


    public void themPhongBan(PhongBan phongBan) {
        // Validate trước khi thêm
        if (phongBan.getMaPb() == null || phongBan.getMaPb().isBlank())
            throw new IllegalArgumentException("Mã phòng ban không được để trống!");
        if (phongBan.getTenPb() == null || phongBan.getTenPb().isBlank())
            throw new IllegalArgumentException("Tên phòng ban không được để trống!");

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // Kiểm tra mã đã tồn tại chưa
            if (phongBanRepo.timById(em, phongBan.getMaPb()) != null)
                throw new IllegalArgumentException("Mã phòng ban '" + phongBan.getMaPb() + "' đã tồn tại!");

            phongBanRepo.them(em, phongBan);
            tx.commit();
            System.out.println("Thêm phòng ban thành công: " + phongBan.getTenPb());

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi thêm phòng ban: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    // ===================== CẬP NHẬT =====================
    public void capNhatPhongBan(PhongBan phongBan) {
        if (phongBan.getTenPb() == null || phongBan.getTenPb().isBlank())
            throw new IllegalArgumentException("Tên phòng ban không được để trống!");

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // Kiểm tra phòng ban có tồn tại không
            if (phongBanRepo.timById(em, phongBan.getMaPb()) == null)
                throw new IllegalArgumentException("Phòng ban '" + phongBan.getMaPb() + "' không tồn tại!");

            phongBanRepo.capNhat(em, phongBan);
            tx.commit();
            System.out.println("Cập nhật phòng ban thành công: " + phongBan.getTenPb());

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi cập nhật phòng ban: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    // ===================== XÓA =====================
    public void xoaPhongBan(String maPb) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            PhongBan pb = phongBanRepo.timById(em, maPb);
            if (pb == null)
                throw new IllegalArgumentException("Phòng ban '" + maPb + "' không tồn tại!");

            // Kiểm tra còn nhân viên trong phòng ban không
            long soNV = em.createQuery(
                            "SELECT COUNT(nv) FROM NhanVien nv WHERE nv.phongBan.maPb = :maPb", Long.class)
                    .setParameter("maPb", maPb)
                    .getSingleResult();
            if (soNV > 0)
                throw new IllegalStateException("Không thể xóa! Phòng ban còn " + soNV + " nhân viên.");

            phongBanRepo.xoa(em, maPb);
            tx.commit();
            System.out.println("Xóa phòng ban thành công: " + maPb);

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi xóa phòng ban: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    // ===================== TRUY VẤN (không cần transaction) =====================

    public PhongBan timTheoMa(String maPb) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return phongBanRepo.timById(em, maPb);
        } finally {
            em.close();
        }
    }

    public List<PhongBan> layTatCa() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return phongBanRepo.layDanhSachSapXep(em);
        } finally {
            em.close();
        }
    }

    public List<PhongBan> timTheoTen(String tenPb) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return phongBanRepo.timTheoTen(em, tenPb);
        } finally {
            em.close();
        }
    }
}
