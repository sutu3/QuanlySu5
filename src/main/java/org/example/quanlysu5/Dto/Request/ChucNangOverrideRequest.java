package org.example.quanlysu5.Dto.Request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChucNangOverrideRequest {
    Set<String> chucNangThem = new HashSet<>();
    Set<String> chucNangBo = new HashSet<>();
}
