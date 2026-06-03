package org.example.quanlysu5.Mapper;

import org.example.quanlysu5.Dto.Request.TrucChiHuyRequest;
import org.example.quanlysu5.Dto.Response.TrucChiHuy.TrucChiHuyResponse;
import org.example.quanlysu5.Form.TrucChiHuyForm;
import org.example.quanlysu5.Module.TrucChiHuyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TrucChiHuyMapper {
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "caTruc", ignore = true)
    TrucChiHuyEntity toEntity(TrucChiHuyRequest request);

    TrucChiHuyResponse toResponse(TrucChiHuyEntity role);
    void update(@MappingTarget TrucChiHuyEntity role, TrucChiHuyForm update);
}
