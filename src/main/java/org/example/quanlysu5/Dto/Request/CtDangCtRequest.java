package org.example.quanlysu5.Dto.Request;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.example.quanlysu5.Dto.Response.DonVi.DonViResponse;
import org.example.quanlysu5.Module.BaseEntity;
import org.example.quanlysu5.Module.DonBaoCaoEntity;
import org.example.quanlysu5.Module.DonViEntity;

import java.time.LocalDateTime;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CtDangCtRequest {

    String tinhHinh;

    String noiDungDotXuat;

    String ketQua;

    String trucBanNoiVu;

    String trucBanCtDangCt;

    String kienNghi;

    LocalDateTime thoiGianBaoCao;

    String donVi;
}
