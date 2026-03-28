package QUANLINHANSU.service;

import QUANLINHANSU.model.HopDong;
import QUANLINHANSU.model.NhanVien;
import QUANLINHANSU.repository.HopDongRepository;
import QUANLINHANSU.util.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDate;
import java.util.List;

public class HopDongService {

    private final HopDongRepository repo = new HopDongRepository();

    // ==================== THÊM ====================
    public void themHopDong(HopDong hd) {

        if (hd == null)
            throw new IllegalArgumentException("Hợp đồng không hợp lệ!");

        if (hd.getMaHD() == null || hd.getMaHD().isBlank())
            throw new IllegalArgumentException("Mã hợp đồng không hợp lệ!");

        if (hd.getLuongCoBan() <= 0)
            throw new IllegalArgumentException("Lương cơ bản phải > 0");

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {

            tx.begin();

            // kiểm tra mã hợp đồng tồn tại chưa
            if (repo.timById(em, hd.getMaHD()) != null)
                throw new IllegalArgumentException("Mã hợp đồng đã tồn tại!");

            // kiểm tra nhân viên tồn tại
            repo.them(em, hd);

            tx.commit();

        } catch (Exception e) {

            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi thêm hợp đồng: " + e.getMessage(), e);

        } finally {

            em.close();

        }
    }

    // ==================== CẬP NHẬT ====================
    public void capNhatHopDong(HopDong hd) {

        if (hd == null || hd.getMaHD() == null)
            throw new IllegalArgumentException("Hợp đồng không hợp lệ!");

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {

            tx.begin();

            if (repo.timById(em, hd.getMaHD()) == null)
                throw new IllegalArgumentException("Hợp đồng không tồn tại!");

            repo.capNhat(em, hd);

            tx.commit();

        } catch (Exception e) {

            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi cập nhật hợp đồng: " + e.getMessage(), e);

        } finally {

            em.close();

        }
    }

    // ==================== XOÁ ====================
    public void xoaHopDong(String maHD) {

        if (maHD == null || maHD.isBlank())
            throw new IllegalArgumentException("Mã hợp đồng không hợp lệ!");

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {

            tx.begin();

            if (repo.timById(em, maHD) == null)
                throw new IllegalArgumentException("Hợp đồng không tồn tại!");

            repo.xoa(em, maHD);

            tx.commit();

        } catch (Exception e) {

            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi xoá hợp đồng: " + e.getMessage(), e);

        } finally {

            em.close();

        }
    }

    // ==================== LẤY TẤT CẢ ====================
    public List<HopDong> layTatCa() {

        EntityManager em = JPAUtil.getEntityManager();

        try {

            return repo.layTatCa(em);

        } finally {

            em.close();

        }
    }

    // ==================== TÌM THEO ID ====================
    public HopDong timTheoId(String maHD) {

        EntityManager em = JPAUtil.getEntityManager();

        try {

            return repo.timById(em, maHD);

        } finally {

            em.close();

        }
    }

    // ==================== LẤY THEO NHÂN VIÊN ====================
    public List<HopDong> layTheoNhanVien(String maNV) {

        EntityManager em = JPAUtil.getEntityManager();

        try {

            return repo.layTheoNhanVien(em, maNV);

        } finally {

            em.close();

        }
    }

    // ==================== LẤY THEO LOẠI ====================
    public List<HopDong> layTheoLoai(String loai) {

        EntityManager em = JPAUtil.getEntityManager();

        try {

            return repo.layTheoLoai(em, loai);

        } finally {

            em.close();

        }
    }
}