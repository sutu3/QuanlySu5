package org.example.quanlysu5.Repo;

import org.example.quanlysu5.Enum.Status;
import org.example.quanlysu5.Module.CtDangCtEntity;
import org.example.quanlysu5.Module.DonBaoCaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CtDangCtRepo extends JpaRepository<CtDangCtEntity,String> {
    Optional<CtDangCtEntity> findByDonVi_MaDonVi(String maDonVi);
    Optional<CtDangCtEntity> findByDonVi_MaDonViAndCreatedAtBetweenAndStatus(
            String idDonVi,
            LocalDateTime start,
            LocalDateTime end,
            Status status
    );
    Optional<CtDangCtEntity> findByDonVi_MaDonViAndCreatedAtBetween(
            String idDonVi,
            LocalDateTime start,
            LocalDateTime end
    );
    List<CtDangCtEntity> findAllByDonVi_MaDonViAndCreatedAtBetween(
            String idDonVi,
            LocalDateTime start,
            LocalDateTime end);
}
