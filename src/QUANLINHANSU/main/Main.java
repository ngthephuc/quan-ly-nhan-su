package QUANLINHANSU.main;
import QUANLINHANSU.model.NhanVien;
import QUANLINHANSU.model.NhanVienFullTime;
import QUANLINHANSU.model.NhanVienPartTime;
import QUANLINHANSU.service.NhanVienService;

public class Main {
    public static void main(String[] args) {

        NhanVienService service = new NhanVienService();

        NhanVien nv1 = new NhanVienFullTime("NV01", "Phuc", 19, 5000);
        NhanVien nv2 = new NhanVienPartTime("NV02","An",20,20,4,50);
        NhanVien nv3 = new NhanVienFullTime("NV03","Tuan",20,7000);
        service.them(nv1);
        service.them(nv2);
        service.them(nv3);

        service.hienThi();
        service.xoa("NV02");
        service.hienThi();


        NhanVien kq = service.tim("NV01");

        if(kq != null){
            System.out.println("Tim thay: " + kq.getTenNV());
        }else{
            System.out.println("Khong tim thay");
        }

    }
}
