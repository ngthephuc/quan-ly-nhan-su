package QUANLINHANSU.repository;

import jakarta.persistence.EntityManager;

import java.util.List;

public abstract class BaseRepository<T>{
    protected final Class<T> entityClass;

    public BaseRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    //ham mau them nv
    public void them (EntityManager em , T entity){
        em.persist(entity);
    }

    public void capNhat(EntityManager em , T entity){
        em.merge(entity);
    }

    public void xoa(EntityManager em , Object id){
        T entity = em.find(entityClass, id);
        if ( entity != null ) em.remove(entity);
    }

    public T timById(EntityManager em, Object id) {
        return em.find(entityClass, id);
    }

    public List<T> layTatCa(EntityManager em) {
        return em.createQuery(
                        "SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                .getResultList();
    }
}
