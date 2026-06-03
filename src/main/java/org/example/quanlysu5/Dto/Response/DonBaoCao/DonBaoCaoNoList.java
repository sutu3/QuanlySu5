package org.example.quanlysu5.Dto.Response.DonBaoCao;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.example.quanlysu5.Dto.Response.CaTruc.CaTrucNoList;
import org.example.quanlysu5.Dto.Response.DonVi.DonViNoList;
import org.example.quanlysu5.Dto.Response.TaiKhoan.TaiKhoanNoList;
import org.example.quanlysu5.Enum.Status;
import org.example.quanlysu5.Module.CaTrucEntity;
import org.example.quanlysu5.Module.DonViEntity;
import org.example.quanlysu5.Module.TaikhoanEntity;

import java.time.LocalDateTime;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DonBaoCaoNoList {
    String idDonBaoCao;
    Integer quanSoTong;
    Integer quanSoHienDien;
    Integer quanSoVang;
    Status trangThai;
    LocalDateTime thoiGianBaoCao;
    String  thongTinVang;
    CaTrucNoList caTruc;
    DonViNoList donVi;


}
