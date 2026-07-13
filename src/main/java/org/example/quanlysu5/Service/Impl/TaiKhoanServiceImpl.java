package org.example.quanlysu5.Service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.quanlysu5.Config.MyWebSocketHandler;
import org.example.quanlysu5.Dto.Request.ChucNangOverrideRequest;
import org.example.quanlysu5.Dto.Request.NhatKyRequest;
import org.example.quanlysu5.Dto.Request.TaiKhoanRequest;
import org.example.quanlysu5.Dto.Request.ThongBaoRequest;
import org.example.quanlysu5.Dto.Response.TaiKhoan.TaiKhoanResponse;
import org.example.quanlysu5.Enum.DoiTuongNhatKy;
import org.example.quanlysu5.Enum.HanhDongNhatKy;
import org.example.quanlysu5.Enum.TrangThaiNhatKy;
import org.example.quanlysu5.Exception.AppException;
import org.example.quanlysu5.Exception.ErrorCode;
import org.example.quanlysu5.Hepler.SecurityUtils;
import org.example.quanlysu5.Mapper.TaiKhoanMapper;
import org.example.quanlysu5.Module.DonViEntity;
import org.example.quanlysu5.Module.TaikhoanEntity;
import org.example.quanlysu5.Module.VaiTroEntity;
import org.example.quanlysu5.Repo.DonViRepo;
import org.example.quanlysu5.Repo.TaiKhoanRepo;
import org.example.quanlysu5.Service.DonViService;
import org.example.quanlysu5.Service.NhatKyService;
import org.example.quanlysu5.Service.TaiKhoanService;
import org.example.quanlysu5.Service.VaiTroService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaiKhoanServiceImpl implements TaiKhoanService {
    private final TaiKhoanRepo taiKhoanRepo;
    TaiKhoanMapper taiKhoanMapper;
    DonViService donViService;
    VaiTroService vaiTroService;
    NhatKyService nhatKyService;
    PasswordEncoder passwordEncoder;
    MyWebSocketHandler myWebSocketHandler;

    @Override
    public TaikhoanEntity getTaiKhoanById(String id) {
        log.info("Id user: " + id);
        return taiKhoanRepo.findByIdTaiKhoanAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
    }

    @Override
    public TaiKhoanResponse getTaiKhoanResponse(String id) {
        TaikhoanEntity account = getTaiKhoanById(id);

        return toResponseWithChucNang(account);
    }

    @Override
    public TaiKhoanResponse createdTaiKhoanResponse(TaiKhoanRequest taiKhoanRequest) {
        TaikhoanEntity taikhoanEntity = taiKhoanMapper.toEntity(taiKhoanRequest);
        String Mk = passwordEncoder.encode(taiKhoanRequest.getMatkhau());
        taiKhoanRepo.findByTenDangNhapAndMatKhau(taiKhoanRequest.getTenDangNhap(), Mk)
                .ifPresent(account -> {
                    nhatKyService.createNhatKy(NhatKyRequest.builder()
                            .doiTuong(DoiTuongNhatKy.TAI_KHOAN)
                            .hanhDong(HanhDongNhatKy.CREATE)
                            .taiKhoan(account.getIdTaiKhoan())
                            .trangThai(TrangThaiNhatKy.THAT_BAI)
                            .moTa("Tài khoản " + SecurityUtils.getUsername() + " Tạo tài khoản thất bại do " + ErrorCode.ACCOUNT_IS_EXIST.getMessage())
                            .build());
                    throw new AppException(ErrorCode.ACCOUNT_IS_EXIST);
                });
        if (Boolean.TRUE.equals(taikhoanEntity.getKhoa())) {
            nhatKyService.createNhatKy(NhatKyRequest.builder()
                    .doiTuong(DoiTuongNhatKy.TAI_KHOAN)
                    .hanhDong(HanhDongNhatKy.CREATE)
                    .taiKhoan(taikhoanEntity.getIdTaiKhoan())
                    .trangThai(TrangThaiNhatKy.THAT_BAI)
                    .moTa("Tài khoản " + SecurityUtils.getUsername() + " Tạo tài khoản thất bại do " + ErrorCode.ACCOUNT_IS_EXIST.getMessage())
                    .build());
            throw new AppException(ErrorCode.ACCOUNT_IS_EXIST);
        }
        DonViEntity donViEntity = donViService.getById(taiKhoanRequest.getDonVi());
        VaiTroEntity vaiTroEntity = vaiTroService.getRoleById(taiKhoanRequest.getVaiTro());
        taikhoanEntity.setMatKhau(Mk);
        taikhoanEntity.setDonVi(donViEntity);
        taikhoanEntity.setVaiTro(vaiTroEntity);
        taikhoanEntity.setIsDeleted(false);
        taikhoanEntity.setCreatedAt(LocalDateTime.now());
        TaikhoanEntity saved = taiKhoanRepo.save(taikhoanEntity);

        return toResponseWithChucNang(saved);
    }

    @Override
    public List<TaiKhoanResponse> getAllTaiKhoan() {
        return taiKhoanRepo.findByIsDeletedFalse()
                .stream()
                .map(this::toResponseWithChucNang)
                .toList();
    }

    @Override
    public TaiKhoanResponse updateTaiKhoan(String idTaiKhoan, TaiKhoanRequest request) {
        TaikhoanEntity taiKhoan = taiKhoanRepo
                .findByIdTaiKhoanAndIsDeletedFalse(idTaiKhoan)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));


        if (!request.getTenTaiKhoan().isEmpty()) {
            taiKhoan.setTenTaiKhoan(request.getTenTaiKhoan());
        }
        VaiTroEntity vaiTro = vaiTroService.getRoleById(request.getVaiTro());
        taiKhoan.setVaiTro(vaiTro);
        DonViEntity donVi = donViService.getById(request.getDonVi());
        taiKhoan.setDonVi(donVi);
        nhatKyService.createNhatKy(NhatKyRequest.builder()
                .doiTuong(DoiTuongNhatKy.TAI_KHOAN)
                .hanhDong(HanhDongNhatKy.UPDATE)
                .doiTuongId(idTaiKhoan)
                .taiKhoan(SecurityUtils.getClaim("sub"))
                .trangThai(TrangThaiNhatKy.THANH_CONG)
                .moTa("Tài khoản " + SecurityUtils.getUsername() + " Cập nhập tài khoản " + request.getTenTaiKhoan())
                .build());

        TaikhoanEntity saved = taiKhoanRepo.save(taiKhoan);

        return toResponseWithChucNang(saved);
    }

    @Override
    public void deleteTaiKhoan(String idTaiKhoan) {
        TaikhoanEntity taiKhoan = taiKhoanRepo
                .findByIdTaiKhoanAndIsDeletedFalse(idTaiKhoan)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        nhatKyService.createNhatKy(NhatKyRequest.builder()
                .doiTuong(DoiTuongNhatKy.TAI_KHOAN)
                .hanhDong(HanhDongNhatKy.DELETE)
                .doiTuongId(idTaiKhoan)
                .taiKhoan(SecurityUtils.getClaim("sub"))
                .trangThai(TrangThaiNhatKy.THANH_CONG)
                .moTa("Tài khoản " + SecurityUtils.getUsername() + " Xóa tài khoản " + taiKhoan.getTenTaiKhoan())
                .build());
        taiKhoan.setIsDeleted(true);
        taiKhoan.setDeletedAt(LocalDateTime.now());
        taiKhoanRepo.save(taiKhoan);
    }

    @Override
    public void resetMatKhau(String idTaiKhoan, String matKhauMoi) {
        TaikhoanEntity taiKhoan = taiKhoanRepo
                .findByIdTaiKhoanAndIsDeletedFalse(idTaiKhoan)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        nhatKyService.createNhatKy(NhatKyRequest.builder()
                .doiTuong(DoiTuongNhatKy.TAI_KHOAN)
                .hanhDong(HanhDongNhatKy.UPDATE)
                .doiTuongId(idTaiKhoan)
                .taiKhoan(SecurityUtils.getClaim("sub"))
                .trangThai(TrangThaiNhatKy.THANH_CONG)
                .moTa("Tài khoản " + SecurityUtils.getUsername() + " Cập nhập mật khẩu tài khoản " + taiKhoan.getTenTaiKhoan())
                .build());
        taiKhoan.setMatKhau(passwordEncoder.encode(matKhauMoi));
        taiKhoanRepo.save(taiKhoan);
    }

    @Override
    public TaiKhoanResponse lockTaiKhoan(String id) {
        TaikhoanEntity taiKhoan = getTaiKhoanById(id);
        taiKhoan.setKhoa(true);
        nhatKyService.createNhatKy(NhatKyRequest.builder()
                .doiTuong(DoiTuongNhatKy.TAI_KHOAN)
                .hanhDong(HanhDongNhatKy.LOCK)
                .doiTuongId(id)
                .taiKhoan(SecurityUtils.getClaim("sub"))
                .trangThai(TrangThaiNhatKy.THANH_CONG)
                .moTa("Tài khoản " + SecurityUtils.getUsername() + " Khóa tài khoản " + taiKhoan.getTenTaiKhoan())
                .build());
        String jsonMessage = """
                {
                    "type":"FORCE_LOGOUT",
                    "title":"Tài khoản bị khóa",
                    "message":"Tài khoản của bạn đã bị khóa. Vui lòng liên hệ quản trị viên để biết thêm thông tin."
                }
                """;
        ThongBaoRequest thongBaoRequest = ThongBaoRequest.builder()
                .loaiThongBao("FORCE_LOGOUT")
                .idMuctieu(taiKhoan.getIdTaiKhoan())
                .tieuDe("Tài khoản bị khóa")
                .noiDung(
                        "Tài khoản của bạn đã bị khóa. Vui lòng liên hệ quản trị viên để biết thêm thông tin."
                )
                .build();
        myWebSocketHandler.sendToUser(taiKhoan.getIdTaiKhoan(), jsonMessage, thongBaoRequest);
        TaikhoanEntity saved = taiKhoanRepo.save(taiKhoan);

        return toResponseWithChucNang(saved);
    }

    @Override
    public TaiKhoanResponse unlockTaiKhoan(String id) {
        TaikhoanEntity taiKhoan = getTaiKhoanById(id);
        nhatKyService.createNhatKy(NhatKyRequest.builder()
                .doiTuong(DoiTuongNhatKy.TAI_KHOAN)
                .hanhDong(HanhDongNhatKy.UNLOCK)
                .doiTuongId(id)
                .taiKhoan(SecurityUtils.getClaim("sub"))
                .trangThai(TrangThaiNhatKy.THANH_CONG)
                .moTa("Tài khoản " + SecurityUtils.getUsername() + " Mở khóa tài khoản " + taiKhoan.getTenTaiKhoan())
                .build());
        taiKhoan.setKhoa(false);
        TaikhoanEntity saved = taiKhoanRepo.save(taiKhoan);

        return toResponseWithChucNang(saved);
    }

    @Override
    public Set<String> getChucNangHieuLuc(TaikhoanEntity account) {
        Set<String> ketQua = new HashSet<>();
        if (account.getVaiTro() != null && account.getVaiTro().getTenChucnang() != null) {
            ketQua.addAll(account.getVaiTro().getTenChucnang());
        }
        if (account.getChucNangThem() != null) {
            ketQua.addAll(account.getChucNangThem());
        }
        if (account.getChucNangBo() != null) {
            ketQua.removeAll(account.getChucNangBo());
        }
        return ketQua;
    }

    // Map entity -> response và gắn sẵn quyền hiệu lực
    private TaiKhoanResponse toResponseWithChucNang(TaikhoanEntity account) {
        TaiKhoanResponse response = taiKhoanMapper.toResponse(account);
        response.setTenChucnang(getChucNangHieuLuc(account));
        return response;
    }

    @Override
    public TaiKhoanResponse updateChucNangOverride(String idTaiKhoan, ChucNangOverrideRequest request) {
        TaikhoanEntity taiKhoan = taiKhoanRepo
                .findByIdTaiKhoanAndIsDeletedFalse(idTaiKhoan)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        taiKhoan.setChucNangThem(request.getChucNangThem() != null
                ? new HashSet<>(request.getChucNangThem()) : new HashSet<>());
        taiKhoan.setChucNangBo(request.getChucNangBo() != null
                ? new HashSet<>(request.getChucNangBo()) : new HashSet<>());

        nhatKyService.createNhatKy(NhatKyRequest.builder()
                .doiTuong(DoiTuongNhatKy.TAI_KHOAN)
                .hanhDong(HanhDongNhatKy.UPDATE_FUNCTION)
                .doiTuongId(idTaiKhoan)
                .taiKhoan(SecurityUtils.getClaim("sub"))
                .trangThai(TrangThaiNhatKy.THANH_CONG)
                .moTa("Tài khoản " + SecurityUtils.getUsername()
                        + " Cập nhật quyền override cho tài khoản " + taiKhoan.getTenTaiKhoan())
                .build());
        String jsonMessage = """
                {
                    "type":"UPDATE_FUNCTION",
                    "title":"Quyền truy cập đã được cập nhật",
                    "message":"Quyền truy cập của tài khoản đã được quản trị viên cập nhật. Vui lòng đăng nhập lại để áp dụng thay đổi."
                }
                """;
        ThongBaoRequest thongBaoRequest = ThongBaoRequest.builder()
                .loaiThongBao("UPDATE_FUNCTION")
                .idMuctieu(idTaiKhoan)
                .tieuDe("Quyền truy cập đã được cập nhật")
                .noiDung("Quyền truy cập của bạn đã được quản trị viên cập nhật. Vui lòng đăng nhập lại để áp dụng thay đổi.")
                .build();
        myWebSocketHandler.sendToUser(idTaiKhoan, jsonMessage, thongBaoRequest);

        return toResponseWithChucNang(taiKhoanRepo.save(taiKhoan));
    }
}
