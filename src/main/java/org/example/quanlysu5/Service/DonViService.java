package org.example.quanlysu5.Service;

import org.example.quanlysu5.Dto.Request.DonviRequest;
import org.example.quanlysu5.Dto.Response.DonVi.DonViResponse;
import org.example.quanlysu5.Form.DonviForm;
import org.example.quanlysu5.Module.DonViEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DonViService {
    Page<DonViResponse> toUnitsPage(Pageable page);

    List<DonViResponse> toUnitsList();

    DonViResponse createDonVi(DonviRequest donviRequest);
    DonViEntity getByKyHieuDonVi(String kyhieuDonvi);

    DonViResponse updateDonVi(String idDonVi, DonviForm update);


    DonViEntity getById(String id);
}
