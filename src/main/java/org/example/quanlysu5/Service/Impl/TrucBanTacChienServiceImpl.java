package org.example.quanlysu5.Service.Impl;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.quanlysu5.Dto.Request.NhatKyRequest;
import org.example.quanlysu5.Dto.Request.TrucBanTacChienRequest;
import org.example.quanlysu5.Dto.Response.TrucBanTacChien.TrucBanTacChienResponse;
import org.example.quanlysu5.Enum.DoiTuongNhatKy;
import org.example.quanlysu5.Enum.HanhDongNhatKy;
import org.example.quanlysu5.Enum.TrangThaiNhatKy;
import org.example.quanlysu5.Exception.AppException;
import org.example.quanlysu5.Exception.ErrorCode;
import org.example.quanlysu5.Form.TrucBanTacChienForm;
import org.example.quanlysu5.Hepler.SecurityUtils;
import org.example.quanlysu5.Mapper.TrucBanTacChienMapper;
import org.example.quanlysu5.Module.CaTrucEntity;
import org.example.quanlysu5.Module.TrucBanTacChienEntity;
import org.example.quanlysu5.Repo.CaTrucRepo;
import org.example.quanlysu5.Repo.TrucBanTacChienRepo;
import org.example.quanlysu5.Service.NhatKyService;
import org.example.quanlysu5.Service.TrucBanTacChienService;
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
public class TrucBanTacChienServiceImpl implements TrucBanTacChienService {
    private final TrucBanTacChienRepo trucBanTacChienRepo;
    TrucBanTacChienMapper trucBanTacChienMapper;
    CaTrucRepo caTrucRepo;
    NhatKyService nhatKyService;
    @Override
    public List<TrucBanTacChienResponse> getAllTrucBanTacChienToResponse() {
        return trucBanTacChienRepo.findAllByIsDeleted(false).stream().map(trucBanTacChienMapper::toResponse)
                .collect((Collectors.toList()));
    }

    @Override
    public TrucBanTacChienEntity getByIdNguoiTruc(String idNguoiTruc) {
        return trucBanTacChienRepo.findById(idNguoiTruc)
                .orElseThrow(()->new AppException(ErrorCode.TRUCBANTACCHIEN_NOT_FOUND));
    }

    @Override
    public TrucBanTacChienResponse getByIdNguoiTrucResponse(String idNguoiTruc) {
        return trucBanTacChienMapper.toResponse(getByIdNguoiTruc(idNguoiTruc));
    }

    @Override
    public TrucBanTacChienResponse getByTenNguoiTruc(String tenNguoiTruc) {
        return trucBanTacChienMapper.toResponse(trucBanTacChienRepo.findByTenNguoitruc(tenNguoiTruc)
                .orElseThrow(()->new AppException(ErrorCode.TRUCBANTACCHIEN_NOT_FOUND)));
    }

    @Override
    public TrucBanTacChienResponse createNguoiTruc(TrucBanTacChienRequest trucBanTacChienRequest) {
        TrucBanTacChienEntity trucBanTacChien=trucBanTacChienMapper.toEntity(trucBanTacChienRequest);
        trucBanTacChien.setIsDeleted(false);
        trucBanTacChien.setCreatedAt(LocalDateTime.now());
        if(trucBanTacChienRepo.findBySodienthoaiAndTenNguoitrucAndIsDeleted(trucBanTacChien.getSodienthoai(),trucBanTacChien.getTenNguoitruc(),false)==null){
            nhatKyService.createNhatKy(NhatKyRequest.builder()
                    .doiTuong(DoiTuongNhatKy.TRUC_BAN_TAC_CHIEN)
                    .hanhDong(HanhDongNhatKy.CREATE)
                    .taiKhoan(SecurityUtils.getClaim("sub"))
                    .trangThai(TrangThaiNhatKy.THAT_BAI)
                    .moTa("Tài khoản " + SecurityUtils.getUsername() + "không cập nhập thông tin trực ban tác chiến do "+ErrorCode.TRUCBANTACCHIEN_IS_EXIST)
                    .build());
            throw new AppException(ErrorCode.TRUCBANTACCHIEN_IS_EXIST);
        }
        trucBanTacChienRepo.save(trucBanTacChien);
        nhatKyService.createNhatKy(NhatKyRequest.builder()
                .doiTuong(DoiTuongNhatKy.TRUC_BAN_TAC_CHIEN)
                .hanhDong(HanhDongNhatKy.UPDATE)
                .doiTuongId(trucBanTacChien.getIdNguoitruc())
                .taiKhoan(SecurityUtils.getClaim("sub"))
                .trangThai(TrangThaiNhatKy.THANH_CONG)
                .moTa("Tài khoản " + SecurityUtils.getUsername() + "cập nhập thông tin trực ban tác chiến "+trucBanTacChien.getTenNguoitruc())
                .build());
        return trucBanTacChienMapper.toResponse(trucBanTacChien);
    }

    @Override
    public TrucBanTacChienResponse updateNguoiTruc(String idNguoiTruc, TrucBanTacChienForm update) {

        if(trucBanTacChienRepo.findBySodienthoaiAndTenNguoitrucAndIsDeleted(update.getSodienthoai(),update.getTenNguoitruc(),false)!=null){
            nhatKyService.createNhatKy(NhatKyRequest.builder()
                    .doiTuong(DoiTuongNhatKy.TRUC_BAN_TAC_CHIEN)
                    .hanhDong(HanhDongNhatKy.UPDATE)
                    .doiTuongId(idNguoiTruc)
                    .taiKhoan(SecurityUtils.getClaim("sub"))
                    .trangThai(TrangThaiNhatKy.THAT_BAI)
                    .moTa("Tài khoản " + SecurityUtils.getUsername() + "không cập nhập thông tin trực ban tác chiến do "+ErrorCode.TRUCBANTACCHIEN_IS_EXIST)
                    .build());
            throw new AppException(ErrorCode.TRUCBANTACCHIEN_IS_EXIST);
        }
        TrucBanTacChienEntity trucBanTacChien=trucBanTacChienRepo.findById(idNguoiTruc)
                .orElseThrow(()->new AppException(ErrorCode.TRUCBANTACCHIEN_NOT_FOUND));
        trucBanTacChienMapper.update(trucBanTacChien,update);
        trucBanTacChienRepo.save(trucBanTacChien);
        nhatKyService.createNhatKy(NhatKyRequest.builder()
                .doiTuong(DoiTuongNhatKy.TRUC_BAN_TAC_CHIEN)
                .hanhDong(HanhDongNhatKy.UPDATE)
                .doiTuongId(idNguoiTruc)
                .taiKhoan(SecurityUtils.getClaim("sub"))
                .trangThai(TrangThaiNhatKy.THANH_CONG)
                .moTa("Tài khoản " + SecurityUtils.getUsername() + "cập nhập thông tin trực ban tác chiến "+trucBanTacChien.getTenNguoitruc())
                .build());
        return trucBanTacChienMapper.toResponse(trucBanTacChien);
    }

    @Override
    public boolean canhBaoTacChien() {

        LocalDate today = LocalDate.now();

        List<CaTrucEntity> ds =
                caTrucRepo.findCaTrucTacChien(today);

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
        TrucBanTacChienEntity trucBanTacChien=trucBanTacChienRepo.findById(idNguoiTruc)
                .orElseThrow(()->{
                    nhatKyService.createNhatKy(NhatKyRequest.builder()
                            .doiTuong(DoiTuongNhatKy.TRUC_BAN_TAC_CHIEN)
                            .hanhDong(HanhDongNhatKy.DELETE)
                            .doiTuongId(idNguoiTruc)
                            .taiKhoan(SecurityUtils.getClaim("sub"))
                            .trangThai(TrangThaiNhatKy.THAT_BAI)
                            .moTa("Tài khoản " + SecurityUtils.getUsername() + "không xóa thông tin trực ban tác chiến do "+ErrorCode.TRUCBANTACCHIEN_NOT_FOUND)
                            .build());
                   return new AppException(ErrorCode.TRUCBANTACCHIEN_NOT_FOUND);
                });
        trucBanTacChien.setIsDeleted(true);
        trucBanTacChienRepo.save(trucBanTacChien);
        nhatKyService.createNhatKy(NhatKyRequest.builder()
                .doiTuong(DoiTuongNhatKy.TRUC_BAN_TAC_CHIEN)
                .hanhDong(HanhDongNhatKy.DELETE)
                .doiTuongId(idNguoiTruc)
                .taiKhoan(SecurityUtils.getClaim("sub"))
                .trangThai(TrangThaiNhatKy.THANH_CONG)
                .moTa("Tài khoản " + SecurityUtils.getUsername() + "xóa thông tin trực ban tác chiến "+trucBanTacChien.getTenNguoitruc())
                .build());
    }
}
