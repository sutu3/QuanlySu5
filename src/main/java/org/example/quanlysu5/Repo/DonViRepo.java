package org.example.quanlysu5.Repo;

import org.example.quanlysu5.Module.DonViEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonViRepo extends JpaRepository<DonViEntity, String>, JpaSpecificationExecutor<DonViEntity> {
    Page<DonViEntity> findAllByIsDeleted(Boolean isDeleted, Pageable pageable);
    List<DonViEntity> findAllByIsDeleted(Boolean isDeleted);
    Optional<DonViEntity> findByKyhieuDonvi(String kyhieuDonvi);
    List<DonViEntity> findByDonViCha(DonViEntity donViCha);
    List<DonViEntity> findByDonViChaIsNull();
    //Page<UnitsEntity> findAllbyPage(Boolean isDeleted, Pageable pageable);

}
