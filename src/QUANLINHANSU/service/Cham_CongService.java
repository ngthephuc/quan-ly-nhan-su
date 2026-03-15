package QUANLINHANSU.service;

import QUANLINHANSU.model.Cham_Cong;
import QUANLINHANSU.repository.Cham_CongRepository;
import QUANLINHANSU.repository.DuAnRepository;
import QUANLINHANSU.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class Cham_CongService {
    private final Cham_CongRepository repo = new Cham_CongRepository();
//    ====================Chấm Công===========
    public void ChamCong(Cham_Cong cc){
        if(cc.getMaNV() ==null || cc.getMaNV().isBlank())
            throw new IllegalArgumentException("không quên không chấm công");
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            repo.them(em,cc);
            tx.commit();
            System.out.println("chấm công thành công");
        }
        catch (Exception e){
            if(tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi kh chấm công"+e.getMessage());
        }
        finally {
            em.close();
        }
    }
//    ================ CẬP NHẬT============
    public  void Capnhatchamcong(Cham_Cong cc){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            if(repo.timById(em , cc.getMaNV())==null)
                throw new IllegalArgumentException("Chấm công không thành công");
            repo.capNhat(em,cc);
            tx.commit();
        }
        catch (Exception e){
            throw new RuntimeException("lỗi khi cập nhật"+e.getMessage());
        }
        finally {
            em.close();
        }
    }
//    ==============Xóa=============
   public void xoachamcong(int id){
       EntityManager em =JPAUtil.getEntityManager();
       EntityTransaction tx = em.getTransaction();
       try {
           tx.begin();
           Cham_Cong cc = repo.timById(em,id);
           if (cc==null)
               throw  new IllegalArgumentException("chấm công không tồn tại");
           repo.xoa(em,id);
           tx.commit();
           System.out.println("xóa dự án thành công");
       }
       catch (Exception e){
           throw new RuntimeException("lỗi chấm công"+e.getMessage());
       }
       finally {
           em.close();
       }
   }
//   ======================TÌM THEO ID==============
    public Cham_Cong timchamcong(int id){
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return repo.timById(em,id);
        }
        finally {
            em.close();
        }
    }
// ==========================Lấy tất cả=============
    public List<Cham_Cong> laytatcachamcong(){
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return repo.layTatCa(em);
        }finally {
            em.close();
        }
    }





}