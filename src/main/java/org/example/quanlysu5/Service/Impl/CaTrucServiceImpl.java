package org.example.quanlysu5.Service.Impl;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.quanlysu5.Dto.Request.CaTrucRequest;
import org.example.quanlysu5.Dto.Request.KhoangThoiGianRequest;
import org.example.quanlysu5.Dto.Request.NhatKyRequest;
import org.example.quanlysu5.Dto.Response.CaTruc.CaTrucResponse;
import org.example.quanlysu5.Dto.Response.CanhBaoCaTrucResponse;
import org.example.quanlysu5.Enum.DoiTuongNhatKy;
import org.example.quanlysu5.Enum.HanhDongNhatKy;
import org.example.quanlysu5.Enum.LoaiBaoBan;
import org.example.quanlysu5.Enum.TrangThaiNhatKy;
import org.example.quanlysu5.Exception.AppException;
import org.example.quanlysu5.Exception.ErrorCode;
import org.example.quanlysu5.Form.CaTrucForm;
import org.example.quanlysu5.Hepler.SecurityUtils;
import org.example.quanlysu5.Mapper.CaTrucMapper;
import org.example.quanlysu5.Module.CaTrucEntity;
import org.example.quanlysu5.Module.TaikhoanEntity;
import org.example.quanlysu5.Module.TrucBanTacChienEntity;
import org.example.quanlysu5.Module.TrucChiHuyEntity;
import org.example.quanlysu5.Repo.*;
import org.example.quanlysu5.Service.CaTrucService;
import org.example.quanlysu5.Service.NhatKyService;
import org.example.quanlysu5.Service.TrucBanTacChienService;
import org.example.quanlysu5.Service.TrucChiHuyService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CaTrucServiceImpl implements CaTrucService {
    private final TaiKhoanRepo taiKhoanRepo;
    private final DonBaoCaoRepo donBaoCaoRepo;
    private final TrucBanTacChienRepo trucBanTacChienRepo;
    private final TrucChiHuyRepo trucChiHuyRepo;
    private final CaTrucRepo caTrucRepo;

    CaTrucMapper caTrucMapper;
    TrucChiHuyService trucChiHuyService;
    TrucBanTacChienService trucBanTacChienService;
    KhungGioBaoCaoRepo khungGioRepo;
    NhatKyService nhatKyService;

    private static final SecureRandom RANDOM = new SecureRandom();

    private static final List<String> DANH_SACH_TINH = List.of(
            "An Giang", "Bắc Ninh", "Cà Mau", "Cao Bằng", "Đà Nẵng",
            "Điện Biên", "Đồng Nai", "Đồng Tháp", "Gia Lai", "Hà Nội",
            "Hà Tĩnh", "Hải Phòng", "Hưng Yên", "Khánh Hòa", "Lai Châu",
            "Lâm Đồng", "Lạng Sơn", "Lào Cai", "Nghệ An", "Ninh Bình",
            "Phú Thọ", "Quảng Ngãi", "Quảng Ninh", "Quảng Trị", "Sơn La",
            "Tây Ninh", "Thái Nguyên", "Thanh Hóa", "Thành phố Hồ Chí Minh",
            "Tuyên Quang", "Vĩnh Long", "Vĩnh Phúc", "Yên Bái", "Huế"
    );

    private String taoMatKhauTinh() {
        List<String> ds = new ArrayList<>(DANH_SACH_TINH);
        Collections.shuffle(ds);

        return ds.get(0) + " - " + ds.get(1);
    }
    @Override
    public List<CaTrucResponse> getAllCaTrucToResponse() {
        return caTrucRepo.findAllByIsDeleted(false).stream()
                .map(caTrucMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public CaTrucEntity getByIdCaTruc(String idNguoiTruc) {
        return caTrucRepo.findById(idNguoiTruc)
                .orElseThrow(()->new AppException(ErrorCode.CATRUC_NOT_FOUND));
    }

    @Override
    public CaTrucResponse getByIdCaTrucResponse(String idNguoiTruc) {
        return caTrucMapper.toResponse(getByIdCaTruc(idNguoiTruc));
    }

    @Override
    @Transactional
    public CaTrucResponse createCaTruc(CaTrucRequest request) {

        int soNgay = 0;

        if (request.getTrucChiHuy() != null) {

            soNgay = Math.max(
                    soNgay,
                    khungGioRepo.findByLoaiBaoBan(
                                    LoaiBaoBan.CATRUC_CHIHUY)
                            .orElseThrow(() -> {
                                nhatKyService.createNhatKy(
                                        NhatKyRequest.builder()
                                                .doiTuong(DoiTuongNhatKy.CA_TRUC)
                                                .hanhDong(HanhDongNhatKy.CREATE)
                                                .taiKhoan(SecurityUtils.getClaim("sub"))
                                                .trangThai(TrangThaiNhatKy.THAT_BAI)
                                                .moTa("Tài khoản " + SecurityUtils.getUsername()
                                                        + " tạo ca trực thất bại: "
                                                        + ErrorCode.TRUCCHIHUY_NOT_FOUND.getMessage())
                                                .build()
                                );

                                return new AppException(ErrorCode.TRUCCHIHUY_NOT_FOUND);
                            })
                            .getSoNgayTruc()
            );
        }

        if (request.getTrucBanTacChien() != null) {

            soNgay = Math.max(
                    soNgay,
                    khungGioRepo.findByLoaiBaoBan(
                                    LoaiBaoBan.CATRUC_BANTACCHIEN)
                            .orElseThrow(() -> {
                                nhatKyService.createNhatKy(
                                        NhatKyRequest.builder()
                                                .doiTuong(DoiTuongNhatKy.CA_TRUC)
                                                .hanhDong(HanhDongNhatKy.CREATE)
                                                .taiKhoan(SecurityUtils.getClaim("sub"))
                                                .trangThai(TrangThaiNhatKy.THAT_BAI)
                                                .moTa("Tài khoản " + SecurityUtils.getUsername()
                                                        + " tạo ca trực thất bại: "
                                                        + ErrorCode.TRUCBANTACCHIEN_NOT_FOUND.getMessage())
                                                .build()
                                );

                                return new AppException(ErrorCode.TRUCBANTACCHIEN_NOT_FOUND);
                            })
                            .getSoNgayTruc()
            );
        }

        LocalDate current = request.getNgaytruc();

        CaTrucEntity lastResult = null;

        for (int i = 0; i < soNgay; i++) {

            lastResult = taoHoacCapNhatCaTruc(
                    current,
                    request
            );

            current = current.plusDays(1);
        }
        nhatKyService.createNhatKy(NhatKyRequest.builder()
                .doiTuong(DoiTuongNhatKy.CA_TRUC)
                .hanhDong(HanhDongNhatKy.CREATE)
                .taiKhoan(SecurityUtils.getClaim("sub"))
                .trangThai(TrangThaiNhatKy.THANH_CONG)
                .moTa("Tài khoản " + SecurityUtils.getUsername() + " tạo thông tin ca trực mới")
                .build());

        return caTrucMapper.toResponse(lastResult);
    }
    @Override
    public CaTrucResponse updateCaTruc(String idCaTruc, CaTrucForm update) {
        CaTrucEntity caTruc=caTrucRepo.findById(idCaTruc).orElseThrow(()->{
            nhatKyService.createNhatKy(NhatKyRequest.builder()
                    .doiTuong(DoiTuongNhatKy.CA_TRUC)
                    .hanhDong(HanhDongNhatKy.UPDATE)
                    .doiTuongId(idCaTruc)
                    .taiKhoan(SecurityUtils.getClaim("sub"))
                    .trangThai(TrangThaiNhatKy.THAT_BAI)
                    .moTa("Tài khoản " + SecurityUtils.getUsername() + " cập nhập thông tin ca trực thất bại do "+ErrorCode.CATRUC_NOT_FOUND)
                    .build());
            return new AppException(ErrorCode.CATRUC_NOT_FOUND);
        });
        caTrucMapper.update(caTruc,update);
        TrucBanTacChienEntity trucBanTacChien=trucBanTacChienService.getByIdNguoiTruc(update.getTrucBanTacChien());
        TrucChiHuyEntity trucChiHuy=trucChiHuyService.getByIdNguoiTruc(update.getTrucChiHuy());
        caTruc.setTrucChiHuy(trucChiHuy);
        caTruc.setTrucBanTacChien(trucBanTacChien);
        if(donBaoCaoRepo.existsByCaTruc_IdCatruc(idCaTruc)){
            nhatKyService.createNhatKy(NhatKyRequest.builder()
                    .doiTuong(DoiTuongNhatKy.CA_TRUC)
                    .hanhDong(HanhDongNhatKy.UPDATE)
                    .doiTuongId(idCaTruc)
                    .taiKhoan(SecurityUtils.getClaim("sub"))
                    .trangThai(TrangThaiNhatKy.THAT_BAI)
                    .moTa("Tài khoản " + SecurityUtils.getUsername() + " cập nhập thông tin ca trực thất bại do "+ErrorCode.SHIFT_CANNOT_UPDATE_BECAUSE_REPORT_EXISTS)
                    .build());
            throw new AppException(ErrorCode.SHIFT_CANNOT_UPDATE_BECAUSE_REPORT_EXISTS);
        }
        caTrucRepo.save(caTruc);
        nhatKyService.createNhatKy(NhatKyRequest.builder()
                .doiTuong(DoiTuongNhatKy.CA_TRUC)
                .hanhDong(HanhDongNhatKy.UPDATE)
                .doiTuongId(idCaTruc)
                .taiKhoan(SecurityUtils.getClaim("sub"))
                .trangThai(TrangThaiNhatKy.THANH_CONG)
                .moTa("Tài khoản " + SecurityUtils.getUsername() + " cập nhập thông tin ca trực ")
                .build());
        return caTrucMapper.toResponse(caTruc);
    }

    @Override
    public void deleteCaTruc(String idCaTruc) {
        CaTrucEntity caTruc=caTrucRepo.findById(idCaTruc).orElseThrow(()->{
            nhatKyService.createNhatKy(NhatKyRequest.builder()
                    .doiTuong(DoiTuongNhatKy.CA_TRUC)
                    .hanhDong(HanhDongNhatKy.UPDATE)
                    .doiTuongId(idCaTruc)
                    .taiKhoan(SecurityUtils.getClaim("sub"))
                    .trangThai(TrangThaiNhatKy.THAT_BAI)
                    .moTa("Tài khoản " + SecurityUtils.getUsername() + " xóa thông tin ca trực thất bại do "+ErrorCode.CATRUC_NOT_FOUND)
                    .build());
            return new AppException(ErrorCode.CATRUC_NOT_FOUND);
        });
        nhatKyService.createNhatKy(NhatKyRequest.builder()
                .doiTuong(DoiTuongNhatKy.CA_TRUC)
                .hanhDong(HanhDongNhatKy.DELETE)
                .doiTuongId(idCaTruc)
                .taiKhoan(SecurityUtils.getClaim("sub"))
                .trangThai(TrangThaiNhatKy.THANH_CONG)
                .moTa("Tài khoản " + SecurityUtils.getUsername() + " xóa thông tin ca trực ")
                .build());
        caTrucRepo.deleteById(idCaTruc);
    }

    @Override
    public CaTrucEntity getByThoiGian(LocalDateTime thoigian) {

        LocalDate ngay = thoigian.toLocalDate();

        return caTrucRepo.findByNgaytruc(ngay)
                .orElseThrow(() ->
                        new AppException(ErrorCode.CATRUC_NOT_FOUND));
    }

    @Override
    public CanhBaoCaTrucResponse checkCaTruc() {
        boolean chiHuy = trucChiHuyService.canhBaoTrucChiHuy();

        boolean tacChien = trucBanTacChienService.canhBaoTacChien();

        return CanhBaoCaTrucResponse.builder()
                .canhBaoChiHuy(chiHuy)
                .canhBaoTacChien(tacChien)
                .messageChiHuy(
                        chiHuy
                                ? "Ca trực chỉ huy sắp hết hoặc chưa được phân công"
                                : ""
                )
                .messageTacChien(
                        tacChien
                                ? "Ca trực tác chiến sắp hết hoặc chưa được phân công"
                                : ""
                )
                .build();
    }

    @Override
    public List<Boolean> getListExistCaTruc(KhoangThoiGianRequest request) {
        List<Boolean> result = new ArrayList<>();

        LocalDate currentDate = request.getThoiGianBatDau();

        while (!currentDate.isAfter(request.getThoiGianKetThuc())) {

            boolean exists =
                    caTrucRepo.existsByNgaytruc(currentDate);

            result.add(exists);

            currentDate = currentDate.plusDays(1);
        }

        return result;
    }

    @Override
    public CaTrucResponse getByNgayTruc(LocalDate ngayTruc) {
        CaTrucEntity caTruc=getByThoiGian(ngayTruc.atStartOfDay());
        return caTrucMapper.toResponse(caTruc);
    }

    @Override
    public CaTrucEntity taoCaTrucTuDongChoNgay(LocalDate ngay) {
        // Đã có ca trực ngày này -> KHÔNG đè, trả về ca trực hiện có
        Optional<CaTrucEntity> existing = caTrucRepo.findByNgaytruc(ngay);
        if (existing.isPresent()) {
            return existing.get();
        }
        TaikhoanEntity tkentity=taiKhoanRepo.findByTenDangNhapIgnoreCase("admin")
                .orElseThrow(()->new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        CaTrucEntity caTruc = new CaTrucEntity();
        caTruc.setNgaytruc(ngay);
        caTruc.setMatkhau(taoMatKhauTinh());
        caTruc.setGhichu("");
        caTruc.setIsDeleted(false);
        // trucChiHuy / trucBanTacChien để null, phân công thủ công sau

        CaTrucEntity saved = caTrucRepo.save(caTruc);

        nhatKyService.createNhatKy(NhatKyRequest.builder()
                .doiTuong(DoiTuongNhatKy.CA_TRUC)
                .hanhDong(HanhDongNhatKy.CREATE)
                .doiTuongId(saved.getIdCatruc())
                .taiKhoan(tkentity.getIdTaiKhoan())
                .trangThai(TrangThaiNhatKy.THANH_CONG)
                .moTa("Hệ thống tự tạo ca trực ngày " + ngay)
                .build());

        return saved;
    }

    private CaTrucEntity taoHoacCapNhatCaTruc(
            LocalDate ngayTruc,
            CaTrucRequest request
    ) {

        CaTrucEntity caTruc =
                caTrucRepo.findByNgaytruc(ngayTruc)
                        .orElseGet(() -> {

                            CaTrucEntity entity =
                                    new CaTrucEntity();

                            entity.setNgaytruc(ngayTruc);

                            return entity;
                        });

        caTruc.setMatkhau(request.getMatkhau());
        caTruc.setGhichu(request.getGhichu());

        // Ghi đè trực chỉ huy
        if (request.getTrucChiHuy() != null) {

            caTruc.setTrucChiHuy(
                    trucChiHuyService.getByIdNguoiTruc(
                            request.getTrucChiHuy()
                    )
            );
        }

        // Ghi đè trực tác chiến
        if (request.getTrucBanTacChien() != null) {

            caTruc.setTrucBanTacChien(
                    trucBanTacChienService.getByIdNguoiTruc(
                            request.getTrucBanTacChien()
                    )
            );
        }

        return caTrucRepo.save(caTruc);
    }



}
