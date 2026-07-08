package org.example.quanlysu5.Mapper;

import org.example.quanlysu5.Dto.Request.CtDangCtRequest;

import org.example.quanlysu5.Dto.Response.CtDangCt.CtDangCtResponse;
import org.example.quanlysu5.Form.CtDangCtForm;
import org.example.quanlysu5.Module.CtDangCtEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CtDangCtMapper {
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "donVi", ignore = true)
    CtDangCtEntity toEntity(CtDangCtRequest request);

    @Mapping(target = "updatedAt", ignore = false)

    CtDangCtResponse toResponse(CtDangCtEntity ctDangCt);
    void update(@MappingTarget CtDangCtEntity ctDangCt, CtDangCtForm update);
}
