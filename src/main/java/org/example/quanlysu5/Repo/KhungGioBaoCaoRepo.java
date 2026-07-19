package org.example.quanlysu5.Repo;

import org.example.quanlysu5.Enum.CapDonVi;
import org.example.quanlysu5.Enum.LoaiBaoBan;
import org.example.quanlysu5.Module.KhungGioBaoCaoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KhungGioBaoCaoRepo extends JpaRepository<KhungGioBaoCaoEntity, String>, JpaSpecificationExecutor<KhungGioBaoCaoEntity> {
    List<KhungGioBaoCaoEntity> findAllByIsDeleted(Boolean isDeleted);

    Optional<KhungGioBaoCaoEntity> findByLoaiBaoBan(LoaiBaoBan s);
    boolean existsByLoaiBaoBan(LoaiBaoBan s);

    Optional<KhungGioBaoCaoEntity> findByLoaiBaoBanAndCapDonVi(LoaiBaoBan loai, CapDonVi cap);
    boolean existsByLoaiBaoBanAndCapDonVi(LoaiBaoBan loai, CapDonVi cap);

}
