package org.example.quanlysu5.Dto.Response;

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
public class CanhBaoCaTrucResponse {
    boolean canhBaoChiHuy;

    boolean canhBaoTacChien;

    String messageChiHuy;

    String messageTacChien;
}
