package org.example.quanlysu5.Dto.Response.CaTruc;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.example.quanlysu5.Dto.Response.DonBaoCao.DonBaoCaoNoList;
import org.example.quanlysu5.Dto.Response.TrucBanTacChien.TrucBanTacChienNoList;
import org.example.quanlysu5.Dto.Response.TrucBanTacChien.TrucBanTacChienResponse;
import org.example.quanlysu5.Dto.Response.TrucChiHuy.TrucChiHuyNoList;
import org.example.quanlysu5.Dto.Response.TrucChiHuy.TrucChiHuyResponse;
import org.example.quanlysu5.Module.BaseEntity;
import org.example.quanlysu5.Module.DonBaoCaoEntity;

import java.time.LocalDate;
import java.util.List;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CaTrucResponse extends BaseEntity {

    String idCatruc;

    LocalDate ngaytruc;

    String matkhau;

    String ghichu;

    TrucBanTacChienNoList trucBanTacChien;

    TrucChiHuyNoList trucChiHuy;

    List<DonBaoCaoNoList> donBaoCao;

}
