package QUANLINHANSU.main;

import QUANLINHANSU.model.Du_An;
import QUANLINHANSU.service.DuAnService;
import QUANLINHANSU.model.PhongBan;
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
    }
}