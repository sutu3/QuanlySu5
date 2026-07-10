package org.example.quanlysu5.Dto.Response.TaiKhoan;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.example.quanlysu5.Dto.Response.DonVi.DonViNoList;
import org.example.quanlysu5.Dto.Response.VaiTroResponse;
import org.example.quanlysu5.Module.BaseEntity;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaiKhoanResponse extends BaseEntity {
    String idTaiKhoan;
    String tenTaiKhoan;
    String tenDangNhap;
    String matKhau;
    DonViNoList donVi;
    VaiTroResponse vaiTro;
    Boolean khoa;
}
