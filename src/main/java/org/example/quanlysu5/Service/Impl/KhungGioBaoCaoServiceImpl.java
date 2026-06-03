package org.example.quanlysu5.Service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import lombok.extern.slf4j.Slf4j;
import org.example.quanlysu5.Dto.Request.KhungGioBaoCaoRequest;
import org.example.quanlysu5.Dto.Response.KhungGioBaoCaoResponse;
import org.example.quanlysu5.Enum.LoaiBaoBan;
import org.example.quanlysu5.Exception.AppException;
import org.example.quanlysu5.Exception.ErrorCode;
import org.example.quanlysu5.Form.KhungGioBaoCaoForm;
import org.example.quanlysu5.Mapper.KhungGioBaoCaoMapper;
import org.example.quanlysu5.Module.KhungGioBaoCaoEntity;
import org.example.quanlysu5.Repo.KhungGioBaoCaoRepo;
import org.example.quanlysu5.Service.KhungGioBaoCaoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class KhungGioBaoCaoServiceImpl implements KhungGioBaoCaoService {

    KhungGioBaoCaoRepo khungGioBaoCaoRepo;
    KhungGioBaoCaoMapper khungGioBaoCaoMapper;

    @Override
    public List<KhungGioBaoCaoResponse> getAllKhungGioBaoCao() {

        return khungGioBaoCaoRepo.findAllByIsDeleted(false)
                .stream()
                .map(khungGioBaoCaoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public KhungGioBaoCaoEntity getById(String idKhunggio) {

        return khungGioBaoCaoRepo.findById(idKhunggio)
                .orElseThrow(() ->
                        new AppException(ErrorCode.KHUNGGIOBAOCAO_NOT_FOUND));
    }

    @Override
    public KhungGioBaoCaoResponse getByIdResponse(String idKhunggio) {

        return khungGioBaoCaoMapper.toResponse(
                getById(idKhunggio)
        );
    }

    @Override
    public KhungGioBaoCaoResponse createKhungGio(
            KhungGioBaoCaoRequest request) {

        KhungGioBaoCaoEntity entity =
                khungGioBaoCaoMapper.toEntity(request);

        entity.setIsDeleted(false);

        return khungGioBaoCaoMapper.toResponse(
                khungGioBaoCaoRepo.save(entity)
        );
    }

    @Override
    public KhungGioBaoCaoResponse createKhungGioBanChiHuy(KhungGioBaoCaoRequest request) {
        KhungGioBaoCaoEntity entity =
                khungGioBaoCaoMapper.toEntity(request);
        entity.setLoaiBaoBan(LoaiBaoBan.CATRUC_CHIHUY);
        entity.setIsDeleted(false);

        return khungGioBaoCaoMapper.toResponse(
                khungGioBaoCaoRepo.save(entity)
        );
    }

    @Override
    public KhungGioBaoCaoResponse createKhungGioBanTacChien(KhungGioBaoCaoRequest request) {
        KhungGioBaoCaoEntity entity =
                khungGioBaoCaoMapper.toEntity(request);
        entity.setLoaiBaoBan(LoaiBaoBan.CATRUC_BANTACCHIEN);
        entity.setIsDeleted(false);

        return khungGioBaoCaoMapper.toResponse(
                khungGioBaoCaoRepo.save(entity)
        );    }

    @Override
    public KhungGioBaoCaoResponse updateKhungGio(
            String idKhunggio,
            KhungGioBaoCaoForm update) {

        KhungGioBaoCaoEntity entity =
                khungGioBaoCaoRepo.findById(idKhunggio)
                        .orElseThrow(() ->
                                new AppException(ErrorCode.KHUNGGIOBAOCAO_NOT_FOUND));

        khungGioBaoCaoMapper.update(entity, update);

        khungGioBaoCaoRepo.save(entity);

        return khungGioBaoCaoMapper.toResponse(entity);
    }

    @Override
    public void deleteKhungGio(String idKhunggio) {

        KhungGioBaoCaoEntity entity =
                khungGioBaoCaoRepo.findById(idKhunggio)
                        .orElseThrow(() ->
                                new AppException(ErrorCode.KHUNGGIOBAOCAO_NOT_FOUND));

        entity.setIsDeleted(true);

        khungGioBaoCaoRepo.save(entity);
    }
}