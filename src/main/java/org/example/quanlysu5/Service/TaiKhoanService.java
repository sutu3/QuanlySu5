package org.example.quanlysu5.Service;

import org.example.quanlysu5.Dto.Request.TaiKhoanRequest;
import org.example.quanlysu5.Dto.Response.TaiKhoan.TaiKhoanResponse;
import org.example.quanlysu5.Module.TaikhoanEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaiKhoanService {
    TaikhoanEntity getTaiKhoanById(String id);

    TaiKhoanResponse getTaiKhoanResponse(String id);

    List<TaiKhoanResponse> getAllTaiKhoan();

    TaiKhoanResponse createdTaiKhoanResponse(TaiKhoanRequest taiKhoanRequest);

    TaiKhoanResponse updateTaiKhoan(String idTaiKhoan, TaiKhoanRequest request);

    void deleteTaiKhoan(String idTaiKhoan);

    void resetMatKhau(String idTaiKhoan, String matKhauMoi);

    TaiKhoanResponse lockTaiKhoan(String id);

    TaiKhoanResponse unlockTaiKhoan(String id);
}