package org.example.quanlysu5.Service.Impl;


import org.example.quanlysu5.Dto.Request.NhatKyRequest;
import org.example.quanlysu5.Enum.CapDonVi;
import org.example.quanlysu5.Enum.DoiTuongNhatKy;
import org.example.quanlysu5.Enum.HanhDongNhatKy;
import org.example.quanlysu5.Enum.TrangThaiNhatKy;
import org.example.quanlysu5.Form.DonviForm;
import org.example.quanlysu5.Hepler.SecurityUtils;
import org.example.quanlysu5.Mapper.DonViMapper;
import org.example.quanlysu5.Module.NhatKyEntity;
import org.example.quanlysu5.Module.TaikhoanEntity;
import org.example.quanlysu5.Repo.TaiKhoanRepo;
import org.example.quanlysu5.Service.Impl.Specification.DonViSpecification;
import org.example.quanlysu5.Service.NhatKyService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.quanlysu5.Dto.Request.DonviRequest;
import org.example.quanlysu5.Dto.Response.DonVi.DonViResponse;
import org.example.quanlysu5.Exception.AppException;
import org.example.quanlysu5.Exception.ErrorCode;
import org.example.quanlysu5.Module.DonViEntity;
import org.example.quanlysu5.Repo.DonViRepo;
import org.example.quanlysu5.Service.DonViService;
import org.example.quanlysu5.Unit.ChildCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DonViServiceImpl implements DonViService {
    private final TaiKhoanRepo taiKhoanRepo;

    ChildCode childCode;
    DonViRepo DonViRepo;
    DonViMapper donViMapper;
    NhatKyService nhatKyService;
    @Override
    public Page<DonViResponse> toUnitsPage(Pageable page) {
        return DonViRepo.findAllByIsDeleted(false,page)
                .map(donViMapper::toResponse);
    }

    @Override
    public List<DonViResponse> toUnitsList() {
        Specification<DonViEntity> specification = Specification.where(DonViSpecification.sortTheoCap()) ;
        return  DonViRepo.findAll(specification).stream()
                .map(donViMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DonViResponse createDonVi(DonviRequest request) {

        DonViEntity donVi = donViMapper.toEntity(request);
        donVi.setCapDonVi(CapDonVi.valueOf(request.getCapDonVi()));
        if (request.getDonViCha() != null) {

            DonViEntity donViCha = DonViRepo.findById(request.getDonViCha())
                    .orElseThrow(() ->
                    {
                        nhatKyService.createNhatKy(NhatKyRequest.builder()
                                .doiTuong(DoiTuongNhatKy.DON_VI)
                                .hanhDong(HanhDongNhatKy.CREATE)
                                .taiKhoan(SecurityUtils.getClaim("sub"))
                                .trangThai(TrangThaiNhatKy.THAT_BAI)
                                .moTa("Tài khoản " + SecurityUtils.getUsername() + "không tạo thông tin đơn vị mới do "+ErrorCode.DONVI_NOT_FOUND)
                                .build());
                        return new AppException(ErrorCode.DONVI_NOT_FOUND);
                    });

            donVi.setDonViCha(donViCha);

            String maDonVi = childCode.generateChildCode(donViCha);
            donVi.setMaDonVi(maDonVi);

        } else {

            String maDonVi = childCode.generateRootCode();
            donVi.setMaDonVi(maDonVi);
        }

        donVi.setHoatDong(true);
        donVi.setIsDeleted(false);

        DonViEntity saved = DonViRepo.save(donVi);
        nhatKyService.createNhatKy(NhatKyRequest.builder()
                .doiTuong(DoiTuongNhatKy.DON_VI)
                .hanhDong(HanhDongNhatKy.CREATE)
                .doiTuongId(saved.getMaDonVi())
                .taiKhoan(SecurityUtils.getClaim("sub"))
                .trangThai(TrangThaiNhatKy.THANH_CONG)
                .moTa("Tài khoản " + SecurityUtils.getUsername() + "tạo thông tin đơn vị mới "+donVi.getTenDonvi())
                .build());
        return donViMapper.toResponse(saved);
    }@Override
    public DonViEntity getByKyHieuDonVi(String kyHieuDonVi) {

        return DonViRepo.findByKyhieuDonvi(kyHieuDonVi)
                .orElseThrow(()->new AppException(ErrorCode.DONVI_NOT_FOUND));
    }

    @Override
    public DonViResponse updateDonVi(String idDonVi, DonviForm update) {
        DonViEntity donVi=getById(idDonVi);
        donViMapper.update(donVi,update);
        donVi.setUpdatedAt(LocalDateTime.now());
        donVi.setKyhieuDonvi(update.getKyhieuDonvi());
        DonViRepo.save(donVi);
        nhatKyService.createNhatKy(NhatKyRequest.builder()
                .doiTuong(DoiTuongNhatKy.DON_VI)
                .hanhDong(HanhDongNhatKy.CREATE)
                .doiTuongId(idDonVi)
                .taiKhoan(SecurityUtils.getClaim("sub"))
                .trangThai(TrangThaiNhatKy.THANH_CONG)
                .moTa("Tài khoản " + SecurityUtils.getUsername() + "tạo thông tin đơn vị mới "+donVi.getTenDonvi())
                .build());
        return donViMapper.toResponse(donVi);
    }

    @Override
    public DonViEntity getById(String id) {
        return DonViRepo.findById(id).orElseThrow(()->new AppException(ErrorCode.DONVI_NOT_FOUND));
    }

}
