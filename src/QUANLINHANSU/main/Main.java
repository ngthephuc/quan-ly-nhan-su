package QUANLINHANSU.main;

import QUANLINHANSU.database.DBConnection;
import QUANLINHANSU.model.NhanVien;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {


        try {
            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO NhanSu VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, "NV10");
            ps.setString(2, "Nguyen Van A");
            ps.setInt(3, 25);
            ps.setString(4, "PB01");
            ps.setString(5, "CV01");
            ps.setString(6, "FullTime");

            ps.executeUpdate();

            System.out.println("Them nhan vien thanh cong!");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
