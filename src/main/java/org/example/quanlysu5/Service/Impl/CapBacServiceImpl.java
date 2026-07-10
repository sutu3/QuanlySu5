package org.example.quanlysu5.Service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.quanlysu5.Dto.Request.CapBacRequest;
import org.example.quanlysu5.Dto.Request.NhatKyRequest;
import org.example.quanlysu5.Dto.Response.CapBacResponse;
import org.example.quanlysu5.Enum.DoiTuongNhatKy;
import org.example.quanlysu5.Enum.HanhDongNhatKy;
import org.example.quanlysu5.Enum.TrangThaiNhatKy;
import org.example.quanlysu5.Exception.AppException;
import org.example.quanlysu5.Exception.ErrorCode;
import org.example.quanlysu5.Hepler.SecurityUtils;
import org.example.quanlysu5.Mapper.CapBacMapper;
import org.example.quanlysu5.Module.CapBacEntity;
import org.example.quanlysu5.Repo.CapBacRepo;
import org.example.quanlysu5.Service.CapBacService;
import org.example.quanlysu5.Service.NhatKyService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CapBacServiceImpl implements CapBacService {
    CapBacRepo CapBacRepo;
    CapBacMapper CapBacMapper;
    NhatKyService nhatKyService;
    @Override
    public List<CapBacResponse> getAllList() {
        return CapBacRepo.findAll().stream().map(CapBacMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CapBacEntity getByid(String id) {
        return CapBacRepo.findById(id)
                .orElseThrow(()->new AppException(ErrorCode.CAPBAC_NOT_FOUND));
    }

    @Override
    public CapBacResponse getByIdResponse(String id) {
        return CapBacMapper.toResponse(getByid(id));
    }

    @Override
    public CapBacResponse createCapBac(CapBacRequest request) {
        CapBacEntity CapBacEntity=CapBacMapper.toEntity(request);
        String tenCapbac=request.getTenCapBac().toLowerCase();
        if(CapBacRepo.findByTenCapBac(tenCapbac)==null){
            throw new AppException(ErrorCode.CAPBAC_IS_EXIST);
        }
        CapBacEntity.setTenCapBac(tenCapbac);
        CapBacEntity.setIsDeleted(false);
        CapBacEntity.setCreatedAt(LocalDateTime.now());
        CapBacRepo.save(CapBacEntity);
        nhatKyService.createNhatKy(NhatKyRequest.builder()
                .doiTuong(DoiTuongNhatKy.CAP_BAC)
                .hanhDong(HanhDongNhatKy.CREATE)
                .taiKhoan(SecurityUtils.getClaim("sub"))
                .trangThai(TrangThaiNhatKy.THANH_CONG)
                .moTa("Tài khoản " + SecurityUtils.getUsername() + " Tạo thông tin cấp bậc mới")
                .build());
        return CapBacMapper.toResponse(CapBacEntity);
    }
}
