package com.example.demo.entity;

import com.example.demo.azr.AZRCommand;
import com.example.demo.azr.AZR_Scanner;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.List;

import static com.example.demo.azr.AZRCommand.idValue;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DiagnosisItem {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String result;

    @Column(length = 30000)
    public String jsonArray;

    public  int vulEntityCount = 0;

    public  int safeEntityCount = 0;

    public  int totalEntityCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    private AZRDescription azrDescription;


    //---------------------------------------------어떻게 해야할지 생각해보기..

   @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
   @JoinColumn(name = "project_id")
    private Project project;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "diagnosis_id")
    private Diagnosis diagnosis;


    //---------------------------------------------
    private int round;


    public void addDiagnosisItem(AZR_Scanner azrScanner, Project project, AZRDescription azrDescription) throws JsonProcessingException {
        //System.out.println(AZRCommand.storageAccountList);
        if (AZRCommand.storageAccountList != null && AZRCommand.storageAccountList.isArray()) {
            AZRCommand.storageAccountList.forEach(storageAccount -> {
                try {
                    AZRCommand.inputAccountName =  storageAccount.get("name").asText();
                    System.out.println("inputAccountName : " + AZRCommand.inputAccountName);
                    AZRCommand.inputResourceGroupName = storageAccount.get("resourceGroup").asText();
                    AZRCommand.idValue = storageAccount.get("id").asText();
                    azrScanner.allCommand();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } else {
            System.out.println("storageAccountList is null or not an array");
        }

        this.name = azrScanner.getName();
        this.azrDescription = azrDescription;
        this.project = project;
        this.round = project.getRound();

        ObjectMapper objectMapper = new ObjectMapper();
        //result를 jsonArray에서 각 dgnssEntityStatus를 뽑아서 하나라도 취약하면 취약으로 설정할것이다.
        this.result =  checkVulnerability(azrScanner.getJsonOutputList());
        this.vulEntityCount = getVulEntityCount();
        this.safeEntityCount = getSafeEntityCount();
        this.totalEntityCount = getVulEntityCount() + getSafeEntityCount();

        // JsonNode 목록을 JSON 문자열로 변환
        String prettyJsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(azrScanner.getJsonOutputList());
        this.jsonArray = prettyJsonString;
    }



    public void addDiagnosisItem2(AZR_Scanner azrScanner, Project project, AZRDescription azrDescription) throws IOException {

        azrScanner.allCommand();

        this.name = azrScanner.getName();
        this.azrDescription = azrDescription;
        this.project = project;
        this.round = project.getRound();

        ObjectMapper objectMapper = new ObjectMapper();

        //result를 jsonArray에서 각 dgnssEntityStatus를 뽑아서 하나라도 취약하면 취약으로 설정할것이다.
        this.result =  checkVulnerability(azrScanner.getJsonOutputList());
        this.vulEntityCount = getVulEntityCount();
        this.safeEntityCount = getSafeEntityCount();
        this.totalEntityCount = getVulEntityCount() + getSafeEntityCount();

        // JsonNode 목록을 JSON 문자열로 변환
        String prettyJsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(azrScanner.getJsonOutputList());
        this.jsonArray = prettyJsonString;
        }



    public String checkVulnerability(List<JsonNode> jsonOutputList) {

        for (JsonNode jsonObject : jsonOutputList) {
            JsonNode dgnssEntityStatusNode = jsonObject.get("dgnssEntityStatus");

            if (dgnssEntityStatusNode != null && !dgnssEntityStatusNode.asBoolean()) {
                vulEntityCount++;
            } else {
                safeEntityCount++;
            }
        }

        // 확인해보자
        System.out.println("취약한 엔티티 수: " + vulEntityCount);
        System.out.println("양호한 엔티티 수: " + safeEntityCount);
        System.out.println();

        // 취약한 객체가 하나라도 있으면 "취약"을 반환, 그렇지 않으면 "양호"반환
        return vulEntityCount > 0 ? "취약" : "양호";
    }



}


