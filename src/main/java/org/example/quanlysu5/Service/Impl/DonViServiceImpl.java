package org.example.quanlysu5.Service.Impl;


import org.springframework.transaction.annotation.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.quanlysu5.Dto.Request.DonviRequest;
import org.example.quanlysu5.Dto.Response.DonVi.DonViResponse;
import org.example.quanlysu5.Exception.AppException;
import org.example.quanlysu5.Exception.ErrorCode;
import org.example.quanlysu5.Mapper.UnitsMapper;
import org.example.quanlysu5.Module.DonViEntity;
import org.example.quanlysu5.Repo.DonViRepo;
import org.example.quanlysu5.Service.DonViService;
import org.example.quanlysu5.Unit.ChildCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DonViServiceImpl implements DonViService {

    ChildCode childCode;
    DonViRepo DonViRepo;
    UnitsMapper unitsMapper;
    @Override
    public Page<DonViResponse> toUnitsPage(Pageable page) {
        return DonViRepo.findAllByIsDeleted(false,page)
                .map(unitsMapper::toResponse);
    }

    @Override
    public List<DonViResponse> toUnitsList() {
        return  DonViRepo.findAll().stream()
                .map(unitsMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DonViResponse createDonVi(DonviRequest request) {

        DonViEntity donVi = unitsMapper.toEntity(request);

        // Nếu có đơn vị cha
        if (request.getDonViCha() != null) {

            DonViEntity donViCha = DonViRepo.findById(request.getDonViCha())
                    .orElseThrow(() ->
                            new AppException(ErrorCode.DONVI_NOT_FOUND));

            donVi.setDonViCha(donViCha);

            // Sinh mã đơn vị
            String maDonVi = childCode.generateChildCode(donViCha);
            donVi.setMaDonVi(maDonVi);

        } else {

            // Đơn vị gốc (sư đoàn)
            String maDonVi = childCode.generateRootCode();
            donVi.setMaDonVi(maDonVi);
        }

        donVi.setHoatDong(true);
        donVi.setIsDeleted(false);

        DonViEntity saved = DonViRepo.save(donVi);

        return unitsMapper.toResponse(saved);
    }

    @Override
    public DonViEntity getById(String id) {
        return DonViRepo.findById(id).orElseThrow(()->new AppException(ErrorCode.DONVI_NOT_FOUND));
    }

}
