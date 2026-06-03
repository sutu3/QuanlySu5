package org.example.quanlysu5.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.quanlysu5.Dto.ApiResponse;
import org.example.quanlysu5.Dto.Request.DonBaoCaoRequest;
import org.example.quanlysu5.Dto.Response.DonBaoCao.DonBaoCaoResponse;
import org.example.quanlysu5.Form.DonBaoCaoForm;
import org.example.quanlysu5.Service.DonBaoCaoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/donbaocao")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Đơn Báo Cáo API", description = "Quản lý đơn báo cáo quân số")
public class DonBaoCaoController {

    DonBaoCaoService donBaoCaoService;

    @GetMapping
    public ApiResponse<List<DonBaoCaoResponse>> getAllDonBaoCao() {

        return ApiResponse.<List<DonBaoCaoResponse>>builder()
                .Result(donBaoCaoService.getAllDonBaoCaoToResponse())
                .message("Lấy danh sách đơn báo cáo thành công")
                .success(true)
                .code(0)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<DonBaoCaoResponse> getDonBaoCaoById(
            @PathVariable String id) {

        return ApiResponse.<DonBaoCaoResponse>builder()
                .Result(donBaoCaoService.getByIdDonBaoCaoReponse(id))
                .message("Lấy thông tin đơn báo cáo thành công")
                .success(true)
                .code(0)
                .build();
    }
    @GetMapping("/search/DonVi/{idDonVi}/children")
    public ApiResponse<List<DonBaoCaoResponse>> getDonBaoCaoDonViConById(
            @PathVariable String idDonVi,
            @RequestParam
            @DateTimeFormat(pattern = "dd/MM/yyyy")
            LocalDate ngayLoc) {

        return ApiResponse.<List<DonBaoCaoResponse>>builder()
                .Result(donBaoCaoService.getAllDonBaoCaoDonViConByDonVi(idDonVi,ngayLoc))
                .message("Lấy danh sách báo cáo đơn vị con thành công")
                .success(true)
                .code(0)
                .build();
    }
    @GetMapping("/search/DonVi/{idDonVi}")
    public ApiResponse<DonBaoCaoResponse> getDonBaoCaoById(
            @PathVariable String idDonVi,
            @RequestParam
            @DateTimeFormat(pattern = "dd/MM/yyyy")
            LocalDate ngayLoc) {

        return ApiResponse.<DonBaoCaoResponse>builder()
                .Result(donBaoCaoService.getAllDonBaoCaoByDonVi(idDonVi,ngayLoc))
                .message("Lấy thông tin đơn báo cáo thành công")
                .success(true)
                .code(0)
                .build();
    }
    @PutMapping("/approve/{idDonBaoCao}")
    public ApiResponse<DonBaoCaoResponse> approveDonBaoCao(
            @PathVariable String idDonBaoCao) {

        return ApiResponse.<DonBaoCaoResponse>builder()
                .success(true)
                .code(0)
                .message("Duyệt đơn báo cáo thành công")
                .Result(
                        donBaoCaoService.updateStatusApprove(idDonBaoCao)
                )
                .build();
    }

    @PutMapping("/refuse/{idDonBaoCao}")
    public ApiResponse<DonBaoCaoResponse> refuseDonBaoCao(
            @PathVariable String idDonBaoCao) {

        return ApiResponse.<DonBaoCaoResponse>builder()
                .success(true)
                .code(0)
                .message("Từ chối đơn báo cáo thành công")
                .Result(
                        donBaoCaoService.updateStatusRefuse(idDonBaoCao)
                )
                .build();
    }
    @GetMapping("/search/DonVi/{idDonVi}/Status/Approvel")
    public ApiResponse<DonBaoCaoResponse> getDonBaoCaoByIdApprove(
            @PathVariable String idDonVi,
            @RequestParam
            @DateTimeFormat(pattern = "dd/MM/yyyy")
            LocalDate ngayLoc) {

        return ApiResponse.<DonBaoCaoResponse>builder()
                .Result(donBaoCaoService.getAllDonBaoCaoByDonViApprove(idDonVi,ngayLoc))
                .message("Lấy thông tin đơn báo cáo thành công")
                .success(true)
                .code(0)
                .build();
    }

    @PostMapping
    public ApiResponse<DonBaoCaoResponse> createDonBaoCao(
            @RequestBody DonBaoCaoRequest request) {

        return ApiResponse.<DonBaoCaoResponse>builder()
                .Result(donBaoCaoService.createDonBaoCaoQuanSoNgay(request))
                .message("Tạo đơn báo cáo thành công")
                .success(true)
                .code(0)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<DonBaoCaoResponse> updateDonBaoCao(
            @PathVariable String id,
            @RequestBody DonBaoCaoForm update) {

        return ApiResponse.<DonBaoCaoResponse>builder()
                .Result(donBaoCaoService.updateDonBaoCao(id, update))
                .message("Cập nhật đơn báo cáo thành công")
                .success(true)
                .code(0)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteDonBaoCao(
            @PathVariable String id) {

        donBaoCaoService.deleteDonBaoCao(id);

        return ApiResponse.<Void>builder()
                .message("Xóa đơn báo cáo thành công")
                .success(true)
                .code(0)
                .build();
    }
}