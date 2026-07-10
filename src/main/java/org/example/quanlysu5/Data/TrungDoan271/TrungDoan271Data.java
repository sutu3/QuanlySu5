package org.example.quanlysu5.Data.TrungDoan271;

import org.example.quanlysu5.Dto.Request.DonviRequest;
import org.example.quanlysu5.Enum.CapDonVi;

import java.util.List;

public class TrungDoan271Data {
    public static List<DonviRequest> getData(String maCha) {

        return List.of(
                DonviRequest.builder()
                        .tenDonvi("Trung doàn bộ")
                        .kyhieuDonvi("ebộ")
                        .donViCha(maCha)
                        .capDonVi(CapDonVi.TRUNG_DOAN.toString())
                        .build(),
                DonviRequest.builder()
                        .tenDonvi("Ban Tham mưu e271")
                        .kyhieuDonvi("BTM-e271")
                        .donViCha(maCha)
                        .capDonVi(CapDonVi.BAN.toString())
                        .build(),
                DonviRequest.builder()
                        .tenDonvi("Ban Chính trị e271")
                        .kyhieuDonvi("BCT-e271")
                        .donViCha(maCha)
                        .capDonVi(CapDonVi.BAN.toString())
                        .build(),
                DonviRequest.builder()
                        .tenDonvi("Ban Hậu cần-Kỹ thuật e271")
                        .kyhieuDonvi("BHC-KT-e271")
                        .donViCha(maCha)
                        .capDonVi(CapDonVi.BAN.toString())
                        .build(),
                DonviRequest.builder()
                        .tenDonvi("Tiểu đoàn 7 e271")
                        .kyhieuDonvi("d7-e271")
                        .donViCha(maCha)
                        .capDonVi(CapDonVi.TIEU_DOAN.toString())
                        .build(),
                DonviRequest.builder()
                        .tenDonvi("Tiểu đoàn 8 e271")
                        .kyhieuDonvi("d8-e271")
                        .donViCha(maCha)
                        .capDonVi(CapDonVi.TIEU_DOAN.toString())
                        .build(),
                DonviRequest.builder()
                        .tenDonvi("Tiểu đoàn 9 e271")
                        .kyhieuDonvi("d9-e271")
                        .donViCha(maCha)
                        .capDonVi(CapDonVi.TIEU_DOAN.toString())
                        .build(),
                DonviRequest.builder()
                        .tenDonvi("Đại đội 14")
                        .kyhieuDonvi("c14-e271")
                        .donViCha(maCha)
                        .capDonVi(CapDonVi.DAI_DOI.toString())
                        .build(),
                DonviRequest.builder()
                        .tenDonvi("Đại đội 15")
                        .kyhieuDonvi("c15-e271")
                        .donViCha(maCha)
                        .capDonVi(CapDonVi.DAI_DOI.toString())
                        .build(),
                DonviRequest.builder()
                        .tenDonvi("Đại đội 16")
                        .kyhieuDonvi("c16-e271")
                        .donViCha(maCha)
                        .capDonVi(CapDonVi.DAI_DOI.toString())
                        .build(),
                DonviRequest.builder()
                        .tenDonvi("Đại đội 17")
                        .kyhieuDonvi("c17-e271")
                        .donViCha(maCha)
                        .capDonVi(CapDonVi.DAI_DOI.toString())
                        .build(),
                DonviRequest.builder()
                        .tenDonvi("Đại đội 18")
                        .kyhieuDonvi("c18-e271")
                        .donViCha(maCha)
                        .capDonVi(CapDonVi.DAI_DOI.toString())
                        .build(),
                DonviRequest.builder()
                        .tenDonvi("Đại đội 20")
                        .kyhieuDonvi("c20-e271")
                        .donViCha(maCha)
                        .capDonVi(CapDonVi.DAI_DOI.toString())
                        .build(),
                DonviRequest.builder()
                        .tenDonvi("Đại đội 24")
                        .kyhieuDonvi("c24-e271")
                        .donViCha(maCha)
                        .capDonVi(CapDonVi.DAI_DOI.toString())
                        .build(),
                DonviRequest.builder()
                        .tenDonvi("Đại đội 25")
                        .kyhieuDonvi("c25-e271")
                        .donViCha(maCha)
                        .capDonVi(CapDonVi.DAI_DOI.toString())
                        .build()
        );
    }
}
