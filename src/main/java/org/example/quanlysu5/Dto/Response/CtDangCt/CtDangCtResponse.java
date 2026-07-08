package org.example.quanlysu5.Dto.Response.CtDangCt;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.example.quanlysu5.Dto.Response.DonVi.DonViNoList;
import org.example.quanlysu5.Dto.Response.DonVi.DonViResponse;
import org.example.quanlysu5.Enum.Status;

import java.time.LocalDateTime;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CtDangCtResponse {
    String idCongtac;

    String tinhHinh;

    String noiDungDotXuat;

    String ketQua;

    String trucBanNoiVu;

    String trucBanCtDangCt;

    String kienNghi;

    Status status;

    DonViNoList donVi;
    LocalDateTime updatedAt;
}
