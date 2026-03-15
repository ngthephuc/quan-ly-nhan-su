package QUANLINHANSU.service;

import QUANLINHANSU.model.NhanVien;
import QUANLINHANSU.repository.NhanVienRepository;
import QUANLINHANSU.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.w3c.dom.Entity;

public class NhanVienService {

    private final NhanVienRepository nhanVienRepo = new NhanVienRepository();


    public void themNhanVien(NhanVien nv){
        // validate trc khi thêm
        if(nv.getMaNV() == null || nv.getMaNV().isBlank()){
            throw new IllegalArgumentException("Mã Nhân viên không được để trống!");
        }
        if(nv.getHoTen() == null || nv.getHoTen().isBlank()){
            throw new IllegalArgumentException("Tên nhân viên không được để trống!");
        }

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx =  em.getTransaction();

        try{
            tx.begin();


            if (nhanVienRepo.timById(em, nv.getMaNV()) != null)
                throw new IllegalArgumentException("Mã nhân viên '" + nv.getMaNV() + "' đã tồn tại!");

            nhanVienRepo.them(em , nv);
            tx.commit();
            System.out.println("Thêm nhân viên thành công: " + nv.getHoTen());
        }catch (Exception e){
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Lỗi khi thêm nhân viên: " + e.getMessage(), e);
        }finally {
            em.close();
        }


    }

    public void capNhatNhanVien(NhanVien nv){
        // update
    }

    public void xoaNhanVien(String maNV){
        // delete
    }

    public NhanVien timNhanVien(String maNV){
        return null;
    }

}
