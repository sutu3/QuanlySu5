package org.example.quanlysu5.Unit;

import lombok.RequiredArgsConstructor;
import org.example.quanlysu5.Module.DonViEntity;
import org.example.quanlysu5.Repo.DonViRepo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ChildCode {
    private final DonViRepo donViRepo;
    public String generateChildCode(DonViEntity parent) {

        List<DonViEntity> children =
                donViRepo.findByDonViCha(parent);

        int next = children.stream()
                .map(DonViEntity::getMaDonVi)
                .map(code -> {
                    String[] parts = code.split("\\.");
                    return Integer.parseInt(parts[parts.length - 1]);
                })
                .max(Integer::compareTo)
                .orElse(0) + 1;

        return parent.getMaDonVi()
                + "."
                + String.format("%03d", next);
    }
    public String generateRootCode() {

        List<DonViEntity> rootUnits =
                donViRepo.findByDonViChaIsNull();

        if(rootUnits.isEmpty()) {
            return "GS001";
        }

        int max = rootUnits.stream()
                .map(DonViEntity::getMaDonVi)
                .filter(code -> code.startsWith("GS"))
                .map(code -> code.substring(2))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);

        return "GS" + String.format("%03d", max + 1);
    }
}
