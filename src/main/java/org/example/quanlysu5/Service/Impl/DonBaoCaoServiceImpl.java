package org.example.quanlysu5.Service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.quanlysu5.Dto.Request.DonBaoCaoRequest;
import org.example.quanlysu5.Dto.Response.DonBaoCao.DonBaoCaoResponse;
import org.example.quanlysu5.Enum.Status;
import org.example.quanlysu5.Exception.AppException;
import org.example.quanlysu5.Exception.ErrorCode;
import org.example.quanlysu5.Form.DonBaoCaoForm;
import org.example.quanlysu5.Mapper.DonBaoCaoMapper;
import org.example.quanlysu5.Module.CaTrucEntity;
import org.example.quanlysu5.Module.DonBaoCaoEntity;
import org.example.quanlysu5.Module.DonViEntity;
import org.example.quanlysu5.Repo.DonBaoCaoRepo;
import org.example.quanlysu5.Service.CaTrucService;
import org.example.quanlysu5.Service.DonBaoCaoService;
import org.example.quanlysu5.Service.DonViService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DonBaoCaoServiceImpl implements DonBaoCaoService {
    private final DonBaoCaoRepo DonBaoCaoRepo;
    DonBaoCaoMapper DonBaoCaoMapper;
    DonViService donViService;
    CaTrucService caTrucService;

    @Override
    public List<DonBaoCaoResponse> getAllDonBaoCaoToResponse() {
        return DonBaoCaoRepo.findAllByIsDeleted(false).stream()
                .map(DonBaoCaoMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public DonBaoCaoEntity getByIdDonBaoCao(String idNguoiTruc) {
        return DonBaoCaoRepo.findById(idNguoiTruc)
                .orElseThrow(()->new AppException(ErrorCode.DONBAOCAO_NOT_FOUND));
    }

    @Override
    public DonBaoCaoResponse getByIdDonBaoCaoReponse(String idNguoiTruc) {
        return DonBaoCaoMapper.toResponse(getByIdDonBaoCao(idNguoiTruc));
    }

    @Override
    public DonBaoCaoResponse createDonBaoCaoQuanSoNgay(DonBaoCaoRequest request) {

        DonBaoCaoEntity DonBaoCaoEntity = DonBaoCaoMapper.toEntity(request);
        DonBaoCaoEntity.setStatus(Status.Chờ_Duyệt);
        CaTrucEntity caTruc=caTrucService.getByThoiGian(LocalDateTime.now());
        DonBaoCaoEntity.setCaTruc(caTruc);
        DonBaoCaoEntity.setIsDeleted(false);
        DonViEntity donViEntity=donViService.getById(request.getDonVi());
        DonBaoCaoEntity.setDonVi(donViEntity);
        DonBaoCaoEntity = DonBaoCaoRepo.save(DonBaoCaoEntity);

        return DonBaoCaoMapper.toResponse(DonBaoCaoEntity);
    }

    @Override
    public List<DonBaoCaoResponse> getAllDonBaoCaoDonViConByDonVi(String idDonVi, LocalDate ngayLoc) {

        DonViEntity donViEntity = donViService.getById(idDonVi);

        List<DonBaoCaoEntity> donBaoCaoEntities = new ArrayList<>();

        LocalDateTime start = ngayLoc.atStartOfDay();
        LocalDateTime end = ngayLoc.atTime(23, 59, 59);

        List<DonViEntity> donviCon = donViEntity.getDonViCon();

        if (!donviCon.isEmpty()) {
            donviCon.forEach(dv -> {

                DonBaoCaoEntity childReports =
                        DonBaoCaoRepo.findByDonVi_MaDonViAndThoiGianBaoCaoBetweenAndStatus(dv.getMaDonVi(),start,end,Status.Đã_Duyệt)
                                .orElseThrow(()->new AppException(ErrorCode.DONBAOCAO_NOT_FOUND));

                if (childReports != null) {
                    donBaoCaoEntities.add(childReports);
                }
            });
        }

        return donBaoCaoEntities.stream()
                .map(DonBaoCaoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DonBaoCaoResponse getAllDonBaoCaoByDonVi(String idDonVi, LocalDate ngayLoc) {
        LocalDateTime start = ngayLoc.atStartOfDay();
        LocalDateTime end = ngayLoc.atTime(23, 59, 59);

        DonBaoCaoEntity Reports =
                DonBaoCaoRepo.findByDonVi_MaDonViAndThoiGianBaoCaoBetween(idDonVi,start,end)
                        .orElseThrow(()->new AppException(ErrorCode.DONBAOCAO_NOT_FOUND));
        return DonBaoCaoMapper.toResponse(Reports);
    }

    @Override
    public DonBaoCaoResponse getAllDonBaoCaoByDonViApprove(
            String idDonVi,
            LocalDate ngayLoc) {

        DonBaoCaoResponse donBaoCaoResponse =
                getAllDonBaoCaoByDonVi(idDonVi, ngayLoc);

        return donBaoCaoResponse != null
                && donBaoCaoResponse.getStatus() == Status.Đã_Duyệt
                ? donBaoCaoResponse
                : null;
    }

    @Override
    public DonBaoCaoResponse updateStatusApprove(String idDonBaoCao) {
        DonBaoCaoEntity donBaoCaoEntity=getByIdDonBaoCao(idDonBaoCao);
        donBaoCaoEntity.setStatus(Status.Đã_Duyệt);
        donBaoCaoEntity.setUpdatedAt(LocalDateTime.now());
        DonBaoCaoRepo.save(donBaoCaoEntity);
        return DonBaoCaoMapper.toResponse(donBaoCaoEntity);
    }

    @Override
    public DonBaoCaoResponse updateStatusRefuse(String idDonBaoCao) {
        DonBaoCaoEntity donBaoCaoEntity=getByIdDonBaoCao(idDonBaoCao);
        donBaoCaoEntity.setStatus(Status.Từ_Chối);
        donBaoCaoEntity.setUpdatedAt(LocalDateTime.now());
        DonBaoCaoRepo.save(donBaoCaoEntity);
        return DonBaoCaoMapper.toResponse(donBaoCaoEntity);    }

    @Override
    public DonBaoCaoResponse updateDonBaoCao(String idDonBaoCao, DonBaoCaoForm update) {
        DonBaoCaoEntity DonBaoCao=DonBaoCaoRepo.findById(idDonBaoCao)
                .orElseThrow(()->new AppException(ErrorCode.DONBAOCAO_NOT_FOUND));
        if(DonBaoCao.getStatus()!=Status.Đã_Duyệt){
            DonBaoCao.setStatus(Status.Chờ_Duyệt);
            DonBaoCaoMapper.update(DonBaoCao,update);
            DonBaoCaoRepo.save(DonBaoCao);
        }
        return DonBaoCaoMapper.toResponse(DonBaoCao);
    }

    @Override
    public void deleteDonBaoCao(String idDonBaoCao) {
        DonBaoCaoEntity DonBaoCao=DonBaoCaoRepo.findById(idDonBaoCao)
                .orElseThrow(()->new AppException(ErrorCode.DONBAOCAO_NOT_FOUND));
        DonBaoCaoRepo.deleteById(idDonBaoCao);
    }
}
