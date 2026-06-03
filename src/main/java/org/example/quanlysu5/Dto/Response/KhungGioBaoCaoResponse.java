package org.example.quanlysu5.Dto.Response;

import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KhungGioBaoCaoResponse {
    String idKhunggio;
    String tenBaocao;
    Integer soNgayTruc;
    Date khunggioBatdau;
    Date khunggioKetthuc;
}
