package org.example.quanlysu5.Dto.Response.TrucChiHuy;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.example.quanlysu5.Module.BaseEntity;
import org.example.quanlysu5.Module.CaTrucEntity;

import java.util.List;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrucChiHuyNoList extends BaseEntity {
    String idNguoitruc;
    String tenNguoitruc;
    String capbacNguoitruc;
    String chucvuNguoitruc;
    String sodienthoai;
}
