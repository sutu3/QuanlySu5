package org.example.quanlysu5.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.quanlysu5.Dto.ApiResponse;
import org.example.quanlysu5.Dto.Request.CaTrucRequest;
import org.example.quanlysu5.Dto.Response.CaTruc.CaTrucResponse;
import org.example.quanlysu5.Dto.Response.CanhBaoCaTrucResponse;
import org.example.quanlysu5.Form.CaTrucForm;
import org.example.quanlysu5.Service.CaTrucService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ca-truc")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(
        name = "Ca Trực API",
        description = "Quản lý ca trực"
)
public class CaTrucController {

    CaTrucService caTrucService;

    @GetMapping
    public ApiResponse<List<CaTrucResponse>> getAll() {

        return ApiResponse.<List<CaTrucResponse>>builder()
                .Result(caTrucService.getAllCaTrucToResponse())
                .message("Lấy danh sách ca trực thành công")
                .success(true)
                .code(0)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<CaTrucResponse> getById(
            @PathVariable String id
    ) {

        return ApiResponse.<CaTrucResponse>builder()
                .Result(
                        caTrucService.getByIdCaTrucResponse(id)
                )
                .message("Lấy thông tin ca trực thành công")
                .success(true)
                .code(0)
                .build();
    }

        @GetMapping("/canh-bao")
        public ApiResponse<CanhBaoCaTrucResponse> checkCaTruc() {

            return ApiResponse.<CanhBaoCaTrucResponse>builder()
                    .success(true)
                    .code(0)
                    .message("Kiểm tra ca trực thành công")
                    .Result(caTrucService.checkCaTruc())
                    .build();
        }


    @PostMapping
    public ApiResponse<CaTrucResponse> create(
            @RequestBody CaTrucRequest request
    ) {

        return ApiResponse.<CaTrucResponse>builder()
                .Result(
                        caTrucService.createCaTruc(request)
                )
                .message("Tạo ca trực thành công")
                .success(true)
                .code(0)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<CaTrucResponse> update(
            @PathVariable String id,
            @RequestBody CaTrucForm update
    ) {

        return ApiResponse.<CaTrucResponse>builder()
                .Result(
                        caTrucService.updateCaTruc(id, update)
                )
                .message("Cập nhật ca trực thành công")
                .success(true)
                .code(0)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(
            @PathVariable String id
    ) {

        caTrucService.deleteCaTruc(id);

        return ApiResponse.<String>builder()
                .Result("Deleted")
                .message("Xóa ca trực thành công")
                .success(true)
                .code(0)
                .build();
    }
}