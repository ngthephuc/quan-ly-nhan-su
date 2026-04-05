package QUANLINHANSU.service;

import QUANLINHANSU.model.TaiKhoan;
import QUANLINHANSU.repository.TaiKhoanRepository;

public class TaiKhoanService {

    private TaiKhoanRepository repo = new TaiKhoanRepository();

    public TaiKhoan login(String tk, String mk) {
        return repo.findByUsernamePassword(tk, mk);
    }
}