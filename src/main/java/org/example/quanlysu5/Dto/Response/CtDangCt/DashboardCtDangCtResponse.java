package org.example.quanlysu5.Dto.Response.CtDangCt;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DashboardCtDangCtResponse {
    int tongDonVi;

    int donViCoKienNghi;

    int donViCoDotXuat;

    List<ThongKeDonViCtDangCtResponse> danhSachDonVi;
}
