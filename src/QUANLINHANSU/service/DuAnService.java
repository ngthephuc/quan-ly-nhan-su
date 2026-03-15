package QUANLINHANSU.service;

import QUANLINHANSU.model.Du_An;
import QUANLINHANSU.repository.DuAnRepository;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DuAnService {
    private DuAnRepository repo = new DuAnRepository();

    public void themduan(EntityManager em, Du_An DA) {
        try {
            em.getTransaction().begin();
            repo.them(em, DA);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }
    public void Capnhat(EntityManager em,Du_An DA){
        try{
            em.getTransaction().begin();
            repo.capNhat(em,DA);
            em.getTransaction().commit();
        }
        catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }
    public void Xoaduan(EntityManager em , int id){
        try {
            em.getTransaction().begin();
            repo.timById(em,id);
            em.getTransaction().commit();
        }
        catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }
    public void Timduan(EntityManager em , int id){
        try {
            em.getTransaction().begin();
            repo.timById(em,id);
            em.getTransaction().commit();
        }
        catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }
    public List<Du_An> LaytatcaDuAn(EntityManager em){
        return repo.layTatCa(em);
    }

}


