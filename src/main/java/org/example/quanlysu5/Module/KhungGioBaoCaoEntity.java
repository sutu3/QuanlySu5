package org.example.quanlysu5.Module;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.example.quanlysu5.Enum.LoaiBaoBan;

import java.util.Date;

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

    @Column(columnDefinition = "DATE COMMENT 'Khung giờ bắt đầu'", nullable = false)
    Date khunggioBatdau;

    @Column(columnDefinition = "DATE COMMENT 'Khung giờ kết thúc'", nullable = false)
    Date khunggioKetthuc;
}
