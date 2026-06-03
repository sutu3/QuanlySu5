package org.example.quanlysu5.Dto.Response.CaTruc;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.example.quanlysu5.Dto.Response.TrucBanTacChien.TrucBanTacChienNoList;
import org.example.quanlysu5.Dto.Response.TrucBanTacChien.TrucBanTacChienResponse;
import org.example.quanlysu5.Dto.Response.TrucChiHuy.TrucChiHuyNoList;

import java.time.LocalDate;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CaTrucNoList {
    String idCatruc;

    LocalDate ngaytruc;

    String matkhau;

    String ghichu;

    TrucBanTacChienNoList trucBanTacChien;

    TrucChiHuyNoList trucChiHuy;
}
