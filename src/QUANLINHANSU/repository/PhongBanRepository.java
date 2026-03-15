package QUANLINHANSU.repository;


import QUANLINHANSU.model.PhongBan;
import jakarta.persistence.EntityManager;
import java.util.List;

public class PhongBanRepository extends BaseRepository<PhongBan>
{
    public PhongBanRepository(Class<PhongBan> entityClass) {
        super(entityClass);
    }

    public PhongBanRepository() {
        super(PhongBan.class);
    }

    public List<PhongBan> timTheoTen(EntityManager em, String tenPb) {
        return em.createQuery(
                        "SELECT p FROM PhongBan p WHERE LOWER(p.tenPb) LIKE LOWER(:ten)", PhongBan.class)
                .setParameter("ten", "%" + tenPb + "%")
                .getResultList();
    }

    public List<PhongBan> layDanhSachSapXep(EntityManager em) {
        return em.createQuery(
                        "SELECT p FROM PhongBan p ORDER BY p.tenPb ASC", PhongBan.class)
                .getResultList();
    }
}
