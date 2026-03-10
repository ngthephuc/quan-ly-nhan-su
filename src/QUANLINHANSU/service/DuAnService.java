package QUANLINHANSU.service;

import QUANLINHANSU.model.Du_An;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class DuAnService {
//    để keetsnoois với database
    private EntityManagerFactory emf;

    public DuAnService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public  void Hienthi(){
        EntityManager em = emf.createEntityManager();
        List<Du_An> ds = em.createQuery("from DUAN",Du_An.class).getResultList();
        for (Du_An DA : ds){
            System.out.println(DA);
        }
        em.close();

    }
    public void ThemDuAn(Du_An DA){
        EntityManager em = emf.createEntityManager();
        Du_An check = em.find(Du_An.class,DA.getMaDA());
        if(check!=null){
            System.out.println("Dự án tồn tại");
            em.close();
            return;
        }
        em.getTransaction().begin();
        em.persist(DA);
        em.getTransaction().commit();

        em.close();
    }
    public  void SuaDuAn(String MADUAN, String tenDAmoi, double kinhphi){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Du_An DA = em.find(Du_An.class,MADUAN);

        if(DA!=null){
            DA.setTenDA(tenDAmoi);
            DA.setKinhPhi(kinhphi);
        }
        em.getTransaction().commit();
        em.close();
    }
    public void xoaDuAn(String MDA){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Du_An DA = em.find(Du_An.class,MDA);
        if(DA!=null){
            em.remove(DA);
        }
        em.getTransaction().commit();
        em.close();
    }


}
