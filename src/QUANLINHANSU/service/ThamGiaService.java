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
    public void themThamGiaChuan(ThamGia tg) {
        if (tg == null || tg.getNhanVien() == null || tg.getDuAn() == null) {
            throw new IllegalArgumentException("Dữ liệu đầu vào không hợp lệ!");
        }

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            String maNV = tg.getNhanVien().getMaNV();
            String maDA = tg.getDuAn().getMaDA();

            // 1. Vớt Object từ DB lên để Hibernate "nhận người quen"
            QUANLINHANSU.model.NhanVien nvReal = em.find(QUANLINHANSU.model.NhanVien.class, maNV);
            QUANLINHANSU.model.Du_An daReal = em.find(QUANLINHANSU.model.Du_An.class, maDA);

            if (nvReal == null) throw new IllegalArgumentException("Mã nhân viên '" + maNV + "' không tồn tại!");
            if (daReal == null) throw new IllegalArgumentException("Mã dự án '" + maDA + "' không tồn tại!");

            // 2. Check trùng bằng hàm Repo vừa viết
            if (repo.kiemTraNhanVienDaThamGia(em, maNV, maDA)) {
                throw new IllegalArgumentException("Nhân viên này đã tham gia dự án rồi!");
            }

            // 3. Gán Object "xịn" vào và lưu
            tg.setNhanVien(nvReal);
            tg.setDuAn(daReal);
            repo.them(em, tg);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException(e.getMessage());
        } finally {
            em.close();
        }
    }
    public void capNhatVaiTro(String maNV, String maDA, String vaiTroMoi) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            ThamGia tg = repo.timThamGia(em, maNV, maDA);
            if (tg == null) throw new Exception("Nhân viên này chưa tham gia dự án này!");

            tg.setVaiTro(vaiTroMoi); // Sửa chức vụ
            repo.capNhat(em, tg);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException(e.getMessage());
        } finally { em.close(); }
    }
//tp
public void xoaKhoiDuAn(String maNV, String maDA) {
    EntityManager em = JPAUtil.getEntityManager();
    EntityTransaction tx = em.getTransaction();

    try {
        tx.begin();

        int deleted = em.createQuery(
                        "DELETE FROM ThamGia t WHERE t.nhanVien.maNV = :maNV AND t.duAn.maDA = :maDA")
                .setParameter("maNV", maNV)
                .setParameter("maDA", maDA)
                .executeUpdate();

        if (deleted == 0) {
            throw new RuntimeException("Không có dữ liệu để xóa!");
        }

        tx.commit();
    } catch (Exception e) {
        if (tx.isActive()) tx.rollback();
        throw new RuntimeException(e.getMessage());
    } finally {
        em.close();
    }
}
//
//    public void xoaKhoiDuAn(String maNV, String maDA) {
//        EntityManager em = JPAUtil.getEntityManager();
//        EntityTransaction tx = em.getTransaction();
//        try {
//            tx.begin();
//            ThamGia tg = repo.timThamGia(em, maNV, maDA);
//            if (tg == null) throw new Exception("Không tìm thấy dữ liệu để xóa!");
//
//            repo.xoa(em, tg.getId()); // Xóa theo ID bản ghi
//            tx.commit();
//        } catch (Exception e) {
//            if (tx.isActive()) tx.rollback();
//            throw new RuntimeException(e.getMessage());
//        } finally { em.close(); }
//    }

//    // ==================== CẬP NHẬT ====================
//    public void capNhat(ThamGia tg) {
//
//        if (tg == null)
//            throw new IllegalArgumentException("Dữ liệu không hợp lệ!");
//
//        if (tg.getId() <= 0)
//            throw new IllegalArgumentException("ID không hợp lệ!");
//
//        EntityManager em = JPAUtil.getEntityManager();
//        EntityTransaction tx = em.getTransaction();
//
//        try {
//            tx.begin();
//
//            if (repo.timById(em, tg.getId()) == null)
//                throw new IllegalArgumentException("Tham gia không tồn tại!");
//
//            repo.capNhat(em, tg);
//
//            tx.commit();
//
//        } catch (Exception e) {
//            if (tx.isActive()) tx.rollback();
//            throw new RuntimeException("Lỗi khi cập nhật tham gia: " + e.getMessage(), e);
//        } finally {
//            em.close();
//        }
//    }


//    // ==================== XOÁ ====================
//    public void xoa(int id) {
//
//        if (id <= 0)
//            throw new IllegalArgumentException("ID không hợp lệ!");
//
//        EntityManager em = JPAUtil.getEntityManager();
//        EntityTransaction tx = em.getTransaction();
//
//        try {
//            tx.begin();
//
//            if (repo.timById(em, id) == null)
//                throw new IllegalArgumentException("Bản ghi không tồn tại!");
//
//            repo.xoa(em, id);
//
//            tx.commit();
//
//        } catch (Exception e) {
//            if (tx.isActive()) tx.rollback();
//            throw new RuntimeException("Lỗi khi xóa tham gia: " + e.getMessage(), e);
//        } finally {
//            em.close();
//        }
//    }
//
//    // ==================== LẤY TẤT CẢ ====================
//    public List<ThamGia> layTatCa() {
//
//        EntityManager em = JPAUtil.getEntityManager();
//
//        try {
//            return repo.layTatCa(em);
//        } finally {
//            em.close();
//        }
//    }
//
//    // ==================== TÌM THEO ID ====================
//    public ThamGia timTheoId(int id) {
//
//        EntityManager em = JPAUtil.getEntityManager();
//
//        try {
//            return repo.timById(em, id);
//        } finally {
//            em.close();
//        }
//    }
//
//    // ==================== LẤY THEO DỰ ÁN ====================
//    public List<ThamGia> layTheoDuAn(String maDA) {
//
//        EntityManager em = JPAUtil.getEntityManager();
//
//        try {
//            return repo.layTheoDuAn(em, maDA);
//        } finally {
//            em.close();
//        }
//    }
//
//    // ==================== LẤY THEO NHÂN VIÊN ====================
//    public List<ThamGia> layTheoNhanVien(String maNV) {
//
//        EntityManager em = JPAUtil.getEntityManager();
//
//        try {
//            return repo.layTheoNhanVien(em, maNV);
//        } finally {
//            em.close();
//        }
//    }
}