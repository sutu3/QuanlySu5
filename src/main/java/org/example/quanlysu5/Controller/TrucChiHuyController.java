package org.example.quanlysu5.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.quanlysu5.Dto.ApiResponse;
import org.example.quanlysu5.Dto.Request.TrucChiHuyRequest;
import org.example.quanlysu5.Dto.Response.TrucChiHuy.TrucChiHuyResponse;
import org.example.quanlysu5.Form.TrucChiHuyForm;
import org.example.quanlysu5.Service.TrucChiHuyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/truc-chi-huy")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(
        name = "Trực Chỉ Huy API",
        description = "Quản lý danh sách trực chỉ huy"
)
public class TrucChiHuyController {

    TrucChiHuyService trucChiHuyService;

    @GetMapping
    public ApiResponse<List<TrucChiHuyResponse>> getAll() {

        return ApiResponse.<List<TrucChiHuyResponse>>builder()
                .Result(trucChiHuyService.getAllTrucChiHuyToResponse())
                .message("Lấy danh sách trực chỉ huy thành công")
                .success(true)
                .code(0)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<TrucChiHuyResponse> getById(
            @PathVariable String id
    ) {

        return ApiResponse.<TrucChiHuyResponse>builder()
                .Result(trucChiHuyService.getByIdNguoiTrucResponse(id))
                .message("Lấy thông tin trực chỉ huy thành công")
                .success(true)
                .code(0)
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<TrucChiHuyResponse> getByTenNguoiTruc(
            @RequestParam String tenNguoiTruc
    ) {

        return ApiResponse.<TrucChiHuyResponse>builder()
                .Result(trucChiHuyService.getByTenNguoiTruc(tenNguoiTruc))
                .message("Tìm kiếm thành công")
                .success(true)
                .code(0)
                .build();
    }

    @PostMapping
    public ApiResponse<TrucChiHuyResponse> create(
            @RequestBody TrucChiHuyRequest request
    ) {

        return ApiResponse.<TrucChiHuyResponse>builder()
                .Result(trucChiHuyService.createNguoiTruc(request))
                .message("Thêm trực chỉ huy thành công")
                .success(true)
                .code(0)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<TrucChiHuyResponse> update(
            @PathVariable String id,
            @RequestBody TrucChiHuyForm update
    ) {

        return ApiResponse.<TrucChiHuyResponse>builder()
                .Result(trucChiHuyService.updateNguoiTruc(id, update))
                .message("Cập nhật thành công")
                .success(true)
                .code(0)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(
            @PathVariable String id
    ) {

        trucChiHuyService.deleteNguoiTruc(id);

        return ApiResponse.<String>builder()
                .Result("Deleted")
                .message("Xóa thành công")
                .success(true)
                .code(0)
                .build();
    }
}