package org.example.quanlysu5.Service.Impl.Specification;

import jakarta.persistence.criteria.Expression;
import org.example.quanlysu5.Enum.CapDonVi;
import org.example.quanlysu5.Module.DonViEntity;
import org.springframework.data.jpa.domain.Specification;


public class DonViSpecification {
    public static Specification<DonViEntity> sortTheoCap() {

        return (root, query, cb) -> {

            Expression<Object> order = cb.selectCase(root.get("capDonVi"))
                    .when(CapDonVi.PHONG, 1)
                    .when(CapDonVi.TRUNG_DOAN, 2)
                    .when(CapDonVi.BAN, 3)
                    .when(CapDonVi.TIEU_DOAN, 4)
                    .when(CapDonVi.DAI_DOI, 7)
                    .otherwise(100);

            query.orderBy(cb.asc(order));

            return null;
        };
    }
    public static Specification<DonViEntity> hasDonViCha(String maDonViCha) {
        return (root, query, cb) -> {

            if (maDonViCha == null || maDonViCha.isBlank()) {
                return null;
            }

            return cb.equal(
                    root.get("donViCha").get("maDonVi"),
                    maDonViCha
            );
        };
    }
}
