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
public class CtDangCtEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_congtac",columnDefinition = "VARCHAR(36) COMMENT 'Id của công tác đảng, chính trị'")
    String idCongtac;

    @Column(columnDefinition = "TEXT COMMENT 'tình hình hoạt động trong ngày'", nullable = false)
    String tinhHinh;

    @Column(columnDefinition = "TEXT COMMENT 'Nội dung đột xuất'", nullable = false)
    String noiDungDotXuat;

    @Enumerated(EnumType.STRING)
    @Column(name = "loai_don_bao_cao", columnDefinition = "VARCHAR(20) COMMENT 'Loại báo cáo: DON_VI hoặc TONG_HOP'")
    LoaiDonBaoCao loaiDonBaoCao;

    @Column(columnDefinition = "TEXT COMMENT 'Kết quả'", nullable = false)
    String ketQua;
    @Column(name = "truc_ban_noi_vu", columnDefinition = "TEXT COMMENT 'thông tin trực ban nội vụ'")
    String trucBanNoiVu;

    @Column(name = "nguoi_tao", columnDefinition = "TEXT COMMENT 'người tạo'")
    String  nguoiTao;

    @Column(name = "ghi_chu", columnDefinition = "TEXT COMMENT 'ghi chú'")
    String ghiChu;

    @Column(name = "truc_ban_ct_dang_ct", columnDefinition = "TEXT COMMENT 'thông tin trực ban công tác đảng, chính trị'")
    String trucBanCtDangCt;

    @Column(columnDefinition = "TEXT COMMENT 'Kiến nghị kết quả'", nullable = false)
    String kienNghi;

    @Column(name = "thoi_gian_bao_cao", columnDefinition = "DATETIME COMMENT 'thời gian báo cáo'")
    LocalDateTime thoiGianBaoCao;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20) COMMENT 'Trạng thái của đơn báo cáo'", nullable = false)
    @NotNull(message = "Trạng thái không được null")
    Status status;
    @ManyToOne
    @JoinColumn(name = "maDonVi",nullable = false)
    DonViEntity donVi;
}

