package org.example.quanlysu5.Service;

import org.example.quanlysu5.Dto.Request.TrucChiHuyRequest;
import org.example.quanlysu5.Dto.Response.TrucChiHuy.TrucChiHuyResponse;
import org.example.quanlysu5.Form.TrucChiHuyForm;
import org.example.quanlysu5.Module.TrucChiHuyEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TrucChiHuyService {
    List<TrucChiHuyResponse> getAllTrucChiHuyToResponse();
    TrucChiHuyEntity getByIdNguoiTruc(String idNguoiTruc);
    TrucChiHuyResponse getByIdNguoiTrucResponse(String idNguoiTruc);
    TrucChiHuyResponse getByTenNguoiTruc(String tenNguoiTruc);
    TrucChiHuyResponse createNguoiTruc(TrucChiHuyRequest TrucChiHuyRequest);
    TrucChiHuyResponse updateNguoiTruc(String idNguoiTruc, TrucChiHuyForm update);
    boolean canhBaoTrucChiHuy();
    void deleteNguoiTruc(String idNguoiTruc);


}
