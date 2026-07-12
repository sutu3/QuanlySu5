package org.example.quanlysu5.Repo;

import org.example.quanlysu5.Enum.Status;
import org.example.quanlysu5.Module.CtDangCtEntity;
import org.example.quanlysu5.Module.DonBaoCaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CtDangCtRepo extends JpaRepository<CtDangCtEntity,String> {
    Optional<CtDangCtEntity> findByDonVi_MaDonVi(
            String maDonVi);
    Optional<CtDangCtEntity> findByDonVi_MaDonViAndCreatedAtBetweenAndStatus(
            String idDonVi,
            LocalDateTime start,
            LocalDateTime end,
            Status status
    );
    List<CtDangCtEntity> findByCreatedAtBetween(
            LocalDateTime start,
            LocalDateTime end
    );
    @Query(value = """
    SELECT ctdang.*
    FROM ct_dang_ct_entity ctdang
    JOIN don_vi_entity dv
        ON ctdang.ma_don_vi = dv.ma_don_vi
    WHERE dv.ma_don_vi REGEXP CONCAT('^', :maDonViCha, '\\\\.[0-9]{3}$')
      AND ctdang.created_at BETWEEN :start AND :end
      AND ctdang.status = 'Đã_Duyệt'
""", nativeQuery = true)
    List<CtDangCtEntity> findAllCap2ByThoiGian(
            @Param("maDonViCha") String maDonViCha,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
    CtDangCtEntity findByDonVi_MaDonViAndCreatedAtBetween(
            String idDonVi,
            LocalDateTime start,
            LocalDateTime end
    );
    List<CtDangCtEntity> findAllByDonVi_MaDonViAndCreatedAtBetween(
            String idDonVi,
            LocalDateTime start,
            LocalDateTime end);
}
