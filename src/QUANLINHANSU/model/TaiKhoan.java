package QUANLINHANSU.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Login") // ⚠️ đúng tên bảng

public class TaiKhoan {

    @Id
    @Column(name = "username") // ⚠️ đúng tên cột
    private String username;

    @Column(name = "password") // ⚠️ đúng tên cột
    private String password;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}