package org.example.quanlysu5.Mapper;

import org.example.quanlysu5.Dto.Request.KhungGioBaoCaoRequest;
import org.example.quanlysu5.Dto.Response.KhungGioBaoCaoResponse;
import org.example.quanlysu5.Form.KhungGioBaoCaoForm;
import org.example.quanlysu5.Module.KhungGioBaoCaoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface KhungGioBaoCaoMapper {
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "loaiBaoBan", ignore = true)
    KhungGioBaoCaoEntity toEntity(KhungGioBaoCaoRequest request);

    KhungGioBaoCaoResponse toResponse(KhungGioBaoCaoEntity role);
    void update(@MappingTarget KhungGioBaoCaoEntity role, KhungGioBaoCaoForm update);
}
