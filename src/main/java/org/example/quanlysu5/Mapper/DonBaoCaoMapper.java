package org.example.quanlysu5.Mapper;

import org.example.quanlysu5.Dto.Request.DonBaoCaoRequest;
import org.example.quanlysu5.Dto.Response.DonBaoCao.DonBaoCaoResponse;
import org.example.quanlysu5.Form.DonBaoCaoForm;
import org.example.quanlysu5.Module.DonBaoCaoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DonBaoCaoMapper {
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "caTruc", ignore = true)
    @Mapping(target = "donVi", ignore = true)
    DonBaoCaoEntity toEntity(DonBaoCaoRequest request);

    DonBaoCaoResponse toResponse(DonBaoCaoEntity role);
    @Mapping(target = "donVi", ignore = true)
    void update(@MappingTarget DonBaoCaoEntity role, DonBaoCaoForm update);
}
