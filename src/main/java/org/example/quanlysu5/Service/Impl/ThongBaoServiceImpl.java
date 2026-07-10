package org.example.quanlysu5.Service.Impl;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.quanlysu5.Config.MyWebSocketHandler;
import org.example.quanlysu5.Dto.Request.ThongBaoRequest;
import org.example.quanlysu5.Dto.Response.ThongBaoResponse;
import org.example.quanlysu5.Enum.LoaiMuctieu;
import org.example.quanlysu5.Mapper.ThongBaoMapper;
import org.example.quanlysu5.Module.ThongBaoEntity;
import org.example.quanlysu5.Repo.TaiKhoanRepo;
import org.example.quanlysu5.Repo.ThongBaoRepo;
import org.example.quanlysu5.Service.DonViService;
import org.example.quanlysu5.Service.ThongBaoService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ThongBaoServiceImpl implements ThongBaoService {

    private final ThongBaoRepo thongBaoRepo;
    ThongBaoMapper thongBaoMapper;
    DonViService donViService;
    TaiKhoanRepo taiKhoanRepo;

    @Override
    public List<ThongBaoResponse> getAllThongBao(String idMucTieu) {
        return thongBaoRepo.findAllByDaDocAndIdMuctieu(false, idMucTieu)
                .stream().map(thongBaoMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<ThongBaoEntity> getAllThongBaoChuaDoc() {
        return thongBaoRepo.findAllByDaDoc(false);
    }

    @Override
    public ThongBaoResponse createThongBaoDonVi(ThongBaoRequest request) {
        ThongBaoEntity thongBaoEntity = thongBaoMapper.toEntity(request);
        donViService.getById(request.getIdMuctieu());
        thongBaoEntity.setLoaiMuctieu(LoaiMuctieu.DONVI);
        thongBaoEntity.setCreatedAt(LocalDateTime.now());
        thongBaoEntity.setDaDoc(false);
        thongBaoEntity.setIsDeleted(false);
        thongBaoRepo.save(thongBaoEntity);
        return thongBaoMapper.toResponse(thongBaoEntity);
    }

    @Override
    public ThongBaoResponse createThongBaoTaiKhoan(ThongBaoRequest request) {
        ThongBaoEntity thongBaoEntity = thongBaoMapper.toEntity(request);
        taiKhoanRepo.findByIdTaiKhoanAndIsDeletedFalse(request.getIdMuctieu());
        thongBaoEntity.setLoaiMuctieu(LoaiMuctieu.USER);
        thongBaoEntity.setCreatedAt(LocalDateTime.now());
        thongBaoEntity.setDaDoc(false);
        thongBaoEntity.setIsDeleted(false);
        thongBaoRepo.save(thongBaoEntity);
        return thongBaoMapper.toResponse(thongBaoEntity);
    }

    @Override
    @Transactional
    public void deleteThongBaoDaDocQuaHan() {
        LocalDateTime expiredTime =
                LocalDateTime.now().minusDays(1);

        thongBaoRepo.deleteAllByDaDocTrueAndCreatedAtBefore(
                expiredTime
        );
    }

    @Override
    public void thongBaoDaDoc(String idMucTieu) {
        log.warn("idMuctieu "+idMucTieu);
        thongBaoRepo.deleteAllByIdMuctieu(idMucTieu);
    }

    @Override
    @Lazy
    public void thongBaoWebsocket(String message, ThongBaoRequest thongBaoRequest,String idMuctieu) {
    }
}