package org.example.quanlysu5.Service.Impl;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.quanlysu5.Dto.Request.NhatKyRequest;
import org.example.quanlysu5.Dto.Request.TrucChiHuyRequest;
import org.example.quanlysu5.Dto.Response.TrucChiHuy.TrucChiHuyResponse;
import org.example.quanlysu5.Enum.DoiTuongNhatKy;
import org.example.quanlysu5.Enum.HanhDongNhatKy;
import org.example.quanlysu5.Enum.TrangThaiNhatKy;
import org.example.quanlysu5.Exception.AppException;
import org.example.quanlysu5.Exception.ErrorCode;
import org.example.quanlysu5.Form.TrucChiHuyForm;
import org.example.quanlysu5.Hepler.SecurityUtils;
import org.example.quanlysu5.Mapper.TrucChiHuyMapper;
import org.example.quanlysu5.Module.CaTrucEntity;
import org.example.quanlysu5.Module.TrucChiHuyEntity;
import org.example.quanlysu5.Repo.CaTrucRepo;
import org.example.quanlysu5.Repo.TrucChiHuyRepo;
import org.example.quanlysu5.Service.NhatKyService;
import org.example.quanlysu5.Service.TrucChiHuyService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TrucChiHuyServiceImpl implements TrucChiHuyService {
    private final TrucChiHuyRepo TrucChiHuyRepo;
    TrucChiHuyMapper TrucChiHuyMapper;
    CaTrucRepo caTrucRepo;
    NhatKyService nhatKyService;

    @Override
    public List<TrucChiHuyResponse> getAllTrucChiHuyToResponse() {
        return TrucChiHuyRepo.findAllByIsDeleted(false).stream().map(TrucChiHuyMapper::toResponse)
                .collect((Collectors.toList()));
    }

    @Override
    public TrucChiHuyEntity getByIdNguoiTruc(String idNguoiTruc) {
        return TrucChiHuyRepo.findById(idNguoiTruc)
                .orElseThrow(()->new AppException(ErrorCode.TRUCCHIHUY_NOT_FOUND));
    }

    @Override
    public TrucChiHuyResponse getByIdNguoiTrucResponse(String idNguoiTruc) {
        return TrucChiHuyMapper.toResponse(getByIdNguoiTruc(idNguoiTruc));
    }

    @Override
    public TrucChiHuyResponse getByTenNguoiTruc(String tenNguoiTruc) {
        return TrucChiHuyMapper.toResponse(TrucChiHuyRepo.findByTenNguoitruc(tenNguoiTruc)
                .orElseThrow(()->new AppException(ErrorCode.TRUCCHIHUY_NOT_FOUND)));
    }

    @Override
    public TrucChiHuyResponse createNguoiTruc(TrucChiHuyRequest TrucChiHuyRequest) {
        TrucChiHuyEntity TrucChiHuy=TrucChiHuyMapper.toEntity(TrucChiHuyRequest);
        TrucChiHuy.setIsDeleted(false);
        TrucChiHuy.setCreatedAt(LocalDateTime.now());
        if(TrucChiHuyRepo.findBySodienthoaiAndTenNguoitrucAndIsDeleted(TrucChiHuy.getSodienthoai(),TrucChiHuy.getTenNguoitruc(),false)==null){
            nhatKyService.createNhatKy(NhatKyRequest.builder()
                    .doiTuong(DoiTuongNhatKy.TRUC_CHI_HUY)
                    .hanhDong(HanhDongNhatKy.CREATE)
                    .taiKhoan(SecurityUtils.getClaim("sub"))
                    .trangThai(TrangThaiNhatKy.THAT_BAI)
                    .moTa("Tài khoản " + SecurityUtils.getUsername() + "không tạo thông tin trực chỉ huy do"+ErrorCode.TRUCCHIHUY_IS_EXIST)
                    .build());
            throw new AppException(ErrorCode.TRUCCHIHUY_IS_EXIST);
        }
        TrucChiHuyRepo.save(TrucChiHuy);
        nhatKyService.createNhatKy(NhatKyRequest.builder()
                .doiTuong(DoiTuongNhatKy.TRUC_CHI_HUY)
                .hanhDong(HanhDongNhatKy.CREATE)
                .doiTuongId(TrucChiHuy.getIdNguoitruc())
                .taiKhoan(SecurityUtils.getClaim("sub"))
                .trangThai(TrangThaiNhatKy.THANH_CONG)
                .moTa("Tài khoản " + SecurityUtils.getUsername() + "tạo thông tin trực chỉ huy "+TrucChiHuy.getTenNguoitruc())
                .build());
        return TrucChiHuyMapper.toResponse(TrucChiHuy);
    }

    @Override
    public TrucChiHuyResponse updateNguoiTruc(String idNguoiTruc, TrucChiHuyForm update) {

        if(TrucChiHuyRepo.findBySodienthoaiAndTenNguoitrucAndIsDeleted(update.getSodienthoai(),update.getTenNguoitruc(),false)!=null){
            nhatKyService.createNhatKy(NhatKyRequest.builder()
                    .doiTuong(DoiTuongNhatKy.TRUC_CHI_HUY)
                    .hanhDong(HanhDongNhatKy.UPDATE)
                    .doiTuongId(idNguoiTruc)
                    .taiKhoan(SecurityUtils.getClaim("sub"))
                    .trangThai(TrangThaiNhatKy.THAT_BAI)
                    .moTa("Tài khoản " + SecurityUtils.getUsername() + "không câp nhập thông tin trực chỉ huy do "+ErrorCode.TRUCCHIHUY_IS_EXIST)
                    .build());
            throw new AppException(ErrorCode.TRUCCHIHUY_IS_EXIST);
        }
        TrucChiHuyEntity TrucChiHuy=TrucChiHuyRepo.findById(idNguoiTruc)
                .orElseThrow(()->new AppException(ErrorCode.TRUCCHIHUY_NOT_FOUND));
        TrucChiHuyMapper.update(TrucChiHuy,update);
        TrucChiHuyRepo.save(TrucChiHuy);
        nhatKyService.createNhatKy(NhatKyRequest.builder()
                .doiTuong(DoiTuongNhatKy.TRUC_CHI_HUY)
                .hanhDong(HanhDongNhatKy.UPDATE)
                .doiTuongId(idNguoiTruc)
                .taiKhoan(SecurityUtils.getClaim("sub"))
                .trangThai(TrangThaiNhatKy.THANH_CONG)
                .moTa("Tài khoản " + SecurityUtils.getUsername() + "câp nhập thông tin trực chỉ huy "+TrucChiHuy.getTenNguoitruc())
                .build());
        return TrucChiHuyMapper.toResponse(TrucChiHuy);
    }

    @Override
    public boolean canhBaoTrucChiHuy() {

        LocalDate today = LocalDate.now();

        List<CaTrucEntity> ds =
                caTrucRepo.findCaTrucChiHuy(today);

        if(ds.isEmpty()){
            return true;
        }

        LocalDate ngayCuoi =
                ds.get(ds.size() - 1).getNgaytruc();

        long soNgayConLai =
                ChronoUnit.DAYS.between(today, ngayCuoi);

        return soNgayConLai <= 1;
    }

    @Override
    public void deleteNguoiTruc(String idNguoiTruc) {
        TrucChiHuyEntity TrucChiHuy=TrucChiHuyRepo.findById(idNguoiTruc)
                .orElseThrow(()->{
                    nhatKyService.createNhatKy(NhatKyRequest.builder()
                            .doiTuong(DoiTuongNhatKy.TRUC_CHI_HUY)
                            .hanhDong(HanhDongNhatKy.DELETE)
                            .doiTuongId(idNguoiTruc)
                            .taiKhoan(SecurityUtils.getClaim("sub"))
                            .trangThai(TrangThaiNhatKy.THAT_BAI)
                            .moTa("Tài khoản " + SecurityUtils.getUsername() + "không xóa thông tin trực chỉ huy do "+ErrorCode.TRUCCHIHUY_NOT_FOUND)
                            .build());
                    return new AppException(ErrorCode.TRUCCHIHUY_NOT_FOUND);
                });
        TrucChiHuy.setIsDeleted(true);
        TrucChiHuyRepo.save(TrucChiHuy);
        nhatKyService.createNhatKy(NhatKyRequest.builder()
                .doiTuong(DoiTuongNhatKy.TRUC_CHI_HUY)
                .hanhDong(HanhDongNhatKy.DELETE)
                .doiTuongId(idNguoiTruc)
                .taiKhoan(SecurityUtils.getClaim("sub"))
                .trangThai(TrangThaiNhatKy.THANH_CONG)
                .moTa("Tài khoản " + SecurityUtils.getUsername() + "xóa thông tin trực chỉ huy "+TrucChiHuy.getTenNguoitruc())
                .build());
    }
}
