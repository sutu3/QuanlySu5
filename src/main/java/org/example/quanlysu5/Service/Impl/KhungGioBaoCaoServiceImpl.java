package org.example.quanlysu5.Service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import lombok.extern.slf4j.Slf4j;
import org.example.quanlysu5.Dto.Request.KhungGioBaoCaoRequest;
import org.example.quanlysu5.Dto.Request.NhatKyRequest;
import org.example.quanlysu5.Dto.Response.KhungGioBaoCaoResponse;
import org.example.quanlysu5.Enum.*;
import org.example.quanlysu5.Exception.AppException;
import org.example.quanlysu5.Exception.ErrorCode;
import org.example.quanlysu5.Form.KhungGioBaoCaoForm;
import org.example.quanlysu5.Hepler.SecurityUtils;
import org.example.quanlysu5.Mapper.KhungGioBaoCaoMapper;
import org.example.quanlysu5.Module.KhungGioBaoCaoEntity;
import org.example.quanlysu5.Module.TaikhoanEntity;
import org.example.quanlysu5.Repo.KhungGioBaoCaoRepo;
import org.example.quanlysu5.Repo.TaiKhoanRepo;
import org.example.quanlysu5.Service.KhungGioBaoCaoService;
import org.example.quanlysu5.Service.NhatKyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class KhungGioBaoCaoServiceImpl implements KhungGioBaoCaoService {
    private final TaiKhoanRepo taiKhoanRepo;

    KhungGioBaoCaoRepo khungGioBaoCaoRepo;
    KhungGioBaoCaoMapper khungGioBaoCaoMapper;
    NhatKyService nhatKyService;
    TaiKhoanServiceImpl taiKhoanService;

    @Override
    public List<KhungGioBaoCaoResponse> getAllKhungGioBaoCao() {

        return khungGioBaoCaoRepo.findAllByIsDeleted(false)
                .stream()
                .map(khungGioBaoCaoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public KhungGioBaoCaoEntity getById(String idKhunggio) {

        return khungGioBaoCaoRepo.findById(idKhunggio)
                .orElseThrow(() ->
                        new AppException(ErrorCode.KHUNGGIOBAOCAO_NOT_FOUND));
    }

    @Override
    public KhungGioBaoCaoResponse getByIdResponse(String idKhunggio) {

        return khungGioBaoCaoMapper.toResponse(
                getById(idKhunggio)
        );
    }

    @Override
    public KhungGioBaoCaoResponse createKhungGio(
            KhungGioBaoCaoRequest request) {

        KhungGioBaoCaoEntity entity =
                khungGioBaoCaoMapper.toEntity(request);

        entity.setIsDeleted(false);
        khungGioBaoCaoRepo.save(entity);
        nhatKyService.createNhatKy(NhatKyRequest.builder()
                .doiTuong(DoiTuongNhatKy.KHUNG_GIO)
                .hanhDong(HanhDongNhatKy.CREATE)
                .doiTuongId(entity.getIdKhunggio())
                .taiKhoan(SecurityUtils.getClaim("sub"))
                .trangThai(TrangThaiNhatKy.THANH_CONG)
                .moTa("Tài khoản " + SecurityUtils.getUsername() + "tạo thông tin khung giờ ")
                .build());
        return khungGioBaoCaoMapper.toResponse(entity);
    }

    @Override
    public KhungGioBaoCaoResponse createKhungGioBanChiHuy(
            KhungGioBaoCaoRequest request
    ) {

        KhungGioBaoCaoEntity khungGioBaoCao =
                khungGioBaoCaoRepo
                        .findByLoaiBaoBan(LoaiBaoBan.CATRUC_CHIHUY)
                        .orElse(null);

        if (khungGioBaoCao != null) {

            khungGioBaoCao.setSoNgayTruc(request.getSoNgayTruc());
            khungGioBaoCao.setKhunggioBatdau(request.getKhunggioBatdau());
            khungGioBaoCao.setKhunggioKetthuc(request.getKhunggioKetthuc());
            khungGioBaoCaoRepo.save(khungGioBaoCao);
            nhatKyService.createNhatKy(NhatKyRequest.builder()
                    .doiTuong(DoiTuongNhatKy.KHUNG_GIO)
                    .hanhDong(HanhDongNhatKy.CREATE)
                    .doiTuongId(khungGioBaoCao.getIdKhunggio())
                    .taiKhoan(SecurityUtils.getClaim("sub"))
                    .trangThai(TrangThaiNhatKy.THANH_CONG)
                    .moTa("Tài khoản " + SecurityUtils.getUsername() + "tạo thông tin khung giờ ")
                    .build());
            return khungGioBaoCaoMapper.toResponse(khungGioBaoCao);
        }

        KhungGioBaoCaoEntity entity =
                khungGioBaoCaoMapper.toEntity(request);
        entity.setTenBaocao("Trực ban chỉ huy");
        entity.setLoaiBaoBan(LoaiBaoBan.CATRUC_CHIHUY);
        entity.setIsDeleted(false);

        return khungGioBaoCaoMapper.toResponse(
                khungGioBaoCaoRepo.save(entity)
        );
    }

    @Override
    public KhungGioBaoCaoResponse createKhungGioBanTacChien(KhungGioBaoCaoRequest request) {
        KhungGioBaoCaoEntity khungGioBaoCao =
                khungGioBaoCaoRepo
                        .findByLoaiBaoBan(LoaiBaoBan.CATRUC_BANTACCHIEN)
                        .orElse(null);

        if (khungGioBaoCao != null) {

            khungGioBaoCao.setSoNgayTruc(request.getSoNgayTruc());
            khungGioBaoCao.setKhunggioBatdau(request.getKhunggioBatdau());
            khungGioBaoCao.setKhunggioKetthuc(request.getKhunggioKetthuc());
            khungGioBaoCaoRepo.save(khungGioBaoCao);
            nhatKyService.createNhatKy(NhatKyRequest.builder()
                    .doiTuong(DoiTuongNhatKy.KHUNG_GIO)
                    .hanhDong(HanhDongNhatKy.CREATE)
                    .doiTuongId(khungGioBaoCao.getIdKhunggio())
                    .taiKhoan(SecurityUtils.getClaim("sub"))
                    .trangThai(TrangThaiNhatKy.THANH_CONG)
                    .moTa("Tài khoản " + SecurityUtils.getUsername() + "tạo thông tin khung giờ ")
                    .build());
            return khungGioBaoCaoMapper.toResponse(khungGioBaoCao);
        }

        KhungGioBaoCaoEntity entity =
                khungGioBaoCaoMapper.toEntity(request);

        entity.setTenBaocao("Trực ban tác chiến");
        entity.setLoaiBaoBan(LoaiBaoBan.CATRUC_BANTACCHIEN);
        entity.setIsDeleted(false);

        return khungGioBaoCaoMapper.toResponse(
                khungGioBaoCaoRepo.save(entity)
        );

           }

    @Override
    public KhungGioBaoCaoResponse getKhungGioBanNgay() {
        return khungGioBaoCaoMapper.toResponse(khungGioBaoCaoRepo.findByLoaiBaoBan(
                LoaiBaoBan.BAOBAN_NGAY).orElseThrow(
                ()->new AppException(ErrorCode.KHUNGGIOBAOCAO_NOT_FOUND)));
    }

    @Override
    public KhungGioBaoCaoResponse createKhungGioBanNgay(KhungGioBaoCaoRequest request) {
        KhungGioBaoCaoEntity khungGioBaoCao =
                khungGioBaoCaoRepo
                        .findByLoaiBaoBanAndCapDonVi(LoaiBaoBan.BAOBAN_NGAY, request.getCapDonVi())
                        .orElse(null);

        if (khungGioBaoCao != null) {

            khungGioBaoCao.setSoNgayTruc(request.getSoNgayTruc());
            khungGioBaoCao.setKhunggioBatdau(request.getKhunggioBatdau());
            khungGioBaoCao.setKhunggioKetthuc(request.getKhunggioKetthuc());

            return khungGioBaoCaoMapper.toResponse(
                    khungGioBaoCaoRepo.save(khungGioBaoCao)
            );
        }

        KhungGioBaoCaoEntity entity =
                khungGioBaoCaoMapper.toEntity(request);

        entity.setTenBaocao("Báo ban ngày - " + request.getCapDonVi());
        entity.setLoaiBaoBan(LoaiBaoBan.BAOBAN_NGAY);
        entity.setCapDonVi(request.getCapDonVi());
        entity.setIsDeleted(false);
        khungGioBaoCaoRepo.save(entity);
        TaikhoanEntity taikhoan=taiKhoanRepo.findByTenDangNhapIgnoreCase("admin")
                .orElseThrow(()->new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        nhatKyService.createNhatKy(NhatKyRequest.builder()
                .doiTuong(DoiTuongNhatKy.KHUNG_GIO)
                .hanhDong(HanhDongNhatKy.CREATE)
                .doiTuongId(entity.getIdKhunggio())
                .taiKhoan(taikhoan.getIdTaiKhoan())
                .trangThai(TrangThaiNhatKy.THANH_CONG)
                .moTa("Tài khoản " + SecurityUtils.getUsername() + "tạo thông tin khung giờ ")
                .build());

        return khungGioBaoCaoMapper.toResponse(entity);
    }

    @Override
    public KhungGioBaoCaoEntity getKhungGioBanNgayTheoCap(CapDonVi cap) {
        return khungGioBaoCaoRepo
                .findByLoaiBaoBanAndCapDonVi(LoaiBaoBan.BAOBAN_NGAY, cap)
                .orElseThrow(() -> new AppException(ErrorCode.KHUNGGIOBAOCAO_NOT_FOUND));
    }

    @Override
    public KhungGioBaoCaoResponse updateKhungGio(
            String idKhunggio,
            KhungGioBaoCaoForm update) {

        KhungGioBaoCaoEntity entity =
                khungGioBaoCaoRepo.findById(idKhunggio)
                        .orElseThrow(() ->{
                            nhatKyService.createNhatKy(NhatKyRequest.builder()
                                    .doiTuong(DoiTuongNhatKy.KHUNG_GIO)
                                    .hanhDong(HanhDongNhatKy.UPDATE)
                                    .doiTuongId(idKhunggio)
                                    .taiKhoan(SecurityUtils.getClaim("sub"))
                                    .trangThai(TrangThaiNhatKy.THAT_BAI)
                                    .moTa("Tài khoản " + SecurityUtils.getUsername() + "không cập nhập thông tin khung giờ do "+ErrorCode.KHUNGGIOBAOCAO_NOT_FOUND)
                                    .build());
                                return new AppException(ErrorCode.KHUNGGIOBAOCAO_NOT_FOUND);});

        khungGioBaoCaoMapper.update(entity, update);

        khungGioBaoCaoRepo.save(entity);
        nhatKyService.createNhatKy(NhatKyRequest.builder()
                .doiTuong(DoiTuongNhatKy.KHUNG_GIO)
                .hanhDong(HanhDongNhatKy.UPDATE)
                .doiTuongId(idKhunggio)
                .taiKhoan(SecurityUtils.getClaim("sub"))
                .trangThai(TrangThaiNhatKy.THANH_CONG)
                .moTa("Tài khoản " + SecurityUtils.getUsername() + "cập nhập thông tin khung giờ ")
                .build());

        return khungGioBaoCaoMapper.toResponse(entity);
    }

    @Override
    public void deleteKhungGio(String idKhunggio) {

        KhungGioBaoCaoEntity entity =
                khungGioBaoCaoRepo.findById(idKhunggio)
                        .orElseThrow(() ->
                        {
                            nhatKyService.createNhatKy(NhatKyRequest.builder()
                                    .doiTuong(DoiTuongNhatKy.KHUNG_GIO)
                                    .hanhDong(HanhDongNhatKy.DELETE)
                                    .doiTuongId(idKhunggio)
                                    .taiKhoan(SecurityUtils.getClaim("sub"))
                                    .trangThai(TrangThaiNhatKy.THAT_BAI)
                                    .moTa("Tài khoản " + SecurityUtils.getUsername() + "xóa thông tin khung giờ thất bại do "+ErrorCode.KHUNGGIOBAOCAO_NOT_FOUND)
                                    .build());
                                return new AppException(ErrorCode.KHUNGGIOBAOCAO_NOT_FOUND);
                        });

        entity.setIsDeleted(true);

        khungGioBaoCaoRepo.save(entity);
        nhatKyService.createNhatKy(NhatKyRequest.builder()
                .doiTuong(DoiTuongNhatKy.KHUNG_GIO)
                .hanhDong(HanhDongNhatKy.DELETE)
                .doiTuongId(idKhunggio)
                .taiKhoan(SecurityUtils.getClaim("sub"))
                .trangThai(TrangThaiNhatKy.THANH_CONG)
                .moTa("Tài khoản " + SecurityUtils.getUsername() + "xóa thông tin khung giờ thành công ")
                .build());
    }
}