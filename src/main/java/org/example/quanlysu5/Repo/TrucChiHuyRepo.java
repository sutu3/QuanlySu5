package org.example.quanlysu5.Repo;

import org.example.quanlysu5.Module.CaTrucEntity;
import org.example.quanlysu5.Module.TrucChiHuyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrucChiHuyRepo extends JpaRepository<TrucChiHuyEntity, String>, JpaSpecificationExecutor<TrucChiHuyEntity> {
    List<TrucChiHuyEntity> findAllByIsDeleted(boolean isDeleted);
    Optional<TrucChiHuyEntity> findByTenNguoitruc(String tenNguoitruc);
    Optional<TrucChiHuyEntity> findBySodienthoaiAndIsDeleted(String sodienthoai,boolean isdeleted);

    //Page<UnitsEntity> findAllbyPage(Boolean isDeleted, Pageable pageable);

}
