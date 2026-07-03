package org.example.quanlysu5.Controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.quanlysu5.Dto.ApiResponse;
import org.example.quanlysu5.Dto.Request.CtDangCtRequest;
import org.example.quanlysu5.Dto.Request.GhiChuRequest;
import org.example.quanlysu5.Dto.Response.CtDangCt.CtDangCtResponse;
import org.example.quanlysu5.Dto.Response.DonBaoCao.DonBaoCaoResponse;
import org.example.quanlysu5.Form.CtDangCtForm;
import org.example.quanlysu5.Service.CtDangCtService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/ctdangct")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CtDangCtController {

    CtDangCtService ctDangCtService;

    @PostMapping
    public ApiResponse<CtDangCtResponse> create(
            @RequestBody @Valid CtDangCtRequest request
    ) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        Jwt jwt = (Jwt) authentication.getPrincipal();

        String scope = jwt.getClaim("sub");
        return ApiResponse.<CtDangCtResponse>builder()
                .code(0)
                .success(true)
                .message("Tạo chính trị đảng công tác thành công")
                .Result(ctDangCtService.create(request,scope))
                .build();
    }

    @PutMapping("/{idCtDangCt}")
    public ApiResponse<CtDangCtResponse> update(
            @PathVariable String idCtDangCt,
            @RequestBody CtDangCtForm update
    ) {

        return ApiResponse.<CtDangCtResponse>builder()
                .code(0)
                .success(true)
                .message("Cập nhật chính trị đảng công tác thành công")
                .Result(
                        ctDangCtService.update(
                                update,
                                idCtDangCt
                        )
                )
                .build();
    }
    @GetMapping("/search/DonVi/{idDonVi}/Status/Approvel")
    public ApiResponse<CtDangCtResponse> getDonBaoCaoByIdApprove(
            @PathVariable String idDonVi,
            @RequestParam
            @DateTimeFormat(pattern = "dd/MM/yyyy")
            LocalDate ngayLoc) {

        return ApiResponse.<CtDangCtResponse>builder()
                .Result(ctDangCtService.getAllCtDangCtoByDonViApprove(idDonVi,ngayLoc))
                .message("Lấy thông tin đơn báo cáo thành công")
                .success(true)
                .code(0)
                .build();
    }
    @GetMapping("/{id}")
    public ApiResponse<CtDangCtResponse> getById(
            @PathVariable String id
    ) {

        return ApiResponse.<CtDangCtResponse>builder()
                .code(0)
                .success(true)
                .message("Lấy thông tin chính trị đảng công tác thành công")
                .Result(ctDangCtService.getByIdResponse(id))
                .build();
    }

    @GetMapping("/search/donVi/{idDonVi}")
    public ApiResponse<CtDangCtResponse> getByIdDonVi(
            @PathVariable String idDonVi
    ) {

        return ApiResponse.<CtDangCtResponse>builder()
                .code(0)
                .success(true)
                .message("Lấy thông tin theo đơn vị thành công")
                .Result(ctDangCtService.getAllByIdDonVi(idDonVi))
                .build();
    }
    @GetMapping("/search/DonVi/{idDonVi}/children")
    public ApiResponse<List<CtDangCtResponse>> getDonBaoCaoDonViConById(
            @PathVariable String idDonVi,
            @RequestParam
            @DateTimeFormat(pattern = "dd/MM/yyyy")
            LocalDate ngayLoc) {

        return ApiResponse.<List<CtDangCtResponse>>builder()
                .Result(ctDangCtService.getAllCtDangCtDonViConByDonVi(idDonVi,ngayLoc))
                .message("Lấy danh sách chính trị đảng công tác của đơn vị con thành công")
                .success(true)
                .code(0)
                .build();
    }
    @GetMapping("/search/DonVi/{idDonVi}")
    public ApiResponse<CtDangCtResponse> getDonBaoCaoById(
            @PathVariable String idDonVi,
            @RequestParam
            @DateTimeFormat(pattern = "dd/MM/yyyy")
            LocalDate ngayLoc) {

        return ApiResponse.<CtDangCtResponse>builder()
                .Result(ctDangCtService.getAllCtDangCtByDonVi(idDonVi,ngayLoc))
                .message("Lấy thông tin chính trị đảng công tác thành công")
                .success(true)
                .code(0)
                .build();
    }
    @GetMapping("/search")
    public ApiResponse<List<CtDangCtResponse>> searchBaoCao(
            @RequestParam String idDonVi,
            @RequestParam LocalDate start,
            @RequestParam LocalDate end
    ){
        return ApiResponse.<List<CtDangCtResponse>>builder()
                .success(true)
                .code(0)
                .message("Lấy thông tin chính trị đảng công tác thành công")
                .Result(
                        ctDangCtService.getAllCtDangCtByDonViVaKhoangThoiGian(idDonVi,start,end)
                )
                .build();
    }

    @GetMapping("/search/donViCha/{idDonViCha}/children")
    public ApiResponse<List<CtDangCtResponse>> getAllByDonViCha(
            @PathVariable String idDonViCha
    ) {

        return ApiResponse.<List<CtDangCtResponse>>builder()
                .code(0)
                .success(true)
                .message("Lấy danh sách theo đơn vị cha thành công")
                .Result(
                        ctDangCtService.getAllByDonViCha(idDonViCha)
                )
                .build();
    }
    @PutMapping("/approve/{id}")
    public ApiResponse<CtDangCtResponse> approve(
            @PathVariable String id
    ) {

        return ApiResponse.<CtDangCtResponse>builder()
                .code(0)
                .success(true)
                .message("Duyệt báo cáo thành công")
                .Result(
                        ctDangCtService.updateStatusApprove(id)
                )
                .build();
    }

    @PutMapping("/waiting-approve/{id}")
    public ApiResponse<CtDangCtResponse> waitingApprove(
            @PathVariable String id
    ) {

        return ApiResponse.<CtDangCtResponse>builder()
                .code(0)
                .success(true)
                .message("Đã gửi báo cáo chờ duyệt")
                .Result(
                        ctDangCtService.updateStatusWaitingApprove(id)
                )
                .build();
    }

    @PutMapping("/draft/{id}")
    public ApiResponse<CtDangCtResponse> backToDraft(
            @PathVariable String id
    ) {

        return ApiResponse.<CtDangCtResponse>builder()
                .code(0)
                .success(true)
                .message("Thu hồi báo cáo thành công")
                .Result(
                        ctDangCtService.updateStatusWaitingDraf(id)
                )
                .build();
    }

    @PutMapping("/refuse/{id}")
    public ApiResponse<CtDangCtResponse> refuse(
            @PathVariable String id,
            @RequestBody GhiChuRequest ghiChu
    ) {

        return ApiResponse.<CtDangCtResponse>builder()
                .code(0)
                .success(true)
                .message("Từ chối báo cáo thành công")
                .Result(
                        ctDangCtService.updateStatusRefuse(
                                id,
                                ghiChu
                        )
                )
                .build();
    }
}