package org.example.quanlysu5.Repo;

import org.example.quanlysu5.Module.CaTrucEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CaTrucRepo extends JpaRepository<CaTrucEntity, String>, JpaSpecificationExecutor<CaTrucEntity> {
    List<CaTrucEntity> findAllByIsDeleted(boolean isDeleted);
    Optional<CaTrucEntity> findByNgaytruc(LocalDate ngay);
    boolean existsByNgaytruc(LocalDate ngay);
    @Query("""
    SELECT c
    FROM CaTrucEntity c
    WHERE c.trucChiHuy IS NOT NULL
    AND c.ngaytruc >= :today
    ORDER BY c.ngaytruc ASC
""")
    List<CaTrucEntity> findCaTrucChiHuy(LocalDate today);
    @Query("""
    SELECT c
    FROM CaTrucEntity c
    WHERE c.trucBanTacChien IS NOT NULL
    AND c.ngaytruc >= :today
    ORDER BY c.ngaytruc ASC
""")
    List<CaTrucEntity> findCaTrucTacChien(LocalDate today);
    //Page<UnitsEntity> findAllbyPage(Boolean isDeleted, Pageable pageable);

}
