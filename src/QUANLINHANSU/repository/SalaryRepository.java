package QUANLINHANSU.repository;

import QUANLINHANSU.model.SaLary;
import jakarta.persistence.EntityManager;
import java.util.List;

public class SalaryRepository extends BaseRepository<SaLary> {
    public SalaryRepository() {
        super(SaLary.class);
    }

    public List<SaLary> layTatCa(EntityManager em) {
        return em.createQuery("SELECT s FROM SaLary s", SaLary.class).getResultList();
    }
    public SaLary timTheoMa(EntityManager em, String maNV) {
        return em.find(SaLary.class, maNV);
    }

    public void xoaTheoMaNV(EntityManager em, String maNV) {
        SaLary s = em.find(SaLary.class, maNV);
        if (s != null) em.remove(s);
    }
}