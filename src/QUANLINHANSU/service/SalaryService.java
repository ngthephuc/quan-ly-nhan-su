package QUANLINHANSU.service;

import QUANLINHANSU.model.ChucVu;
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

            // 1. Tìm nhân viên (Dùng JOIN FETCH để kéo luôn list Bổ nhiệm về, tránh Lazy lỗi)
            NhanVien nv = em.createQuery(
                            "SELECT n FROM NhanVien n LEFT JOIN FETCH n.danhSachBoNhiem WHERE n.maNV = :ma", NhanVien.class)
                    .setParameter("ma", maNV)
                    .getSingleResult();

            if (nv == null) throw new RuntimeException("Nhân viên không tồn tại!");

            // 2. Tìm hoặc khởi tạo bản ghi lương
            SaLary s = em.find(SaLary.class, maNV);
            if (s == null) {
                s = new SaLary(nv);
                em.persist(s); // Persist luôn để Hibernate quản lý
            }

            // 3. LẤY CHỨC VỤ MỚI NHẤT TỪ BẢNG BỔ NHIỆM
            // Sử dụng hàm getChucVuHienTai() mà anh em mình đã viết trong class NhanVien
            ChucVu cvHienTai = nv.getChucVuHienTai();

            // 4. Tính toán các thành phần lương
            double tongSoCong = chamCongRepo.tinhTongCongThang(em, maNV, thang, nam);
            double luongGoc = (nv.getHopDong() != null) ? nv.getHopDong().getLuongCoBan() : 0;
            double heSo = (nv.getPhongBan() != null) ? nv.getPhongBan().getHeSoLuong() : 1.0;

            // Lấy phụ cấp từ chức vụ trong bảng Bổ Nhiệm
            double phuCap = (cvHienTai != null) ? cvHienTai.getPhuCap() : 0;

            // Công thức tính: ((Lương gốc * Hệ số) / 26) * Công + Phụ cấp
            double thucNhan = ((luongGoc * heSo) / 26.0) * tongSoCong + phuCap;

            // 5. Cập nhật vào Object Salary
            s.setSoNgayCong(tongSoCong);
            s.setLuongCoBan(luongGoc);
            s.setPhuCapChucVu(phuCap);
            s.setTongLuongThucNhan(Math.round(thucNhan));

            // 6. Chốt hạ
            em.merge(s);
            tx.commit();
            System.out.println(">>> Đã tính lương xong cho: " + nv.getHoTen() + " - Chức vụ: " + (cvHienTai != null ? cvHienTai.getTenCV() : "N/A"));

        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
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