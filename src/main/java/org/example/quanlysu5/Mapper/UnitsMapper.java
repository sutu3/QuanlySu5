package org.example.quanlysu5.Mapper;

import org.example.quanlysu5.Dto.Request.DonviRequest;
import org.example.quanlysu5.Dto.Response.DonVi.DonViResponse;
import org.example.quanlysu5.Module.DonViEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UnitsMapper {

    @Mapping(target = "donViCha", ignore = true)
    @Mapping(target = "donViCon", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    DonViEntity toEntity(DonviRequest request);

    @Mapping(
            target = "donViCha",
            source = "donViCha.tenDonvi"
    )
    @Mapping(
            target = "donViCon",
            source = "donViCon"
    )
    DonViResponse toResponse(DonViEntity donViEntity);

    default List<String> map(List<DonViEntity> donViEntities) {

        if (donViEntities == null) {
            return List.of();
        }

        return donViEntities.stream()
                .map(DonViEntity::getTenDonvi)
                .toList();
    }
}