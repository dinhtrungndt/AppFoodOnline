package com.example.appfoodv2.Interface;

public interface GioHangView {
    void OnSucess();

    void OnFail();

    void getDataSanPham(String id, String idsp, String tensp, Long giatien, String hinhanh, String loaisp, Long soluong, String hansudung, Long type, String trongluong);
}
