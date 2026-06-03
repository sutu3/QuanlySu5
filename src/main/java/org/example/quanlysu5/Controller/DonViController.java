package org.example.quanlysu5.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.quanlysu5.Dto.ApiResponse;
import org.example.quanlysu5.Dto.Request.DonviRequest;
import org.example.quanlysu5.Dto.Response.DonVi.DonViResponse;
import org.example.quanlysu5.Service.DonViService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/donvi")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Đơn vị API", description = "Quản lý đơn vị")
public class DonViController {

    DonViService donViService;

    @GetMapping
    public ApiResponse<List<DonViResponse>> getAllDonVi() {

        return ApiResponse.<List<DonViResponse>>builder()
                .Result(donViService.toUnitsList())
                .message("Lấy danh sách đơn vị thành công")
                .success(true)
                .code(0)
                .build();
    }

    @GetMapping("/page")
    public ApiResponse<Page<DonViResponse>> getAllDonViPaging(
            Pageable pageable) {

        return ApiResponse.<Page<DonViResponse>>builder()
                .Result(donViService.toUnitsPage(pageable))
                .message("Lấy danh sách đơn vị phân trang thành công")
                .success(true)
                .code(0)
                .build();
    }

    @PostMapping
    public ApiResponse<DonViResponse> createDonVi(
            @RequestBody DonviRequest request) {

        return ApiResponse.<DonViResponse>builder()
                .Result(donViService.createDonVi(request))
                .message("Tạo đơn vị thành công")
                .success(true)
                .code(0)
                .build();
    }
}