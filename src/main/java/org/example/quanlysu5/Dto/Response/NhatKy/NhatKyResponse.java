package org.example.quanlysu5.Dto.Response.NhatKy;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.example.quanlysu5.Dto.Response.TaiKhoan.TaiKhoanResponse;
import org.example.quanlysu5.Enum.DoiTuongNhatKy;
import org.example.quanlysu5.Enum.HanhDongNhatKy;
import org.example.quanlysu5.Enum.TrangThaiNhatKy;

import java.time.LocalDateTime;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NhatKyResponse {

    String idNhatKy;

    TaiKhoanResponse taiKhoan;

    HanhDongNhatKy hanhDong;

    DoiTuongNhatKy doiTuong;

    String doiTuongId;

    String moTa;

    String giaTriCu;

    String giaTriMoi;

    TrangThaiNhatKy trangThai;

    String thongBaoLoi;
    LocalDateTime createdAt;
}
