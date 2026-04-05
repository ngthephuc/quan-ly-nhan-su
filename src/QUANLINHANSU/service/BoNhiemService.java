package QUANLINHANSU.service;

import QUANLINHANSU.model.BoNhiem;
import QUANLINHANSU.model.BoNhiemId;
import QUANLINHANSU.model.NhanVien;
import QUANLINHANSU.model.PhongBan;
import QUANLINHANSU.repository.BoNhiemRepository;
import QUANLINHANSU.repository.ChucVuRepository;
import QUANLINHANSU.repository.NhanVienRepository;
import QUANLINHANSU.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.time.LocalDate;
import java.util.List;

public class BoNhiemService {

    private final BoNhiemRepository boNhiemRepo = new BoNhiemRepository();
    private final NhanVienRepository nhanVienRepo = new NhanVienRepository();
    private final ChucVuRepository chucVuRepo = new ChucVuRepository();

    // ===================== BỔ NHIỆM MỚI =====================
    // Tự động kết thúc chức vụ cũ rồi thêm chức vụ mới — 2 bước trong 1 transaction
    public void boNhiem(String maNV, String maCV,LocalDate tuNgay, String quyetDinhSo) {
        if (maNV == null || maNV.isBlank())
            throw new IllegalArgumentException("Mã nhân viên không được để trống!");
        if (maCV == null || maCV.isBlank())
            throw new IllegalArgumentException("Mã chức vụ không được để trống!");
        if (quyetDinhSo == null || quyetDinhSo.isBlank())
            throw new IllegalArgumentException("Số quyết định không được để trống!");
        if(tuNgay==null ){
            throw new IllegalArgumentException("Ngày bổ nhiệm không được bỏ trống!");
        } if(tuNgay.isBefore(LocalDate.now()) ){
            throw new IllegalArgumentException("Ngày bổ nhiệm không được là ngày quá khứ!");
        }

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            if (nhanVienRepo.timById(em, maNV) == null)
                throw new IllegalArgumentException("Nhân viên '" + maNV + "' không tồn tại!");
            if (chucVuRepo.timById(em, maCV) == null)
                throw new IllegalArgumentException("Chức vụ '" + maCV + "' không tồn tại!");

            // Kết thúc chức vụ hiện tại nếu có
            BoNhiem cu = boNhiemRepo.layChucVuHienTai(em, maNV);
            if (cu != null) {
                cu.setDenNgay(LocalDate.now());
                boNhiemRepo.capNhat(em, cu);
            }
            //tp
            // 🔥 tránh trùng khóa (maNV + maCV + tuNgay)
            while (em.find(BoNhiem.class, new BoNhiemId(maNV, maCV, tuNgay)) != null) {
                tuNgay = tuNgay.plusDays(1); // tự động +1 ngày
            }

            BoNhiemId id = new BoNhiemId(maNV, maCV, tuNgay);

// 🔥 thêm đoạn này
//            BoNhiem tonTai = em.find(BoNhiem.class, id);
//            if (tonTai != null) {
//                throw new RuntimeException("Bổ nhiệm đã tồn tại!");
//            }
            // 🔥 check chức vụ hiện tại (không check lịch sử nữa)
            BoNhiem hienTai = boNhiemRepo.layChucVuHienTai(em, maNV);

            if (hienTai != null && hienTai.getChucVu().getMaCV().equals(maCV)) {
                throw new RuntimeException("Nhân viên đang giữ chức vụ này!");
            }

// tạo mới
            BoNhiem boNhiemMoi = new BoNhiem(
                    id,
                    nhanVienRepo.timById(em, maNV),
                    chucVuRepo.timById(em, maCV),
                    null,
                    quyetDinhSo
            );

            boNhiemRepo.them(em, boNhiemMoi);
            //
            // Tạo bổ nhiệm mới
//            BoNhiemId id = new BoNhiemId(maNV, maCV, tuNgay);
//            BoNhiem boNhiemMoi = new BoNhiem(
//                    id,
//                    nhanVienRepo.timById(em, maNV),
//                    chucVuRepo.timById(em, maCV),
//                    null,   // denNgay = null → đang giữ chức vụ
//                    quyetDinhSo
//            );
//            boNhiemRepo.them(em, boNhiemMoi);
            tx.commit();

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi bổ nhiệm: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
//tp
public void ketThucChucVu(String maNV) {
    EntityManager em = JPAUtil.getEntityManager();
    EntityTransaction tx = em.getTransaction();
    try {
        tx.begin();

        BoNhiem bn = boNhiemRepo.layChucVuHienTai(em, maNV);
        if (bn == null)
            throw new IllegalStateException("Nhân viên '" + maNV + "' không có chức vụ nào đang giữ!");

        // 🔥 1. XỬ LÝ TRƯỞNG PHÒNG TRƯỚC
        if (bn.getChucVu().getTenCV().equalsIgnoreCase("Trưởng phòng")) {

            NhanVien nv = bn.getNhanVien();

            if (nv.getPhongBan() != null) {
                PhongBan pb = nv.getPhongBan();

                if (pb.getTruongPhong() != null &&
                        pb.getTruongPhong().getMaNV().equals(nv.getMaNV())) {

                    pb.setTruongPhong(null);
                    em.merge(pb);
                }
            }
        }

        // 🔥 2. SAU ĐÓ MỚI KẾT THÚC CHỨC VỤ
        bn.setDenNgay(LocalDate.now());
        boNhiemRepo.capNhat(em, bn);

        tx.commit();

    } catch (Exception e) {
        if (tx.isActive()) tx.rollback();
        throw new RuntimeException("Lỗi khi kết thúc chức vụ: " + e.getMessage(), e);
    } finally {
        em.close();
    }
}
//
//    // ===================== KẾT THÚC CHỨC VỤ =====================
//    public void ketThucChucVu(String maNV) {
//        EntityManager em = JPAUtil.getEntityManager();
//        EntityTransaction tx = em.getTransaction();
//        try {
//            tx.begin();
//
//            BoNhiem bn = boNhiemRepo.layChucVuHienTai(em, maNV);
//            if (bn == null)
//                throw new IllegalStateException("Nhân viên '" + maNV + "' không có chức vụ nào đang giữ!");
//
//            bn.setDenNgay(LocalDate.now());
//            boNhiemRepo.capNhat(em, bn);
//            tx.commit();
//
//        } catch (Exception e) {
//            if (tx.isActive()) tx.rollback();
//            throw new RuntimeException("Lỗi khi kết thúc chức vụ: " + e.getMessage(), e);
//        } finally {
//            em.close();
//        }
//    }

    // ===================== TRUY VẤN =====================
    public BoNhiem layChucVuHienTai(String maNV) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return boNhiemRepo.layChucVuHienTai(em, maNV);
        } finally {
            em.close();
        }
    }

    public List<BoNhiem> layLichSuChucVu(String maNV) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return boNhiemRepo.layLichSuChucVu(em, maNV);
        } finally {
            em.close();
        }
    }
}