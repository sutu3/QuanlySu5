package org.example.quanlysu5.Module;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.example.quanlysu5.Enum.CapDonVi;
import org.example.quanlysu5.Enum.LoaiBaoBan;

import java.time.LocalTime;

@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KhungGioBaoCaoEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_khunggio",columnDefinition = "VARCHAR(36) COMMENT 'Id của khung giờ'")
    String idKhunggio;

    @Column(columnDefinition = "VARCHAR(255) COMMENT 'tên báo cáo'", nullable = false)
    String tenBaocao;

    @Column(columnDefinition = "INTEGER COMMENT 'số ngày trực'", nullable = false)
    Integer soNgayTruc;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20) COMMENT 'Loại báo ban'", nullable = false)
    @NotNull(message = "Loại báo ban không được null")
    LoaiBaoBan loaiBaoBan;

    // Cấp đơn vị áp dụng khung giờ này (null = áp dụng chung, vd trực chỉ huy/tác chiến)
    @Enumerated(EnumType.STRING)
    @Column(name = "cap_don_vi", columnDefinition = "VARCHAR(30) COMMENT 'Cấp đơn vị áp dụng'")
    CapDonVi capDonVi;

    @Column(columnDefinition = "TIME COMMENT 'Khung giờ bắt đầu'", nullable = false)
    LocalTime khunggioBatdau;

    @Column(columnDefinition = "TIME COMMENT 'Khung giờ kết thúc'", nullable = false)
    LocalTime  khunggioKetthuc;
}