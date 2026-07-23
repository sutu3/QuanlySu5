package org.example.quanlysu5.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.quanlysu5.Dto.ApiResponse;
import org.example.quanlysu5.Dto.Request.NhiemVuNgayRequest;
import org.example.quanlysu5.Dto.Response.DonBaoCao.DonBaoCaoResponse;
import org.example.quanlysu5.Dto.Response.NhiemvuNgay.NhiemVuNgayResponse;
import org.example.quanlysu5.Enum.LoaiDonBaoCao;
import org.example.quanlysu5.Form.NhiemVuNgayForm;
import org.example.quanlysu5.Service.NhiemVuNgayService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/nhiemvungay")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Nhiệm vụ ngày")
public class NhiemVuNgayController {

    NhiemVuNgayService nhiemVuNgayService;

    @GetMapping
    public ApiResponse<List<NhiemVuNgayResponse>> getAll() {

        return ApiResponse.<List<NhiemVuNgayResponse>>builder()
                .Result(nhiemVuNgayService.getAllList())
                .success(true)
                .code(0)
                .message("Tìm danh sách nhiệm vụ ngày thành công")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<NhiemVuNgayResponse> getById(
            @PathVariable String id) {

        return ApiResponse.<NhiemVuNgayResponse>builder()
                .Result(nhiemVuNgayService.getById(id))
                .success(true)
                .code(0)
                .message("Tìm nhiệm vụ ngày thành công")
                .build();
    }

    @GetMapping("/donbaocao/{idDonBaoCao}")
    public ApiResponse<NhiemVuNgayResponse> getByIdDonBaoCao(
            @PathVariable String idDonBaoCao) {

        return ApiResponse.<NhiemVuNgayResponse>builder()
                .Result(nhiemVuNgayService.getByIdDonBaoCao(idDonBaoCao))
                .success(true)
                .code(0)
                .message("Tìm nhiệm vụ ngày thành công")
                .build();
    }

    @PostMapping
    public ApiResponse<NhiemVuNgayResponse> create(
            @RequestBody NhiemVuNgayRequest request) {

        return ApiResponse.<NhiemVuNgayResponse>builder()
                .Result(nhiemVuNgayService.createNhiemVuNgay(request))
                .success(true)
                .code(0)
                .message("Tạo nhiệm vụ ngày thành công")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<NhiemVuNgayResponse> update(
            @RequestBody NhiemVuNgayForm update, @PathVariable String id) {

        return ApiResponse.<NhiemVuNgayResponse>builder()
                .Result(nhiemVuNgayService.updateNhiemVuNgay(update, id))
                .success(true)
                .code(0)
                .message("Tạo nhiệm vụ ngày thành công")
                .build();
    }@GetMapping("/search/donvi/{idDonVi}/children")
    public ApiResponse<List<NhiemVuNgayResponse>> getAllListByIdDonVi(
            @PathVariable String idDonVi,
            @RequestParam(required = false) LoaiDonBaoCao loaiDonBaoCao,
            @RequestParam
            @DateTimeFormat(pattern = "dd/MM/yyyy")
            LocalDate ngayLoc){

        return ApiResponse.<List<NhiemVuNgayResponse>>builder()
                .Result(nhiemVuNgayService.getAllListByIdDonVi(idDonVi,ngayLoc,loaiDonBaoCao.toString()))
                .message("Lấy danh sách nhiệm vụ của đơn vị con thành công")
                .success(true)
                .code(0)
                .build();
    }


}
