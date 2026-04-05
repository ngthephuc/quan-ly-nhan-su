package QUANLINHANSU.repository;

import QUANLINHANSU.model.TaiKhoan;
import QUANLINHANSU.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class TaiKhoanRepository {

//    public TaiKhoan findByUsernamePassword(String tk, String mk) {
//        EntityManager em = JPAUtil.getEntityManager();
//        try {
//            String jpql = "SELECT t FROM TaiKhoan t WHERE t.tk = :tk AND t.mk = :mk";
//
//            TypedQuery<TaiKhoan> query = em.createQuery(jpql, TaiKhoan.class);
//            query.setParameter("tk", tk);
//            query.setParameter("mk", mk);
//
//            return query.getResultStream().findFirst().orElse(null);
//
//        } finally {
//            em.close();
//        }
//    }

    public TaiKhoan findByUsernamePassword(String tk, String mk) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT t FROM TaiKhoan t WHERE t.username = :tk AND t.password = :mk";

            TypedQuery<TaiKhoan> query = em.createQuery(jpql, TaiKhoan.class);
            query.setParameter("tk", tk);
            query.setParameter("mk", mk);

            TaiKhoan result = query.getResultStream().findFirst().orElse(null);

            // 🔥 IN RA KẾT QUẢ
            if (result != null) {
                System.out.println("TK: " + result.getUsername());
                System.out.println("MK: " + result.getPassword());
            } else {
                System.out.println("Không tìm thấy tài khoản!");
            }


            List<TaiKhoan> list = em.createQuery("SELECT t FROM TaiKhoan t", TaiKhoan.class).getResultList();

            System.out.println("Số tài khoản: " + list.size());

            for (TaiKhoan t : list) {
                System.out.println(t.getUsername() + " - " + t.getPassword());
            }

            return result;

        } finally {
            em.close();
        }
    }

}