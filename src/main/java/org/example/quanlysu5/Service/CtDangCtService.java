package org.example.quanlysu5.Service;

import org.example.quanlysu5.Dto.Request.CtDangCtRequest;
import org.example.quanlysu5.Dto.Request.GhiChuRequest;
import org.example.quanlysu5.Dto.Response.CtDangCt.CtDangCtResponse;
import org.example.quanlysu5.Form.CtDangCtForm;
import org.example.quanlysu5.Module.CtDangCtEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface CtDangCtService {
    List<CtDangCtResponse> getAllByDonViCha(String idDonViCha);
    CtDangCtResponse getAllByIdDonVi(String idDonVi);
    CtDangCtResponse create(CtDangCtRequest request,String idNguoiTao);
    CtDangCtResponse update(CtDangCtForm update,String idCtDangCt);
    List<CtDangCtResponse> getAllCtDangCtDonViConByDonVi(String idDonVi, LocalDate ngayLoc);
    CtDangCtResponse getAllCtDangCtByDonVi(String idDonVi, LocalDate ngayLoc);
    List<CtDangCtResponse> getAllCtDangCtByDonViVaKhoangThoiGian(String idDonVi, LocalDate start, LocalDate end);
    CtDangCtResponse getAllCtDangCtoByDonViApprove(String idDonVi, LocalDate ngayLoc);
    CtDangCtEntity getById(String id);
    CtDangCtResponse getByIdResponse(String id);
    CtDangCtResponse updateStatusApprove(String id);
    CtDangCtResponse updateStatusWaitingApprove(String id);
    CtDangCtResponse updateStatusWaitingDraf(String id);
    CtDangCtResponse updateStatusRefuse(String id, GhiChuRequest ghichu);



}
