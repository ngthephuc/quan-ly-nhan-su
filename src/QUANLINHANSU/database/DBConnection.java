package QUANLINHANSU.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection() {
        try {

            String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=QuanLiNhanSu;encrypt=true;trustServerCertificate=true;integratedSecurity=true";
            Connection conn = DriverManager.getConnection(url);



            System.out.println("Ket noi thanh cong!");
            return conn;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
