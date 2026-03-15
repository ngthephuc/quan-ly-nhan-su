package QUANLINHANSU.service;

import QUANLINHANSU.model.ThamGia;
import QUANLINHANSU.repository.ThamGiaRepository;
import QUANLINHANSU.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class ThamGiaService {

    private final ThamGiaRepository repo = new ThamGiaRepository();

    // thêm tham gia
    public void themThamGia(ThamGia tg) {

        if (tg.getNhanVien() == null)
            throw new IllegalArgumentException("Nhan vien khong hop le");

        if (tg.getMaDA() == null || tg.getMaDA().isBlank())
            throw new IllegalArgumentException("Ma du an khong hop le");

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            repo.them(em, tg);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Loi khi them tham gia", e);
        } finally {
            em.close();
        }
    }

    // lấy danh sách
    public List<ThamGia> layDanhSach() {

        EntityManager em = JPAUtil.getEntityManager();
        try {
            return repo.layTatCa(em);
        } finally {
            em.close();
        }
    }

    // cập nhật
    public void capNhat(ThamGia tg) {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            repo.capNhat(em, tg);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Loi khi cap nhat tham gia", e);
        } finally {
            em.close();
        }
    }

    // xóa
    public void xoa(int id) {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            repo.xoa(em, id);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Loi khi xoa tham gia", e);
        } finally {
            em.close();
        }
    }
}