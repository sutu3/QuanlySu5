package org.example.quanlysu5.Service.Impl;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.quanlysu5.Dto.Request.CaTrucRequest;
import org.example.quanlysu5.Dto.Response.CaTruc.CaTrucResponse;
import org.example.quanlysu5.Dto.Response.CanhBaoCaTrucResponse;
import org.example.quanlysu5.Enum.LoaiBaoBan;
import org.example.quanlysu5.Exception.AppException;
import org.example.quanlysu5.Exception.ErrorCode;
import org.example.quanlysu5.Form.CaTrucForm;
import org.example.quanlysu5.Mapper.CaTrucMapper;
import org.example.quanlysu5.Module.CaTrucEntity;
import org.example.quanlysu5.Repo.CaTrucRepo;
import org.example.quanlysu5.Repo.KhungGioBaoCaoRepo;
import org.example.quanlysu5.Repo.TrucBanTacChienRepo;
import org.example.quanlysu5.Repo.TrucChiHuyRepo;
import org.example.quanlysu5.Service.CaTrucService;
import org.example.quanlysu5.Service.TrucBanTacChienService;
import org.example.quanlysu5.Service.TrucChiHuyService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CaTrucServiceImpl implements CaTrucService {
    private final TrucBanTacChienRepo trucBanTacChienRepo;
    private final TrucChiHuyRepo trucChiHuyRepo;
    private final CaTrucRepo caTrucRepo;
    CaTrucMapper caTrucMapper;
    TrucChiHuyService trucChiHuyService;
    TrucBanTacChienService trucBanTacChienService;
    KhungGioBaoCaoRepo khungGioRepo;
    @Override
    public List<CaTrucResponse> getAllCaTrucToResponse() {
        return caTrucRepo.findAllByIsDeleted(false).stream()
                .map(caTrucMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public CaTrucEntity getByIdCaTruc(String idNguoiTruc) {
        return caTrucRepo.findById(idNguoiTruc)
                .orElseThrow(()->new AppException(ErrorCode.CATRUC_NOT_FOUND));
    }

    @Override
    public CaTrucResponse getByIdCaTrucResponse(String idNguoiTruc) {
        return caTrucMapper.toResponse(getByIdCaTruc(idNguoiTruc));
    }

    @Transactional
    @Override
    public CaTrucResponse createCaTruc(CaTrucRequest request) {

        CaTrucEntity result = null;
        LocalDate tuNgay = request.getNgaytruc();

        int soNgayTruc;

        if(request.getTrucChiHuy() != null){

            soNgayTruc =
                    khungGioRepo.findByLoaiBaoBan(
                                    LoaiBaoBan.CATRUC_CHIHUY)
                            .get().getSoNgayTruc();

        }
        else{

            soNgayTruc =
                    khungGioRepo.findByLoaiBaoBan(
                                    LoaiBaoBan.CATRUC_BANTACCHIEN)
                            .get().getSoNgayTruc();
        }

        LocalDate denNgay =
                tuNgay.plusDays(soNgayTruc - 1);

        LocalDate current = tuNgay;

        while(!current.isAfter(denNgay)){

            Optional<CaTrucEntity> optional =
                    caTrucRepo.findByNgaytruc(current);

            if (optional.isPresent()) {

                CaTrucEntity caTruc = optional.get();

                if (request.getTrucChiHuy() != null) {

                    if (caTruc.getTrucChiHuy() != null) {
                        throw new AppException(ErrorCode.CATRUC_CHIHUY_EXIST);
                    }

                    caTruc.setTrucChiHuy(
                            trucChiHuyRepo.findById(request.getTrucChiHuy())
                                    .orElseThrow(() ->
                                            new AppException(ErrorCode.TRUCCHIHUY_NOT_FOUND))
                    );
                }

                if (request.getTrucBanTacChien() != null) {

                    if (caTruc.getTrucBanTacChien() != null) {
                        throw new AppException(ErrorCode.CATRUC_TACCHIEN_EXIST);
                    }

                    caTruc.setTrucBanTacChien(
                            trucBanTacChienRepo.findById(request.getTrucBanTacChien())
                                    .orElseThrow(() ->
                                            new AppException(ErrorCode.TRUCBANTACCHIEN_NOT_FOUND))
                    );
                }

                result = caTrucRepo.save(caTruc);

            } else {

                CaTrucEntity caTruc = new CaTrucEntity();

                caTruc.setNgaytruc(current);
                caTruc.setMatkhau(request.getMatkhau());
                caTruc.setGhichu(request.getGhichu());

                if (request.getTrucChiHuy() != null) {
                    caTruc.setTrucChiHuy(
                            trucChiHuyRepo.findById(request.getTrucChiHuy())
                                    .orElseThrow(() ->
                                            new AppException(ErrorCode.TRUCCHIHUY_NOT_FOUND))
                    );
                }

                if (request.getTrucBanTacChien() != null) {
                    caTruc.setTrucBanTacChien(
                            trucBanTacChienRepo.findById(request.getTrucBanTacChien())
                                    .orElseThrow(() ->
                                            new AppException(ErrorCode.TRUCBANTACCHIEN_NOT_FOUND))
                    );
                }

                result = caTrucRepo.save(caTruc);
            }

            current = current.plusDays(1);
        }

        return caTrucMapper.toResponse(result);
    }

    @Override
    public CaTrucResponse updateCaTruc(String idCaTruc, CaTrucForm update) {
        CaTrucEntity caTruc=caTrucRepo.findById(idCaTruc).orElseThrow(()->new AppException(ErrorCode.CATRUC_NOT_FOUND));
        caTrucMapper.update(caTruc,update);
        caTrucRepo.save(caTruc);
        return caTrucMapper.toResponse(caTruc);
    }

    @Override
    public void deleteCaTruc(String idCaTruc) {
        CaTrucEntity caTruc=caTrucRepo.findById(idCaTruc).orElseThrow(()->new AppException(ErrorCode.CATRUC_NOT_FOUND));
        caTrucRepo.deleteById(idCaTruc);
    }

    @Override
    public CaTrucEntity getByThoiGian(LocalDateTime thoigian) {

        LocalDate ngay = thoigian.toLocalDate();

        return caTrucRepo.findByNgaytruc(ngay)
                .orElseThrow(() ->
                        new AppException(ErrorCode.CATRUC_NOT_FOUND));
    }

    @Override
    public CanhBaoCaTrucResponse checkCaTruc() {
        boolean chiHuy = trucChiHuyService.canhBaoTrucChiHuy();

        boolean tacChien = trucBanTacChienService.canhBaoTacChien();

        return CanhBaoCaTrucResponse.builder()
                .canhBaoChiHuy(chiHuy)
                .canhBaoTacChien(tacChien)
                .messageChiHuy(
                        chiHuy
                                ? "Ca trực chỉ huy sắp hết hoặc chưa được phân công"
                                : ""
                )
                .messageTacChien(
                        tacChien
                                ? "Ca trực tác chiến sắp hết hoặc chưa được phân công"
                                : ""
                )
                .build();
    }


}
