package org.example.quanlysu5.Repo;

import org.example.quanlysu5.Module.TaikhoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;


@Repository
public interface TaiKhoanRepo extends JpaRepository<TaikhoanEntity,String> , JpaSpecificationExecutor<TaikhoanEntity> {
    Optional<TaikhoanEntity> findByTenDangNhapIgnoreCase(String tenTaiKhoan);
    Optional<TaikhoanEntity> findByTenDangNhapAndMatKhau(String tenDangNhap,String matKhau);

    List<TaikhoanEntity> findByIsDeletedFalse();

    Optional<TaikhoanEntity> findByIdTaiKhoanAndIsDeletedFalse(String idTaiKhoan);
    boolean existsByVaiTro_IdVaiTro(String idVaiTro);

}
