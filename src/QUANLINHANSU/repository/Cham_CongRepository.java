package QUANLINHANSU.repository;

import QUANLINHANSU.model.Cham_Cong;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

public class Cham_CongRepository extends BaseRepository<Cham_Cong> {

    public Cham_CongRepository() {
        super(Cham_Cong.class);
    }

    // HÀM CHUẨN: Lấy tổng số công thực tế trong tháng
    public double tinhTongCongThang(EntityManager em, String maNV, int thang, int nam) {
        // Mốc từ ngày 1 đến ngày cuối tháng
        LocalDate tuNgay = LocalDate.of(nam, thang, 1);
        LocalDate denNgay = tuNgay.withDayOfMonth(tuNgay.lengthOfMonth());

        // Dùng SUM(cc.socong) để lấy đúng giá trị thực tế
        Double tongCong = em.createQuery(
                        "SELECT SUM(cc.socong) FROM Cham_Cong cc WHERE cc.nhanVien.maNV = :maNV " +
                                "AND cc.id.ngay BETWEEN :tuNgay AND :denNgay",
                        Double.class)
                .setParameter("maNV", maNV)
                .setParameter("tuNgay", tuNgay)
                .setParameter("denNgay", denNgay)
                .getSingleResult();

        return (tongCong != null) ? tongCong : 0.0;
    }

    public List<Cham_Cong> layTheoNgay(EntityManager em, LocalDate ngay) {
        return em.createQuery(
                        "SELECT cc FROM Cham_Cong cc WHERE cc.id.ngay = :ngay",
                        Cham_Cong.class)
                .setParameter("ngay", ngay)
                .getResultList();
    }
}