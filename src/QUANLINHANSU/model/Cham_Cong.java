package QUANLINHANSU.model;

public class Cham_Cong {
    private String maNV;
    private String ngay;
    private String gioVao;
    private String gioRa;

    public Cham_Cong(String maNV, String ngay, String gioVao, String gioRa) {
        this.maNV = maNV;
        this.ngay = ngay;
        this.gioVao = gioVao;
        this.gioRa = gioRa;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getGioVao() {
        return gioVao;
    }

    public void setGioVao(String gioVao) {
        this.gioVao = gioVao;
    }

    public String getGioRa() {
        return gioRa;
    }

    public void setGioRa(String gioRa) {
        this.gioRa = gioRa;
    }

    @Override
    public String toString() {
        return "ChamCong{" +
                "maNV='" + maNV + '\'' +
                ", ngay='" + ngay + '\'' +
                ", gioVao='" + gioVao + '\'' +
                ", gioRa='" + gioRa + '\'' +
                '}';
    }
}
