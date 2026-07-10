package org.example.quanlysu5.Service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.quanlysu5.Dto.Request.NhatKyRequest;
import org.example.quanlysu5.Dto.Request.TimKiemNhatKyRequest;
import org.example.quanlysu5.Dto.Response.NhatKy.NhatKyResponse;
import org.example.quanlysu5.Enum.DoiTuongNhatKy;
import org.example.quanlysu5.Enum.HanhDongNhatKy;
import org.example.quanlysu5.Enum.TrangThaiNhatKy;
import org.example.quanlysu5.Exception.AppException;
import org.example.quanlysu5.Exception.ErrorCode;
import org.example.quanlysu5.Mapper.NhatKyMapper;
import org.example.quanlysu5.Module.NhatKyEntity;
import org.example.quanlysu5.Module.TaikhoanEntity;
import org.example.quanlysu5.Repo.NhatKyRepo;
import org.example.quanlysu5.Repo.TaiKhoanRepo;
import org.example.quanlysu5.Service.Impl.Specification.NhatKySpecification;
import org.example.quanlysu5.Service.NhatKyService;
import org.example.quanlysu5.Service.TaiKhoanService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NhatKyServiceImpl implements NhatKyService {
    private final NhatKyRepo nhatKyRepo;
    NhatKyMapper nhatKyMapper;
    TaiKhoanRepo taiKhoanRepo;
    @Override
    public NhatKyResponse createNhatKy(NhatKyRequest entity) {
        NhatKyEntity nhatKy=nhatKyMapper.toEntity(entity);
        TaikhoanEntity taikhoanEntity=taiKhoanRepo.findById(entity.getTaiKhoan())
                .orElseThrow(()->new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        nhatKy.setTaiKhoan(taikhoanEntity);
        nhatKy.setCreatedAt(LocalDateTime.now());
        nhatKy.setIsDeleted(false);
        nhatKyRepo.save(nhatKy);
        return nhatKyMapper.toResponse(nhatKy);
    }
    @Override
    public Page<NhatKyResponse> search(TimKiemNhatKyRequest request, Pageable pageable) {
        Specification<NhatKyEntity> specification = Specification
                .where(NhatKySpecification.hasTaiKhoanId(request.getTaiKhoan()))
                .and(NhatKySpecification.hasHanhDong(request.getHanhDong()))
                .and(NhatKySpecification.hasDoiTuong(request.getDoiTuong()))
                .and(NhatKySpecification.hasTrangThai(request.getTrangThai()))
                .and(NhatKySpecification.hasMoTa(request.getMoTa()))
                .and(
                        NhatKySpecification.createdFrom(
                                request.getTuNgay() == null
                                        ? null
                                        : request.getTuNgay()
                        )
                )
                .and(
                        NhatKySpecification.createdTo(
                                request.getDenNgay() == null
                                        ? null
                                        : request.getDenNgay()
                        )
                );
        return nhatKyRepo.findAll(specification, pageable).map(nhatKyMapper::toResponse);
    }


    @Override
    public NhatKyEntity getById(String idNhatKy) {
        return nhatKyRepo.findById(idNhatKy)
                .orElseThrow(()->new AppException(ErrorCode.NHATKY_NOT_FOUND));
    }

    @Override
    public NhatKyResponse getByIdResposne(String idNhatKy) {
        return nhatKyMapper.toResponse(getById(idNhatKy));
    }

    @Override
    public void deleteNhatKy(String idNhatKy) {
        NhatKyEntity nhatKy=getById(idNhatKy);
        nhatKyRepo.deleteById(nhatKy.getIdNhatKy());
    }
}
