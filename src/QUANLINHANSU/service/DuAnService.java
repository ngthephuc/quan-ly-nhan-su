package QUANLINHANSU.service;

import QUANLINHANSU.model.Du_An;
import QUANLINHANSU.model.NhanVien;
import QUANLINHANSU.repository.DuAnRepository;
import QUANLINHANSU.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class DuAnService {
    private final DuAnRepository repo = new DuAnRepository();

    // ===================== 1. ĐỌC DỮ LIỆU (READ) =====================

    public List<Du_An> layTatCaDuAn() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return repo.layTatCaRepo(em);
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    public List<Du_An> layTatCaKemSoLuong() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return repo.layDanhSachDuAnKemSoLuong(em);
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    public List<NhanVien> layNhanVienChiTiet(String maDA) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return repo.layNhanVienTheoDuAn(em, maDA);
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    // ===================== 2. NGHIỆP VỤ THÊM MỚI (CREATE) =====================

    public void themDuAn(Du_An duAn) {
        // Check logic trước khi đụng vào DB
        if (duAn.getMaDA() == null || duAn.getMaDA().trim().isEmpty())
            throw new RuntimeException("Mã dự án không được để trống!");
        if (duAn.getKinhPhi() < 0)
            throw new RuntimeException("Kinh phí không được là số âm!");

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            // Kiểm tra trùng mã dự án
            if (repo.timById(em, duAn.getMaDA()) != null) {
                throw new RuntimeException("Mã dự án " + duAn.getMaDA() + " đã tồn tại!");
            }
            repo.them(em, duAn);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    // ===================== 3. NGHIỆP VỤ CẬP NHẬT (UPDATE) =====================

    public void capNhatDuAn(Du_An duAn) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Du_An existing = repo.timById(em, duAn.getMaDA());
            if (existing == null) {
                throw new RuntimeException("Không tìm thấy dự án mã: " + duAn.getMaDA() + " để cập nhật!");
            }
            // Cập nhật các trường dữ liệu
            existing.setTenDA(duAn.getTenDA());
            existing.setKinhPhi(duAn.getKinhPhi());
            existing.setNgayBatDau(duAn.getNgayBatDau());
            existing.setNgayKetThuc(duAn.getNgayKetThuc());

            repo.capNhat(em, existing);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    // ===================== 4. NGHIỆP VỤ XÓA (DELETE) =====================

    public void xoaDuAn(String maDA) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Du_An existing = repo.timById(em, maDA);
            if (existing == null) {
                throw new RuntimeException("Dự án không tồn tại để xóa!");
            }

            // Check ràng buộc: Nếu dự án đang có người tham gia thì không cho xóa
            List<NhanVien> dsNV = repo.layNhanVienTheoDuAn(em, maDA);
            if (dsNV != null && !dsNV.isEmpty()) {
                throw new RuntimeException("Dự án này đang có nhân viên tham gia, không thể xóa!");
            }

            repo.xoa(em, maDA);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }
}