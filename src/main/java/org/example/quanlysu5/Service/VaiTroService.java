package org.example.quanlysu5.Service;

import org.example.quanlysu5.Dto.Request.VaiTroRequest;
import org.example.quanlysu5.Dto.Response.VaiTroResponse;
import org.example.quanlysu5.Form.VaiTroForm;
import org.example.quanlysu5.Module.VaiTroEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface VaiTroService {
    List<VaiTroResponse> getAllRole();

    VaiTroEntity getRoleById(String id);

    VaiTroResponse getRoleByTaiKhoan(String idTaiKhoan);

    VaiTroEntity getRoleByName(String name);

    VaiTroResponse getRoleResponseById(String id);

    VaiTroResponse createRole(VaiTroRequest vaiTroRequest);

    VaiTroResponse updateRole(VaiTroForm update,String idRole);

    void deletedRole(String idRole);

}
