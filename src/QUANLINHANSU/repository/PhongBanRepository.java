package QUANLINHANSU.repository;


import QUANLINHANSU.model.PhongBan;
import jakarta.persistence.EntityManager;

import java.util.List;

public class PhongBanRepository extends Baserepository
{
    public PhongBanRepository(Class entityClass) {
        super(entityClass);
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
