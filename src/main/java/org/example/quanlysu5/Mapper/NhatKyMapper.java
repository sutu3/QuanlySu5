package org.example.quanlysu5.Mapper;

import org.example.quanlysu5.Dto.Request.DonviRequest;
import org.example.quanlysu5.Dto.Request.NhatKyRequest;
import org.example.quanlysu5.Dto.Response.DonVi.DonViResponse;
import org.example.quanlysu5.Dto.Response.NhatKy.NhatKyResponse;
import org.example.quanlysu5.Form.DonviForm;
import org.example.quanlysu5.Module.DonViEntity;
import org.example.quanlysu5.Module.NhatKyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NhatKyMapper {

    @Mapping(target = "taiKhoan", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    NhatKyEntity toEntity(NhatKyRequest request);
    @Mapping(target = "createdAt", ignore = false)
    NhatKyResponse toResponse(NhatKyEntity nhatKyEntity);

    //void update(@MappingTarget NhatKyEntity nhatKy, DonviForm update);

}