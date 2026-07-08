package org.example.quanlysu5.Controller;

import lombok.RequiredArgsConstructor;
import org.example.quanlysu5.Dto.ApiResponse;
import org.example.quanlysu5.Dto.Response.CtDangCt.DashboardCtDangCtResponse;
import org.example.quanlysu5.Dto.Response.ThongKe.ThongKeResponse;
import org.example.quanlysu5.Service.ThongKeService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/thong-ke")
@RequiredArgsConstructor
public class ThongKeController {

    private final ThongKeService thongKeService;

    @GetMapping
    public ApiResponse<ThongKeResponse> thongKeQuanSo(
            @RequestParam
            @DateTimeFormat(pattern = "dd/MM/yyyy")
            LocalDate ngayBaoCao
    ){

        return ApiResponse.<ThongKeResponse>builder()
                .success(true)
                .code(0)
                .message("Lấy thống kê thành công")
                .Result(
                        thongKeService.thongKeQuanSo(ngayBaoCao)
                )
                .build();
    }
    @GetMapping("/ctDangCt")
    public ApiResponse<DashboardCtDangCtResponse> dashboard(
            @RequestParam
            @DateTimeFormat(pattern = "dd/MM/yyyy")
            LocalDate date
    ) {


        return ApiResponse.<DashboardCtDangCtResponse>builder()
                .success(true)
                .code(0)
                .message("Thống kê dashboard thành công")
                .Result(thongKeService.thongKeDashboard(date))
                .build();
    }
}
