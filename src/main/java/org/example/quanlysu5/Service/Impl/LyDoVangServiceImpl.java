package org.example.quanlysu5.Service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.quanlysu5.Dto.Request.LyDoVangRequest;
import org.example.quanlysu5.Dto.Request.NhatKyRequest;
import org.example.quanlysu5.Dto.Response.LyDoVangResponse;
import org.example.quanlysu5.Enum.DoiTuongNhatKy;
import org.example.quanlysu5.Enum.HanhDongNhatKy;
import org.example.quanlysu5.Enum.TrangThaiNhatKy;
import org.example.quanlysu5.Exception.AppException;
import org.example.quanlysu5.Exception.ErrorCode;
import org.example.quanlysu5.Hepler.SecurityUtils;
import org.example.quanlysu5.Mapper.LyDoVangMapper;
import org.example.quanlysu5.Module.LyDoVangEntity;
import org.example.quanlysu5.Repo.LyDoVangRepo;
import org.example.quanlysu5.Service.LyDoVangService;
import org.example.quanlysu5.Service.NhatKyService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LyDoVangServiceImpl implements LyDoVangService {
    LyDoVangRepo LyDoVangRepo;
    LyDoVangMapper LyDoVangMapper;
    NhatKyService nhatKyService;
    @Override
    public List<LyDoVangResponse> getAllList() {
        return LyDoVangRepo.findAll().stream().map(LyDoVangMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public LyDoVangEntity getByid(String id) {
        return LyDoVangRepo.findById(id)
                .orElseThrow(()->new AppException(ErrorCode.LYDOVANG_NOT_FOUND));
    }

    @Override
    public LyDoVangResponse getByIdResponse(String id) {
        return LyDoVangMapper.toResponse(getByid(id));
    }

    @Override
    public LyDoVangResponse createLyDoVang(LyDoVangRequest request) {
        LyDoVangEntity LyDoVangEntity=LyDoVangMapper.toEntity(request);
        LyDoVangEntity.setIsDeleted(false);
        LyDoVangEntity.setCreatedAt(LocalDateTime.now());
        LyDoVangRepo.save(LyDoVangEntity);
        nhatKyService.createNhatKy(NhatKyRequest.builder()
                .doiTuong(DoiTuongNhatKy.LY_DO_VANG)
                .hanhDong(HanhDongNhatKy.CREATE)
                .doiTuongId(LyDoVangEntity.getIdLyDoVang())
                .taiKhoan(SecurityUtils.getClaim("sub"))
                .trangThai(TrangThaiNhatKy.THANH_CONG)
                .moTa("Tài khoản " + SecurityUtils.getUsername() + "tạo thông tin lý do vắng ")
                .build());
        return LyDoVangMapper.toResponse(LyDoVangEntity);
    }
}
