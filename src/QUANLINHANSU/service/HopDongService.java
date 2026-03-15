package QUANLINHANSU.service;

import QUANLINHANSU.model.HopDong;
import QUANLINHANSU.repository.HopDongRepository;
import QUANLINHANSU.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class HopDongService {

    private final HopDongRepository repo = new HopDongRepository();

    // thêm hợp đồng
    public void themHopDong(HopDong hd) {

        if (hd.getNhanVien() == null)
            throw new IllegalArgumentException("Nhan vien khong hop le");

        if (hd.getMaHD() == null || hd.getMaHD().isBlank())
            throw new IllegalArgumentException("Ma hop dong khong hop le");

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            repo.them(em, hd);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Loi khi them hop dong", e);
        } finally {
            em.close();
        }
    }

    // lấy danh sách
    public List<HopDong> layDanhSach() {

        EntityManager em = JPAUtil.getEntityManager();
        try {
            return repo.layTatCa(em);
        } finally {
            em.close();
        }
    }

    // cập nhật
    public void capNhat(HopDong hd) {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            repo.capNhat(em, hd);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Loi khi cap nhat hop dong", e);
        } finally {
            em.close();
        }
    }

    // xóa
    public void xoa(String maHD) {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            repo.xoa(em, maHD);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Loi khi xoa hop dong", e);
        } finally {
            em.close();
        }
    }
}