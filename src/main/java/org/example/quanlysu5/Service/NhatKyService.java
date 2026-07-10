package org.example.quanlysu5.Service;

import org.example.quanlysu5.Dto.Request.NhatKyRequest;
import org.example.quanlysu5.Dto.Request.TimKiemNhatKyRequest;
import org.example.quanlysu5.Dto.Response.NhatKy.NhatKyResponse;
import org.example.quanlysu5.Module.NhatKyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface NhatKyService {
    NhatKyResponse createNhatKy(NhatKyRequest entity);
    Page<NhatKyResponse> search(TimKiemNhatKyRequest request,
                                 Pageable pageable);
    NhatKyEntity getById(String idNhatKy);
    NhatKyResponse getByIdResposne(String idNhatKy);
    void deleteNhatKy(String idNhatKy);

}
