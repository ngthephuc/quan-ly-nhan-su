package QUANLINHANSU.repository;

import QUANLINHANSU.model.Cham_Cong;
import QUANLINHANSU.model.ChamCongId;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

public class Cham_CongRepository extends BaseRepository<Cham_Cong> {

    public Cham_CongRepository() {
        super(Cham_Cong.class);
    }

    /** Lấy chấm công theo maNV và tháng/năm */
    public List<Cham_Cong> layTheoNhanVienVaThang(EntityManager em, String maNV, int thang, int nam) {
        LocalDate tuNgay = LocalDate.of(nam, thang, 1);
        LocalDate denNgay = tuNgay.withDayOfMonth(tuNgay.lengthOfMonth());
        return em.createQuery(
                        "SELECT cc FROM Cham_Cong cc " +
                                "WHERE cc.id.maNV = :maNV " +
                                "AND cc.id.ngay BETWEEN :tuNgay AND :denNgay " +
                                "ORDER BY cc.id.ngay ASC", Cham_Cong.class)
                .setParameter("maNV", maNV)
                .setParameter("tuNgay", tuNgay)
                .setParameter("denNgay", denNgay)
                .getResultList();
    }

    /** Đếm số ngày công trong tháng */
    public long demNgayCong(EntityManager em, String maNV, int thang, int nam) {
        LocalDate tuNgay = LocalDate.of(nam, thang, 1);
        LocalDate denNgay = tuNgay.withDayOfMonth(tuNgay.lengthOfMonth());
        return em.createQuery(
                        "SELECT COUNT(cc) FROM Cham_Cong cc " +
                                "WHERE cc.id.maNV = :maNV " +
                                "AND cc.id.ngay BETWEEN :tuNgay AND :denNgay", Long.class)
                .setParameter("maNV", maNV)
                .setParameter("tuNgay", tuNgay)
                .setParameter("denNgay", denNgay)
                .getSingleResult();
    }

    /** Lấy chấm công theo ngày cụ thể (dùng cho Dashboard) */
    public List<Cham_Cong> layTheoNgay(EntityManager em, LocalDate ngay) {
        return em.createQuery(
                        "SELECT cc FROM Cham_Cong cc WHERE cc.id.ngay = :ngay",
                        Cham_Cong.class)
                .setParameter("ngay", ngay)
                .getResultList();
    }

    /** Kiểm tra NV đã chấm công ngày này chưa — dùng find theo EmbeddedId */
    public boolean daChamCong(EntityManager em, String maNV, LocalDate ngay) {
        return em.find(Cham_Cong.class, new ChamCongId(maNV, ngay)) != null;
    }

    /** Lấy danh sách có gioRa để tính tổng giờ (Part-time) */
    public List<Cham_Cong> layTheoNhanVienVaThangCoGioRa(EntityManager em, String maNV, int thang, int nam) {
        LocalDate tuNgay = LocalDate.of(nam, thang, 1);
        LocalDate denNgay = tuNgay.withDayOfMonth(tuNgay.lengthOfMonth());
        return em.createQuery(
                        "SELECT cc FROM Cham_Cong cc " +
                                "WHERE cc.id.maNV = :maNV " +
                                "AND cc.id.ngay BETWEEN :tuNgay AND :denNgay " +
                                "AND cc.gioRa IS NOT NULL " +
                                "ORDER BY cc.id.ngay ASC", Cham_Cong.class)
                .setParameter("maNV", maNV)
                .setParameter("tuNgay", tuNgay)
                .setParameter("denNgay", denNgay)
                .getResultList();
    }
}