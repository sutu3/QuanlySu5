package org.example.quanlysu5.Service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.quanlysu5.Dto.Request.ChucVuRequest;
import org.example.quanlysu5.Dto.Request.NhatKyRequest;
import org.example.quanlysu5.Dto.Response.ChucVuResponse;
import org.example.quanlysu5.Enum.DoiTuongNhatKy;
import org.example.quanlysu5.Enum.HanhDongNhatKy;
import org.example.quanlysu5.Enum.TrangThaiNhatKy;
import org.example.quanlysu5.Exception.AppException;
import org.example.quanlysu5.Exception.ErrorCode;
import org.example.quanlysu5.Hepler.SecurityUtils;
import org.example.quanlysu5.Mapper.ChucVuMapper;
import org.example.quanlysu5.Module.ChucVuEntity;
import org.example.quanlysu5.Repo.ChucVuRepo;
import org.example.quanlysu5.Service.ChucVuService;
import org.example.quanlysu5.Service.NhatKyService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ChucVuServiceImpl implements ChucVuService {
    ChucVuRepo chucVuRepo;
    ChucVuMapper chucVuMapper;
    NhatKyService nhatKyService;
    @Override
    public List<ChucVuResponse> getAllList() {
        return chucVuRepo.findAll().stream().map(chucVuMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ChucVuEntity getByid(String id) {
        return chucVuRepo.findById(id)
                .orElseThrow(()->new AppException(ErrorCode.CHUCVU_NOT_FOUND));
    }

    @Override
    public ChucVuResponse getByIdResponse(String id) {
        return chucVuMapper.toResponse(getByid(id));
    }

    @Override
    public ChucVuResponse createChucVu(ChucVuRequest request) {
        ChucVuEntity chucVuEntity=chucVuMapper.toEntity(request);
        String tenChucVu=request.getTenChucVu().toLowerCase();
        if(chucVuRepo.findByTenChucVu(tenChucVu)==null){
            nhatKyService.createNhatKy(NhatKyRequest.builder()
                    .doiTuong(DoiTuongNhatKy.CHUC_VU)
                    .hanhDong(HanhDongNhatKy.CREATE)
                    .taiKhoan(SecurityUtils.getClaim("sub"))
                    .trangThai(TrangThaiNhatKy.THAT_BAI)
                    .moTa("Tài khoản " + SecurityUtils.getUsername() + " tạo thông tin chức vụ thất bại do "+ErrorCode.CHUCVU_IS_EXIST)
                    .build());
            throw new AppException(ErrorCode.CHUCVU_IS_EXIST);
        }
        chucVuEntity.setTenChucVu(tenChucVu);
        chucVuEntity.setIsDeleted(false);
        chucVuEntity.setCreatedAt(LocalDateTime.now());
        chucVuRepo.save(chucVuEntity);
        nhatKyService.createNhatKy(NhatKyRequest.builder()
                .doiTuong(DoiTuongNhatKy.CHUC_VU)
                .hanhDong(HanhDongNhatKy.CREATE)
                .taiKhoan(SecurityUtils.getClaim("sub"))
                .trangThai(TrangThaiNhatKy.THANH_CONG)
                .moTa("Tài khoản " + SecurityUtils.getUsername() + " tạo thông tin chức vụ mới"+chucVuEntity.getTenChucVu())
                .build());
        return chucVuMapper.toResponse(chucVuEntity);
    }
}
