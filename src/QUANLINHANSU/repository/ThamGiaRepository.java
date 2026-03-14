package QUANLINHANSU.repository;

import QUANLINHANSU.model.ThamGia;
import QUANLINHANSU.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ThamGiaRepository {
    public void create(ThamGia tg) {

        EntityManager em = JPAUtil.getEntityManager();

        em.getTransaction().begin();
        em.persist(tg);
        em.getTransaction().commit();

        em.close();
    }

    public List<ThamGia> readAll() {

        EntityManager em = JPAUtil.getEntityManager();

        List<ThamGia> list =
                em.createQuery("SELECT t FROM ThamGia t", ThamGia.class)
                        .getResultList();

        em.close();
        return list;
    }

    public void update(ThamGia tg) {

        EntityManager em = JPAUtil.getEntityManager();

        em.getTransaction().begin();
        em.merge(tg);
        em.getTransaction().commit();

        em.close();
    }

    public void delete(int id) {

        EntityManager em = JPAUtil.getEntityManager();

        em.getTransaction().begin();

        ThamGia tg = em.find(ThamGia.class, id);
        if (tg != null) {
            em.remove(tg);
        }

        em.getTransaction().commit();
        em.close();
    }
}
