package QUANLINHANSU.service;

import QUANLINHANSU.model.HopDong;
import QUANLINHANSU.model.NhanVien;
import QUANLINHANSU.repository.HopDongRepository;
import QUANLINHANSU.repository.NhanVienRepository;
import QUANLINHANSU.repository.PhongBanRepository;
import QUANLINHANSU.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.time.LocalDate;
import java.util.List;

public class NhanVienService {

    private final NhanVienRepository nhanVienRepo = new NhanVienRepository();
    private final PhongBanRepository phongBanRepo = new PhongBanRepository();
    private final HopDongRepository hopDongRepo = new HopDongRepository();

    // thêm nhân viên
    public void themNhanVienDayDu(NhanVien nhanVien, HopDong hopDong) {

        //  Validate nhân viên
        if (nhanVien.getMaNV() == null || nhanVien.getMaNV().isBlank())
            throw new IllegalArgumentException("Mã nhân viên không được để trống!");
        if (nhanVien.getHoTen() == null || nhanVien.getHoTen().isBlank())
            throw new IllegalArgumentException("Họ tên không được để trống!");
        if (nhanVien.getNgaySinh() == null)
            throw new IllegalArgumentException("Ngày sinh không được để trống!");
        if (nhanVien.getNgaySinh().isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Ngày sinh không được là ngày tương lai!");
        if (nhanVien.getCccd() == null || !nhanVien.getCccd().matches("\\d{12}"))
            throw new IllegalArgumentException("CCCD phải đủ 12 chữ số!");
        if (nhanVien.getSdt() == null || !nhanVien.getSdt().matches("\\d{10}"))
            throw new IllegalArgumentException("Số điện thoại phải đủ 10 chữ số!");
        if (nhanVien.getEmail() == null || !nhanVien.getEmail().contains("@"))
            throw new IllegalArgumentException("Email không hợp lệ!");
        if (nhanVien.getNgayVaoLam() == null)
            throw new IllegalArgumentException("Ngày vào làm không được để trống!");
        if (nhanVien.getNgayVaoLam().isBefore(nhanVien.getNgaySinh()))
            throw new IllegalArgumentException("Ngày vào làm không được trước ngày sinh!");
        if (nhanVien.getPhongBan() == null)
            throw new IllegalArgumentException("Phòng ban không được để trống!");

        // Validate hợp đồng
        if (hopDong.getMaHD() == null || hopDong.getMaHD().isBlank())
            throw new IllegalArgumentException("Mã hợp đồng không được để trống!");
        if (hopDong.getLoaiHD() == null || hopDong.getLoaiHD().isBlank())
            throw new IllegalArgumentException("Loại hợp đồng không được để trống!");
        if (hopDong.getNgayBatDau() == null)
            throw new IllegalArgumentException("Ngày bắt đầu hợp đồng không được để trống!");
        if (hopDong.getNgayKetThuc() != null
                && hopDong.getNgayKetThuc().isBefore(hopDong.getNgayBatDau()))
            throw new IllegalArgumentException("Ngày kết thúc hợp đồng phải sau ngày bắt đầu!");
        if (hopDong.getLuongCoBan() <= 0)
            throw new IllegalArgumentException("Lương cơ bản phải lớn hơn 0!");

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            if (nhanVienRepo.timById(em, nhanVien.getMaNV()) != null)
                throw new IllegalArgumentException("Mã nhân viên '" + nhanVien.getMaNV() + "' đã tồn tại!");
            if (nhanVienRepo.timTheoCCCD(em, nhanVien.getCccd()) != null)
                throw new IllegalArgumentException("CCCD '" + nhanVien.getCccd() + "' đã được đăng ký!");
            if (phongBanRepo.timById(em, nhanVien.getPhongBan().getMaPb()) == null)
                throw new IllegalArgumentException("Phòng ban không tồn tại!");
            if (hopDongRepo.timById(em, hopDong.getMaHD()) != null)
                throw new IllegalArgumentException("Mã hợp đồng '" + hopDong.getMaHD() + "' đã tồn tại!");

            // Gắn NV vào HĐ rồi lưu NV trước, HĐ sau (vì HĐ có FK tới NV)
            hopDong.setNhanVien(nhanVien);
            nhanVienRepo.them(em, nhanVien);
            hopDongRepo.them(em, hopDong);

            tx.commit();

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi thêm nhân viên: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    // ==================== CẬP NHẬT ====================
    public void capNhatNhanVien(NhanVien nhanVien) {
        if (nhanVien.getHoTen() == null || nhanVien.getHoTen().isBlank())
            throw new IllegalArgumentException("Họ tên không được để trống!");
        if (nhanVien.getNgaySinh() != null && nhanVien.getNgaySinh().isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Ngày sinh không hợp lệ!");
        if (nhanVien.getSdt() != null && !nhanVien.getSdt().matches("\\d{10}"))
            throw new IllegalArgumentException("Số điện thoại phải đủ 10 chữ số!");
        if (nhanVien.getEmail() != null && !nhanVien.getEmail().contains("@"))
            throw new IllegalArgumentException("Email không hợp lệ!");
        if (nhanVien.getPhongBan() == null)
            throw new IllegalArgumentException("Phòng ban không được để trống!");

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            if (nhanVienRepo.timById(em, nhanVien.getMaNV()) == null)
                throw new IllegalArgumentException("Nhân viên '" + nhanVien.getMaNV() + "' không tồn tại!");

            NhanVien nvCCCD = nhanVienRepo.timTheoCCCD(em, nhanVien.getCccd());
            if (nvCCCD != null && !nvCCCD.getMaNV().equals(nhanVien.getMaNV()))
                throw new IllegalArgumentException("CCCD đã được đăng ký bởi nhân viên khác!");

            if (phongBanRepo.timById(em, nhanVien.getPhongBan().getMaPb()) == null)
                throw new IllegalArgumentException("Phòng ban không tồn tại!");

            nhanVienRepo.capNhat(em, nhanVien);
            tx.commit();

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi cập nhật nhân viên: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    // ==================== NGHỈ VIỆC ====================
    public void nghi(String maNV) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            NhanVien nv = nhanVienRepo.timById(em, maNV);
            if (nv == null)
                throw new IllegalArgumentException("Nhân viên '" + maNV + "' không tồn tại!");
            if ("Đã nghỉ".equals(nv.getTrangThai()))
                throw new IllegalStateException("Nhân viên này đã nghỉ việc rồi!");

            nv.setTrangThai("Đã nghỉ");
            nhanVienRepo.capNhat(em, nv);
            tx.commit();

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi cập nhật trạng thái: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    // ==================== TRUY VẤN ====================
    public NhanVien timTheoMa(String maNV) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return nhanVienRepo.timById(em, maNV);
        } finally {
            em.close();
        }
    }

    public List<NhanVien> layTatCa() {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return nhanVienRepo.layTatCa(em);
        }
    }

    public List<NhanVien> timTheoTen(String hoTen) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return nhanVienRepo.timTheoTen(em, hoTen);
        }
    }

    public List<NhanVien> layTheoPhongBan(String maPb) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return nhanVienRepo.layTheoPhongBan(em, maPb);
        }
    }

    public List<NhanVien> layDangLam() {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return nhanVienRepo.layTheoTrangThai(em, "Đang làm");
        }
    }

    public long demNhanVienDangLam() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return nhanVienRepo.demNhanVienDangLam(em);
        } finally {
            em.close();
        }
    }
}