package org.example.quanlysu5.Service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.quanlysu5.Config.MyWebSocketHandler;
import org.example.quanlysu5.Dto.Request.DonBaoCaoRequest;
import org.example.quanlysu5.Dto.Request.GhiChuRequest;
import org.example.quanlysu5.Dto.Request.NhatKyRequest;
import org.example.quanlysu5.Dto.Request.ThongBaoRequest;
import org.example.quanlysu5.Dto.Response.DonBaoCao.DonBaoCaoResponse;
import org.example.quanlysu5.Enum.*;
import org.example.quanlysu5.Exception.AppException;
import org.example.quanlysu5.Exception.ErrorCode;
import org.example.quanlysu5.Form.DonBaoCaoForm;
import org.example.quanlysu5.Hepler.SecurityUtils;
import org.example.quanlysu5.Mapper.DonBaoCaoMapper;
import org.example.quanlysu5.Module.CaTrucEntity;
import org.example.quanlysu5.Module.DonBaoCaoEntity;
import org.example.quanlysu5.Module.DonViEntity;
import org.example.quanlysu5.Repo.DonBaoCaoRepo;
import org.example.quanlysu5.Service.CaTrucService;
import org.example.quanlysu5.Service.DonBaoCaoService;
import org.example.quanlysu5.Service.DonViService;
import org.example.quanlysu5.Service.NhatKyService;
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
    MyWebSocketHandler myWebSocketHandler;
    NhatKyService nhatKyService;

    @Override
    public List<DonBaoCaoResponse> getAllDonBaoCaoToResponse() {
        return DonBaoCaoRepo.findAllByIsDeleted(false).stream()
                .map(DonBaoCaoMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public DonBaoCaoEntity getByIdDonBaoCao(String idNguoiTruc) {
        return DonBaoCaoRepo.findById(idNguoiTruc)
                .orElseThrow(() -> new AppException(ErrorCode.DONBAOCAO_NOT_FOUND));
    }

    @Override
    public DonBaoCaoResponse getByIdDonBaoCaoReponse(String idNguoiTruc) {
        return DonBaoCaoMapper.toResponse(getByIdDonBaoCao(idNguoiTruc));
    }

    @Override
    public DonBaoCaoResponse createDonBaoCaoQuanSoNgay(DonBaoCaoRequest request, String idNguoiTao) {

        DonBaoCaoEntity DonBaoCaoEntity = DonBaoCaoMapper.toEntity(request);
        CaTrucEntity caTruc = caTrucService.getByThoiGian(LocalDateTime.now());
        DonBaoCaoEntity.setCaTruc(caTruc);
        DonBaoCaoEntity.setCreatedAt(LocalDateTime.now());
        DonBaoCaoEntity.setIsDeleted(false);
        DonBaoCaoEntity.setNguoiTao(idNguoiTao);
        DonBaoCaoEntity.setThoiGianBaoCao(LocalDateTime.now());
        DonViEntity donViEntity = donViService.getById(request.getDonVi());
        DonBaoCaoEntity.setStatus(Status.Nháp);
        DonBaoCaoEntity.setDonVi(donViEntity);
        DonBaoCaoEntity = DonBaoCaoRepo.save(DonBaoCaoEntity);
        nhatKyService.createNhatKy(NhatKyRequest.builder()
                .doiTuong(DoiTuongNhatKy.BAO_CAO)
                .hanhDong(HanhDongNhatKy.CREATE)
                .doiTuongId(DonBaoCaoEntity.getIdDonBaoCao())
                .taiKhoan(SecurityUtils.getClaim("sub"))
                .trangThai(TrangThaiNhatKy.THANH_CONG)
                .moTa("Tài khoản " + SecurityUtils.getUsername() + "Tạo thông tin đơn báo cáo mới ")
                .build());
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
                log.warn(dv.getMaDonVi());
                DonBaoCaoRepo.findByDonVi_MaDonViAndThoiGianBaoCaoBetweenAndStatus(
                        dv.getMaDonVi(),
                        start,
                        end,
                        Status.Đã_Duyệt
                ).ifPresent(donBaoCaoEntities::add);
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
                DonBaoCaoRepo.findByDonVi_MaDonViAndThoiGianBaoCaoBetween(idDonVi, start, end)
                        .orElseThrow(() -> new AppException(ErrorCode.DONBAOCAO_NOT_FOUND));
        return DonBaoCaoMapper.toResponse(Reports);
    }

    @Override
    public List<DonBaoCaoResponse> getAllDonBaoCaoByDonViVaKhoangThoiGian(String idDonVi, LocalDate start, LocalDate end) {

        List<DonBaoCaoEntity> donBaoCaoEntityList = DonBaoCaoRepo.findAllByDonVi_MaDonViAndThoiGianBaoCaoBetween(idDonVi, start.atStartOfDay(), end.atTime(23, 59, 59));

        return donBaoCaoEntityList.stream().map(DonBaoCaoMapper::toResponse).collect(Collectors.toList());
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
        DonBaoCaoEntity donBaoCaoEntity = getByIdDonBaoCao(idDonBaoCao);
        donBaoCaoEntity.setStatus(Status.Đã_Duyệt);
        donBaoCaoEntity.setCapDuyet(donBaoCaoEntity.getDonVi().getMaDonVi());
        donBaoCaoEntity.setUpdatedAt(LocalDateTime.now());
        DonBaoCaoRepo.save(donBaoCaoEntity);
        nhatKyService.createNhatKy(NhatKyRequest.builder()
                .doiTuong(DoiTuongNhatKy.BAO_CAO)
                .hanhDong(HanhDongNhatKy.APPROVE)
                .doiTuongId(donBaoCaoEntity.getIdDonBaoCao())
                .taiKhoan(SecurityUtils.getClaim("sub"))
                .trangThai(TrangThaiNhatKy.THANH_CONG)
                .moTa("Tài khoản " + SecurityUtils.getUsername() + "Cập nhập thông tin đơn báo cáo thành "+Status.Đã_Duyệt)
                .build());
        String jsonMessage = String.format(
                "{\"title\":\"Báo cáo đã được duyệt\",\"message\":\"Đơn vị %s đã nộp và được duyệt báo cáo thành công\",\"type\":\"SUCCESS\"}",
                donBaoCaoEntity.getDonVi().getTenDonvi()
        );
        ThongBaoRequest thongBaoRequest = ThongBaoRequest.builder()
                .loaiThongBao("SUCCESS")
                .idMuctieu(donBaoCaoEntity.getDonVi().getMaDonVi())
                .noiDung("Đơn vị " + donBaoCaoEntity.getDonVi().getTenDonvi() + " đã nộp và được duyệt báo cáo thành công")
                .tieuDe("Báo cáo đã được duyệt")
                .build();
        myWebSocketHandler.sendToUser(donBaoCaoEntity.getNguoiTao(), jsonMessage, thongBaoRequest);
        String jsonMessageDonVi = String.format(
                "{\"title\":\"Đã duyệt báo cáo\",\"message\":\"Báo cáo của đơn vị %s đã được phê duyệt và hoàn tất xử lý\",\"type\":\"SUCCESS\"}",
                donBaoCaoEntity.getDonVi().getTenDonvi()
        );

        ThongBaoRequest thongBaoDonVi = ThongBaoRequest.builder()
                .loaiThongBao("SUCCESS")
                .idMuctieu(donBaoCaoEntity.getDonVi().getDonViCha().getMaDonVi())
                .tieuDe("Đã duyệt báo cáo")
                .noiDung(
                        "Báo cáo của đơn vị "
                                + donBaoCaoEntity.getDonVi().getTenDonvi()
                                + " đã được phê duyệt và hoàn tất xử lý"
                )
                .build();
        myWebSocketHandler.sendToDonVi(donBaoCaoEntity.getDonVi().getDonViCha().getMaDonVi(), jsonMessageDonVi, thongBaoDonVi);

        return DonBaoCaoMapper.toResponse(donBaoCaoEntity);
    }

    @Override
    public DonBaoCaoResponse updateStatusWaitingApprove(String idDonBaoCao) {
        DonBaoCaoEntity donBaoCaoEntity = getByIdDonBaoCao(idDonBaoCao);
        if ((donBaoCaoEntity.getDonVi().getCapDonVi().equals(CapDonVi.TIEU_DOAN) && !donBaoCaoEntity.getDonVi().getKyhieuDonvi().matches("dbộ"))
                || (donBaoCaoEntity.getDonVi().getCapDonVi().equals(CapDonVi.TRUNG_DOAN) && !donBaoCaoEntity.getDonVi().getKyhieuDonvi().matches("ebộ"))) {
            nhatKyService.createNhatKy(NhatKyRequest.builder()
                    .doiTuong(DoiTuongNhatKy.BAO_CAO)
                    .hanhDong(HanhDongNhatKy.UPDATE)
                    .doiTuongId(donBaoCaoEntity.getIdDonBaoCao())
                    .taiKhoan(SecurityUtils.getClaim("sub"))
                    .trangThai(TrangThaiNhatKy.THANH_CONG)
                    .moTa("Tài khoản " + SecurityUtils.getUsername() + "Cập nhập thông tin đơn báo cáo thành "+Status.Chờ_Duyệt)
                    .build());
            donBaoCaoEntity.setStatus(Status.Chờ_Duyệt);
        } else {
            nhatKyService.createNhatKy(NhatKyRequest.builder()
                    .doiTuong(DoiTuongNhatKy.BAO_CAO)
                    .hanhDong(HanhDongNhatKy.APPROVE)
                    .doiTuongId(donBaoCaoEntity.getIdDonBaoCao())
                    .taiKhoan(SecurityUtils.getClaim("sub"))
                    .trangThai(TrangThaiNhatKy.THANH_CONG)
                    .moTa("Tài khoản " + SecurityUtils.getUsername() + "Cập nhập thông tin đơn báo cáo thành "+Status.Đã_Duyệt)
                    .build());
            donBaoCaoEntity.setStatus(Status.Đã_Duyệt);
        }
        donBaoCaoEntity.setUpdatedAt(LocalDateTime.now());
        DonBaoCaoRepo.save(donBaoCaoEntity);
        String jsonMessage = String.format(
                "{\"title\":\"Báo cáo đã nộp\",\"message\":\"Đơn vị %s đã nộp báo cáo\",\"type\":\"SUCCESS\"}",
                donBaoCaoEntity.getDonVi().getTenDonvi()
        );
        ThongBaoRequest thongBaoRequest = ThongBaoRequest.builder()
                .loaiThongBao("SUCCESS")
                .idMuctieu(donBaoCaoEntity.getDonVi().getMaDonVi())
                .noiDung("Đơn vị " + donBaoCaoEntity.getDonVi().getTenDonvi() + " đã nộp báo cao")
                .tieuDe("Báo cáo đã nộp")
                .build();
        myWebSocketHandler.sendToDonVi(donBaoCaoEntity.getDonVi().getMaDonVi(), jsonMessage, thongBaoRequest);
        return DonBaoCaoMapper.toResponse(donBaoCaoEntity);
    }

    @Override
    public DonBaoCaoResponse updateStatusWaitingDraf(String idDonBaoCao) {
        DonBaoCaoEntity donBaoCaoEntity = getByIdDonBaoCao(idDonBaoCao);
        donBaoCaoEntity.setStatus(Status.Nháp);
        donBaoCaoEntity.setUpdatedAt(LocalDateTime.now());
        DonBaoCaoRepo.save(donBaoCaoEntity);
        nhatKyService.createNhatKy(NhatKyRequest.builder()
                .doiTuong(DoiTuongNhatKy.BAO_CAO)
                .hanhDong(HanhDongNhatKy.UPDATE)
                .doiTuongId(donBaoCaoEntity.getIdDonBaoCao())
                .taiKhoan(SecurityUtils.getClaim("sub"))
                .trangThai(TrangThaiNhatKy.THANH_CONG)
                .moTa("Tài khoản " + SecurityUtils.getUsername() + "Cập nhập thông tin đơn báo cáo thành "+Status.Nháp)
                .build());
        String jsonMessage = String.format(
                "{\"title\":\"Báo cáo đã bị thu hồi\",\"message\":\"Báo cáo của đơn vị %s đã được thu hồi và không còn chờ phê duyệt\",\"type\":\"WARNING\"}",
                donBaoCaoEntity.getDonVi().getTenDonvi()
        );
        ThongBaoRequest thongBaoRequest = ThongBaoRequest.builder()
                .loaiThongBao("WARNING")
                .idMuctieu(donBaoCaoEntity.getNguoiTao())
                .tieuDe("Báo cáo đã bị thu hồi")
                .noiDung(
                        "Báo cáo của đơn vị "
                                + donBaoCaoEntity.getDonVi().getTenDonvi()
                                + " đã được thu hồi. Vui lòng kiểm tra, chỉnh sửa và nộp lại khi cần."
                )
                .build();
        myWebSocketHandler.sendToDonVi(donBaoCaoEntity.getDonVi().getMaDonVi(), jsonMessage, thongBaoRequest);

        return DonBaoCaoMapper.toResponse(donBaoCaoEntity);
    }

    @Override
    public DonBaoCaoResponse updateStatusRefuse(String idDonBaoCao, GhiChuRequest ghiChuRequest) {
        DonBaoCaoEntity donBaoCaoEntity = getByIdDonBaoCao(idDonBaoCao);
        donBaoCaoEntity.setGhiChu(ghiChuRequest.getGhiChu());
        donBaoCaoEntity.setStatus(Status.Từ_Chối);
        donBaoCaoEntity.setUpdatedAt(LocalDateTime.now());
        nhatKyService.createNhatKy(NhatKyRequest.builder()
                .doiTuong(DoiTuongNhatKy.BAO_CAO)
                .hanhDong(HanhDongNhatKy.REJECT)
                .doiTuongId(donBaoCaoEntity.getIdDonBaoCao())
                .taiKhoan(SecurityUtils.getClaim("sub"))
                .trangThai(TrangThaiNhatKy.THANH_CONG)
                .moTa("Tài khoản " + SecurityUtils.getUsername() + "Cập nhập thông tin đơn báo cáo thành "+Status.Từ_Chối)
                .build());
        String jsonMessage = String.format(
                "{\"title\":\"Báo cáo bị từ chối\",\"message\":\"Báo cáo của đơn vị %s đã bị từ chối. Vui lòng kiểm tra ghi chú và chỉnh sửa.\",\"type\":\"WARNING\"}",
                donBaoCaoEntity.getDonVi().getTenDonvi()
        );
        ThongBaoRequest thongBaoRequest = ThongBaoRequest.builder()
                .loaiThongBao("WARNING")
                .idMuctieu(donBaoCaoEntity.getDonVi().getDonViCha().getMaDonVi())
                .tieuDe("Báo cáo bị từ chối")
                .noiDung(
                        "Báo cáo của đơn vị "
                                + donBaoCaoEntity.getDonVi().getTenDonvi()
                                + " đã bị từ chối. Vui lòng xem ghi chú để bổ sung hoặc chỉnh sửa."
                )
                .build();
        myWebSocketHandler.sendToUser(donBaoCaoEntity.getNguoiTao(), jsonMessage, thongBaoRequest);

        DonBaoCaoRepo.save(donBaoCaoEntity);
        return DonBaoCaoMapper.toResponse(donBaoCaoEntity);
    }

    @Override
    public DonBaoCaoResponse updateDonBaoCao(String idDonBaoCao, DonBaoCaoForm update) {
        DonBaoCaoEntity DonBaoCao = DonBaoCaoRepo.findById(idDonBaoCao)
                .orElseThrow(() -> {
                    nhatKyService.createNhatKy(NhatKyRequest.builder()
                            .doiTuong(DoiTuongNhatKy.BAO_CAO)
                            .hanhDong(HanhDongNhatKy.UPDATE)
                            .doiTuongId(idDonBaoCao)
                            .taiKhoan(SecurityUtils.getClaim("sub"))
                            .trangThai(TrangThaiNhatKy.THAT_BAI)
                            .moTa("Tài khoản " + SecurityUtils.getUsername() + "không cập nhập thông tin đơn báo cáo do "+ErrorCode.DONBAOCAO_NOT_FOUND)
                            .build());
                    return new AppException(ErrorCode.DONBAOCAO_NOT_FOUND);
                });
        if (!DonBaoCao.getStatus().equals(Status.Đã_Duyệt)) {
            DonBaoCao.setStatus(Status.Nháp);
            DonBaoCao.setGhiChu("");
            DonBaoCaoMapper.update(DonBaoCao, update);
            DonBaoCaoRepo.save(DonBaoCao);
            nhatKyService.createNhatKy(NhatKyRequest.builder()
                    .doiTuong(DoiTuongNhatKy.BAO_CAO)
                    .hanhDong(HanhDongNhatKy.APPROVE)
                    .doiTuongId(DonBaoCao.getIdDonBaoCao())
                    .taiKhoan(SecurityUtils.getClaim("sub"))
                    .trangThai(TrangThaiNhatKy.THANH_CONG)
                    .moTa("Tài khoản " + SecurityUtils.getUsername() + "Cập nhập thông tin đơn báo cáo thành "+Status.Nháp)
                    .build());

        }
        return DonBaoCaoMapper.toResponse(DonBaoCao);
    }

    @Override
    public void deleteDonBaoCao(String idDonBaoCao) {
        DonBaoCaoEntity DonBaoCao = DonBaoCaoRepo.findById(idDonBaoCao)
                .orElseThrow(() -> {
                    nhatKyService.createNhatKy(NhatKyRequest.builder()
                            .doiTuong(DoiTuongNhatKy.BAO_CAO)
                            .hanhDong(HanhDongNhatKy.UPDATE)
                            .doiTuongId(idDonBaoCao)
                            .taiKhoan(SecurityUtils.getClaim("sub"))
                            .trangThai(TrangThaiNhatKy.THAT_BAI)
                            .moTa("Tài khoản " + SecurityUtils.getUsername() + "không cập nhập thông tin đơn báo cáo do "+ErrorCode.DONBAOCAO_NOT_FOUND)
                            .build());
                    return new AppException(ErrorCode.DONBAOCAO_NOT_FOUND);
                });
        nhatKyService.createNhatKy(NhatKyRequest.builder()
                .doiTuong(DoiTuongNhatKy.BAO_CAO)
                .hanhDong(HanhDongNhatKy.DELETE)
                .doiTuongId(idDonBaoCao)
                .taiKhoan(SecurityUtils.getClaim("sub"))
                .trangThai(TrangThaiNhatKy.THANH_CONG)
                .moTa("Tài khoản " + SecurityUtils.getUsername() + "xóa thông tin đơn báo cáo")
                .build());
        DonBaoCaoRepo.deleteById(idDonBaoCao);
    }
}
