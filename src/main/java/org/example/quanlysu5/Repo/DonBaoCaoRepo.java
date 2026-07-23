package org.example.quanlysu5.Repo;

import org.example.quanlysu5.Enum.LoaiDonBaoCao;
import org.example.quanlysu5.Enum.Status;
import org.example.quanlysu5.Module.DonBaoCaoEntity;
import org.example.quanlysu5.Module.DonViEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DonBaoCaoRepo extends JpaRepository<DonBaoCaoEntity,String>, JpaSpecificationExecutor<DonBaoCaoEntity> {
    List<DonBaoCaoEntity> findAllByIsDeleted(boolean isDeleted);
    boolean existsByCaTruc_IdCatruc(String idCaTruc);

    Boolean existsByDonViAndThoiGianBaoCaoBetween(DonViEntity DonVi, LocalDateTime start,
                                                  LocalDateTime end);
    Optional<DonBaoCaoEntity> findByDonVi_MaDonViAndThoiGianBaoCaoBetweenAndLoaiDonBaoCao(
            String idDonVi,
            LocalDateTime start,
            LocalDateTime end,
            LoaiDonBaoCao loaiDonBaoCao
    );

    List<DonBaoCaoEntity> findByDonVi_MaDonViAndThoiGianBaoCaoBetweenAndStatusAndLoaiDonBaoCao(
            String idDonVi,
            LocalDateTime start,
            LocalDateTime end,
            Status status,
            LoaiDonBaoCao loaiDonBaoCao
    );

    boolean existsByDonVi_MaDonViAndThoiGianBaoCaoBetweenAndLoaiDonBaoCao(
            String idDonVi,
            LocalDateTime start,
            LocalDateTime end,
            LoaiDonBaoCao loaiDonBaoCao
    );
    Optional<DonBaoCaoEntity> findByDonVi_MaDonViAndThoiGianBaoCaoBetween(
            String idDonVi,
            LocalDateTime start,
            LocalDateTime end
    );
    List<DonBaoCaoEntity> findAllByDonVi_MaDonViAndThoiGianBaoCaoBetween(
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

    @Query(value = """
    SELECT dbc.*
    FROM don_bao_cao_entity dbc
    JOIN don_vi_entity dv
        ON dbc.ma_don_vi = dv.ma_don_vi
    WHERE dv.ma_don_vi REGEXP CONCAT('^', :maDonViCha, '\\\\.[0-9]{3}$')
      AND dbc.thoi_gian_bao_cao BETWEEN :start AND :end
      AND dbc.status = 'Đã_Duyệt'
      AND dbc.loai_don_bao_cao = :loaiBaoCao
""", nativeQuery = true)
    List<DonBaoCaoEntity> findAllCap2ByThoiGian(
            @Param("maDonViCha") String maDonViCha,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("loaiBaoCao") LoaiDonBaoCao loaiBaoCao
    );


    @Query(value = """
    SELECT dbc.*
    FROM don_bao_cao_entity dbc
    WHERE (dbc.cap_duyet IS NULL OR dbc.cap_duyet NOT LIKE 'GS003.%')
      AND dbc.thoi_gian_bao_cao BETWEEN :start AND :end
      AND dbc.status = 'Đã_Duyệt'
""", nativeQuery = true)
    List<DonBaoCaoEntity> findChuaDuyetDenCapGS003(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}
