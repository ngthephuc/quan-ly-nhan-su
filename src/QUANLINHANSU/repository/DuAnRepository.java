package QUANLINHANSU.repository;

import QUANLINHANSU.model.Du_An;
import QUANLINHANSU.model.NhanVien;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class DuAnRepository extends BaseRepository<Du_An> {

    public DuAnRepository() {
        super(Du_An.class);
    }

    // SELECT dự án kèm số lượng thành viên cho bảng chính
    public List<Du_An> layDanhSachDuAnKemSoLuong(EntityManager em) {
        String jpql = "SELECT d, COUNT(tg) FROM Du_An d LEFT JOIN d.danhSachThamGia tg GROUP BY d";
        List<Object[]> result = em.createQuery(jpql, Object[].class).getResultList();
        List<Du_An> list = new ArrayList<>();
        for (Object[] row : result) {
            Du_An da = (Du_An) row[0];
            Long count = (Long) row[1];
            da.setSoNguoiThamGia(count.intValue()); // field @Transient
            list.add(da);
        }
        return list;
    }

    // SELECT nhân viên tham gia của 1 dự án cho bảng chi tiết
    public List<NhanVien> layNhanVienTheoDuAn(EntityManager em, String maDA) {
        String jpql = "SELECT tg.nhanVien FROM ThamGia tg WHERE tg.duAn.maDA = :maDA";
        return em.createQuery(jpql, NhanVien.class)
                .setParameter("maDA", maDA)
                .getResultList();
    }

    public List<Du_An> layTatCaRepo(EntityManager em) {
        return em.createQuery("SELECT d FROM Du_An d", Du_An.class).getResultList();
    }
}