package org.example.quanlysu5.Module;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CaTrucEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_catruc",columnDefinition = "VARCHAR(36) COMMENT 'Id của ca trực'")
    String idCatruc;

    @Column(
            name = "ngay_truc",
            nullable = false,
            unique = true
    )
    LocalDate ngaytruc;

    @Column(columnDefinition = "VARCHAR(255) COMMENT 'mật khẩu'", nullable = false)
    String matkhau;

    @Column(columnDefinition = "TEXT COMMENT 'ghi chú'", nullable = false)
    String ghichu;

    @ManyToOne
    @JoinColumn(name = "id_truc_ban_tac_chien")
    TrucBanTacChienEntity trucBanTacChien;

    @ManyToOne
    @JoinColumn(name = "id_truc_chi_huy")
    TrucChiHuyEntity trucChiHuy;

    @OneToMany(mappedBy="caTruc")
    List<DonBaoCaoEntity> donBaoCao;

}
