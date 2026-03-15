package QUANLINHANSU.repository;

import QUANLINHANSU.model.Cham_Cong;

import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

public class Cham_CongRepository extends BaseRepository<Cham_Cong> {


    public Cham_CongRepository() {
        super(Cham_Cong.class);
    }
    public List<Cham_Cong> layTheoNhanVienVaThang(EntityManager em, String maNV, int thang, int nam) {
        LocalDate tuNgay = LocalDate.of(nam, thang, 1);
        LocalDate denNgay = tuNgay.withDayOfMonth(tuNgay.lengthOfMonth());
        return em.createQuery(
                        "SELECT cc FROM Cham_Cong cc WHERE cc.nhanVien.maNV = :maNV " +
                                "AND cc.ngay BETWEEN :tuNgay AND :denNgay ORDER BY cc.ngay ASC",
                        Cham_Cong.class)
                .setParameter("maNV", maNV)
                .setParameter("tuNgay", tuNgay)
                .setParameter("denNgay", denNgay)
                .getResultList();
    }

    public long demNgayCong(EntityManager em, String maNV, int thang, int nam) {
        LocalDate tuNgay = LocalDate.of(nam, thang, 1);
        LocalDate denNgay = tuNgay.withDayOfMonth(tuNgay.lengthOfMonth());
        return em.createQuery(
                        "SELECT COUNT(cc) FROM Cham_Cong cc WHERE cc.nhanVien.maNV = :maNV " +
                                "AND cc.ngay BETWEEN :tuNgay AND :denNgay",
                        Long.class)
                .setParameter("maNV", maNV)
                .setParameter("tuNgay", tuNgay)
                .setParameter("denNgay", denNgay)
                .getSingleResult();
    }

    public List<Cham_Cong> layTheoNgay(EntityManager em, LocalDate ngay) {
        return em.createQuery(
                        "SELECT cc FROM Cham_Cong cc WHERE cc.ngay = :ngay", Cham_Cong.class)
                .setParameter("ngay", ngay)
                .getResultList();
    }

}