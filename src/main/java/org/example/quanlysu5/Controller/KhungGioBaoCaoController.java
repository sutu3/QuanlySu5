package org.example.quanlysu5.Controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.quanlysu5.Dto.ApiResponse;
import org.example.quanlysu5.Dto.Request.KhungGioBaoCaoRequest;
import org.example.quanlysu5.Dto.Response.KhungGioBaoCaoResponse;
import org.example.quanlysu5.Form.KhungGioBaoCaoForm;
import org.example.quanlysu5.Service.KhungGioBaoCaoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/khung-gio-bao-cao")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KhungGioBaoCaoController {

    KhungGioBaoCaoService khungGioBaoCaoService;

    @GetMapping
    public ApiResponse<List<KhungGioBaoCaoResponse>> getAll() {

        return ApiResponse.<List<KhungGioBaoCaoResponse>>builder()
                .success(true)
                .message("Lấy danh sách khung giờ báo cáo thành công")
                .Result(khungGioBaoCaoService.getAllKhungGioBaoCao())
                .build();
    }

    @GetMapping("/{idKhunggio}")
    public ApiResponse<KhungGioBaoCaoResponse> getById(
            @PathVariable String idKhunggio) {

        return ApiResponse.<KhungGioBaoCaoResponse>builder()
                .success(true)
                .message("Lấy thông tin khung giờ báo cáo thành công")
                .Result(khungGioBaoCaoService.getByIdResponse(idKhunggio))
                .build();
    }

    @PostMapping
    public ApiResponse<KhungGioBaoCaoResponse> create(
            @RequestBody KhungGioBaoCaoRequest request) {

        return ApiResponse.<KhungGioBaoCaoResponse>builder()
                .success(true)
                .message("Tạo khung giờ báo cáo thành công")
                .Result(khungGioBaoCaoService.createKhungGio(request))
                .build();
    }
    @PostMapping("/banChiHuy")
    public ApiResponse<KhungGioBaoCaoResponse> createKhungGioBanChiHuy(
            @RequestBody KhungGioBaoCaoRequest request) {

        return ApiResponse.<KhungGioBaoCaoResponse>builder()
                .success(true)
                .message("Tạo khung giờ báo cáo thành công")
                .Result(khungGioBaoCaoService.createKhungGioBanChiHuy(request))
                .build();
    }
    @PostMapping("/banTacChien")
    public ApiResponse<KhungGioBaoCaoResponse> createKhungGioBanTacChien(
            @RequestBody KhungGioBaoCaoRequest request) {

        return ApiResponse.<KhungGioBaoCaoResponse>builder()
                .success(true)
                .message("Tạo khung giờ báo cáo thành công")
                .Result(khungGioBaoCaoService.createKhungGioBanTacChien(request))
                .build();
    }

    @PutMapping("/{idKhunggio}")
    public ApiResponse<KhungGioBaoCaoResponse> update(
            @PathVariable String idKhunggio,
            @RequestBody KhungGioBaoCaoForm update) {

        return ApiResponse.<KhungGioBaoCaoResponse>builder()
                .success(true)
                .message("Cập nhật khung giờ báo cáo thành công")
                .Result(khungGioBaoCaoService.updateKhungGio(idKhunggio, update))
                .build();
    }

    @DeleteMapping("/{idKhunggio}")
    public ApiResponse<Void> delete(
            @PathVariable String idKhunggio) {

        khungGioBaoCaoService.deleteKhungGio(idKhunggio);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Xóa khung giờ báo cáo thành công")
                .build();
    }
}