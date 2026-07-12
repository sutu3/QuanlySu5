package org.example.quanlysu5.Service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.quanlysu5.Dto.Request.VaiTroRequest;
import org.example.quanlysu5.Dto.Response.VaiTroResponse;
import org.example.quanlysu5.Exception.AppException;
import org.example.quanlysu5.Exception.ErrorCode;
import org.example.quanlysu5.Form.VaiTroForm;
import org.example.quanlysu5.Mapper.VaiTroMapper;
import org.example.quanlysu5.Module.TaikhoanEntity;import org.example.quanlysu5.Module.VaiTroEntity;
import org.example.quanlysu5.Repo.TaiKhoanRepo;
import org.example.quanlysu5.Repo.VaiTroRepo;
import org.example.quanlysu5.Service.TaiKhoanService;import org.example.quanlysu5.Service.VaiTroService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class VaiTroServiceImpl implements VaiTroService {
    private final TaiKhoanRepo taiKhoanRepo;
    VaiTroRepo vaiTroRepo;
    VaiTroMapper vaiTroMapper;
    TaiKhoanService taiKhoanService;


    @Override
    public List<VaiTroResponse> getAllRole() {
        return vaiTroRepo.findAll().stream()
                .map(vaiTroMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public VaiTroEntity getRoleById(String id) {
        return vaiTroRepo.findById(id)
                .orElseThrow(()->new AppException(ErrorCode.ROLE_NOT_FOUND));
    }@Override
    public VaiTroResponse getRoleByTaiKhoan(String idTaiKhoan) {
        TaikhoanEntity taikhoan= taiKhoanService.getTaiKhoanById(idTaiKhoan);
        return vaiTroMapper.toResponse(taikhoan.getVaiTro());
    }

    @Override
    public VaiTroEntity getRoleByName(String name) {
        return vaiTroRepo.findByTenVaiTro(name)
                .orElseThrow(()->new AppException(ErrorCode.ROLE_NOT_FOUND));
    }

    @Override
    public VaiTroResponse getRoleResponseById(String id) {
        return vaiTroMapper.toResponse(getRoleById(id));
    }

    @Override
    public VaiTroResponse createRole(VaiTroRequest vaiTroRequest) {
        VaiTroEntity vaiTroEntity = vaiTroMapper.toEntity(vaiTroRequest);
        vaiTroEntity.setIsDeleted(false);
        vaiTroEntity.setCreatedAt(LocalDateTime.now());
        log.info(vaiTroRequest.getTenVaiTro().toString());
        if(vaiTroRepo.findByTenVaiTro(vaiTroEntity.getTenVaiTro()).isPresent()){
            throw new AppException(ErrorCode.ROLE_IS_EXIST);
        }
        return vaiTroMapper.toResponse(vaiTroRepo.save(vaiTroEntity));
    }

    @Override
    public VaiTroResponse updateRole(VaiTroForm update, String idRole) {
        VaiTroEntity vaitro=getRoleById(idRole);
        vaiTroMapper.update(vaitro,update);
        vaitro.setUpdatedAt(LocalDateTime.now());
        return vaiTroMapper.toResponse(vaiTroRepo.save(vaitro));
    }

    @Override
    public void deletedRole(String idRole) {
        VaiTroEntity vaitro=getRoleById(idRole);
        if (taiKhoanRepo.existsByVaiTro_IdVaiTro(vaitro.getIdVaiTro())) {
            throw new AppException(ErrorCode.ROLE_IN_USE);
        }
        vaiTroRepo.deleteById(vaitro.getIdVaiTro());
    }
}
