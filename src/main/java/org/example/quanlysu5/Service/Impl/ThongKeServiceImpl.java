package org.example.quanlysu5.Service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.quanlysu5.Dto.Response.CtDangCt.DashboardCtDangCtResponse;
import org.example.quanlysu5.Dto.Response.CtDangCt.ThongKeDonViCtDangCtResponse;
import org.example.quanlysu5.Dto.Response.ThongKe.*;
import org.example.quanlysu5.Enum.CapDonVi;
import org.example.quanlysu5.Hepler.SecurityUtils;
import org.example.quanlysu5.Hepler.TimeUtils;
import org.example.quanlysu5.Module.CtDangCtEntity;
import org.example.quanlysu5.Module.DonBaoCaoEntity;
import org.example.quanlysu5.Module.TaikhoanEntity;
import org.example.quanlysu5.Repo.CtDangCtRepo;
import org.example.quanlysu5.Repo.DonBaoCaoRepo;
import org.example.quanlysu5.Service.TaiKhoanService;
import org.example.quanlysu5.Service.ThongKeService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ThongKeServiceImpl implements ThongKeService {
    private final CtDangCtRepo ctDangCtRepo;
    TaiKhoanService taiKhoanService;

    private final DonBaoCaoRepo donBaoCaoRepo;
    TimeUtils timeUtils;

    @Override
    public ThongKeResponse thongKeQuanSo(LocalDate ngayBaoCao) {

        TaikhoanEntity taikhoan= taiKhoanService.getTaiKhoanById(SecurityUtils.getClaim("sub"));
        LocalDateTime start = ngayBaoCao.atStartOfDay();
        LocalDateTime end = ngayBaoCao.atTime(23,59,59);

        List<DonBaoCaoEntity> reports =
                donBaoCaoRepo.findAllCap2ByThoiGian(taikhoan.getDonVi().getCapDonVi()== CapDonVi.SU_DOAN||taikhoan.getDonVi().getCapDonVi()== CapDonVi.PHONG?"GS003":taikhoan.getDonVi().getMaDonVi(),start,end);

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

    @Override
    public DashboardCtDangCtResponse thongKeDashboard(LocalDate date) {
        TaikhoanEntity taikhoan= taiKhoanService.getTaiKhoanById(SecurityUtils.getClaim("sub"));
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(23,59,59);;

        List<CtDangCtEntity> list =
                ctDangCtRepo.findAllCap2ByThoiGian(taikhoan.getDonVi().getCapDonVi()== CapDonVi.SU_DOAN||taikhoan.getDonVi().getCapDonVi()== CapDonVi.PHONG?"GS003":taikhoan.getDonVi().getMaDonVi(),start, end);
        int tongDonVi = list.size();

        int donViCoKienNghi = (int) list.stream()
                .filter(x ->
                        x.getKienNghi() != null
                                && !x.getKienNghi().trim().isEmpty()
                )
                .count();

        int donViCoDotXuat = (int) list.stream()
                .filter(x ->
                        x.getNoiDungDotXuat() != null
                                && !x.getNoiDungDotXuat().trim().isEmpty()
                )
                .count();

        List<ThongKeDonViCtDangCtResponse> donViResponses =
                list.stream()
                        .map(x -> {

                            long soKienNghi =
                                    (x.getKienNghi() != null
                                            && !x.getKienNghi().trim().isEmpty())
                                            ? 1 : 0;
                            log.warn("Kieesn Nghị: "+(x.getKienNghi() != null
                                    && !x.getKienNghi().trim().isEmpty()));

                            long soDotXuat =
                                    (x.getNoiDungDotXuat() != null
                                            && !x.getNoiDungDotXuat().trim().isEmpty())
                                            ? 1 : 0;

                            long tongVanDe =
                                    soKienNghi + soDotXuat;

                            String mucDo = "Tốt";

                            if (tongVanDe >= 2) {
                                mucDo = "Có vấn đề";
                            }
                            else if (tongVanDe == 1) {
                                mucDo = "Cần chú ý";
                            }

                            return ThongKeDonViCtDangCtResponse.builder()
                                    .tenDonVi(x.getDonVi().getTenDonvi())
                                    .soKienNghi(soKienNghi)
                                    .soDotXuat(soDotXuat)
                                    .updateAt(TimeUtils.toRelative(x.getUpdatedAt()))
                                    .idDonVi(x.getDonVi().getMaDonVi())
                                    .tongVanDe(tongVanDe)
                                    .mucDo(mucDo)
                                    .build();
                        })
                        .collect(Collectors.toList());

        return DashboardCtDangCtResponse.builder()
                .tongDonVi(tongDonVi)
                .donViCoKienNghi(donViCoKienNghi)
                .donViCoDotXuat(donViCoDotXuat)
                .danhSachDonVi(donViResponses)
                .build();
    }

    // ==================================
    // Hàm tính tổng hiện diện theo ngày
    // ==================================

    private int tongHienDienTheoNgay(LocalDate ngay) {

        LocalDateTime start = ngay.atStartOfDay();
        LocalDateTime end = ngay.atTime(23,59,59);

        return donBaoCaoRepo
                .findAllCap2ByThoiGian("GS003",start,end)
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