package org.example.quanlysu5.Sheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.quanlysu5.Module.CaTrucEntity;
import org.example.quanlysu5.Service.CaTrucService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class CaTrucScheduler {

    private final CaTrucService caTrucService;

    // Chạy lúc 00:00 mỗi ngày theo giờ VN
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Ho_Chi_Minh")
    public void taoCaTrucHangNgay() {
        LocalDate today = LocalDate.now();
        CaTrucEntity caTruc = caTrucService.taoCaTrucTuDongChoNgay(today);
        log.info("Đã đảm bảo ca trực cho ngày {} (id={})",
                today, caTruc.getIdCatruc());
    }
}
