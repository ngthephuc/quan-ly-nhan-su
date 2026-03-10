package QUANLINHANSU.main;

import QUANLINHANSU.database.DBConnection;
import QUANLINHANSU.model.NhanVien;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {


        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM NhanSu";

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                System.out.println(
                        rs.getString("MaNV") + " - " +
                                rs.getString("TenNV") + " - " +
                                rs.getInt("Tuoi") + " - "

                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
