package org.example.quanlysu5.Dto.Request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.example.quanlysu5.Enum.CapDonVi;

import java.time.LocalTime;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KhungGioBaoCaoRequest {
    Integer soNgayTruc;
    CapDonVi capDonVi;
    LocalTime khunggioBatdau;
    LocalTime khunggioKetthuc;
}