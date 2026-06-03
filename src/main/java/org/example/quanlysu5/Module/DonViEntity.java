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
public class DonViEntity extends BaseEntity{
    @Id
    @Column(unique = true, nullable = false)
    String maDonVi;

    @Column(columnDefinition = "VARCHAR(255) COMMENT 'tên đơn vị'", nullable = false)
    String tenDonvi;

    @Column(columnDefinition = "VARCHAR(25) COMMENT 'ký hiệu đơn vị'", nullable = false)
    String kyhieuDonvi;

    @Column(columnDefinition = "INTEGER COMMENT 'Tổng quân số trong đơn vị đó'",nullable = false)
    Integer quanSoTong;

    @Column(columnDefinition = "INTEGER COMMENT 'Quân số hạ sĩ quan binh sĩ'",nullable = false)
    Integer quanSoHsqBs;

    @Column(columnDefinition = "INTEGER COMMENT 'Quân số si quan đơn vị đó'",nullable = false)
    Integer quanSoSiQuan;

    @Column(columnDefinition = "INTEGER COMMENT 'Quân số QNCN trong đơn vị đó'",nullable = false)
    Integer quanSoQncn;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_don_vi_cha")
    DonViEntity donViCha;

    @OneToMany(mappedBy = "donViCha")
    List<DonViEntity> donViCon;
    @Column(columnDefinition = "BOOLEAN  COMMENT 'trạng thái hoạt động'",nullable = false)
    Boolean hoatDong;

    @OneToMany(mappedBy = "donVi")
    List<TaikhoanEntity> taiKhoan;

    @OneToMany(mappedBy = "donVi")
    List<DonBaoCaoEntity> donBaoCao;
}
