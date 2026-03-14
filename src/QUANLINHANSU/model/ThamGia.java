package QUANLINHANSU.model;

public class ThamGia {
    private NhanVien nhanVien;
    private String maDA;
    private String vaiTro;
    private int soGio;

    public ThamGia(NhanVien nhanVien, String maDA, String vaiTro, int soGio) {
        this.nhanVien = nhanVien;
        this.maDA = maDA;
        this.vaiTro = vaiTro;
        this.soGio = soGio;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public String getMaDA() {
        return maDA;
    }

    public void setMaDA(String maDA) {
        this.maDA = maDA;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public int getSoGio() {
        return soGio;
    }

    public void setSoGio(int soGio) {
        this.soGio = soGio;
    }
}
