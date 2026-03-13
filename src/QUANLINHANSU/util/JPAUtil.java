package QUANLINHANSU.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Singleton quản lý EntityManagerFactory.
 * EntityManagerFactory tốn tài nguyên - chỉ tạo 1 lần duy nhất.
 * Gọi JPAUtil.getEntityManager() mỗi khi cần thao tác DB.
 */
public class JPAUtil {

    private static final String PERSISTENCE_UNIT = "QuanLyNhanSuPU";
    private static EntityManagerFactory emf;

    static {
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        } catch (Exception e) {
            System.err.println("Không thể kết nối database: " + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

    // Ngăn tạo instance
    private JPAUtil() {}
    /**
     * Lấy EntityManager mới (mỗi lần gọi ra 1 instance riêng).
     * Nhớ đóng sau khi dùng xong: em.close()
     */
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    /**
     * Gọi khi tắt ứng dụng để giải phóng connection pool.
     */
    public static void shutdown() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}