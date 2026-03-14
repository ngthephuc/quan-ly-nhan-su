package QUANLINHANSU.repository;

import QUANLINHANSU.model.HopDong;
import QUANLINHANSU.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class HopDongRepository {
    public void create(HopDong hd) {

        EntityManager em = JPAUtil.getEntityManager();

        em.getTransaction().begin();
        em.persist(hd);
        em.getTransaction().commit();

        em.close();
    }

    public List<HopDong> readAll() {

        EntityManager em = JPAUtil.getEntityManager();

        List<HopDong> list =
                em.createQuery("SELECT h FROM HopDong h", HopDong.class)
                        .getResultList();

        em.close();
        return list;
    }

    public void update(HopDong hd) {

        EntityManager em = JPAUtil.getEntityManager();

        em.getTransaction().begin();
        em.merge(hd);
        em.getTransaction().commit();

        em.close();
    }

    public void delete(String maHD) {

        EntityManager em = JPAUtil.getEntityManager();

        em.getTransaction().begin();

        HopDong hd = em.find(HopDong.class, maHD);
        if (hd != null) {
            em.remove(hd);
        }

        em.getTransaction().commit();
        em.close();
    }
}
