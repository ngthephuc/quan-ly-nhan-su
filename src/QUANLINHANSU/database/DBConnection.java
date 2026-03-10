package QUANLINHANSU.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection() {
        try {

            Connection conn =
                    DriverManager.getConnection(
                            "jdbc:sqlserver://localhost:1433;databaseName=QuanLiNhanSu;encrypt=true;trustServerCertificate=true",
                            "sa",
                            "123456"
                    );




            System.out.println("Ket noi thanh cong!");
            return conn;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
