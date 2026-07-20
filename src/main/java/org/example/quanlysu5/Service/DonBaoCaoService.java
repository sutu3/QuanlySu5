package org.example.quanlysu5.Service;

import org.example.quanlysu5.Dto.Request.DonBaoCaoRequest;
import org.example.quanlysu5.Dto.Request.GhiChuRequest;
import org.example.quanlysu5.Dto.Response.DonBaoCao.DonBaoCaoResponse;
import org.example.quanlysu5.Form.DonBaoCaoForm;
import org.example.quanlysu5.Module.DonBaoCaoEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface DonBaoCaoService {
    List<DonBaoCaoResponse> getAllDonBaoCaoToResponse();

    DonBaoCaoEntity getByIdDonBaoCao(String idNguoiTruc);

    DonBaoCaoResponse getByIdDonBaoCaoReponse(String idNguoiTruc);

    DonBaoCaoResponse createDonBaoCaoQuanSoNgay(DonBaoCaoRequest DonBaoCaoRequest, String idNguoiTao);

    List<DonBaoCaoResponse> getAllDonBaoCaoDonViConByDonVi(String idDonVi, LocalDate ngayLoc,String loaiBaoBan);

    DonBaoCaoResponse getAllDonBaoCaoByDonVi(String idDonVi, LocalDate ngayLoc,String loaiBaoBan);

    List<DonBaoCaoResponse> getAllDonBaoCaoByDonViVaKhoangThoiGian(String idDonVi, LocalDate start, LocalDate end);

    DonBaoCaoResponse getAllDonBaoCaoByDonViApprove(String idDonVi, LocalDate ngayLoc,String loaiBaoBan);

    DonBaoCaoResponse updateStatusApprove(String idDonBaoCao);

    DonBaoCaoResponse updateStatusWaitingApprove(String idDonBaoCao);

    DonBaoCaoResponse updateStatusWaitingDraf(String idDonBaoCao);

    DonBaoCaoResponse updateStatusRefuse(String idDonBaoCao, GhiChuRequest ghichu);

    DonBaoCaoResponse updateDonBaoCao(String idDonBaoCao, DonBaoCaoForm update);

    void deleteDonBaoCao(String idDonBaoCao);
}
