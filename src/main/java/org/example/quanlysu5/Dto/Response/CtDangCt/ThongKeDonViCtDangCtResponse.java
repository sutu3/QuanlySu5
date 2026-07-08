package org.example.quanlysu5.Dto.Response.CtDangCt;

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
public class ThongKeDonViCtDangCtResponse {
    String idDonVi;

    String tenDonVi;

    long soKienNghi;

    long soDotXuat;

    long tongVanDe;

    String mucDo;
}
