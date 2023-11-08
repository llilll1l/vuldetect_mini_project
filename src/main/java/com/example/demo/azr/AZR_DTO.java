package com.example.demo.azr;

import com.example.demo.entity.Diagnosis;
import com.example.demo.entity.DiagnosisItem;
import com.example.demo.entity.Project;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter @Setter
public class AZR_DTO {

    private Long id;

    private String name;

    //private String vulnerabilityGrade;

    private int vulnerabilityLevel;

    private String description;

    public String category;

    private boolean result;

    public String resourceGroup;

    public String azrCriteria;


    private Project project;

    private Diagnosis diagnosis;

    private int round;

    private JsonNode jsonObject; // JSON 데이터를 저장할 필드

    private  String dgnssEntityKey;
    private  String dgnssEntityStatus;
    private  String dgnssCheckCmd;
    private  String dgnssSecSettingCmd;
    private  String dgnssResultByValueAndKey;

    public List<AZR_DTO> azrDto (List<DiagnosisItem> diagnosisItems) {
        List<AZR_DTO> jsonNodeDTOs = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (DiagnosisItem item : diagnosisItems) {
            try {
                // JSON 문자열을 JsonNode로 파싱
                JsonNode jsonArray = objectMapper.readTree(item.getJsonArray());

                // jsonArray의 각 객체를 추출하여 DTO에 저장
                for (JsonNode jsonNode : jsonArray) {
                    AZR_DTO jsonNodeDTO = new AZR_DTO();
                    jsonNodeDTO.setName(item.getName()); // item의 name 설정
                    jsonNodeDTO.setDescription(item.getAzrDescription().getInspectionDescription());
                    //jsonNodeDTO.setVulnerabilityGrade(item.getVulnerabilityGrade());
                    jsonNodeDTO.setVulnerabilityLevel(item.getAzrDescription().getVulnerableLevel());
                    jsonNodeDTO.setJsonObject(jsonNode); // 현재 순회 중인 jsonNode 설정
                    jsonNodeDTO.setAzrCriteria(item.getAzrDescription().getCriteria());

                    JsonNode dgnssEntityKeyNode = jsonNode.get("dgnssEntityKey");
                    JsonNode dgnssEntityStatusNode = jsonNode.get("dgnssEntityStatus");
                    JsonNode dgnssCheckCmdNode = jsonNode.get("dgnssCheckCmd");
                    JsonNode dgnssSecSettingCmdNode = jsonNode.get("dgnssSecSettingCmd");
                    JsonNode dgnssResultByValueAndKeyNode = jsonNode.get("dgnssResultByValueAndKey");

                    ((ObjectNode) jsonNode).remove("dgnssCheckCmd");
                    ((ObjectNode) jsonNode).remove("dgnssSecSettingCmd");
                    ((ObjectNode) jsonNode).remove("dgnssResultByValueAndKey");


                    if (dgnssEntityKeyNode != null) {
                        jsonNodeDTO.setDgnssEntityKey(dgnssEntityKeyNode.asText());
                    }

                    if (dgnssEntityStatusNode != null) {
                        jsonNodeDTO.setDgnssEntityStatus(dgnssEntityStatusNode.asText());
                    }
                    if (dgnssCheckCmdNode != null) {
                        jsonNodeDTO.setDgnssCheckCmd(dgnssCheckCmdNode.asText());
                    }
                    if (dgnssSecSettingCmdNode != null) {
                        jsonNodeDTO.setDgnssSecSettingCmd(dgnssSecSettingCmdNode.asText());
                    }
                    if (dgnssSecSettingCmdNode != null) {
                        jsonNodeDTO.setDgnssResultByValueAndKey(dgnssResultByValueAndKeyNode.asText());
                    }
                    jsonNodeDTOs.add(jsonNodeDTO);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return jsonNodeDTOs;
    }

}
