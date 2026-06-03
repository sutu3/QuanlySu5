package org.example.quanlysu5.Dto.Request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.example.quanlysu5.Module.BaseEntity;

import java.time.LocalDate;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CaTrucRequest {

    LocalDate ngaytruc;

    String matkhau;

    String ghichu;

    String trucBanTacChien;

    String trucChiHuy;

}
