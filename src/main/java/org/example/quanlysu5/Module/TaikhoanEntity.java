package org.example.quanlysu5.Module;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaikhoanEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_tai_khoan",columnDefinition = "VARCHAR(36) COMMENT 'Id của tài khoản'")
    String idTaiKhoan;

    @Column(name = "ten_tai_khoan",columnDefinition = "VARCHAR(255) COMMENT 'tên tài khoản'", nullable = false)
    String tenTaiKhoan;

    @Column(name = "ten_dang_nhap",columnDefinition = "VARCHAR(255) COMMENT 'tên người dùng'", nullable = false)
    String tenDangNhap;

    @Column(name = "mat_khau",columnDefinition = "VARCHAR(255) COMMENT 'mật khẩu tài khoản'", nullable = false)
    String matKhau;

    @ManyToOne
    @JoinColumn(name = "id_vaitro",nullable = false)
    VaiTroEntity vaiTro;

    @ManyToOne
    @JoinColumn(name = "maDonVi",nullable = false)
    DonViEntity donVi;

    @Column(columnDefinition = "BOOL COMMENT 'trạng thái khóa tài khoản'", nullable = false)
    Boolean khoa = false;

}
