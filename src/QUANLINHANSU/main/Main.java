import QUANLINHANSU.model.Du_An;
import QUANLINHANSU.model.PhongBan;
import QUANLINHANSU.service.DuAnService;
import QUANLINHANSU.service.PhongBanService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("QLNS");

        System.out.println("Ket noi thanh cong!");

        PhongBanService service = new PhongBanService(emf);
        DuAnService da = new DuAnService(emf);
        da.ThemDuAn(new Du_An("P1",
                "CHIEN",
                2000,
                LocalDate.of(2025,6,9),
                LocalDate.of(2025,7,10)));
        service.themPhongBan(
                new PhongBan("PB05","IT",2.5)
        );

        service.themPhongBan(
                new PhongBan("PB04","Marketing",2.1)
        );

        service.hienThi();
        service.suaPhongBan("PB01","IT New",3.0);
        service.suaPhongBan("PB04","Mảketing New",3.0);

        service.xoaPhongBan("PB05");

        service.hienThi();
        emf.close();
    }
}