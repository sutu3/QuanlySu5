package org.example.quanlysu5.Repo;

import org.example.quanlysu5.Enum.Status;
import org.example.quanlysu5.Module.DonBaoCaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DonBaoCaoRepo extends JpaRepository<DonBaoCaoEntity,String>, JpaSpecificationExecutor<DonBaoCaoEntity> {
    List<DonBaoCaoEntity> findAllByIsDeleted(boolean isDeleted);
    Optional<DonBaoCaoEntity> findByDonVi_MaDonViAndThoiGianBaoCaoBetween(
            String idDonVi,
            LocalDateTime start,
            LocalDateTime end
    );
    Optional<DonBaoCaoEntity> findByDonVi_MaDonViAndThoiGianBaoCaoBetweenAndStatus(
            String idDonVi,
            LocalDateTime start,
            LocalDateTime end,
            Status status
    );

    List<DonBaoCaoEntity> findAllByThoiGianBaoCaoBetween(LocalDateTime start,LocalDateTime end);
}
