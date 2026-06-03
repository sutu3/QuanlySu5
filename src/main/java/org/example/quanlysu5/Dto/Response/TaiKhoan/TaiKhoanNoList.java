package org.example.quanlysu5.Dto.Response.TaiKhoan;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.example.quanlysu5.Module.BaseEntity;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaiKhoanNoList extends BaseEntity {
    String idTaiKhoan;
    String tenTaiKhoan;
    String tenDangNhap;
    String matKhau;
}
