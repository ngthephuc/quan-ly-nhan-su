package QUANLINHANSU.service;

import QUANLINHANSU.model.PhongBan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class PhongBanService {
    private EntityManagerFactory emf;

    public PhongBanService(EntityManagerFactory emf){
        this.emf = emf;
    }



    public void hienThi(){

        EntityManager em = emf.createEntityManager();

        List<PhongBan> list =
                em.createQuery("from PhongBan",PhongBan.class)
                        .getResultList();

        for(PhongBan pb : list){
            System.out.println(pb);
        }

        em.close();
    }
    public void themPhongBan(PhongBan pb){

        EntityManager em = emf.createEntityManager();

        PhongBan check = em.find(PhongBan.class, pb.getMaPb());

        if(check != null){
            System.out.println("Phong ban da ton tai!");
            em.close();
            return;
        }

        em.getTransaction().begin();
        em.persist(pb);
        em.getTransaction().commit();

        em.close();
    }
    public void suaPhongBan(String maPb, String tenMoi, Double heSoMoi){

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        PhongBan pb = em.find(PhongBan.class, maPb);

        if(pb != null){
            pb.setTenPb(tenMoi);
            pb.setHeSoLuong(heSoMoi);
        }

        em.getTransaction().commit();
        em.close();
    }
    public void xoaPhongBan(String maPb){

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        PhongBan pb = em.find(PhongBan.class, maPb);

        if(pb != null){
            em.remove(pb);
        }

        em.getTransaction().commit();
        em.close();
    }
}
