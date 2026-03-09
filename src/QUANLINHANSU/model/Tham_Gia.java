package QUANLINHANSU.model;

public class Tham_Gia {
    private String maNV;
    private String maDA;
    private String vaiTro;
    private int soGio;

    public Tham_Gia(String maNV, String maDA, String vaiTro, int soGio) {
        this.maNV = maNV;
        this.maDA = maDA;
        this.vaiTro = vaiTro;
        this.soGio = soGio;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
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
