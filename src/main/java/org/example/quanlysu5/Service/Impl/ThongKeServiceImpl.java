package org.example.quanlysu5.Service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.quanlysu5.Dto.Response.ThongKe.*;
import org.example.quanlysu5.Module.DonBaoCaoEntity;
import org.example.quanlysu5.Repo.DonBaoCaoRepo;
import org.example.quanlysu5.Service.ThongKeService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ThongKeServiceImpl implements ThongKeService {

    private final DonBaoCaoRepo donBaoCaoRepo;

    @Override
    public ThongKeResponse thongKeQuanSo(LocalDate ngayBaoCao) {

        LocalDateTime start = ngayBaoCao.atStartOfDay();
        LocalDateTime end = ngayBaoCao.atTime(23,59,59);

        List<DonBaoCaoEntity> reports =
                donBaoCaoRepo.findAllByThoiGianBaoCaoBetween(start,end);

        // ======================
        // Tổng hợp quân số
        // ======================

        int tongQuanSo = reports.stream()
                .mapToInt(DonBaoCaoEntity::getQuanSoTong)
                .sum();

        int tongVang = reports.stream()
                .mapToInt(DonBaoCaoEntity::getQuanSoVang)
                .sum();

        int tongHienDien = reports.stream()
                .mapToInt(DonBaoCaoEntity::getQuanSoHienDien)
                .sum();

        double tyLeHienDien = tongQuanSo == 0
                ? 0
                : (double) tongHienDien * 100 / tongQuanSo;

        double tyLeVang = tongQuanSo == 0
                ? 0
                : (double) tongVang * 100 / tongQuanSo;

        // ======================
        // Danh sách đơn vị
        // ======================

        List<ThongKeDonViResponse> danhSachDonVi =
                reports.stream()
                        .map(report -> {

                            double tyLe =
                                    report.getQuanSoTong() == 0
                                            ? 0
                                            : ((double) report.getQuanSoHienDien()
                                               / report.getQuanSoTong()) * 100;

                            return ThongKeDonViResponse.builder()
                                    .tenDonVi(
                                            report.getDonVi().getTenDonvi()
                                    )
                                    .quanSoTong(
                                            report.getQuanSoTong()
                                    )
                                    .quanSoHienDien(
                                            report.getQuanSoHienDien()
                                    )
                                    .quanSoVang(
                                            report.getQuanSoVang()
                                    )
                                    .tyLeHienDien(
                                            Math.round(tyLe * 10) / 10.0
                                    )
                                    .build();
                        }).collect(Collectors.toList());

        // ======================
        // So sánh
        // ======================

        ChiSoResponse homQua =
                tinhSoSanh(
                        tongHienDien,
                        tongHienDienTheoNgay(
                                ngayBaoCao.minusDays(1)
                        )
                );

        ChiSoResponse tuanTruoc =
                tinhSoSanh(
                        tongHienDien,
                        tongHienDienTheoNgay(
                                ngayBaoCao.minusWeeks(1)
                        )
                );

        ChiSoResponse thangTruoc =
                tinhSoSanh(
                        tongHienDien,
                        tongHienDienTheoNgay(
                                ngayBaoCao.minusMonths(1)
                        )
                );

        SoSanhResponse soSanh =
                SoSanhResponse.builder()
                        .homQua(homQua)
                        .tuanTruoc(tuanTruoc)
                        .thangTruoc(thangTruoc)
                        .build();

        // ======================
        // Đơn vị tiêu biểu
        // ======================

        DonViTyLeResponse hienDienCaoNhat =
                reports.stream()
                        .map(report -> {

                            double tyLe =
                                    report.getQuanSoTong() == 0
                                            ? 0
                                            : ((double) report.getQuanSoHienDien()
                                               / report.getQuanSoTong()) * 100;

                            return DonViTyLeResponse.builder()
                                    .ten(report.getDonVi().getTenDonvi())
                                    .tyLe(
                                            Math.round(tyLe * 10) / 10.0
                                    )
                                    .build();
                        })
                        .max(
                                Comparator.comparing(
                                        DonViTyLeResponse::getTyLe
                                )
                        )
                        .orElse(null);

        DonViTyLeResponse vangCaoNhat =
                reports.stream()
                        .map(report -> {

                            double tyLe =
                                    report.getQuanSoTong() == 0
                                            ? 0
                                            : ((double) report.getQuanSoVang()
                                               / report.getQuanSoTong()) * 100;

                            return DonViTyLeResponse.builder()
                                    .ten(report.getDonVi().getTenDonvi())
                                    .tyLe(
                                            Math.round(tyLe * 10) / 10.0
                                    )
                                    .build();
                        })
                        .max(
                                Comparator.comparing(
                                        DonViTyLeResponse::getTyLe
                                )
                        )
                        .orElse(null);

        DonViTieuBieuResponse donViTieuBieu =
                DonViTieuBieuResponse.builder()
                        .hienDienCaoNhat(hienDienCaoNhat)
                        .vangCaoNhat(vangCaoNhat)
                        .build();

        return ThongKeResponse.builder()
                .ngayBaoCao(ngayBaoCao)

                .tongQuanSo(tongQuanSo)
                .tongHienDien(tongHienDien)
                .tongVang(tongVang)

                .tyLeHienDien(
                        Math.round(tyLeHienDien * 10) / 10.0
                )

                .tyLeVang(
                        Math.round(tyLeVang * 10) / 10.0
                )

                .soSanh(soSanh)

                .donViTieuBieu(donViTieuBieu)

                .danhSachDonVi(danhSachDonVi)

                .build();
    }

    // ==================================
    // Hàm tính tổng hiện diện theo ngày
    // ==================================

    private int tongHienDienTheoNgay(LocalDate ngay) {

        LocalDateTime start = ngay.atStartOfDay();
        LocalDateTime end = ngay.atTime(23,59,59);

        return donBaoCaoRepo
                .findAllByThoiGianBaoCaoBetween(start,end)
                .stream()
                .mapToInt(DonBaoCaoEntity::getQuanSoHienDien)
                .sum();
    }

    // ==================================
    // Hàm tính chênh lệch
    // ==================================

    private ChiSoResponse tinhSoSanh(
            int hienTai,
            int giaTriCu
    ) {

        int tangGiam = hienTai - giaTriCu;

        double phanTram =
                giaTriCu == 0
                        ? 0
                        : ((double) tangGiam / giaTriCu) * 100;

        return ChiSoResponse.builder()
                .tangGiam(tangGiam)
                .phanTram(
                        Math.round(phanTram * 10) / 10.0
                )
                .build();
    }
}
