package org.example.quanlysu5.Repo;

import org.example.quanlysu5.Enum.LoaiDonBaoCao;
import org.example.quanlysu5.Module.LyDoVangEntity;
import org.example.quanlysu5.Module.NhiemVuNgayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface NhiemVuNgayRepo extends JpaRepository<NhiemVuNgayEntity, String>, JpaSpecificationExecutor<LyDoVangEntity> {
    List<NhiemVuNgayEntity> findAllByIsDeleted(boolean isDeleted);

    Optional<NhiemVuNgayEntity> findByDonBaoCao_idDonBaoCao(String idDonBaoCao);

    Optional<NhiemVuNgayEntity> findByDonBaoCao_DonVi_MaDonViAndDonBaoCao_ThoiGianBaoCaoBetweenAndDonBaoCao_LoaiDonBaoCao(String maDonVi
            , LocalDateTime start,
                                                                                                                          LocalDateTime end, LoaiDonBaoCao loaiDonBaoCao);
}
