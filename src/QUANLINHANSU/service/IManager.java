package QUANLINHANSU.service;

public interface IManager<T> {
    void them(T t);

    void xoa(String id);

    void hienThi();
    T tim(String id);
}
