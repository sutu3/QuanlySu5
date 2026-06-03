package org.example.quanlysu5.Dto.Response.ThongKe;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThongKeResponse {
     LocalDate ngayBaoCao;

     Integer tongQuanSo;
     Integer tongHienDien;
     Integer tongVang;

     Double tyLeHienDien;
     Double tyLeVang;

     SoSanhResponse soSanh;

     DonViTieuBieuResponse donViTieuBieu;

     List<ThongKeDonViResponse> danhSachDonVi;

}
//{
//        "success": true,
//        "result": {
//        "ngayBaoCao": "2026-06-03",
//        "tongQuanSo": 5800,
//        "tongHienDien": 5417,
//        "tongVang": 383,
//        "tyLeHienDien": 93.4,
//        "tyLeVang": 6.6,
//
//        "soSanh": {
//        "homQua": { "tangGiam": 5, "phanTram": 0.1 },
//        "tuanTruoc": { "tangGiam": -12, "phanTram": -0.2 },
//        "thangTruoc": { "tangGiam": 23, "phanTram": 0.4 }
//        },
//
//        "donViTieuBieu": {
//        "hienDienCaoNhat": { "ten": "Tiểu đoàn 24", "tyLe": 100.0 },
//        "vangCaoNhat": { "ten": "Trung đoàn 4", "tyLe": 80.0 }
//        },
//
//        "danhSachDonVi": [
//        // Phòng ban
//        {
//        "tenDonVi": "Phòng Chính Trị",
//        "quanSoTong": 95,
//        "quanSoHienDien": 87,
//        "quanSoVang": 8,
//        "tyLeHienDien": 91.6
//        },
//        // ... các phòng khác
//
//        // Trung đoàn
//        {
//
//        "tenDonVi": "Trung đoàn 4",
//        "quanSoTong": 1320,
//        "quanSoHienDien": 1056,
//        "quanSoVang": 264,
//        "tyLeHienDien": 80.0
//        }
//        }
//        ]
//        }
