package org.example.quanlysu5.Mapper;

import org.example.quanlysu5.Dto.Request.TrucBanTacChienRequest;
import org.example.quanlysu5.Dto.Response.TrucBanTacChien.TrucBanTacChienResponse;
import org.example.quanlysu5.Form.TrucBanTacChienForm;
import org.example.quanlysu5.Module.TrucBanTacChienEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TrucBanTacChienMapper {
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "caTruc", ignore = true)
    TrucBanTacChienEntity toEntity(TrucBanTacChienRequest request);

    TrucBanTacChienResponse toResponse(TrucBanTacChienEntity role);
    void update(@MappingTarget TrucBanTacChienEntity role, TrucBanTacChienForm update);
}
