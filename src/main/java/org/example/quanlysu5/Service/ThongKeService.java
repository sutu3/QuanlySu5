package org.example.quanlysu5.Service;

import org.example.quanlysu5.Dto.Response.ThongKe.ThongKeResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service

public interface ThongKeService {

    ThongKeResponse thongKeQuanSo(LocalDate ngayBaoCao);

}
