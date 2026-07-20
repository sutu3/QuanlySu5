package org.example.quanlysu5.Dto.Request;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.example.quanlysu5.Enum.LoaiDonBaoCao;
import org.example.quanlysu5.Module.BaseEntity;
import org.example.quanlysu5.Module.CaTrucEntity;
import org.example.quanlysu5.Module.DonViEntity;
import org.example.quanlysu5.Module.TaikhoanEntity;

import java.time.LocalDateTime;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DonBaoCaoRequest {
    Integer quanSoTong;
    Integer quanSoHienDien;
    Integer quanSoVang;
    LocalDateTime thoiGianBaoCao;
    String  ghiChu;
    String  chiTietVang;
    String trucBanChiHuy;
    String trucBanTacChien;
    String  thongTinVang;
    LoaiDonBaoCao loaiDonBaoCao;
    //ca trực dựa trên thời gian tạo phiếu báo cáo mà tìm đc ca trực
    String donVi;
}
