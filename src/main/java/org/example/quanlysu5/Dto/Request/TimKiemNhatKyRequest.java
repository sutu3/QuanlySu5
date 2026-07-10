package org.example.quanlysu5.Dto.Request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.example.quanlysu5.Enum.DoiTuongNhatKy;
import org.example.quanlysu5.Enum.HanhDongNhatKy;
import org.example.quanlysu5.Enum.TrangThaiNhatKy;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimKiemNhatKyRequest {

    String taiKhoan;

    HanhDongNhatKy hanhDong;

    DoiTuongNhatKy doiTuong;

    String doiTuongId;

    String moTa;

    LocalDateTime tuNgay;

    LocalDateTime denNgay;

    TrangThaiNhatKy trangThai;

    String thongBaoLoi;
}
