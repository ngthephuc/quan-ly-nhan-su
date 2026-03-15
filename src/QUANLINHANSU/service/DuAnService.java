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

        if (duAn.getTenDA() == null || duAn.getTenDA().isBlank())
            throw new IllegalArgumentException("Tên dự án không được để trống!");

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            repo.them(em, duAn);

            tx.commit();
            System.out.println("Thêm dự án thành công!");

        } catch (Exception e) {

            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi thêm dự án: " + e.getMessage());

        } finally {
            em.close();
        }
    }

    // ===================== CẬP NHẬT =====================
    public void capNhatDuAn(Du_An duAn) {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            if (repo.timById(em, duAn.getMaDA()) == null)
                throw new IllegalArgumentException("Dự án không tồn tại!");

            repo.capNhat(em, duAn);

            tx.commit();
            System.out.println("Cập nhật dự án thành công!");

        } catch (Exception e) {

            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi cập nhật dự án: " + e.getMessage());

        } finally {
            em.close();
        }
    }

    // ===================== XÓA =====================
    public void xoaDuAn(int id) {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Du_An da = repo.timById(em, id);

            if (da == null)
                throw new IllegalArgumentException("Dự án không tồn tại!");

            repo.xoa(em, id);

            tx.commit();
            System.out.println("Xóa dự án thành công!");

        } catch (Exception e) {

            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi xóa dự án: " + e.getMessage());

        } finally {
            em.close();
        }
    }

    // ===================== TÌM THEO ID =====================
    public Du_An timDuAn(int id) {

        try (EntityManager em = JPAUtil.getEntityManager()) {
            return repo.timById(em, id);
        }
    }

    // ===================== LẤY TẤT CẢ =====================
    public List<Du_An> layTatCaDuAn() {

        try (EntityManager em = JPAUtil.getEntityManager()) {
            return repo.layTatCa(em);
        }
    }
}