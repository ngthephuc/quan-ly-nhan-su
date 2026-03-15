package QUANLINHANSU.repository;

import QUANLINHANSU.model.Du_An;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DuAnRepository extends BaseRepository<Du_An> {


    public DuAnRepository() {
        super(Du_An.class);
    }

    public List<Du_An> timTheoTen(EntityManager em, String tenDA) {
        return em.createQuery(
                        "SELECT d FROM Du_An d WHERE LOWER(d.tenDA) LIKE LOWER(:ten)", Du_An.class)
                .setParameter("ten", "%" + tenDA + "%")
                .getResultList();
    }

    public List<Du_An> layDuAnDangThucHien(EntityManager em) {
        return em.createQuery(
                        "SELECT d FROM Du_An d WHERE d.ngayKetThuc >= CURRENT_DATE OR d.ngayKetThuc IS NULL",
                        Du_An.class)
                .getResultList();
    }
}
