package com.example.demo.dto;


import com.example.demo.entity.DiagnosisItem;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class DiagnosisItem_DTO {

    String itemName;
    int round;
    String result;
    int vulnerability_level;

    public List<DiagnosisItem_DTO> diagnosisItemDtoList (List<DiagnosisItem> diagnosisItemList) {
        List<DiagnosisItem_DTO> diagnosisItemDtos = new ArrayList<>();

        for (DiagnosisItem item : diagnosisItemList) {
            // 각 DiagnosisItem 객체에 접근
            DiagnosisItem_DTO diagnosisItemDTO = new DiagnosisItem_DTO();
            diagnosisItemDTO.setItemName(item.getName());
            diagnosisItemDTO.setResult(item.getResult());
            diagnosisItemDTO.setRound(item.getDiagnosis().getRound());
            diagnosisItemDTO.setVulnerability_level(item.getAzrDescription().getVulnerableLevel());

            diagnosisItemDtos.add(diagnosisItemDTO);
        }
        return diagnosisItemDtos;
    }

}
