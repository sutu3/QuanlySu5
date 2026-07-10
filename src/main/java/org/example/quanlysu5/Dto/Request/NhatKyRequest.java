package org.example.quanlysu5.Dto.Request;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.example.quanlysu5.Dto.Response.TaiKhoan.TaiKhoanResponse;
import org.example.quanlysu5.Enum.DoiTuongNhatKy;
import org.example.quanlysu5.Enum.HanhDongNhatKy;
import org.example.quanlysu5.Enum.TrangThaiNhatKy;
import org.example.quanlysu5.Module.TaikhoanEntity;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NhatKyRequest {

    String taiKhoan;

    HanhDongNhatKy hanhDong;

    DoiTuongNhatKy doiTuong;

    String doiTuongId;

    String moTa;

    String giaTriCu;

    String giaTriMoi;

    TrangThaiNhatKy trangThai;

    String thongBaoLoi;
}
