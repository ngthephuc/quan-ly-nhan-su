package QUANLINHANSU.service;

import QUANLINHANSU.model.Du_An;
import QUANLINHANSU.repository.DuAnRepository;
import QUANLINHANSU.util.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class DuAnService {

    private final DuAnRepository repo = new DuAnRepository();

    // ===================== THÊM DỰ ÁN =====================
    public void themDuAn(Du_An duAn) {

        if (duAn == null)
            throw new IllegalArgumentException("Dữ liệu dự án không hợp lệ!");

        if (duAn.getMaDA() == null || duAn.getMaDA().isBlank())
            throw new IllegalArgumentException("Mã dự án không hợp lệ!");

        if (duAn.getTenDA() == null || duAn.getTenDA().isBlank())
            throw new IllegalArgumentException("Tên dự án không được để trống!");

        if (duAn.getKinhPhi() < 0)
            throw new IllegalArgumentException("Kinh phí phải >= 0!");

        if (duAn.getNgayBatDau() != null && duAn.getNgayKetThuc() != null &&
                duAn.getNgayKetThuc().isBefore(duAn.getNgayBatDau()))
            throw new IllegalArgumentException("Ngày kết thúc phải sau ngày bắt đầu!");

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {

            tx.begin();

            if (repo.timById(em, duAn.getMaDA()) != null)
                throw new IllegalArgumentException("Mã dự án đã tồn tại!");

            repo.them(em, duAn);

            tx.commit();

        } catch (Exception e) {

            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi thêm dự án: " + e.getMessage(), e);

        } finally {

            em.close();

        }
    }

    // ===================== CẬP NHẬT =====================
    public void capNhatDuAn(Du_An duAn) {

        if (duAn == null || duAn.getMaDA() == null)
            throw new IllegalArgumentException("Dữ liệu dự án không hợp lệ!");

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {

            tx.begin();

            if (repo.timById(em, duAn.getMaDA()) == null)
                throw new IllegalArgumentException("Dự án không tồn tại!");

            repo.capNhat(em, duAn);

            tx.commit();

        } catch (Exception e) {

            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi cập nhật dự án: " + e.getMessage(), e);

        } finally {

            em.close();

        }
    }

    // ===================== XÓA =====================
    public void xoaDuAn(String maDA) {

        if (maDA == null || maDA.isBlank())
            throw new IllegalArgumentException("Mã dự án không hợp lệ!");

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {

            tx.begin();

            if (repo.timById(em, maDA) == null)
                throw new IllegalArgumentException("Dự án không tồn tại!");

            repo.xoa(em, maDA);

            tx.commit();

        } catch (Exception e) {

            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi xóa dự án: " + e.getMessage(), e);

        } finally {

            em.close();

        }
    }

    // ===================== TÌM THEO ID =====================
    public Du_An timDuAn(String maDA) {

        EntityManager em = JPAUtil.getEntityManager();

        try {

            return repo.timById(em, maDA);

        } finally {

            em.close();

        }
    }

    // ===================== LẤY TẤT CẢ =====================
    public List<Du_An> layTatCaDuAn() {

        EntityManager em = JPAUtil.getEntityManager();

        try {

            return repo.layTatCa(em);

        } finally {

            em.close();

        }
    }
}