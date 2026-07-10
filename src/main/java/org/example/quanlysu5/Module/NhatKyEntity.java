package org.example.quanlysu5.Module;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.example.quanlysu5.Enum.DoiTuongNhatKy;
import org.example.quanlysu5.Enum.HanhDongNhatKy;
import org.example.quanlysu5.Enum.TrangThaiNhatKy;

@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NhatKyEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_nhat_ky", nullable = false, updatable = false)
    String idNhatKy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tai_khoan")
    TaikhoanEntity taiKhoan;

    @Enumerated(EnumType.STRING)
    @Column(
            columnDefinition = "VARCHAR(30) COMMENT 'Hành động thực hiện'",
            nullable = false
    )
    HanhDongNhatKy hanhDong;

    @Enumerated(EnumType.STRING)
    @Column(
            columnDefinition = "VARCHAR(30) COMMENT 'Đối tượng thực hiện'",
            nullable = false
    )
    DoiTuongNhatKy doiTuong;

    @Column(columnDefinition = "VARCHAR(100) COMMENT 'ID đối tượng bị tác động'")
    String doiTuongId;

    @Column(columnDefinition = "VARCHAR(500) COMMENT 'Mô tả'")
    String moTa;

    @Lob
    @Column(columnDefinition = "LONGTEXT COMMENT 'Giá trị trước khi thay đổi'")
    String giaTriCu;

    @Lob
    @Column(columnDefinition = "LONGTEXT COMMENT 'Giá trị sau khi thay đổi'")
    String giaTriMoi;

    @Enumerated(EnumType.STRING)
    @Column(
            columnDefinition = "VARCHAR(20) COMMENT 'Trạng thái thực hiện'",
            nullable = false
    )
    TrangThaiNhatKy trangThai;

    @Column(columnDefinition = "VARCHAR(1000) COMMENT 'Thông báo lỗi'")
    String thongBaoLoi;
}
