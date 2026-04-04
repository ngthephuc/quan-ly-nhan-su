package QUANLINHANSU.service;

import  QUANLINHANSU.model.*;
import QUANLINHANSU.repository.*;
import QUANLINHANSU.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class SalaryService {
    private final SalaryRepository salaryRepo = new SalaryRepository();
    private final NhanVienRepository nhanVienRepo = new NhanVienRepository();

    // 1. Lấy nhân viên chi tiết để đổ lên Form
    public NhanVien layThongTinForm(String maNV) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return salaryRepo.timNhanVienKemChiTiet(em, maNV);
        } finally {
            em.close();
        }
    }

    // 2. Lấy phụ cấp mới nhất để đổ lên Form
    public double layPhuCapForm(String maNV) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return salaryRepo.layPhuCapMoiNhat(em, maNV);
        } finally {
            em.close();
        }
    }

    // 3. Lấy tổng công thực tế từ Database (ChamCong) để đổ lên Form
    public double laySoCongForm(String maNV) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return salaryRepo.tinhTongCongTuDb(em, maNV);
        } finally {
            em.close();
        }
    }

    // 4. Tìm bản ghi lương theo mã (Hàm này nãy báo lỗi đây)
    public SaLary timBangLuongTheoMa(String ma) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return salaryRepo.timById(em, ma);
        } finally {
            em.close();
        }
    }

    // 5. Logic tính toán và lưu lương tự động
    public void tinhVaLuuTuDong(String maNV,double soNgayCong ,String maPhieuLuong) {

        if (maPhieuLuong == null || maPhieuLuong.isBlank()) {
            throw new IllegalArgumentException("Mã phiếu lương không được bỏ chống!!");
        }
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            NhanVien nvDB = nhanVienRepo.timById(em, maNV);
            if (nvDB == null)
                throw new IllegalArgumentException("Nhân viên '" + nvDB.getMaNV() + "' không tồn tại!");
            if(salaryRepo.timById(em, maPhieuLuong) != null)
                throw new IllegalArgumentException("Mã phiếu lương đã tồn tại");

            double heSoLuong = nvDB.getPhongBan().getHeSoLuong();
            double luongCB = nvDB.getHopDong().getLuongCoBan();
            double phuCap = nvDB.getPhuCapHienTai();
            // Công thức: ((Lương CB * Hệ số) / 26) * Công thực tế + Phụ cấp
            double thucNhan = ((luongCB * heSoLuong) / 26.0) * soNgayCong + phuCap;

            // Lưu hoặc Update
            SaLary sa = new SaLary();
            sa.setMaPhieuLuong(maPhieuLuong);
            sa.setNhanVien(nvDB);
            sa.setLuongCoBan(luongCB);
            sa.setSoNgayCong(soNgayCong);
            sa.setPhuCapChucVu(phuCap);
            sa.setTongLuongThucNhan(thucNhan);

            salaryRepo.them(em,sa);
            tx.commit();
        } catch (Exception e) {
                if (tx.isActive()) tx.rollback();
                throw new RuntimeException("Lỗi khi tinh luong: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    // 6. Lấy danh sách lương cho TableView
    public List<SaLary> layDanhSachLuong() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return salaryRepo.layTatCaKemChiTiet(em);
        } finally {
            em.close();
        }
    }

    public List<SaLary> layLichSuTinhLuong(String maNV) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return salaryRepo.layLichSuTinhLuong(em, maNV);
        } finally {
            em.close();
        }
    }

}