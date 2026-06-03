package org.example.quanlysu5.Service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.quanlysu5.Dto.Request.TaiKhoanRequest;
import org.example.quanlysu5.Dto.Response.TaiKhoan.TaiKhoanResponse;
import org.example.quanlysu5.Exception.AppException;
import org.example.quanlysu5.Exception.ErrorCode;
import org.example.quanlysu5.Mapper.TaiKhoanMapper;
import org.example.quanlysu5.Module.DonViEntity;
import org.example.quanlysu5.Module.TaikhoanEntity;
import org.example.quanlysu5.Module.VaiTroEntity;
import org.example.quanlysu5.Repo.DonViRepo;
import org.example.quanlysu5.Repo.TaiKhoanRepo;
import org.example.quanlysu5.Service.DonViService;
import org.example.quanlysu5.Service.TaiKhoanService;
import org.example.quanlysu5.Service.VaiTroService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaiKhoanServiceImpl implements TaiKhoanService {
    private final DonViRepo donViRepo;
    private final TaiKhoanRepo taiKhoanRepo;
    TaiKhoanMapper taiKhoanMapper;
    DonViService donViService;
    VaiTroService vaiTroService;
    PasswordEncoder passwordEncoder;

    @Override
    public TaikhoanEntity getTaiKhoanById(String id) {
    log.info("Id user: "+id);
        return taiKhoanRepo.findById(id)
                .orElseThrow(()->new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
    }

    @Override
    public TaiKhoanResponse getTaiKhoanResponse(String id) {
        TaikhoanEntity account=getTaiKhoanById(id);
        log.info(account.toString());
        return taiKhoanMapper.toResponse(account);
    }

    @Override
    public List<TaiKhoanResponse> getAllTaiKhoan() {
        return taiKhoanRepo.findAll().stream().map(taiKhoanMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TaiKhoanResponse createdTaiKhoanResponse(TaiKhoanRequest taiKhoanRequest) {
        TaikhoanEntity taikhoanEntity=taiKhoanMapper.toEntity(taiKhoanRequest);
        String Mk=passwordEncoder.encode(taiKhoanRequest.getMatkhau());
        taiKhoanRepo.findByTenDangNhapAndMatKhau(taiKhoanRequest.getTenDangNhap(),Mk)
                .ifPresent(account -> {
                    throw new AppException(ErrorCode.ACCOUNT_IS_EXIST);
                });
        DonViEntity donViEntity=donViService.getById(taiKhoanRequest.getDonVi());
        VaiTroEntity vaiTroEntity=vaiTroService.getRoleById(taiKhoanRequest.getVaiTro());
        taikhoanEntity.setMatKhau(Mk);
        taikhoanEntity.setDonVi(donViEntity);
        taikhoanEntity.setVaiTro(vaiTroEntity);
        taikhoanEntity.setIsDeleted(false);
        return taiKhoanMapper.toResponse(taiKhoanRepo.save(taikhoanEntity));
    }
}
