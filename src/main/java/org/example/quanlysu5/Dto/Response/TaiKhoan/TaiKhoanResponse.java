package org.example.quanlysu5.Dto.Response.TaiKhoan;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.example.quanlysu5.Dto.Response.DonVi.DonViNoList;
import org.example.quanlysu5.Dto.Response.VaiTroResponse;
import org.example.quanlysu5.Module.BaseEntity;

import java.util.Set;

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
    Set<String> chucNangThem;
    Set<String> chucNangBo;
    // Quyền cuối cùng đã tính sẵn cho FE dùng trực tiếp
    Set<String> tenChucnang;
}
