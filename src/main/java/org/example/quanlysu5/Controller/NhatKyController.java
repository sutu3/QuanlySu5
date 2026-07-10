package org.example.quanlysu5.Controller;


import lombok.RequiredArgsConstructor;
import org.example.quanlysu5.Dto.ApiResponse;
import org.example.quanlysu5.Dto.Request.TimKiemNhatKyRequest;
import org.example.quanlysu5.Dto.Response.NhatKy.NhatKyResponse;
import org.example.quanlysu5.Service.NhatKyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nhatky")
@RequiredArgsConstructor
public class NhatKyController {

    private final NhatKyService nhatKyService;

    /**
     * Tìm kiếm nhật ký
     */
    @PostMapping("/search")
    public ResponseEntity<ApiResponse<Page<NhatKyResponse>>> search(
            @RequestBody TimKiemNhatKyRequest request,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(direction, sortBy)
        );

        Page<NhatKyResponse> result = nhatKyService.search(request, pageable);

        return ResponseEntity.ok(
                ApiResponse.<Page<NhatKyResponse>>builder()
                        .Result(result)
                        .message("Lấy danh sách nhật ký thành công")
                        .build()
        );
    }

    /**
     * Chi tiết nhật ký
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NhatKyResponse>> getById(
            @PathVariable String id
    ) {

        return ResponseEntity.ok(
                ApiResponse.<NhatKyResponse>builder()
                        .Result(nhatKyService.getByIdResposne(id))
                        .message("Lấy chi tiết nhật ký thành công")
                        .build()
        );
    }

    /**
     * Xóa nhật ký
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable String id
    ) {

        nhatKyService.deleteNhatKy(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .message("Xóa nhật ký thành công")
                        .build()
        );
    }

}
