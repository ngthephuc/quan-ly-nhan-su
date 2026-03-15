package QUANLINHANSU.service;

import QUANLINHANSU.model.Cham_Cong;
import QUANLINHANSU.repository.Cham_CongRepository;
import jakarta.persistence.EntityManager;

import java.util.List;

public class Cham_CongService {

    private Cham_CongRepository repo = new Cham_CongRepository();
    public void themChamCong(EntityManager em, Cham_Cong cc){
        try{
            em.getTransaction().begin();

            repo.them(em, cc);

            em.getTransaction().commit();
        }
        catch(Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    // cập nhật chấm công
    public void capNhatChamCong(EntityManager em, Cham_Cong cc){
        try{
            em.getTransaction().begin();

            repo.capNhat(em, cc);

            em.getTransaction().commit();
        }
        catch(Exception e){
            em.getTransaction().rollback();
        }
    }

    // xoá
    public void xoaChamCong(EntityManager em, int id){
        try{
            em.getTransaction().begin();

            repo.xoa(em, id);

            em.getTransaction().commit();
        }
        catch(Exception e){
            em.getTransaction().rollback();
        }
    }

    // tìm theo id
    public Cham_Cong timChamCong(EntityManager em, int id){
        return repo.timById(em,id);
    }

    // lấy tất cả
    public List<Cham_Cong> layTatCaChamCong(EntityManager em){
        return repo.layTatCa(em);
    }



}