package QUANLINHANSU.service;

import QUANLINHANSU.model.ThamGia;
import QUANLINHANSU.repository.ThamGiaRepository;
import QUANLINHANSU.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class ThamGiaService {

    private final ThamGiaRepository repo = new ThamGiaRepository();

    // ==================== THÊM ====================
    public void themThamGia(ThamGia tg) {

        if (tg == null)
            throw new IllegalArgumentException("Dữ liệu tham gia không hợp lệ!");

        if (tg.getNhanVien() == null || tg.getNhanVien().getMaNV() == null)
            throw new IllegalArgumentException("Nhân viên không hợp lệ!");

        if (tg.getDuAn() == null)
            throw new IllegalArgumentException("Mã dự án không hợp lệ!");

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // kiểm tra nhân viên tồn tại
            if (em.find(QUANLINHANSU.model.NhanVien.class,
                    tg.getNhanVien().getMaNV()) == null)
                throw new IllegalArgumentException("Nhân viên không tồn tại!");

            // kiểm tra dự án tồn tại
            if (em.find(QUANLINHANSU.model.Du_An.class,
                    tg.getDuAn()) == null)
                throw new IllegalArgumentException("Dự án không tồn tại!");

            // kiểm tra nhân viên đã tham gia dự án chưa
            List<ThamGia> list = repo.layTheoNhanVien(em, tg.getNhanVien().getMaNV());

            for (ThamGia item : list) {
                if (item.getDuAn().equals(tg.getDuAn()))
                    throw new IllegalArgumentException("Nhân viên đã tham gia dự án này!");
            }

            repo.them(em, tg);

            tx.commit();

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi thêm tham gia: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    // ==================== CẬP NHẬT ====================
    public void capNhat(ThamGia tg) {

        if (tg == null)
            throw new IllegalArgumentException("Dữ liệu không hợp lệ!");

        if (tg.getId() <= 0)
            throw new IllegalArgumentException("ID không hợp lệ!");

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            if (repo.timById(em, tg.getId()) == null)
                throw new IllegalArgumentException("Tham gia không tồn tại!");

            repo.capNhat(em, tg);

            tx.commit();

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi cập nhật tham gia: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    // ==================== XOÁ ====================
    public void xoa(int id) {

        if (id <= 0)
            throw new IllegalArgumentException("ID không hợp lệ!");

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            if (repo.timById(em, id) == null)
                throw new IllegalArgumentException("Bản ghi không tồn tại!");

            repo.xoa(em, id);

            tx.commit();

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi xóa tham gia: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    // ==================== LẤY TẤT CẢ ====================
    public List<ThamGia> layTatCa() {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            return repo.layTatCa(em);
        } finally {
            em.close();
        }
    }

    // ==================== TÌM THEO ID ====================
    public ThamGia timTheoId(int id) {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            return repo.timById(em, id);
        } finally {
            em.close();
        }
    }

    // ==================== LẤY THEO DỰ ÁN ====================
    public List<ThamGia> layTheoDuAn(String maDA) {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            return repo.layTheoDuAn(em, maDA);
        } finally {
            em.close();
        }
    }

    // ==================== LẤY THEO NHÂN VIÊN ====================
    public List<ThamGia> layTheoNhanVien(String maNV) {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            return repo.layTheoNhanVien(em, maNV);
        } finally {
            em.close();
        }
    }
}