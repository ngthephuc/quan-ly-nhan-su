package QUANLINHANSU.service;

import QUANLINHANSU.model.Cham_Cong;
import QUANLINHANSU.model.ChamCongId;
import QUANLINHANSU.model.NhanVien;
import QUANLINHANSU.repository.Cham_CongRepository;
import QUANLINHANSU.repository.NhanVienRepository;
import QUANLINHANSU.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Cham_CongService {

    private final Cham_CongRepository chamCongRepo = new Cham_CongRepository();
    private final NhanVienRepository nhanVienRepo  = new NhanVienRepository();

    // ===================== THÊM ĐƠN LẺ =====================
    public void chamCong(String maNV, LocalDate ngay, LocalTime gioVao, LocalTime gioRa) {
        if (maNV == null || maNV.isBlank())
            throw new IllegalArgumentException("Mã nhân viên không được để trống!");
        if (ngay == null)
            throw new IllegalArgumentException("Ngày chấm công không được để trống!");
        if (gioVao == null || gioRa == null)
            throw new IllegalArgumentException("Giờ vào và giờ ra không được để trống!");
        if (gioRa.isBefore(gioVao))
            throw new IllegalArgumentException("Giờ ra phải sau giờ vào!");

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            if (nhanVienRepo.timById(em, maNV) == null)
                throw new IllegalArgumentException("Nhân viên '" + maNV + "' không tồn tại!");

            // Kiểm tra đã chấm công ngày này chưa — dùng EmbeddedId
            if (chamCongRepo.daChamCong(em, maNV, ngay))
                throw new IllegalStateException("Nhân viên đã được chấm công ngày " + ngay + " rồi!");

            // Tính soCong = số giờ làm
            double socong = Duration.between(gioVao, gioRa).toMinutes() / 60.0;

            ChamCongId id = new ChamCongId(maNV, ngay);
            Cham_Cong cc  = new Cham_Cong(id, nhanVienRepo.timById(em, maNV), gioVao, gioRa);
            chamCongRepo.them(em, cc);
            tx.commit();

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi chấm công: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    // ===================== CHẤM CÔNG HÀNG LOẠT =====================
    public int chamCongHangLoat(List<NhanVien> danhSachNV, LocalDate ngay,
                                LocalTime gioVao, LocalTime gioRa) {
        if (danhSachNV == null || danhSachNV.isEmpty())
            throw new IllegalArgumentException("Danh sách nhân viên không được để trống!");
        if (ngay == null)
            throw new IllegalArgumentException("Ngày chấm công không được để trống!");
        if (gioVao == null || gioRa == null)
            throw new IllegalArgumentException("Giờ vào và giờ ra không được để trống!");
        if (gioRa.isBefore(gioVao))
            throw new IllegalArgumentException("Giờ ra phải sau giờ vào!");


        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        int soLuongThanhCong = 0;
        try {
            tx.begin();
            for (NhanVien nv : danhSachNV) {
                // Bỏ qua nếu đã chấm rồi
                if (chamCongRepo.daChamCong(em, nv.getMaNV(), ngay)) continue;

                // 👇 dòng quan trọng fix lỗi
                NhanVien nvManaged = em.find(NhanVien.class, nv.getMaNV());


                ChamCongId id = new ChamCongId(nv.getMaNV(), ngay);
                Cham_Cong cc  = new Cham_Cong(id, nvManaged, gioVao, gioRa);
                chamCongRepo.them(em, cc);
                soLuongThanhCong++;
            }
            tx.commit();
            return soLuongThanhCong;

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi chấm công hàng loạt: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    // ===================== CẬP NHẬT =====================
    public void capNhat(Cham_Cong chamCong) {
        if (chamCong.getGioRa().isBefore(chamCong.getGioVao()))
            throw new IllegalArgumentException("Giờ ra phải sau giờ vào!");

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            if (chamCongRepo.timById(em, chamCong.getId()) == null)
                throw new IllegalArgumentException("Bản ghi chấm công không tồn tại!");
            chamCongRepo.capNhat(em, chamCong);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi cập nhật chấm công: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    // ===================== XÓA =====================
    public void xoa(ChamCongId id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            if (chamCongRepo.timById(em, id) == null)
                throw new IllegalArgumentException("Bản ghi chấm công không tồn tại!");
            chamCongRepo.xoa(em, id);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi xóa chấm công: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    // ===================== TRUY VẤN =====================
    public List<Cham_Cong> layTheoNhanVienVaThang(String maNV, int thang, int nam) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return chamCongRepo.layTheoNhanVienVaThang(em, maNV, thang, nam);
        } finally {
            em.close();
        }
    }

    public long demNgayCong(String maNV, int thang, int nam) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return chamCongRepo.demNgayCong(em, maNV, thang, nam);
        } finally {
            em.close();
        }
    }

    public List<Cham_Cong> layTheoNgay(LocalDate ngay) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return chamCongRepo.layTheoNgay(em, ngay);
        } finally {
            em.close();
        }
    }

    /** Tính tổng số giờ làm trong tháng (dùng cho Part-time) */
//    public double tinhTongSoGio(String maNV, int thang, int nam) {
//        EntityManager em = JPAUtil.getEntityManager();
//        try {
//            List<Cham_Cong> ds = chamCongRepo
//                    .layTheoNhanVienVaThangCoGioRa(em, maNV, thang, nam);
//            return ds.stream()
//                    .mapToDouble(Cham_Cong::getSocong)
//                    .sum();
//        } finally {
//            em.close();
//        }
//   }
}