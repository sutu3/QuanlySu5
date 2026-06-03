package org.example.quanlysu5.Dto.Response.DonVi;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.example.quanlysu5.Module.BaseEntity;

import java.util.List;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DonViResponse extends BaseEntity {
    String maDonVi;
    String tenDonvi;
    String donViCha;
    String kyhieuDonvi;
    int quanSoTong;
    int quanSoHsqBs;
    int quanSoSiQuan;
    int quanSoQncn;
    List<String> donViCon;
}
