package org.example.quanlysu5.Repo;

import org.example.quanlysu5.Module.TaikhoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaiKhoanRepo extends JpaRepository<TaikhoanEntity,String> , JpaSpecificationExecutor<TaikhoanEntity> {
    Optional<TaikhoanEntity> findByTenDangNhap(String tenTaiKhoan);
    Optional<TaikhoanEntity> findByTenDangNhapAndMatKhau(String tenDangNhap,String matKhau);
}
