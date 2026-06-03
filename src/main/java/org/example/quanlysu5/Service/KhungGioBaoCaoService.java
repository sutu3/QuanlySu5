package org.example.quanlysu5.Service;

import org.example.quanlysu5.Dto.Request.KhungGioBaoCaoRequest;
import org.example.quanlysu5.Dto.Response.KhungGioBaoCaoResponse;
import org.example.quanlysu5.Form.KhungGioBaoCaoForm;
import org.example.quanlysu5.Module.KhungGioBaoCaoEntity;

import java.util.List;

public interface KhungGioBaoCaoService {

    List<KhungGioBaoCaoResponse> getAllKhungGioBaoCao();

    KhungGioBaoCaoEntity getById(String idKhunggio);

    KhungGioBaoCaoResponse getByIdResponse(String idKhunggio);

    KhungGioBaoCaoResponse createKhungGio(KhungGioBaoCaoRequest request);
    KhungGioBaoCaoResponse createKhungGioBanChiHuy(KhungGioBaoCaoRequest request);
    KhungGioBaoCaoResponse createKhungGioBanTacChien(KhungGioBaoCaoRequest request);


    KhungGioBaoCaoResponse updateKhungGio(String idKhunggio,
                                          KhungGioBaoCaoForm update);

    void deleteKhungGio(String idKhunggio);
}
