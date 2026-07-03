package org.example.quanlysu5.Service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.quanlysu5.Config.MyWebSocketHandler;
import org.example.quanlysu5.Dto.Request.CtDangCtRequest;
import org.example.quanlysu5.Dto.Request.GhiChuRequest;
import org.example.quanlysu5.Dto.Request.ThongBaoRequest;
import org.example.quanlysu5.Dto.Response.CtDangCt.CtDangCtResponse;
import org.example.quanlysu5.Enum.CapDonVi;
import org.example.quanlysu5.Enum.Status;
import org.example.quanlysu5.Exception.AppException;
import org.example.quanlysu5.Exception.ErrorCode;
import org.example.quanlysu5.Form.CtDangCtForm;
import org.example.quanlysu5.Mapper.CtDangCtMapper;
import org.example.quanlysu5.Module.CtDangCtEntity;
import org.example.quanlysu5.Module.DonBaoCaoEntity;
import org.example.quanlysu5.Module.DonViEntity;
import org.example.quanlysu5.Repo.CtDangCtRepo;
import org.example.quanlysu5.Service.CtDangCtService;
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
public class CtDangCtServiceImpl implements CtDangCtService {
    CtDangCtMapper ctDangCtMapper;
    CtDangCtRepo ctDangCtRepo;
    DonViService donViService;
    MyWebSocketHandler myWebSocketHandler;
    @Override
    public List<CtDangCtResponse> getAllByDonViCha(String idDonViCha) {
        DonViEntity donViCha=donViService.getById(idDonViCha);
        List<CtDangCtEntity> ctEntities=new ArrayList<CtDangCtEntity>();
        if(donViCha.getDonViCon()!=null){
            donViCha.getDonViCon().forEach(donViCon->{
                CtDangCtEntity ctDangCtEntity=ctDangCtRepo.findByDonVi_MaDonVi(donViCon.getMaDonVi())
                        .orElseThrow(()->new AppException(ErrorCode.CTDANGCT_NOT_FOUND));
                ctEntities.add(ctDangCtEntity);
            });
        }
        
        return ctEntities.stream()
                .map(ctDangCtMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CtDangCtResponse getAllByIdDonVi(String idDonVi) {
        CtDangCtEntity ctDangCtEntity=ctDangCtRepo.findByDonVi_MaDonVi(idDonVi)
                .orElseThrow(()->new AppException(ErrorCode.CTDANGCT_NOT_FOUND));
        return ctDangCtMapper.toResponse(ctDangCtEntity);
    }

    @Override
    public CtDangCtResponse create(CtDangCtRequest request,String idNguoiTao) {
        CtDangCtEntity ctDangCtEntity=ctDangCtMapper.toEntity(request);
        DonViEntity donViEntity=donViService.getById(request.getDonVi());
        ctDangCtEntity.setCreatedAt(LocalDateTime.now());
        ctDangCtEntity.setDonVi(donViEntity);
        ctDangCtEntity.setNguoiTao(idNguoiTao);
        ctDangCtEntity.setStatus(Status.Nháp);
        ctDangCtEntity.setIsDeleted(false);
        ctDangCtRepo.save(ctDangCtEntity);
        return ctDangCtMapper.toResponse(ctDangCtEntity);
    }

    @Override
    public CtDangCtResponse update(CtDangCtForm update, String idCtDangCt) {
        CtDangCtEntity ctDangCtEntity=getById(idCtDangCt);
        ctDangCtMapper.update(ctDangCtEntity,update);
        ctDangCtEntity.setStatus(Status.Nháp);
        ctDangCtRepo.save(ctDangCtEntity);
        return ctDangCtMapper.toResponse(ctDangCtEntity);
    }

    @Override
    public List<CtDangCtResponse> getAllCtDangCtDonViConByDonVi(String idDonVi, LocalDate ngayLoc) {

        DonViEntity donViEntity = donViService.getById(idDonVi);

        List<CtDangCtEntity> ctDangCtEntities = new ArrayList<>();

        LocalDateTime start = ngayLoc.atStartOfDay();
        LocalDateTime end = ngayLoc.atTime(23, 59, 59);

        List<DonViEntity> donviCon = donViEntity.getDonViCon();

        if (!donviCon.isEmpty()) {
            donviCon.forEach(dv -> {
                log.warn(dv.getMaDonVi());
                ctDangCtRepo.findByDonVi_MaDonViAndCreatedAtBetweenAndStatus(
                        dv.getMaDonVi(),
                        start,
                        end,
                        Status.Đã_Duyệt
                ).ifPresent(ctDangCtEntities::add);
            });
        }

        return ctDangCtEntities.stream()
                .map(ctDangCtMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CtDangCtResponse getAllCtDangCtByDonVi(String idDonVi, LocalDate ngayLoc) {
        LocalDateTime start = ngayLoc.atStartOfDay();
        LocalDateTime end = ngayLoc.atTime(23, 59, 59);

        CtDangCtEntity Reports =
                ctDangCtRepo.findByDonVi_MaDonViAndCreatedAtBetween(idDonVi, start, end)
                        .orElseThrow(() -> new AppException(ErrorCode.CTDANGCT_NOT_FOUND));
        return ctDangCtMapper.toResponse(Reports);    }

    @Override
    public List<CtDangCtResponse> getAllCtDangCtByDonViVaKhoangThoiGian(String idDonVi, LocalDate start, LocalDate end) {
        List<CtDangCtEntity> ctDangCtEntity = ctDangCtRepo.findAllByDonVi_MaDonViAndCreatedAtBetween(idDonVi, start.atStartOfDay(), end.atTime(23, 59, 59));

        return ctDangCtEntity.stream().map(ctDangCtMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public CtDangCtResponse getAllCtDangCtoByDonViApprove(String idDonVi, LocalDate ngayLoc) {
        LocalDateTime start = ngayLoc.atStartOfDay();
        LocalDateTime end = ngayLoc.atTime(23, 59, 59);

        CtDangCtEntity Reports =
                ctDangCtRepo.findByDonVi_MaDonViAndCreatedAtBetweenAndStatus(idDonVi, start, end,Status.Đã_Duyệt)
                        .orElseThrow(() -> new AppException(ErrorCode.CTDANGCT_NOT_FOUND));
        return ctDangCtMapper.toResponse(Reports);
    }

    @Override
    public CtDangCtEntity getById(String id) {
        return ctDangCtRepo.findById(id)
                .orElseThrow(()->new AppException(ErrorCode.CTDANGCT_NOT_FOUND));
    }

    @Override
    public CtDangCtResponse getByIdResponse(String id) {
        return ctDangCtMapper.toResponse(getById(id));
    }

    @Override
    public CtDangCtResponse updateStatusApprove(String id) {
        CtDangCtEntity ctDangCtEntity = getById(id);
        ctDangCtEntity.setStatus(Status.Đã_Duyệt);
        ctDangCtEntity.setUpdatedAt(LocalDateTime.now());
        ctDangCtRepo.save(ctDangCtEntity);
        String jsonMessageDonVi = String.format(
                "{\"title\":\"Đã duyệt báo cáo\",\"message\":\"Báo cáo Công tác Đảng, Chính trị của đơn vị %s đã được phê duyệt và hoàn tất xử lý\",\"type\":\"SUCCESS\"}",
                ctDangCtEntity.getDonVi().getTenDonvi()
        );

        ThongBaoRequest thongBaoDonVi = ThongBaoRequest.builder()
                .loaiThongBao("SUCCESS")
                .idMuctieu(ctDangCtEntity.getDonVi().getDonViCha().getMaDonVi())
                .tieuDe("Đã duyệt báo cáo")
                .noiDung(
                        "Báo cáo Công tác Đảng, Chính trị của đơn vị "
                                + ctDangCtEntity.getDonVi().getTenDonvi()
                                + " đã được phê duyệt và hoàn tất xử lý"
                )
                .build();
        myWebSocketHandler.sendToDonVi(ctDangCtEntity.getDonVi().getDonViCha().getMaDonVi(), jsonMessageDonVi, thongBaoDonVi);

        return ctDangCtMapper.toResponse(ctDangCtEntity);
    }

    @Override
    public CtDangCtResponse updateStatusWaitingApprove(String id) {
        CtDangCtEntity ctDangCtEntity=getById(id);
        if ((ctDangCtEntity.getDonVi().getCapDonVi().equals(CapDonVi.TIEU_DOAN) && !ctDangCtEntity.getDonVi().getKyhieuDonvi().matches("dbộ"))
                || (ctDangCtEntity.getDonVi().getCapDonVi().equals(CapDonVi.TRUNG_DOAN) && !ctDangCtEntity.getDonVi().getKyhieuDonvi().matches("ebộ"))) {
            ctDangCtEntity.setStatus(Status.Chờ_Duyệt);
        } else {
            ctDangCtEntity.setStatus(Status.Đã_Duyệt);
        }
        ctDangCtEntity.setUpdatedAt(LocalDateTime.now());
        ctDangCtRepo.save(ctDangCtEntity);
        String jsonMessage = String.format(
                "{\"title\":\"Báo cáo đã nộp\",\"message\":\"Đơn vị %s đã nộp báo cáo Công tác đảng, Chính trị\",\"type\":\"SUCCESS\"}",
                ctDangCtEntity.getDonVi().getTenDonvi()
        );
        ThongBaoRequest thongBaoRequest = ThongBaoRequest.builder()
                .loaiThongBao("SUCCESS")
                .idMuctieu(ctDangCtEntity.getDonVi().getMaDonVi())
                .noiDung("Đơn vị " + ctDangCtEntity.getDonVi().getTenDonvi() + " đã nộp báo cao Công tác đảng, Chính trị")
                .tieuDe("Báo cáo đã nộp")
                .build();
        myWebSocketHandler.sendToDonVi(ctDangCtEntity.getDonVi().getMaDonVi(), jsonMessage, thongBaoRequest);
        return ctDangCtMapper.toResponse(ctDangCtEntity);
    }

    @Override
    public CtDangCtResponse updateStatusWaitingDraf(String id) {
        CtDangCtEntity ctDangCtEntity = getById(id);
        ctDangCtEntity.setStatus(Status.Nháp);
        ctDangCtEntity.setUpdatedAt(LocalDateTime.now());
        ctDangCtRepo.save(ctDangCtEntity);
        String jsonMessage = String.format(
                "{\"title\":\"Báo cáo Công tác Đảng, Chính trị đã bị thu hồi\",\"message\":\"Báo cáo của đơn vị %s đã được thu hồi và không còn chờ phê duyệt\",\"type\":\"WARNING\"}",
                ctDangCtEntity.getDonVi().getTenDonvi()
        );
        ThongBaoRequest thongBaoRequest = ThongBaoRequest.builder()
                .loaiThongBao("WARNING")
                .idMuctieu(ctDangCtEntity.getDonVi().getMaDonVi())
                .tieuDe("Báo cáo Công tác Đảng, Chính trị  đã bị thu hồi")
                .noiDung(
                        "Báo cáo của đơn vị "
                                + ctDangCtEntity.getDonVi().getTenDonvi()
                                + " đã được thu hồi. Vui lòng kiểm tra, chỉnh sửa và nộp lại khi cần."
                )
                .build();
        myWebSocketHandler.sendToDonVi(ctDangCtEntity.getDonVi().getMaDonVi(), jsonMessage, thongBaoRequest);

        return ctDangCtMapper.toResponse(ctDangCtEntity);
    }

    @Override
    public CtDangCtResponse updateStatusRefuse(String id, GhiChuRequest ghichu) {
        CtDangCtEntity ctDangCtEntity = getById(id);
        ctDangCtEntity.setGhiChu(ghichu.getGhiChu());
        ctDangCtEntity.setStatus(Status.Từ_Chối);
        ctDangCtEntity.setUpdatedAt(LocalDateTime.now());
        String jsonMessage = String.format(
                "{\"title\":\"Báo cáo bị từ chối\",\"message\":\"Báo cáo của đơn vị %s đã bị từ chối. Vui lòng kiểm tra ghi chú và chỉnh sửa.\",\"type\":\"WARNING\"}",
                ctDangCtEntity.getDonVi().getTenDonvi()
        );
        ThongBaoRequest thongBaoRequest = ThongBaoRequest.builder()
                .loaiThongBao("WARNING")
                .idMuctieu(ctDangCtEntity.getDonVi().getDonViCha().getMaDonVi())
                .tieuDe("Báo cáo bị từ chối")
                .noiDung(
                        "Báo cáo của đơn vị "
                                + ctDangCtEntity.getDonVi().getTenDonvi()
                                + " đã bị từ chối. Vui lòng xem ghi chú để bổ sung hoặc chỉnh sửa."
                )
                .build();
        myWebSocketHandler.sendToUser(ctDangCtEntity.getNguoiTao(), jsonMessage, thongBaoRequest);

        ctDangCtRepo.save(ctDangCtEntity);
        return ctDangCtMapper.toResponse(ctDangCtEntity);
    }

}
