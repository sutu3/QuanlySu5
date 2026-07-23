package org.example.quanlysu5.Service;

import org.example.quanlysu5.Dto.Request.NhiemVuNgayRequest;
import org.example.quanlysu5.Dto.Response.NhiemvuNgay.NhiemVuNgayResponse;
import org.example.quanlysu5.Form.NhiemVuNgayForm;

import java.time.LocalDate;
import java.util.List;

public interface NhiemVuNgayService {
    List<NhiemVuNgayResponse> getAllList();

    NhiemVuNgayResponse getById(String id);

    NhiemVuNgayResponse getByIdDonBaoCao(String idDonBaoCao);

    NhiemVuNgayResponse createNhiemVuNgay(NhiemVuNgayRequest nhiemVuNgayRequest);

    NhiemVuNgayResponse updateNhiemVuNgay(NhiemVuNgayForm update, String nhiemVuNgay);

    List<NhiemVuNgayResponse> getAllListByIdDonVi(String idDonVi, LocalDate ngayLoc, String loaiDonBaoCao);
}
