package org.example.quanlysu5.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.quanlysu5.Dto.ApiResponse;
import org.example.quanlysu5.Dto.Request.TrucBanTacChienRequest;
import org.example.quanlysu5.Dto.Response.TrucBanTacChien.TrucBanTacChienResponse;
import org.example.quanlysu5.Form.TrucBanTacChienForm;
import org.example.quanlysu5.Service.TrucBanTacChienService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/truc-ban-tac-chien")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(
        name = "Trực Ban Tác Chiến API",
        description = "Quản lý danh sách trực ban tác chiến"
)
public class TrucBanTacChienController {

    TrucBanTacChienService trucBanTacChienService;

    @GetMapping
    public ApiResponse<List<TrucBanTacChienResponse>> getAll() {

        return ApiResponse.<List<TrucBanTacChienResponse>>builder()
                .Result(trucBanTacChienService.getAllTrucBanTacChienToResponse())
                .message("Lấy danh sách trực ban tác chiến thành công")
                .success(true)
                .code(0)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<TrucBanTacChienResponse> getById(
            @PathVariable String id
    ) {

        return ApiResponse.<TrucBanTacChienResponse>builder()
                .Result(trucBanTacChienService.getByIdNguoiTrucResponse(id))
                .message("Lấy thông tin trực ban tác chiến thành công")
                .success(true)
                .code(0)
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<TrucBanTacChienResponse> getByTenNguoiTruc(
            @RequestParam String tenNguoiTruc
    ) {

        return ApiResponse.<TrucBanTacChienResponse>builder()
                .Result(
                        trucBanTacChienService.getByTenNguoiTruc(tenNguoiTruc)
                )
                .message("Tìm kiếm thành công")
                .success(true)
                .code(0)
                .build();
    }

    @PostMapping
    public ApiResponse<TrucBanTacChienResponse> create(
            @RequestBody TrucBanTacChienRequest request
    ) {

        return ApiResponse.<TrucBanTacChienResponse>builder()
                .Result(
                        trucBanTacChienService.createNguoiTruc(request)
                )
                .message("Thêm trực ban tác chiến thành công")
                .success(true)
                .code(0)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<TrucBanTacChienResponse> update(
            @PathVariable String id,
            @RequestBody TrucBanTacChienForm update
    ) {

        return ApiResponse.<TrucBanTacChienResponse>builder()
                .Result(
                        trucBanTacChienService.updateNguoiTruc(id, update)
                )
                .message("Cập nhật thành công")
                .success(true)
                .code(0)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(
            @PathVariable String id
    ) {

        trucBanTacChienService.deleteNguoiTruc(id);

        return ApiResponse.<String>builder()
                .Result("Deleted")
                .message("Xóa thành công")
                .success(true)
                .code(0)
                .build();
    }
}