package org.example.quanlysu5.Config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.quanlysu5.Data.DaiDoiTrucThuocSuData;
import org.example.quanlysu5.Data.PhongBanTrucThuocSuData;
import org.example.quanlysu5.Data.TieuDoan14.TieuDoan14Data;
import org.example.quanlysu5.Data.TieuDoan15.TieuDoan15Data;
import org.example.quanlysu5.Data.TieuDoan16.TieuDoan16Data;
import org.example.quanlysu5.Data.TieuDoan17.TieuDoan17Data;
import org.example.quanlysu5.Data.TieuDoan18.TieuDoan18Data;
import org.example.quanlysu5.Data.TieuDoan24.TieuDoan24Data;
import org.example.quanlysu5.Data.TieuDoan25.TieuDoan25Data;
import org.example.quanlysu5.Data.TieuDoanTrucThuocSuData;
import org.example.quanlysu5.Data.TrungDoan271.TieuDoan7E271Data;
import org.example.quanlysu5.Data.TrungDoan271.TieuDoan8E271Data;
import org.example.quanlysu5.Data.TrungDoan271.TieuDoan9E271Data;
import org.example.quanlysu5.Data.TrungDoan271.TrungDoan271Data;
import org.example.quanlysu5.Data.TrungDoan4.TieuDoan1E4Data;
import org.example.quanlysu5.Data.TrungDoan4.TieuDoan2E4Data;
import org.example.quanlysu5.Data.TrungDoan4.TieuDoan3E4Data;
import org.example.quanlysu5.Data.TrungDoan4.TrungDoan4Data;
import org.example.quanlysu5.Data.TrungDoan5.TieuDoan4E5Data;
import org.example.quanlysu5.Data.TrungDoan5.TieuDoan5E5Data;
import org.example.quanlysu5.Data.TrungDoan5.TieuDoan6E5Data;
import org.example.quanlysu5.Data.TrungDoan5.TrungDoan5Data;
import org.example.quanlysu5.Data.TrungDoanTrucThuocSuData;
import org.example.quanlysu5.Dto.Request.KhungGioBaoCaoRequest;
import org.example.quanlysu5.Enum.CapDonVi;
import org.example.quanlysu5.Enum.LoaiBaoBan;
import org.example.quanlysu5.Exception.AppException;
import org.example.quanlysu5.Exception.ErrorCode;
import org.example.quanlysu5.Hepler.DataInitializer;
import org.example.quanlysu5.Module.*;
import org.example.quanlysu5.Repo.DonViRepo;
import org.example.quanlysu5.Repo.TaiKhoanRepo;
import org.example.quanlysu5.Repo.VaiTroRepo;
import org.example.quanlysu5.Service.CaTrucService;
import org.example.quanlysu5.Service.DonViService;
import org.example.quanlysu5.Service.KhungGioBaoCaoService;
import org.example.quanlysu5.Service.VaiTroService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(TaiKhoanRepo taiKhoanRepository, VaiTroRepo vaiTroRepository, TaiKhoanRepo taiKhoanRepo,
                                        DonViRepo donViRepo, CaTrucService caTrucService, DonViService donViService, KhungGioBaoCaoService khungGioBaoCaoService, VaiTroService vaiTroService, DataInitializer dataInitializer) {
        return args -> {
            LocalDate today = LocalDate.now();
            caTrucService.taoCaTrucTuDongChoNgay(today);


            // Initial data setup
            if (taiKhoanRepository.findByTenDangNhapIgnoreCase("admin").isEmpty()) {
                for (CapDonVi cap : CapDonVi.values()) {
                    KhungGioBaoCaoRequest khungGioBaoBanNgay = KhungGioBaoCaoRequest.builder()
                            .capDonVi(cap)
                            .khunggioBatdau(LocalTime.parse("06:30:00"))
                            .khunggioKetthuc(LocalTime.parse("21:30:00"))
                            .soNgayTruc(1)
                            .build();
                    khungGioBaoCaoService.createKhungGioBanNgay(khungGioBaoBanNgay);
                }
                VaiTroEntity vaiTro =
                        vaiTroRepository.findByTenVaiTro("Quản Trị Viên")
                                .orElseGet(() -> {

                                    VaiTroEntity role = VaiTroEntity.builder()
                                            .tenVaiTro("Quản Trị Viên")
                                            .isDeleted(false)
                                            .tenChucnang(new HashSet<>(
                                                    Arrays.asList("Báo Ban", "Thống Kê")
                                            ))

                                            .build();

                                    return vaiTroRepository.save(role);
                                });
                KhungGioBaoCaoRequest khungGioBaoCaoTrucChiHuy= KhungGioBaoCaoRequest.builder()
                        .khunggioBatdau(LocalTime.parse("00:00:00"))
                        .khunggioKetthuc(LocalTime.parse("00:00:00"))
                        .soNgayTruc(1)
                        .build();
                KhungGioBaoCaoRequest khungGioBaoCaoTrucBanTacChien=KhungGioBaoCaoRequest.builder()
                        .khunggioBatdau(LocalTime.parse("00:00:00"))
                        .khunggioKetthuc(LocalTime.parse("00:00:00"))
                        .soNgayTruc(1)
                        .build();
                khungGioBaoCaoService.createKhungGioBanChiHuy(khungGioBaoCaoTrucChiHuy);
                khungGioBaoCaoService.createKhungGioBanTacChien(khungGioBaoCaoTrucBanTacChien);

                // Seed khung giờ báo ban ngày cho TỪNG cấp đơn vị (khung giờ mẫu 06:30 - 07:30)



                TaikhoanEntity user = TaikhoanEntity.builder()
                        .tenTaiKhoan("admin")
                        .tenDangNhap("admin")
                        .matKhau(passwordEncoder.encode("admin"))
                        .createdAt(LocalDateTime.now())
                        .vaiTro(vaiTro)
                        .isDeleted(false)
                        .build();
                DonViEntity Su5 = DonViEntity.builder()
                        .tenDonvi("Sư Đoàn 5")
                        .hoatDong(true)
                        .kyhieuDonvi("CH/f")
                        .maDonVi("GS003")
                        .capDonVi(CapDonVi.SU_DOAN)
                        .quanSoTong(0)
                        .quanSoHsqBs(0)
                        .quanSoSiQuan(0)
                        .quanSoQncn(0)
                        .isDeleted(false)
                        .build();

                donViRepo.save(Su5);

                dataInitializer.saveAll(
                        TrungDoanTrucThuocSuData.getData(
                                Su5.getMaDonVi()
                        )
                );
                dataInitializer.saveAll(
                        TieuDoanTrucThuocSuData.getData(
                                Su5.getMaDonVi()
                        )
                );
                dataInitializer.saveAll(
                        DaiDoiTrucThuocSuData.getData(
                                Su5.getMaDonVi()
                        )
                );

                DonViEntity trungdoan4=donViRepo.findByKyhieuDonvi("e4")
                        .orElseThrow(()->new AppException(ErrorCode.DONVI_NOT_FOUND));
                dataInitializer.saveAll(TrungDoan4Data.getData(trungdoan4.getMaDonVi()));
                DonViEntity tieudoan1=donViRepo.findByKyhieuDonvi("d1-e4")
                        .orElseThrow(()->new AppException(ErrorCode.DONVI_NOT_FOUND));
                dataInitializer.saveAll(TieuDoan1E4Data.getData(tieudoan1.getMaDonVi()));
                DonViEntity tieudoan2=donViRepo.findByKyhieuDonvi("d2-e4")
                        .orElseThrow(()->new AppException(ErrorCode.DONVI_NOT_FOUND));
                dataInitializer.saveAll(TieuDoan2E4Data.getData(tieudoan2.getMaDonVi()));
                DonViEntity tieudoan3=donViRepo.findByKyhieuDonvi("d3-e4")
                        .orElseThrow(()->new AppException(ErrorCode.DONVI_NOT_FOUND));
                dataInitializer.saveAll(TieuDoan3E4Data.getData(tieudoan3.getMaDonVi()));

                DonViEntity trungdoan5=donViRepo.findByKyhieuDonvi("e5")
                        .orElseThrow(()->new AppException(ErrorCode.DONVI_NOT_FOUND));
                dataInitializer.saveAll(TrungDoan5Data.getData(trungdoan5.getMaDonVi()));
                DonViEntity tieudoan4=donViRepo.findByKyhieuDonvi("d4-e5")
                        .orElseThrow(()->new AppException(ErrorCode.DONVI_NOT_FOUND));
                dataInitializer.saveAll(TieuDoan4E5Data.getData(tieudoan4.getMaDonVi()));
                DonViEntity tieudoan5=donViRepo.findByKyhieuDonvi("d5-e5")
                        .orElseThrow(()->new AppException(ErrorCode.DONVI_NOT_FOUND));
                dataInitializer.saveAll(TieuDoan5E5Data.getData(tieudoan5.getMaDonVi()));
                DonViEntity tieudoan6=donViRepo.findByKyhieuDonvi("d6-e5")
                        .orElseThrow(()->new AppException(ErrorCode.DONVI_NOT_FOUND));
                dataInitializer.saveAll(TieuDoan6E5Data.getData(tieudoan6.getMaDonVi()));

                DonViEntity trungdoan271=donViRepo.findByKyhieuDonvi("e271")
                        .orElseThrow(()->new AppException(ErrorCode.DONVI_NOT_FOUND));
                dataInitializer.saveAll(TrungDoan271Data.getData(trungdoan271.getMaDonVi()));
                DonViEntity tieudoan7=donViRepo.findByKyhieuDonvi("d7-e271")
                        .orElseThrow(()->new AppException(ErrorCode.DONVI_NOT_FOUND));
                dataInitializer.saveAll(TieuDoan7E271Data.getData(tieudoan7.getMaDonVi()));
                DonViEntity tieudoan8=donViRepo.findByKyhieuDonvi("d8-e271")
                        .orElseThrow(()->new AppException(ErrorCode.DONVI_NOT_FOUND));
                dataInitializer.saveAll(TieuDoan8E271Data.getData(tieudoan8.getMaDonVi()));
                DonViEntity tieudoan9=donViRepo.findByKyhieuDonvi("d9-e271")
                        .orElseThrow(()->new AppException(ErrorCode.DONVI_NOT_FOUND));
                dataInitializer.saveAll(TieuDoan9E271Data.getData(tieudoan9.getMaDonVi()));

                DonViEntity tieudoan14=donViRepo.findByKyhieuDonvi("d14")
                        .orElseThrow(()->new AppException(ErrorCode.DONVI_NOT_FOUND));
                dataInitializer.saveAll(TieuDoan14Data.getData(tieudoan14.getMaDonVi()));

                DonViEntity tieudoan15=donViRepo.findByKyhieuDonvi("d15")
                        .orElseThrow(()->new AppException(ErrorCode.DONVI_NOT_FOUND));
                dataInitializer.saveAll(TieuDoan15Data.getData(tieudoan15.getMaDonVi()));

                DonViEntity tieudoan16=donViRepo.findByKyhieuDonvi("d16")
                        .orElseThrow(()->new AppException(ErrorCode.DONVI_NOT_FOUND));
                dataInitializer.saveAll(TieuDoan16Data.getData(tieudoan16.getMaDonVi()));

                DonViEntity tieudoan17=donViRepo.findByKyhieuDonvi("d17")
                        .orElseThrow(()->new AppException(ErrorCode.DONVI_NOT_FOUND));
                dataInitializer.saveAll(TieuDoan17Data.getData(tieudoan17.getMaDonVi()));

                DonViEntity tieudoan18=donViRepo.findByKyhieuDonvi("d18")
                        .orElseThrow(()->new AppException(ErrorCode.DONVI_NOT_FOUND));
                dataInitializer.saveAll(TieuDoan18Data.getData(tieudoan18.getMaDonVi()));

                DonViEntity tieudoan24=donViRepo.findByKyhieuDonvi("d24")
                        .orElseThrow(()->new AppException(ErrorCode.DONVI_NOT_FOUND));
                dataInitializer.saveAll(TieuDoan24Data.getData(tieudoan24.getMaDonVi()));

                DonViEntity tieudoan25=donViRepo.findByKyhieuDonvi("d25")
                        .orElseThrow(()->new AppException(ErrorCode.DONVI_NOT_FOUND));
                dataInitializer.saveAll(TieuDoan25Data.getData(tieudoan25.getMaDonVi()));

                user.setDonVi(Su5);
                taiKhoanRepo.save(user);
            }

            log.warn("user admin created with default password username is admin");
        };
    }
}
