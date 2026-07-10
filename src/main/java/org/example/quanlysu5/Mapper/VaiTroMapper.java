package org.example.quanlysu5.Mapper;

import org.example.quanlysu5.Dto.Request.VaiTroRequest;
import org.example.quanlysu5.Dto.Response.VaiTroResponse;
import org.example.quanlysu5.Form.RoleUpdate;
import org.example.quanlysu5.Form.VaiTroForm;
import org.example.quanlysu5.Module.VaiTroEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VaiTroMapper {
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    VaiTroEntity toEntity(VaiTroRequest request);

    VaiTroResponse toResponse(VaiTroEntity role);
    void update(@MappingTarget VaiTroEntity role, VaiTroForm update);
}
