package org.example.quanlysu5.Repo;

import org.example.quanlysu5.Module.CaTrucEntity;
import org.example.quanlysu5.Module.DonViEntity;
import org.example.quanlysu5.Module.TrucBanTacChienEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrucBanTacChienRepo extends JpaRepository<TrucBanTacChienEntity, String>, JpaSpecificationExecutor<TrucBanTacChienEntity> {
    List<TrucBanTacChienEntity> findAllByIsDeleted(boolean isDeleted);
    Optional<TrucBanTacChienEntity> findByTenNguoitruc(String tenNguoitruc);
    Optional<TrucBanTacChienEntity> findBySodienthoaiAndIsDeleted(String sodienthoai,boolean isdeleted);

    //Page<UnitsEntity> findAllbyPage(Boolean isDeleted, Pageable pageable);

}
