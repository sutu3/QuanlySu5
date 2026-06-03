package org.example.quanlysu5.Dto.Response.ThongKe;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThongKeDonViResponse {
     String tenDonVi;

     Integer quanSoTong;

     Integer quanSoHienDien;

     Integer quanSoVang;

     Double tyLeHienDien;
}
