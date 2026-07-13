package org.example.quanlysu5.Mapper;

import org.example.quanlysu5.Dto.Request.TaiKhoanRequest;
import org.example.quanlysu5.Dto.Response.TaiKhoan.TaiKhoanResponse;
import org.example.quanlysu5.Module.TaikhoanEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaiKhoanMapper {
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "vaiTro",ignore = true)
    @Mapping(target = "donVi",ignore = true)
    @Mapping(target = "chucNangThem", ignore = true)
    @Mapping(target = "chucNangBo", ignore = true)
    TaikhoanEntity toEntity(TaiKhoanRequest request);

    @Mapping(target = "tenChucnang", ignore = true)
    TaiKhoanResponse toResponse(TaikhoanEntity taikhoanEntity);
}
