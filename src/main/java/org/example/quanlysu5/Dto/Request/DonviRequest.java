package org.example.quanlysu5.Dto.Request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.example.quanlysu5.Enum.Status;
import org.example.quanlysu5.Module.BaseEntity;

import java.util.List;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DonviRequest extends BaseEntity {
    String tenDonvi;
    String kyhieuDonvi;
    int quanSoTong;
    int quanSoHsqBs;
    int quanSoSiQuan;
    int quanSoQncn;
    String donViCha;
    List<String> donViCon;
}
