package QUANLINHANSU.repository;

import QUANLINHANSU.model.BoNhiem;
import QUANLINHANSU.model.NhanVien;
import jakarta.persistence.EntityManager;
import java.util.List;

public class BoNhiemRepository extends BaseRepository<BoNhiem> {

    public BoNhiemRepository(Class<BoNhiem> entityClass) {
        super(entityClass);
    }

    public BoNhiemRepository() {
        super(BoNhiem.class);
    }

    // Lấy chức vụ hiện tại (denNgay = null nghĩa là đang giữ)
    public BoNhiem layChucVuHienTai(EntityManager em ,String maNV) {
        List<BoNhiem> result = em.createQuery("SELECT b FROM BoNhiem b WHERE b.nhanVien.maNV = :maNV AND b.denNgay IS NULL" , BoNhiem.class)
                .setParameter("maNV", maNV).getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    //Lấy toàn bộ lịch sử chức vụ của nhân viên
    public List<BoNhiem> layLichSuChucVu(EntityManager em, String maNV) {
        return em.createQuery("SELECT b FROM BoNhiem b WHERE b.nhanVien.maNV = :maNV ORDER by b.id.tuNgay ASC" , BoNhiem.class)
                .setParameter("maNV", maNV).getResultList();
    }
}
