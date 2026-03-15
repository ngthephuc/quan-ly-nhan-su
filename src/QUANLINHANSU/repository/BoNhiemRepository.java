package QUANLINHANSU.repository;

import QUANLINHANSU.model.BoNhiem;
import QUANLINHANSU.model.NhanVien;

public class BoNhiemRepository extends BaseRepository<BoNhiem> {

    public BoNhiemRepository(Class<NhanVien> entityClass) {
        super(entityClass);
    }


}
