package QUANLINHANSU.service;

import QUANLINHANSU.model.PhongBan;

public interface IManager<T> {
    void them(T t);



    void xoa(String id);

    void hienThi();

    T tim(String id);

    void sua(String id, T t);

}
