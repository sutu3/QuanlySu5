package org.example.quanlysu5.Module;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.example.quanlysu5.Enum.LoaiDonBaoCao;
import org.example.quanlysu5.Enum.Status;

import java.time.LocalDateTime;

@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DonBaoCaoEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_don_bao_cao",columnDefinition = "VARCHAR(36) COMMENT 'Id của đơn báo cáo'")
    String idDonBaoCao;

    @Column(name = "quan_so_tong",columnDefinition = "INTEGER COMMENT 'tổng quân số'")
    Integer quanSoTong;

    @Column(name = "quan_so_hien_dien",columnDefinition = "INTEGER COMMENT 'hiện diện '")
    Integer quanSoHienDien;

    @Column(name = "quan_so_vang",columnDefinition = "INTEGER COMMENT 'tổng vắng'")
    Integer quanSoVang;

    @Enumerated(EnumType.STRING)
    @Column(name = "loai_don_bao_cao", columnDefinition = "VARCHAR(20) COMMENT 'Loại báo cáo: DON_VI hoặc TONG_HOP'")
    LoaiDonBaoCao loaiDonBaoCao;

    @Column(name = "thoi_gian_bao_cao", columnDefinition = "DATETIME COMMENT 'thời gian báo cáo'")
    LocalDateTime thoiGianBaoCao;

    @Column(name = "thoi_gian_gui", columnDefinition = "DATETIME COMMENT 'thời gian gửi'")
    LocalDateTime thoiGianGui;


    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20) COMMENT 'Trạng thái của đơn báo cáo'", nullable = false)
    @NotNull(message = "Trạng thái không được null")
    Status status;

    @Column(name = "thong_tin_vang", columnDefinition = "TEXT COMMENT 'thông tin vắng'")
    String  thongTinVang;

    @Column(name = "nguoi_tao", columnDefinition = "TEXT COMMENT 'người tạo'")
    String  nguoiTao;

    @Column(name = "chi_tiet_vang", columnDefinition = "LONGTEXT COMMENT 'chi tiet vắng'")
    String  chiTietVang;

    @Column(name = "ghi_chu", columnDefinition = "TEXT COMMENT 'ghi chú'")
    String  ghiChu;

    @Column(name = "cap_duyet", columnDefinition = "VARCHAR(50) COMMENT 'Cấp duyệt hiện tại của đơn báo cáo'")
    String capDuyet;

    @Column(name = "truc_ban_chi_huy", columnDefinition = "TEXT COMMENT 'thông tin trực ban chỉ huy'")
    String trucBanChiHuy;
    @Column(name = "truc_ban_tac_chien", columnDefinition = "TEXT COMMENT 'thông tin trực ban tác chiến'")
    String trucBanTacChien;

    @OneToOne(mappedBy = "donBaoCao",
            cascade = CascadeType.ALL)
    NhiemVuNgayEntity nhiemVuNgay;

    @ManyToOne
    @JoinColumn(name = "idCatruc",nullable = false)
    CaTrucEntity caTruc;

    @ManyToOne
    @JoinColumn(name = "maDonVi",nullable = false)
    DonViEntity donVi;


}
