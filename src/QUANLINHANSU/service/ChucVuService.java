package QUANLINHANSU.service;

import QUANLINHANSU.model.ChucVu;
import QUANLINHANSU.model.NhanVien;
import QUANLINHANSU.repository.ChucVuRepository;
import QUANLINHANSU.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ChucVuService  {


    private final ChucVuRepository chucVuRepo = new ChucVuRepository();


    public List<ChucVu> layTatCa() {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return chucVuRepo.layTatCa(em);
        }
    }
}
