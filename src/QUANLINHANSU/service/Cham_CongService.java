package QUANLINHANSU.service;

import QUANLINHANSU.model.Cham_Cong;
import QUANLINHANSU.model.ChamCongId;
import QUANLINHANSU.model.NhanVien;
import QUANLINHANSU.repository.Cham_CongRepository;
import QUANLINHANSU.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.time.LocalDate;
import java.util.List;

public class Cham_CongService {

    private final Cham_CongRepository repo = new Cham_CongRepository();

    // ==================== LƯU CHẤM CÔNG ====================
    public void chamCong(Cham_Cong cc) {
        if (cc == null || cc.getNhanVien() == null || cc.getId() == null)
            throw new IllegalArgumentException("Dữ liệu không hợp lệ!");

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            if (em.find(NhanVien.class, cc.getNhanVien().getMaNV()) == null)
                throw new IllegalArgumentException("Nhân viên không tồn tại!");
            repo.them(em, cc);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi: " + e.getMessage());
        } finally { em.close(); }
    }

    // ==================== CẬP NHẬT ====================
    public void capNhatChamCong(Cham_Cong cc) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            repo.capNhat(em, cc);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException(e.getMessage());
        } finally { em.close(); }
    }

    // ==================== XÓA ====================
    public void xoaChamCong(ChamCongId id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            repo.xoa(em, id);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException(e.getMessage());
        } finally { em.close(); }
    }

    // ==================== LẤY TỔNG CÔNG (CHO BẢNG LƯƠNG) ====================
    public double layTongCongThang(String maNV, int thang, int nam) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return repo.tinhTongCongThang(em, maNV, thang, nam);
        } finally { em.close(); }
    }
    public List<Cham_Cong> layTheoNgay(LocalDate ngay) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // Gọi xuống Repository để truy vấn SQL
            return repo.layTheoNgay(em, ngay);
        } finally {
            em.close();
        }
    }
}