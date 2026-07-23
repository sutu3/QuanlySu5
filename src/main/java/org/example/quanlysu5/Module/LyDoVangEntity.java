package org.example.quanlysu5.Module;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LyDoVangEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_cap_bac",columnDefinition = "VARCHAR(36) COMMENT 'Id của lý do vắng'")
    String idLyDoVang;

    @Column(columnDefinition = "TEXT COMMENT 'lý do vắng'", nullable = false)
    String lyDoVang;


}
