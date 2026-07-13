package org.example.quanlysu5.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.quanlysu5.Dto.ApiResponse;
import org.example.quanlysu5.Dto.Request.ChucNangOverrideRequest;
import org.example.quanlysu5.Dto.Request.ResetPasswordRequest;
import org.example.quanlysu5.Dto.Request.TaiKhoanRequest;
import org.example.quanlysu5.Dto.Response.TaiKhoan.TaiKhoanResponse;
import org.example.quanlysu5.Service.TaiKhoanService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Tag(name = "Account API", description = "Quản lý thông tin tài khoản")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {
    TaiKhoanService taiKhoanService;
    @GetMapping()
    public ApiResponse<TaiKhoanResponse> getAccountByToken(){
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        Jwt jwt = (Jwt) authentication.getPrincipal();

        String scope = jwt.getClaim("sub");
        return ApiResponse.<TaiKhoanResponse>builder()
                .code(0)
                .Result(taiKhoanService.getTaiKhoanResponse(scope))
                .message("Successfully")
                .success(true)
                .build();
    }

    @PostMapping()
    public ApiResponse<TaiKhoanResponse> createTaiKhoan(@RequestBody TaiKhoanRequest taiKhoanRequest){
        return ApiResponse.<TaiKhoanResponse>builder()
                .code(0)
                .Result(taiKhoanService.createdTaiKhoanResponse(taiKhoanRequest))
                .message("Successfully")
                .success(true)
                .build();
    }
    @GetMapping("/getAll")
    public ApiResponse<List<TaiKhoanResponse>> getAllTaiKhoan(){
        return ApiResponse.<List<TaiKhoanResponse>>builder()
                .code(0)
                .Result(taiKhoanService.getAllTaiKhoan())
                .message("Successfully")
                .success(true)
                .build();
    }
    @PutMapping("/{id}")
    public ApiResponse<TaiKhoanResponse> updateTaiKhoan(
            @PathVariable String id,
            @RequestBody TaiKhoanRequest request) {
        return ApiResponse.<TaiKhoanResponse>builder()
                .code(0)
                .Result(taiKhoanService.updateTaiKhoan(id, request))
                .message("Successfully")
                .success(true)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteTaiKhoan(@PathVariable String id) {
        taiKhoanService.deleteTaiKhoan(id);
        return ApiResponse.<Void>builder()
                .code(0)
                .message("Successfully")
                .success(true)
                .build();
    }

    @PutMapping("/{id}/reset-password")
    public ApiResponse<Void> resetMatKhau(
            @PathVariable String id,
            @RequestBody ResetPasswordRequest request) {
        taiKhoanService.resetMatKhau(id, request.getMatKhauMoi());
        return ApiResponse.<Void>builder()
                .code(0)
                .message("Successfully")
                .success(true)
                .build();
    }

    @PutMapping("/{id}/lock")
    public ApiResponse<TaiKhoanResponse> lock(@PathVariable String id) {
        return ApiResponse.<TaiKhoanResponse>builder()
                .success(true)
                .code(1000)
                .message("Khóa tài khoản thành công")
                .Result(taiKhoanService.lockTaiKhoan(id))
                .build();
    }

    @PutMapping("/{id}/unlock")
    public ApiResponse<TaiKhoanResponse> unlock(@PathVariable String id) {
        return ApiResponse.<TaiKhoanResponse>builder()
                .success(true)
                .code(1000)
                .message("Mở khóa tài khoản thành công")
                .Result(taiKhoanService.unlockTaiKhoan(id))
                .build();
    }
    @PutMapping("/{idTaiKhoan}/chucnang")
    public ApiResponse<TaiKhoanResponse> updateOverride(
            @PathVariable String idTaiKhoan,
            @RequestBody ChucNangOverrideRequest request) {

        return ApiResponse.<TaiKhoanResponse>builder()
                .success(true)
                .code(1000)
                .message("Cập nhâp tài khoản thành công")
                .Result(
                        taiKhoanService.updateChucNangOverride(
                                idTaiKhoan,
                                request
                        )
                )
                .build();
    }
}
