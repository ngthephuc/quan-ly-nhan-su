package QUANLINHANSU.repository;

import QUANLINHANSU.model.ChucVu;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ChucVuRepository extends BaseRepository<ChucVu> {

    public ChucVuRepository() {
        super(ChucVu.class);
    }

    public List<ChucVu> timTheoTen(EntityManager em, String tenCV) {
        return em.createQuery(
                        "SELECT c FROM ChucVu c WHERE LOWER(c.tenCV) LIKE LOWER(:ten)", ChucVu.class)
                .setParameter("ten", "%" + tenCV + "%")
                .getResultList();
    }

    public List<ChucVu> layTheoMucPhuCap(EntityManager em, double mucPhuCap) {
        return em.createQuery(
                        "SELECT c FROM ChucVu c WHERE c.phuCap >= :muc ORDER BY c.phuCap DESC", ChucVu.class)
                .setParameter("muc", mucPhuCap)
                .getResultList();
    }
}
