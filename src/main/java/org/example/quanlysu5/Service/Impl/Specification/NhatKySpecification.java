package org.example.quanlysu5.Service.Impl.Specification;


import org.example.quanlysu5.Enum.DoiTuongNhatKy;
import org.example.quanlysu5.Enum.HanhDongNhatKy;
import org.example.quanlysu5.Enum.TrangThaiNhatKy;
import org.example.quanlysu5.Module.NhatKyEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class NhatKySpecification {

    public static Specification<NhatKyEntity> hasTaiKhoanId(String taiKhoanId) {
        return (root, query, cb) ->
                taiKhoanId == null || taiKhoanId.isBlank()
                        ? null
                        : cb.equal(root.get("taiKhoan").get("idTaiKhoan"), taiKhoanId);
    }

    public static Specification<NhatKyEntity> hasHanhDong(HanhDongNhatKy hanhDong) {
        return (root, query, cb) ->
                hanhDong == null
                        ? null
                        : cb.equal(root.get("hanhDong"), hanhDong);
    }

    public static Specification<NhatKyEntity> hasDoiTuong(DoiTuongNhatKy doiTuong) {
        return (root, query, cb) ->
                doiTuong == null
                        ? null
                        : cb.equal(root.get("doiTuong"), doiTuong);
    }

    public static Specification<NhatKyEntity> hasDoiTuongId(String doiTuongId) {
        return (root, query, cb) ->
                doiTuongId == null || doiTuongId.isBlank()
                        ? null
                        : cb.equal(root.get("doiTuongId"), doiTuongId);
    }

    public static Specification<NhatKyEntity> hasTrangThai(TrangThaiNhatKy trangThai) {
        return (root, query, cb) ->
                trangThai == null
                        ? null
                        : cb.equal(root.get("trangThai"), trangThai);
    }

    public static Specification<NhatKyEntity> hasMoTa(String moTa) {
        return (root, query, cb) ->
                moTa == null || moTa.isBlank()
                        ? null
                        : cb.like(
                        cb.lower(root.get("moTa")),
                        "%" + moTa.toLowerCase() + "%"
                );
    }

    public static Specification<NhatKyEntity> hasThongBaoLoi(String thongBaoLoi) {
        return (root, query, cb) ->
                thongBaoLoi == null || thongBaoLoi.isBlank()
                        ? null
                        : cb.like(
                        cb.lower(root.get("thongBaoLoi")),
                        "%" + thongBaoLoi.toLowerCase() + "%"
                );
    }

    public static Specification<NhatKyEntity> createdFrom(LocalDateTime from) {
        return (root, query, cb) ->
                from == null
                        ? null
                        : cb.greaterThanOrEqualTo(root.get("createdAt"), from);
    }

    public static Specification<NhatKyEntity> createdTo(LocalDateTime to) {
        return (root, query, cb) ->
                to == null
                        ? null
                        : cb.lessThanOrEqualTo(root.get("createdAt"), to);
    }


}
