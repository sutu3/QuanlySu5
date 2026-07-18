package org.example.quanlysu5.Mapper;

import org.example.quanlysu5.Dto.Request.DonviRequest;
import org.example.quanlysu5.Dto.Response.DonVi.DonViResponse;
import org.example.quanlysu5.Form.DonviForm;
import org.example.quanlysu5.Module.DonViEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
@Mapper(componentModel = "spring")
public interface DonViMapper {

    @Mapping(target = "donViCha", ignore = true)
    @Mapping(target = "donViCon", ignore = true)
    @Mapping(target = "capDonVi", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    DonViEntity toEntity(DonviRequest request);

    @Mapping(target = "donViCha", source = "donViCha.tenDonvi")
    @Mapping(target = "donViCon", source = "donViCon")
    DonViResponse toResponse(DonViEntity donViEntity);

    @AfterMapping
    default void formatKyHieu(DonViEntity entity,
                              @MappingTarget DonViResponse response) {

        if (entity.getKyhieuDonvi() != null) {
            response.setKyhieuDonvi(entity.getKyhieuDonvi().split("-")[0]);
        }
    }

    @Mapping(target = "donViCha", ignore = true)
    @Mapping(target = "donViCon", ignore = true)
    @Mapping(target = "capDonVi", ignore = true)
    @Mapping(target = "maDonVi", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    void update(@MappingTarget DonViEntity donVi, DonviForm update);

    default List<String> map(List<DonViEntity> donViEntities) {
        if (donViEntities == null) {
            return List.of();
        }

        return donViEntities.stream()
                .map(DonViEntity::getTenDonvi)
                .toList();
    }
}