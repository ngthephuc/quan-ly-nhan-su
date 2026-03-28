package QUANLINHANSU.service;

import QUANLINHANSU.model.NhanVien;
import QUANLINHANSU.model.SaLary;
import QUANLINHANSU.repository.Cham_CongRepository;
import QUANLINHANSU.repository.SalaryRepository; // // Sử dụng Repo của ông giáo
import QUANLINHANSU.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class SalaryService {
    // // Khai báo các Repository để dùng chung trong Service
    private final Cham_CongRepository chamCongRepo = new Cham_CongRepository();
    private final SalaryRepository repo = new SalaryRepository();

    // // --- HÀM TÍNH LƯƠNG TỰ ĐỘNG ---
    public void tinhVaLuuTuDong(String maNV, int thang, int nam) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            // // 1. Tìm nhân viên
            NhanVien nv = em.find(NhanVien.class, maNV);
            if (nv == null) throw new RuntimeException("Nhân viên không tồn tại!");

            // // 2. Tìm bản ghi lương cũ (Dùng repo tìm cho chuẩn)
            SaLary s = repo.timTheoMa(em, maNV);
            boolean isNew = false;
            if (s == null) {
                s = new SaLary(nv); // // Constructor gán ID mà anh em mình đã sửa
                isNew = true;
            }

            // // 3. Tính toán công thức (Lương gốc * Hệ số / 26 * Công + Phụ cấp)
            double tongSoCong = chamCongRepo.tinhTongCongThang(em, maNV, thang, nam);
            double luongGoc = (nv.getHopDong() != null) ? nv.getHopDong().getLuongCoBan() : 0;
            double heSo = (nv.getPhongBan() != null) ? nv.getPhongBan().getHeSoLuong() : 1.0;
            double phuCap = (nv.getChucVu() != null) ? nv.getChucVu().getPhuCap() : 0;
            double thucNhan = ((luongGoc * heSo) / 26.0) * tongSoCong + phuCap;

            s.setSoNgayCong(tongSoCong);
            s.setLuongCoBan(luongGoc);
            s.setPhuCapChucVu(phuCap);
            s.setTongLuongThucNhan(Math.round(thucNhan));

            // // 4. Lưu dữ liệu
            if (isNew) {
                em.persist(s);
            } else {
                em.merge(s);
            }

            em.flush(); // // Chốt hạ để tránh lỗi AssertionFailure
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally { em.close(); }
    }

    // // --- HÀM LẤY TẤT CẢ (Đã mở khóa và dùng Repo) ---
    public List<SaLary> layTatCa() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // // Gọi hàm layTatCa từ Repository và truyền 'em' sang
            return repo.layTatCa(em);
        } finally {
            em.close();
        }
    }

    // // --- HÀM TÌM THEO MÃ ---
    public SaLary timTheoMa(String maNV) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return repo.timTheoMa(em, maNV);
        } finally {
            em.close();
        }
    }

    // // --- HÀM XÓA LƯƠNG ---
    public void xoaLuong(String maNV) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            // // Tìm bản ghi qua repo trước khi xóa
            SaLary s = repo.timTheoMa(em, maNV);
            if (s != null) em.remove(s);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally { em.close(); }
    }
}