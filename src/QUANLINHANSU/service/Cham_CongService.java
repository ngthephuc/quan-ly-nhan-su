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

    // ==================== CHẤM CÔNG ====================
    public void chamCong(Cham_Cong cc) {

        if (cc == null)
            throw new IllegalArgumentException("Dữ liệu chấm công không hợp lệ!");

        if (cc.getNhanVien() == null || cc.getNhanVien().getMaNV() == null)
            throw new IllegalArgumentException("Nhân viên không hợp lệ!");

        if (cc.getId() == null)
            throw new IllegalArgumentException("ID chấm công không hợp lệ!");

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {

            tx.begin();

            // kiểm tra nhân viên tồn tại
            if (em.find(NhanVien.class, cc.getNhanVien().getMaNV()) == null)
                throw new IllegalArgumentException("Nhân viên không tồn tại!");

            repo.them(em, cc);

            tx.commit();

        } catch (Exception e) {

            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi chấm công: " + e.getMessage(), e);

        } finally {

            em.close();

        }
    }

    // ==================== CẬP NHẬT ====================
    public void capNhatChamCong(Cham_Cong cc) {

        if (cc == null || cc.getId() == null)
            throw new IllegalArgumentException("Dữ liệu chấm công không hợp lệ!");

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {

            tx.begin();

            if (repo.timById(em, cc.getId()) == null)
                throw new IllegalArgumentException("Bản ghi chấm công không tồn tại!");

            repo.capNhat(em, cc);

            tx.commit();

        } catch (Exception e) {

            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi cập nhật chấm công: " + e.getMessage(), e);

        } finally {

            em.close();

        }
    }

    // ==================== XÓA ====================
    public void xoaChamCong(ChamCongId id) {

        if (id == null)
            throw new IllegalArgumentException("ID không hợp lệ!");

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {

            tx.begin();

            if (repo.timById(em, id) == null)
                throw new IllegalArgumentException("Chấm công không tồn tại!");

            repo.xoa(em, id);

            tx.commit();

        } catch (Exception e) {

            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi xóa chấm công: " + e.getMessage(), e);

        } finally {

            em.close();

        }
    }

    // ==================== TÌM THEO ID ====================
    public Cham_Cong timChamCong(ChamCongId id) {

        EntityManager em = JPAUtil.getEntityManager();

        try {

            return repo.timById(em, id);

        } finally {

            em.close();

        }
    }
    // dem tong ngay cong
    public long demNgayCong(String maNV, int thang, int nam) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return repo.demNgayCong(em, maNV, thang, nam);
        } finally {
            em.close();
        }
    }

    public List<Cham_Cong> layTheoNgay(LocalDate ngay) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return repo.layTheoNgay(em, ngay);
        } finally {
            em.close();
        }
    }


}