package org.example.quanlysu5.Service;

import org.example.quanlysu5.Dto.Request.ThongBaoRequest;
import org.example.quanlysu5.Dto.Response.ThongBaoResponse;
import org.example.quanlysu5.Module.ThongBaoEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ThongBaoService {
    List<ThongBaoResponse> getAllThongBao(String idMucTieu);

    List<ThongBaoEntity> getAllThongBaoChuaDoc();

    ThongBaoResponse createThongBaoDonVi(ThongBaoRequest request);

    ThongBaoResponse createThongBaoTaiKhoan(ThongBaoRequest request);

    void deleteThongBaoDaDocQuaHan();

    void thongBaoDaDoc(String idMucTieu);

    void thongBaoWebsocket(String message,ThongBaoRequest request,String idMuctieu);


}
