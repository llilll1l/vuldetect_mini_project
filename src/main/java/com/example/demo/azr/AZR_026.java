package com.example.demo.azr;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.azr.AZRCommand.idValue;
import static com.example.demo.azr.CommonMethod.*;

@Component
@Getter
@Setter
public class AZR_026 implements AZR_Scanner{
    private JsonNode jsonOutput;

    public String name;

    public String VulnerabilityGrade;

    public int vulnerabilityLevel;

    public String description;

    public String category;

    public Boolean Result; //이것은 필요할것인가..?

    public String resourceGroup;

    public String jsonArray;

    public List<JsonNode> jsonOutputList = new ArrayList<>();

    public static void main(String[] args)  {
        AZR_026 azr026 = new AZR_026();
        azr026.allCommand();
    }


    @Override
    public void allCommand() {
        setName("AZR_026");
        setVulnerabilityGrade("H");
        setVulnerabilityLevel(3);
        setDescription("머신 SQL 서버용 MS Defender On 설정 확인");
        setCategory("Security Solutions");


        List<String> commandAndArgs = new ArrayList<>();
        commandAndArgs.add(AZRCommand.POWERSHELL);
        commandAndArgs.add(AZRCommand.AZ);
        commandAndArgs.add(AZRCommand.SECURITY);
        commandAndArgs.add(AZRCommand.PRICING);
        commandAndArgs.add(AZRCommand.SHOW);
        commandAndArgs.add(AZRCommand.N);
        commandAndArgs.add(AZRCommand.SQLSERVERVIRTUALMACHINES);

        //API: az security pricing show -n sqlservervirtualmachines
        //RES: “pricingTier” : “Standard”면 양호, Free 면 취약
        //점검 방법: az security pricing show -n appservices --query pricingTier
        //보안 설정 방법: az resource update --ids /subscriptions/{subscription-id}/resourceGroups/{resource-group-name}/providers/{resource-provider-namespace}/{resource-type}/{resource-name} --set properties.pricingTier=standard


        //networkRulet 노드의 defaultAction 키의 밸류값이 Allow면 취약 Deny면 양호
        jsonOutput = executeProcessAndGetJsonOutput_2(commandAndArgs);
        System.out.println(getName());
        System.out.println(jsonOutput.toPrettyString());

        setResult(azrScan());
        AZRCommand.idValue = jsonOutput.get("id").asText();
        ((ObjectNode) jsonOutput).put("dgnssEntityKey", idValue);
        ((ObjectNode) jsonOutput).put("dgnssEntityStatus", getResult());

        String[] targetFields = {"pricingTier"};
        String valueAndKey = extractFields(jsonOutput, targetFields);
        ((ObjectNode) jsonOutput).put("dgnssResultByValueAndKey", valueAndKey);

        ((ObjectNode) jsonOutput).put("dgnssCheckCmd", vulCheck());
        ((ObjectNode) jsonOutput).put("dgnssSecSettingCmd", secSetting());

        jsonOutputList.add(jsonOutput.deepCopy());

    }


    @Override
    public Boolean azrScan(){
        return parseAndResult_1key_1value(jsonOutput,"pricingTier", "Standard");
    }

    @Override
    public String vulCheck() {
        return  AZRCommand.AZ
                +AZRCommand.SPACE
                +AZRCommand.RESOURCE
                +AZRCommand.SPACE
                +AZRCommand.SHOW
                +AZRCommand.SPACE
                +AZRCommand.IDS
                +AZRCommand.SPACE
                +idValue
                +AZRCommand.SPACE
                +AZRCommand.QUERY
                +AZRCommand.SPACE
                +AZRCommand.PROPERTIES
                +AZRCommand.DOT
                +AZRCommand.PRICING_TIER;
    }

    @Override
    public String secSetting() {
        return AZRCommand.AZ
                +AZRCommand.SPACE
                +AZRCommand.RESOURCE
                +AZRCommand.SPACE
                +AZRCommand.UPDATE
                +AZRCommand.SPACE
                +AZRCommand.IDS
                +AZRCommand.SPACE
                +idValue
                +AZRCommand.SPACE
                +AZRCommand.SET
                +AZRCommand.SPACE
                +AZRCommand.PROPERTIES
                +AZRCommand.DOT
                +AZRCommand.PRICING_TIER
                +AZRCommand.EQUAL
                +AZRCommand.STANDARD;
    }

}